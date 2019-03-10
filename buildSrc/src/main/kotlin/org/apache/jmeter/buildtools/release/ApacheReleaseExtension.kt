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

package org.apache.jmeter.buildtools.release

import org.gradle.api.Project
import org.gradle.kotlin.dsl.listProperty
import org.gradle.kotlin.dsl.mapProperty
import org.gradle.kotlin.dsl.property
import java.net.URI

/**
 * Setting up local release environment:
 *
 * ```
 * docker run -d --name svn-server -p 80:80 -p 3960:3960 elleflorio/svn-server
 * docker exec -t svn-server htpasswd -b /etc/subversion/passwd test test
 * docker exec -t svn-server svnadmin create /home/svn/dist
 * ```
 * or
 * ```
 * git clone https://github.com/vlsi/asflike-release-environment.git
 * cd asflike-release-environment && vagrant up
 * ```
 */
open class ApacheReleaseExtension(private val project: Project) {
    internal val repositoryIdStore = NexusRepositoryIdStore(project)

    val repositoryType = project.objects.property<RepositoryType>()
        .convention(RepositoryType.TEST)
    val distRepository = project.objects.property<URI>()
        .convention(
            repositoryType.map {
                when (it) {
                    RepositoryType.PROD -> project.uri("https://dist.apache.org/repos/dist")
                    RepositoryType.TEST -> project.uri("http://127.0.0.1/svn/dist")
                }
            })
    val tlp = project.objects.property<String>()
    val tlpUrl
        get() = tlp.get().toLowerCase()
    val voteText = project.objects.property<(ReleaseParams) -> String>()
    val archives = project.objects.listProperty<Any>()
    val tag = project.objects.property<String>().convention("v${project.version}")

    val releaseStageFolder = project.objects.property<String>()
        .convention(project.provider {
            "dev/$tlpUrl/${tag.get()}"
        })

    val releaseFinalFolder = project.objects.property<String>()
        .convention(project.provider {
            "release/$tlpUrl"
        })

    val releaseSubfolder = project.objects.mapProperty<Regex, String>()
}

class ReleaseArtifact(
    val name: String,
    val sha512: String
)

class ReleaseParams(
    val tlp: String,
    val version: String,
    val gitSha: String,
    val tag: String,
    val artifacts: List<ReleaseArtifact>,
    val stagingRepositoryUri: URI,
    val stagingRepositoryId: String
) {
    val shortGitSha
        get() = gitSha.subSequence(0, 10)

    val tlpUrl
        get() = tlp.toLowerCase()
}

enum class RepositoryType {
    PROD, TEST
}
