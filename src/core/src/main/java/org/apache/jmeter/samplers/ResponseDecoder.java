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

package org.apache.jmeter.samplers;

import java.io.IOException;

/**
 * Interface for decoding response data that may be compressed.
 * This allows for lazy decompression of HTTP response bodies,
 * improving memory and CPU efficiency by only decompressing when needed.
 *
 * @since 6.0
 */
public interface ResponseDecoder {

    /**
     * Decode (decompress if necessary) the provided response data.
     *
     * @param compressedData the raw response data, which may be compressed
     * @return the decompressed response data
     * @throws IOException if decompression fails
     */
    byte[] decode(byte[] compressedData) throws IOException;

    /**
     * Get the content encoding type handled by this decoder.
     *
     * @return the content encoding name (e.g., "gzip", "deflate", "br", or null for no encoding)
     */
    String getContentEncoding();

    /**
     * Check if this decoder actually performs decompression.
     *
     * @return true if the decoder decompresses data, false if it returns data as-is
     */
    default boolean isCompressed() {
        return getContentEncoding() != null && !getContentEncoding().isEmpty();
    }
}
