/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package org.apache.jmeter.protocol.http.control;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.Socket;

import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.jorphan.util.JOrphanUtils;
import org.apache.log.Logger;
import org.apache.oro.text.regex.MatchResult;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternMatcherInput;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;

/**
 * Thread to handle one client request. Gets the request from the client and
 * sends the response back to the client.
 */
public class HttpMirrorThread extends Thread {
    private static final Logger log = LoggingManager.getLoggerForClass();

    private static final String ISO_8859_1 = "ISO-8859-1"; //$NON-NLS-1$
    private static final byte[] CRLF = { 0x0d, 0x0a };

    /** Socket to client. */
    private final Socket clientSocket;

    public HttpMirrorThread(Socket _clientSocket) {
        this.clientSocket=_clientSocket;
    }

    /**
     * Main processing method for the HttpMirror object
     */
    public void run() {
        log.info("Starting thread");
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        
        try {           
            in = new BufferedInputStream(clientSocket.getInputStream());
            out = new BufferedOutputStream(clientSocket.getOutputStream());
            // The headers are written using ISO_8859_1 encoding
            out.write("HTTP/1.0 200 OK".getBytes(ISO_8859_1));
            out.write(CRLF);
            out.write("Content-Type: text/plain".getBytes(ISO_8859_1));
            out.write(CRLF);
            out.write(CRLF);
            out.flush();
            
            // Read the header part, we will be looking for a content-length
            // header, so we know how much we should read.
            // We assume headers are in ISO_8859_1
            // If we do not find such a header, we will just have to read until
            // we have to block to read more, until we support chunked transfer
            int contentLength = -1;
            boolean isChunked = false;
            byte[] buffer = new byte[1024];
            StringBuffer headers = new StringBuffer();
            int length = 0;
            boolean haveAllHeaders = false;
            int positionOfBody = 0;
            while(!haveAllHeaders && ((length = in.read(buffer)) != -1)) {
                headers.append(new String(buffer, 0, length, ISO_8859_1));
                // Check if we have read all the headers
                positionOfBody = getPositionOfBody(headers.toString());
                haveAllHeaders = positionOfBody > 0;                                
            }
            
            // Write the headers
            out.write(headers.toString().getBytes(ISO_8859_1));
            
            // Check if we have found a content-length header
            String contentLengthHeaderValue = getRequestHeaderValue(headers.toString(), "Content-Length"); //$NON-NLS-1$
            if(contentLengthHeaderValue != null) {
                contentLength = new Integer(contentLengthHeaderValue).intValue();
            }
            String transferEncodingHeaderValue = getRequestHeaderValue(headers.toString(), "Transfer-Encoding"); //$NON-NLS-1$
            if(transferEncodingHeaderValue != null) {
                isChunked = transferEncodingHeaderValue.equalsIgnoreCase("chunked"); //$NON-NLS-1$
                // We only support chunked transfer encoding
                if(!isChunked) {
                    log.error("Transfer-Encoding header set, the value is not supported : " + transferEncodingHeaderValue);
                }
            }
            
            // If we know the content lenght, we can allow the reading of
            // the request to block until more data arrives.
            // If it is chunked transfer, we cannot allow the reading to
            // block, because we do not know when to stop reading, because
            // the chunked transfer is not properly supported yet
            length = 0;
            if(contentLength > 0) {
                // Check how much of the body we have already read as part of reading
                // the headers
                // We subtract two bytes for the crlf divider between header and body
                int totalReadBytes = headers.toString().length() - positionOfBody - 2;
                
                // We know when to stop reading, so we can allow the read method to block
                while((totalReadBytes < contentLength) && ((length = in.read(buffer)) != -1)) {
                    out.write(buffer, 0, length);
                    
                    totalReadBytes += length;
                }
            }
            else if (isChunked) {
                // It is chunked transfer encoding, which we do not really support yet.
                // So we just read without blocking, because we do not know when to
                // stop reading, so we cannot block
                // TODO propery implement support for chunked transfer, i.e. to
                // know when we have read the whole request, and therefore allow
                // the reading to block
                while(in.available() > 0 && ((length = in.read(buffer)) != -1)) {
                    out.write(buffer, 0, length);
                }
            }
            else {
                log.error("No content-length header found, and it is not chunked transfer, we cannot read the request");
            }
            out.flush();
        } catch (Exception e) {
            log.error("", e);
        } finally {
            JOrphanUtils.closeQuietly(out);
            JOrphanUtils.closeQuietly(in);
            JOrphanUtils.closeQuietly(clientSocket);
        }
        log.info("End of Thread");
    }
    
    private String getRequestHeaderValue(String requestHeaders, String headerName) {
        Perl5Matcher localMatcher = JMeterUtils.getMatcher();
        String expression = ".*" + headerName + ": (\\d*).*"; // $NON-NLS-1$ $NON-NLS-2$
        Pattern pattern = JMeterUtils.getPattern(expression, Perl5Compiler.READ_ONLY_MASK | Perl5Compiler.CASE_INSENSITIVE_MASK | Perl5Compiler.SINGLELINE_MASK);
        if(localMatcher.matches(requestHeaders, pattern)) {
            // The value is in the first group, group 0 is the whole match
            return localMatcher.getMatch().group(1);
        }
        else {
            return null;
        }
    }
    
    private int getPositionOfBody(String stringToCheck) {
        Perl5Matcher localMatcher = JMeterUtils.getMatcher();
        // The headers and body are divided by a blank line
        String regularExpression = "^.$"; // $NON-NLS-1$
        Pattern pattern = JMeterUtils.getPattern(regularExpression, Perl5Compiler.READ_ONLY_MASK | Perl5Compiler.CASE_INSENSITIVE_MASK | Perl5Compiler.MULTILINE_MASK);
        
        PatternMatcherInput input = new PatternMatcherInput(stringToCheck);
        while(localMatcher.contains(input, pattern)) {
            MatchResult match = localMatcher.getMatch();
            return match.beginOffset(0);
        }
        // No divider was found
        return -1;
    }
}
