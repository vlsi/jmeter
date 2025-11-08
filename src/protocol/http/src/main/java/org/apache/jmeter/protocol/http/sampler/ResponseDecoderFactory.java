/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.jmeter.protocol.http.sampler;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

import org.apache.jmeter.protocol.http.sampler.hc.LaxDeflateInputStream;
import org.apache.jmeter.protocol.http.sampler.hc.LaxGZIPInputStream;
import org.apache.jmeter.samplers.PlainResponseDecoder;
import org.apache.jmeter.samplers.ResponseDecoder;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.util.JMeterUtils;
import org.brotli.dec.BrotliInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Factory for creating ResponseDecoder instances based on Content-Encoding header values.
 * Supports gzip, deflate, brotli, and plain (uncompressed) responses.
 *
 * @since 6.0
 */
public final class ResponseDecoderFactory {

    private static final Logger log = LoggerFactory.getLogger(ResponseDecoderFactory.class);

    private static final boolean GZIP_RELAX_MODE =
            JMeterUtils.getPropDefault("httpclient4.gzip_relax_mode", false);

    private static final boolean DEFLATE_RELAX_MODE =
            JMeterUtils.getPropDefault("httpclient4.deflate_relax_mode", false);

    private static final Map<String, ResponseDecoder> DECODER_CACHE = new HashMap<>();

    static {
        // Pre-register standard decoders
        DECODER_CACHE.put("", PlainResponseDecoder.INSTANCE);
        GzipResponseDecoder gzipDecoder = new GzipResponseDecoder();
        DeflateResponseDecoder deflateDecoder = new DeflateResponseDecoder();
        BrotliResponseDecoder brotliDecoder = new BrotliResponseDecoder();

        DECODER_CACHE.put("gzip", gzipDecoder);
        DECODER_CACHE.put("x-gzip", gzipDecoder);
        DECODER_CACHE.put("deflate", deflateDecoder);
        DECODER_CACHE.put("br", brotliDecoder);

        // Register decoders with SampleResult for lazy decompression
        SampleResult.registerResponseDecoder("gzip", data -> {
            try {
                return gzipDecoder.decode(data);
            } catch (IOException e) {
                throw new RuntimeException("Failed to decompress gzip data", e);
            }
        });
        SampleResult.registerResponseDecoder("x-gzip", data -> {
            try {
                return gzipDecoder.decode(data);
            } catch (IOException e) {
                throw new RuntimeException("Failed to decompress x-gzip data", e);
            }
        });
        SampleResult.registerResponseDecoder("deflate", data -> {
            try {
                return deflateDecoder.decode(data);
            } catch (IOException e) {
                throw new RuntimeException("Failed to decompress deflate data", e);
            }
        });
        SampleResult.registerResponseDecoder("br", data -> {
            try {
                return brotliDecoder.decode(data);
            } catch (IOException e) {
                throw new RuntimeException("Failed to decompress brotli data", e);
            }
        });
    }

    private ResponseDecoderFactory() {
        // Utility class
    }

    /**
     * Get a ResponseDecoder for the specified content encoding.
     *
     * @param contentEncoding the Content-Encoding header value (may be null or empty)
     * @return the appropriate ResponseDecoder, never null
     */
    public static ResponseDecoder getDecoder(String contentEncoding) {
        if (contentEncoding == null || contentEncoding.isEmpty()) {
            return PlainResponseDecoder.INSTANCE;
        }

        String encoding = contentEncoding.toLowerCase(Locale.ENGLISH).trim();
        ResponseDecoder decoder = DECODER_CACHE.get(encoding);

        if (decoder == null) {
            log.warn("Unknown content encoding '{}', using plain decoder", contentEncoding);
            return PlainResponseDecoder.INSTANCE;
        }

        return decoder;
    }

    /**
     * Base class for decoders that perform decompression.
     */
    private abstract static class AbstractCompressionDecoder implements ResponseDecoder {

        protected abstract InputStream createInputStream(InputStream in) throws IOException;

        @Override
        public byte[] decode(byte[] compressedData) throws IOException {
            if (compressedData == null || compressedData.length == 0) {
                return compressedData;
            }

            try (ByteArrayInputStream bais = new ByteArrayInputStream(compressedData);
                 InputStream decompressor = createInputStream(bais);
                 ByteArrayOutputStream baos = new ByteArrayOutputStream(compressedData.length * 2)) {

                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = decompressor.read(buffer)) != -1) {
                    baos.write(buffer, 0, bytesRead);
                }
                return baos.toByteArray();
            }
        }
    }

    /**
     * Decoder for gzip-compressed responses.
     */
    private static class GzipResponseDecoder extends AbstractCompressionDecoder {

        @Override
        protected InputStream createInputStream(InputStream in) throws IOException {
            if (GZIP_RELAX_MODE) {
                return new LaxGZIPInputStream(in);
            }
            return new GZIPInputStream(in);
        }

        @Override
        public String getContentEncoding() {
            return "gzip";
        }
    }

    /**
     * Decoder for deflate-compressed responses.
     */
    private static class DeflateResponseDecoder extends AbstractCompressionDecoder {

        @Override
        protected InputStream createInputStream(InputStream in) throws IOException {
            if (DEFLATE_RELAX_MODE) {
                return new LaxDeflateInputStream(in);
            }
            return new InflaterInputStream(in);
        }

        @Override
        public String getContentEncoding() {
            return "deflate";
        }
    }

    /**
     * Decoder for brotli-compressed responses.
     */
    private static class BrotliResponseDecoder extends AbstractCompressionDecoder {

        @Override
        protected InputStream createInputStream(InputStream in) throws IOException {
            return new BrotliInputStream(in);
        }

        @Override
        public String getContentEncoding() {
            return "br";
        }
    }
}
