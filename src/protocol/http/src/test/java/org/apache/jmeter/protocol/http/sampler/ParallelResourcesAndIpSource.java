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

package org.apache.jmeter.protocol.http.sampler;

import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.protocol.http.parser.LagartoBasedHtmlParser;
import org.apache.jmeter.protocol.http.util.HTTPConstants;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.testelement.property.FunctionProperty;
import org.apache.jmeter.testkit.BugId;
import org.apache.jmeter.threads.JMeterVariables;
import org.apache.jmeter.util.JMeterContextExtension;
import org.apache.jmeter.wiremock.WireMockExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

@BugId("52310")
@ExtendWith(WireMockExtension.class)
@ExtendWith(JMeterContextExtension.class)
public class ParallelResourcesAndIpSource {

    private void configureStubs(WireMockServer server) {
        server.stubFor(WireMock.get("/index.html")
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "text/html; encoding=utf-8")
                        .withBody("<html><body><img src='image1.png'><img src='image2.png'><img src='image3.png'></body>")));
        for (int i = 1; i <= 3; i++) {
            server.stubFor(WireMock.get("/image" + i + ".png")
                    .willReturn(WireMock.aResponse()
                            .withHeader("Content-Type", "image/png")
                            .withBody("content" + i)));
        }
    }

    public static Iterable<Arguments> implementationsAndIps() throws SocketException {
        List<Arguments> res = new ArrayList<>();

        // Caveat: 127.0.0.1
        InetAddress local4 = null;
        InetAddress local6 = null;

        List<InetAddress> localIps = Collections.list(NetworkInterface.getNetworkInterfaces())
                .stream()
                .filter(networkInterface -> {
                    try {
                        return networkInterface.isLoopback();
                    } catch (SocketException e) {
                        return false;
                    }
                })
                .flatMap(ni -> ni.getInterfaceAddresses().stream())
                .map(InterfaceAddress::getAddress)
                .collect(Collectors.toList());

        // We don't want to have accidental O(N^2) in case there are lots of source IPs
        // So we use just two for the source IP
        // So we shuffle them to use different ones every time
        Collections.shuffle(localIps);
        for (InetAddress localIp : localIps) {
            if (local4 == null && localIp instanceof Inet4Address) {
                local4 = localIp;
            } else if (local6 == null && localIp instanceof Inet6Address) {
                local6 = localIp;
            }
        }
        // This is to allow the variables for use in lambda below (the vars should be effectively final)
        InetAddress finalLocal4 = local4;
        InetAddress finalLocal6 = local6;

        // This is to make test names pretty
        localIps.sort(Comparator.comparing(InetAddress::toString));

        for (String impl : HTTPSamplerFactory.getImplementations()) {
            for (String targetHost : new String[]{"127.0.0.1", "[::1]", "localhost"}) {
                if ("localhost".equals(targetHost)) {
                    // Neither implementation supports resolving localhost
                    // with the given source IP
                    continue;
                }

                localIps.forEach(localIp -> {
                    if (finalLocal4 != null) {
                        res.add(arguments(impl, targetHost, finalLocal4));
                    }
                    if (finalLocal6 != null) {
                        res.add(arguments(impl, targetHost, finalLocal6));
                    }
                });
            }
        }
        return res;
    }

    @ParameterizedTest(name = "{0}, targetHost={1}, sourceIp={2}")
    @MethodSource("implementationsAndIps")
    public void test(String httpImplementation, String targetHost, InetAddress sourceIp,
                     WireMockServer server, JMeterVariables vars) throws SocketException {
        configureStubs(server);

        HTTPSamplerBase http = HTTPSamplerFactory.newInstance(httpImplementation);

        vars.put("IP_ADDR", sourceIp.getHostAddress());

        // Typically it is registered with jmeter.properties, however we don't run full JMeter in the test
        HTTPSamplerBase.registerParser("text/html", LagartoBasedHtmlParser.class.getName());

        http.setConnectTimeout("2000");
        http.setResponseTimeout("4000");
        http.setMethod(HTTPConstants.GET);
        // Theoretically it should work, however it practice it fails
        // in case HTTP Client (implementation) resolves localhost as 120.0.0.1 even in case
        // localIp is IPv6
        http.setDomain(targetHost);
        http.setPort(server.port());
        http.setPath("/index.html");
        http.setFollowRedirects(true);
        http.setUseKeepAlive(true);
        http.setProperty(new FunctionProperty(HTTPSampler.IP_SOURCE, new CompoundVariable("${IP_ADDR}")));
        http.setImageParser(true);
        http.setConcurrentDwn(true);
        http.setConcurrentPool("6");
        http.setEmbeddedUrlRE(".*image.*");

        http.setRunningVersion(true);

        SampleResult result = http.sample();

        if (sourceIp instanceof Inet4Address && targetHost.startsWith("[") ||
                sourceIp instanceof Inet6Address && Character.isDigit(targetHost.charAt(0))) {
            // Connection from IPv4 to IPv6 must fail
            // Connection from IPv6 to IPv4 must fail
            if (HTTPSamplerFactory.IMPL_JAVA.equals(httpImplementation)) {
                // Java implementation is known to ignore source IP, so it should connect anyway
                // pass to "successful" assertion below
                //noinspection ResultOfMethodCallIgnored
                "checkstyle: Must have at least one statement. [EmptyBlock]".length();
            } else if (result.isSuccessful() || result.isResponseCodeOK() ||
                    (!result.getResponseDataAsString().contains("ConnectException") &&
                     !result.getResponseDataAsString().contains("SocketException"))) {
                Assertions.fail("IPv4 <-> IPv6 connectivity must fail." +
                        " sourceIp = " + sourceIp + ", targetHost = " + targetHost +
                        " result = " + ResultAsString.toString(result));
            } else {
                return;
            }
        }

        String expected =
                "url: http://wiremock/index.html\n" +
                        "response: OK\n" +
                        "data.size: 85\n" +
                        "data: <html><body><img src='image1.png'><img src='image2.png'><img src='image3.png'></body>\n" +
                        "- url: http://wiremock/image1.png\n" +
                        "  response: OK\n" +
                        "  data.size: 8\n" +
                        "  data: content1\n" +
                        "- url: http://wiremock/image2.png\n" +
                        "  response: OK\n" +
                        "  data.size: 8\n" +
                        "  data: content2\n" +
                        "- url: http://wiremock/image3.png\n" +
                        "  response: OK\n" +
                        "  data.size: 8\n" +
                        "  data: content3\n" +
                        "- url: http://wiremock/index.html\n" +
                        "  response: OK\n" +
                        "  data.size: 85\n" +
                        "  data: <html><body><img src='image1.png'><img src='image2.png'><img src='image3.png'></body>\n";
        expected = expected.replace("http://wiremock", "http://" + targetHost + ":" + server.port());
        Assertions.assertEquals(
                expected.replaceAll("\n", System.lineSeparator()),
                ResultAsString.toString(result));
    }
}
