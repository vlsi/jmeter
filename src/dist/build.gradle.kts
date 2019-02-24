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

import org.apache.jmeter.buildtools.LineEndings
import org.apache.jmeter.buildtools.filter
import org.apache.jmeter.buildtools.from
import versions.BuildTools
import versions.Libs

var jars = arrayOf(
        ":src:launcher",
        ":src:components",
        ":src:core",
        //":src:examples",
        ":src:functions",
        ":src:jorphan",
        ":src:protocol:ftp",
        ":src:protocol:http",
        ":src:protocol:java",
        ":src:protocol:jdbc",
        ":src:protocol:jms",
        ":src:protocol:junit",
        ":src:protocol:ldap",
        ":src:protocol:mail",
        ":src:protocol:mongodb",
        ":src:protocol:native",
        ":src:protocol:tcp")

val buildDocs by configurations.creating

dependencies {
    for (p in jars) {
        compile(project(p))
        testCompile(project(p, "testClasses"))
    }
    compile(Libs.log4j_slf4j_impl) {
        because("""
            JMeter core libraries do not depend on the specific implementation of Slf4j,
            So Log4j implementation is added for distribution explicitly
            This enables third party projects to use JMeter as a library and pick their own slf4j
            implementations (e.g. logback)""".trimIndent())
    }
    implementation(Libs.darcula) {
        because("""
            It just looks good, however Darcula is not used explicitly,
             so the dependency is added for distribution only""".trimIndent())
    }

    buildDocs(BuildTools.velocity)
    buildDocs(Libs.jdom)

    compile(BuildTools.velocity)
}

tasks.named(BasePlugin.CLEAN_TASK_NAME).configure {
    doLast {
        // createDist can't yet remove outdated jars (e.g. when dependency is updated to a newer version)
        // so we enhance "clean" task to kill the jars
        delete(fileTree("$rootDir/bin") { include("ApacheJMeter.jar") })
        delete(fileTree("$rootDir/lib") { include("*.jar") })
        delete(fileTree("$rootDir/lib/ext") { include("ApacheJMeter*.jar") })
    }
}

// Libs are populated dynamically since we can't get the full set of dependencies
// before we execute all the build scripts
val libs = copySpec {
    // Third-party dependencies + jorphan.jar
}

val libsExt = copySpec {
    // Apache JMeter jars
}

val binLibs = copySpec {
    // ApacheJMeter.jar launcher
}

// Splits jar dependencies to "lib", "lib/ext", and "bin" folders
val populateLibs by tasks.registering {
    dependsOn(configurations.runtime)
    doLast {
        val deps = configurations.runtime.get().resolvedConfiguration.resolvedArtifacts
        // This ensures project exists, if project is renamed, names should be corrected here as wells
        val launcherProject = project(":src:launcher").path
        val jorphanProject = project(":src:jorphan").path
        listOf(libs, libsExt, binLibs).forEach {
            it.fileMode = "644".toInt(8)
            it.dirMode = "755".toInt(8)
        }
        for (dep in deps) {
            val compId = dep.id.componentIdentifier
            // The path is "relative" to rootDir/lib
            when (compId) {
                is ProjectComponentIdentifier ->
                    (when (compId.projectPath) {
                        launcherProject -> binLibs
                        jorphanProject -> libs
                        else -> libsExt
                    }).from(dep.file) {
                        // Technically speaking, current JMeter artifacts do not have version in the name
                        // however rename is here just in case
                        rename { dep.name + "." + dep.extension }
                    }
                else -> libs.from(dep.file)
            }
        }
    }
}

val copyLibs by tasks.registering(Copy::class) {
    dependsOn(populateLibs)
    val junitSampleJar = project(":src:protocol:junit-sample").tasks.named(JavaPlugin.JAR_TASK_NAME)
    dependsOn(junitSampleJar)
    // Can't use $rootDir since Gradle somehow reports .gradle/caches/ as "always modified"
    rootSpec.into("$rootDir/lib")
    with(libs)
    into("ext") {
        with(libsExt)
    }
    into("junit") {
        from(junitSampleJar) {
            rename { "test.jar" }
        }
    }
}

