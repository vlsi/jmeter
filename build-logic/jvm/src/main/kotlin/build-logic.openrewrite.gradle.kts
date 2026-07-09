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

plugins {
    id("build-logic.openrewrite-base")
}

dependencies {
    // rewrite-core, rewrite-java, rewrite-kotlin and the other parsers arrive transitively
    // with the recipe modules below; the isolated worker resolves everything from here.
    "openrewrite"(platform("org.openrewrite.recipe:rewrite-recipe-bom:3.34.0"))
    "openrewrite"("org.openrewrite.recipe:rewrite-static-analysis")
    "openrewrite"("org.openrewrite.recipe:rewrite-testing-frameworks")
    // JavaParser.fromJavaVersion() picks the parser backend matching the worker JVM, so the
    // version-specific parsers must be on the recipe classpath.
    "openrewrite"("org.openrewrite:rewrite-java-17")
    "openrewrite"("org.openrewrite:rewrite-java-21")
    "openrewrite"("org.openrewrite:rewrite-java-25")
}

openrewrite {
    configFile.set(rootProject.file("config/openrewrite/rewrite.yml"))
    // Report violations instead of failing the dry run so the aggregate styleCheck task can
    // collect the reports from every module.
    failOnDryRunResults.set(false)

    activeStyles.add("org.apache.jmeter.style.Style")

    // See config/openrewrite/rewrite.yml. These static-analysis recipes run on Java and Kotlin.
    activeRecipes.add("org.apache.jmeter.staticanalysis.CodeCleanup")
    activeRecipes.add("org.apache.jmeter.staticanalysis.CommonStaticAnalysis")

    // The JUnit 5 recipes are Java-only: their JavaTemplate rewrites throw on Kotlin test
    // sources, so enable them only for modules whose tests are pure Java.
    if (!file("src/test/kotlin").isDirectory) {
        plugins.withId("build-logic.test-junit5") {
            activeRecipes.add("org.openrewrite.java.testing.junit5.JUnit5BestPractices")
            activeRecipes.add("org.openrewrite.java.testing.junit5.CleanupAssertions")
        }
    }
}
