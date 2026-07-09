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
    id("org.openrewrite.rewrite")
}

dependencies {
    // rewrite-kotlin, rewrite-java, rewrite-groovy and the other language parsers
    // ship with the Gradle plugin, so only the recipe modules are declared here.
    rewrite(platform("org.openrewrite.recipe:rewrite-recipe-bom:latest.release"))
    rewrite("org.openrewrite.recipe:rewrite-static-analysis")
    rewrite("org.openrewrite.recipe:rewrite-testing-frameworks")
}

rewrite {
    configFile = rootProject.file("config/openrewrite/rewrite.yml")
    // Report violations instead of failing the dry run so the aggregate
    // styleCheck task can collect the reports from every module.
    failOnDryRunResults = false

    activeStyle("org.apache.jmeter.style.Style")

    // See config/openrewrite/rewrite.yml
    // These static-analysis recipes run cleanly on both Java and Kotlin sources.
    activeRecipe("org.apache.jmeter.staticanalysis.CodeCleanup")
    activeRecipe("org.apache.jmeter.staticanalysis.CommonStaticAnalysis")

    // The JUnit 5 recipes from rewrite-testing-frameworks are Java-only: their
    // JavaTemplate-based rewrites throw on Kotlin test sources, e.g.
    // AssertThrowsOnLastStatement fails on jorphan's RandomStringGeneratorTest.kt
    // with "Expected a template that would generate exactly one statement ...".
    // Enable them only for modules whose tests are pure Java.
    if (!file("src/test/kotlin").isDirectory) {
        plugins.withId("build-logic.test-junit5") {
            activeRecipe("org.openrewrite.java.testing.junit5.JUnit5BestPractices")
            activeRecipe("org.openrewrite.java.testing.junit5.CleanupAssertions")
        }
    }
}