val copyBinLibs by tasks.registering(Copy::class) {
    dependsOn(populateLibs)
    // Can't use $rootDir since Gradle somehow reports .gradle/caches/ as "always modified"
    rootSpec.into("$rootDir/bin")
    with(binLibs)
}

val createDist by tasks.registering {
    group = LifecycleBasePlugin.BUILD_GROUP
    description = "Copies JMeter jars and dependencies to projectRoot/lib/ folder"
    dependsOn(copyLibs)
    dependsOn(copyBinLibs)
}


fun licenseNotice(textEol: LineEndings) = copySpec {
    from(rootDir) {
        filter(textEol)
        include("LICENSE")
        include("NOTICE")
    }
}

fun commonFiles(textEol: LineEndings) = copySpec {
    filteringCharset = "UTF-8"
    with(licenseNotice(textEol))
    into("bin") {
        from("$rootDir/bin", textEol) {
            text("*.bshrc", "*.properties", "*.parameters", "*.xml", "*.conf")
            text("utility.groovy")
            exclude("*.log", "*.jmx")
            shell("create-rmi-keystore",
                    "heapdump",
                    "jmeter",
                    "jmeter-n",
                    "jmeter-n-r",
                    "jmeter-server",
                    "jmeter-t",
                    "jmeterw",
                    "mirror-server",
                    "shutdown",
                    "stoptest",
                    "threaddump")
        }
        into("templates") {
            from("$rootDir/bin/templates", textEol) {
                text("*.jmx", "*.dtd", "*.xml")
            }
        }
        into("examples") {
            from("$rootDir/bin/examples", textEol) {
                text("**/*.jmx", "**/*.jsp", "**/*.csv")
                binary("**/*.png")
            }
        }
        into("report-template") {
            from("$rootDir/bin/report-template", textEol) {
                text("**/*") // all except binary
                binary("**/*.png", "**/*.ttf", "**/*.woff", "**/*.woff2", "**/*.eot", "**/*.otf")
            }
        }
    }
    into("lib/ext") {
        from("$rootDir/lib/ext", textEol) {
            text("readme.txt")
        }
    }
    into("extras") {
        from("$rootDir/extras", textEol) {
            shell("proxycert", "schematic")
            text("*.json", "*.jmx", "*.txt", "*.xml", "*.bsh", "*.xsl")
            binary("*.jar", "*.png")
        }
    }
}

fun createAnakiaTask(taskName: String,
                     baseDir: String, extension: String = ".html", style: String,
                     velocityProperties: String, projectFile: String, excludes: Array<String>,
                     includes: Array<String>): TaskProvider<Task> {
    val outputDir = "$buildDir/docs/$taskName"

    val prepareProps = tasks.register("prepareProperties$taskName") {
        // AnakiaTask can't use relative paths, and it forbids ../, so we create a dedicated
        // velocity.properties file that contains absolute path
        inputs.file(velocityProperties)
        val outputProps = "$buildDir/docProps/$taskName/velocity.properties"
        outputs.file(outputProps)
        doLast {
            val p = `java.util`.Properties()
            file(velocityProperties).reader().use {
                p.load(it)
            }
            p["resource.loader"] = "file"
            p["file.resource.loader.path"] = baseDir
            p["file.resource.loader.class"] = "org.apache.velocity.runtime.resource.loader.FileResourceLoader"
            file(outputProps).apply {
                parentFile.run { isDirectory || mkdirs() } || throw IllegalStateException("Unable to create directory $parentFile")

                writer().use {
                    p.store(it, "Auto-generated from $velocityProperties to pass absolute path to Velocity")
                }
            }
        }
    }

    return tasks.register(taskName) {
        inputs.files(fileTree(baseDir) {
            include(style)
            include(projectFile)
            include(*includes)
            exclude(*excludes)
        })
        inputs.properties["extension"] = extension
        outputs.dir(outputDir)
        dependsOn(prepareProps)

        doLast {
            ant.withGroovyBuilder {
                "taskdef"("name" to "anakia",
                        "classname" to "org.apache.velocity.anakia.AnakiaTask",
                        "classpath" to buildDocs.asPath)
                "anakia"("basedir" to baseDir,
                        "destdir" to outputDir,
                        "extension" to extension,
                        "style" to style,
                        "projectFile" to projectFile,
                        "excludes" to excludes.joinToString(" "),
                        "includes" to includes.joinToString(" "),
                        "lastModifiedCheck" to "true",
                        "velocityPropertiesFile" to prepareProps.get().outputs.files.singleFile)
            }
        }
    }
}

