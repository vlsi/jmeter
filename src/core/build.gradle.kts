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

import versions.*

dependencies {
    compile(project(":src:launcher"))
    compile(project(":src:jorphan"))
    testCompile(project(":src:jorphan", "testClasses"))

    compile(Libs.bsf)
    compile(Libs.caffeine)
    compile(Libs.oro)
    compile(Libs.jackson_annotations)
    compile(Libs.jackson_core)
    compile(Libs.jackson_databind)
    compile(Libs.freemarker)
    compileOnly(Libs.tika_parsers)
    compile(Libs.rsyntaxtextarea)
    compile(Libs.rhino)
    compile(Libs.xmlgraphics_commons)
    compile(Libs.commons_text)
    compile(Libs.jodd_core)
    compile(Libs.jodd_props)
    compile(Libs.xalan)
    // TODO: are xerces and xml-apis required?
    // compile(Libs.xercesImpl)
    // compile(Libs.xml_apis)
    // Note: Saxon should go AFTER xalan so xalan XSLT is used
    // org.apache.jmeter.util.XPathUtilTest.testFormatXmlSimple assumes xalan transformer
    compile(Libs.Saxon_HE)
    compile(Libs.jtidy)
    compile(Libs.xstream)
    compile(Libs.log4j_12_api)
    compile(Libs.log4j_api)
    compile(Libs.log4j_core)

    testCompile(Libs.commons_net)
    testImplementation(Libs.spock_core)
}