val xdocs = "$rootDir/xdocs"

fun docCssAndImages(textEol: LineEndings) = copySpec {
    filteringCharset = "UTF-8"

    into("css") {
        from("$xdocs/css")
        filter(textEol)
    }
    into("images") {
        from("$xdocs/images")
    }
}

fun printableDocumentation(textEol: LineEndings) = copySpec {
    filteringCharset = "UTF-8"

    into("docs") {
        with(docCssAndImages(textEol))
    }
    into("printable_docs") {
        from(buildPrintableDoc) {
            filter(textEol)
        }
        into("demos") {
            from("$xdocs/demos")
            filter(textEol)
        }
        into("extending") {
            from("$xdocs/extending/jmeter_tutorial.pdf")
        }
        into("usermanual") {
            from("$xdocs/usermanual") {
                include("*.pdf")
            }
        }
    }
}

val buildPrintableDoc = createAnakiaTask("buildPrintableDoc", baseDir = xdocs,
        style = "stylesheets/site_printable.vsl",
        velocityProperties = "$xdocs/velocity.properties",
        projectFile = "stylesheets/printable_project.xml",
        excludes = arrayOf("**/stylesheets/**", "extending.xml", "extending/*.xml"),
        includes = arrayOf("**/*.xml"))

val previewPrintableDocs by tasks.registering(Copy::class) {
    group = JavaBasePlugin.DOCUMENTATION_GROUP
    description = "Creates preview of a printable documentation to build/docs/printable_preview"
    into("$buildDir/docs/printable_preview")
    with(printableDocumentation(LineEndings.current()))
}

val distributionGroup = "distribution"
val baseFolder = "apache-jmeter-${rootProject.version}"

fun binaryLayout(textEol: LineEndings) = copySpec {
    into(baseFolder) {
        with(licenseNotice(textEol))
        with(commonFiles(textEol))
        into("bin") {
            with(binLibs)
        }
        into("lib") {
            with(libs)
            into("ext") {
                with(libsExt)
            }
        }
        with(printableDocumentation(textEol))
        into("docs/api") {
            from(javadocAggregate, textEol) {
                text("**/*") // all except binary
                binary("**/*.zip", "**/*.png")
            }
        }
    }
}

fun sourceLayout(textEol: LineEndings) = copySpec {
    into(baseFolder + "_src") {
        with(commonFiles(textEol))
        into("gradle") {
            from("$rootDir/gradle", textEol) {
                text("**/*.kts", "**/*.properties")
                binary("wrapper/gradle-wrapper.jar")
            }
        }
        from(rootDir, textEol) {
            text("*.kts", "*.md", "*.yml", "*.xml", "*.xsl")
            text("rat-excludes.txt")
            shell("gradlew")
        }
        into("buildSrc") {
            from("$rootDir/buildSrc", textEol) {
                text("**/*.kts", "**/*.kt", "**/*.properties")
                exclude("build", ".gradle")
            }
        }
        into("config") {
            from("$rootDir/config", textEol) {
                text("**/*.xml")
                text("**/*.regex")
            }
        }
        into("src") {
            filteringCharset = "Cp1252"
            from("$rootDir/src", textEol) {
                text("**/*cp1252*")
                exclude("*/build", "*/out")
                exclude("protocol/*/build", "protocol/*/out")
            }
        }
        into("bin/testfiles") {
            from("$rootDir/bin/testfiles", textEol) {
                text("**/*.xml", "**/*.xsd", "**/*.dtd", "**/*.csv", "**/*.txt", "**/*.tsv", "**/*.json")
                text("**/*.html", "**/*.htm")
                text("**/*.jmx", "**/*.jtl")
                text("**/*.bsh")
                text("**/*.properties")
                exclude("testReport*")
            }
        }
        into("src") {
            from("$rootDir/src", textEol) {
                text("**/*.java", "**/*.groovy", "**/*.kts")
                // resources
                text("**/*.properties")
                text("**/*.html", "**/*.htm", "**/*.css", "**/*.svg")
                text("**/*.xml", "**/*.dtd", "**/*.csv", "**/*.txt", "**/*.jmx")
                text("**/*.yml")
                text("**/*.eml", "**/*.pem")
                text("**/*.all", "**/*.set") // e.g. HTMLParserTestCase.all
                text("**/.git*", "**/.git*")
                binary("**/*.png", "**/*.gif", "**/*.jpg")
                // Default encoding is UTF-8, so cp1252 files are included above
                exclude("**/*cp1252*")
                exclude("**/jmeter.log")
                exclude("**/*.iml")
                exclude("*/build", "*/out")
                exclude("protocol/*/build", "protocol/*/out")
            }
        }
        into("xdocs") {
            from("$rootDir/xdocs", textEol) {
                text("**/*.html", "**/*.htm", "**/*.css", "**/*.svg")
                text("**/*.xml", "**/*.dtd", "**/*.csv", "**/*.txt", "**/*.jmx")
                text("**/*.txt", "**/*.TXT")
                text("**/*.properties")
                text("**/*.vsl", "**/*.xsl", "**/*.xsd")
                binary("**/*") // All the rest as binary
                exclude("**/jmeter.log")
            }
        }
    }
}

val javadocAggregate by tasks.registering(Javadoc::class) {
    group = JavaBasePlugin.DOCUMENTATION_GROUP
    description = "Generates aggregate javadoc for all the artifacts"

    classpath = files(jars.map { project(it).configurations.compileClasspath })
    setSource(jars.flatMap { project(it).sourceSets.main.get().allJava })
    setDestinationDir(file("$buildDir/docs/javadocAggregate"))
}

val distZip by tasks.registering(Zip::class) {
    group = distributionGroup
    description = "Creates binary distribution with CRLF line endings for text files"
    dependsOn(populateLibs)

    archiveBaseName.set("apache-jmeter")
    with(binaryLayout(LineEndings.CRLF))
}

val distTar by tasks.registering(Tar::class) {
    group = distributionGroup
    description = "Creates binary distribution with LF line endings for text files"
    compression = Compression.GZIP
    dependsOn(populateLibs)

    archiveBaseName.set("apache-jmeter")
    with(binaryLayout(LineEndings.LF))
}

val distZipSource by tasks.registering(Zip::class) {
    group = distributionGroup
    description = "Creates source distribution with CRLF line endings for text files"

    archiveBaseName.set("apache-jmeter_src")
    with(sourceLayout(LineEndings.CRLF))
}

val distTarSource by tasks.registering(Tar::class) {
    group = distributionGroup
    description = "Creates source distribution with LF line endings for text files"
    compression = Compression.GZIP

    archiveBaseName.set("apache-jmeter_src")
    with(sourceLayout(LineEndings.LF))
}


tasks.named(BasePlugin.ASSEMBLE_TASK_NAME).configure {
    dependsOn(distTar, distZip, distTarSource, distZipSource)
}
