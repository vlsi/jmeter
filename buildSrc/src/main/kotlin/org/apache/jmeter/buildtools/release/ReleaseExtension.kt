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
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.listProperty
import org.gradle.kotlin.dsl.mapProperty
import org.gradle.kotlin.dsl.newInstance
import org.gradle.kotlin.dsl.property
import java.net.URI
import javax.inject.Inject

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
open class ReleaseExtension @Inject constructor(
    private val project: Project,
    private val objects: ObjectFactory
) {
    internal val repositoryIdStore = NexusRepositoryIdStore(project)

    val repositoryType = objects.property<RepositoryType>()
        .convention(RepositoryType.TEST)
    val tlp = objects.property<String>()
    val tlpUrl
        get() = tlp.get().toLowerCase()
    val voteText = objects.property<(ReleaseParams) -> String>()
    val tag = objects.property<String>()
        .convention(project.provider { "v${project.version}" })

    val archives = objects.listProperty<Any>()
    val previewSiteContents = objects.listProperty<Any>()

    val svnDist = objects.newInstance<SvnDistConfig>(this, project)

    fun svnDist(action: SvnDistConfig.() -> Unit) {
        svnDist.action()
    }

    val nexus = objects.newInstance<NexusConfig>(this, project)

    fun nexus(action: NexusConfig.() -> Unit) {
        nexus.action()
    }
}

open class SvnDistConfig @Inject constructor(
    private val ext: ReleaseExtension,
    private val project: Project,
    private val objects: ObjectFactory
) {
    val url = objects.property<URI>()
        .convention(
            ext.repositoryType.map {
                when (it) {
                    RepositoryType.PROD -> project.uri("https://dist.apache.org/repos/dist")
                    RepositoryType.TEST -> project.uri("http://127.0.0.1/svn/dist")
                }
            })

    val stageFolder = objects.property<String>()
        .convention(project.provider {
            "dev/${ext.tlpUrl}/${ext.tag.get()}"
        })

    val finalFolder = objects.property<String>()
        .convention(project.provider {
            "release/${ext.tlpUrl}"
        })

    val releaseSubfolder = objects.mapProperty<Regex, String>()
}

open class NexusConfig @Inject constructor(
    private val ext: ReleaseExtension,
    private val project: Project,
    private val objects: ObjectFactory
) {
    val url = objects.property<URI>()
        .convention(
            ext.repositoryType.map {
                when (it) {
                    RepositoryType.PROD -> project.uri("https://repository.apache.org")
                    RepositoryType.TEST -> project.uri("http://127.0.0.1:8080")
                }
            })
    val username = objects.property<String>()
    val password = objects.property<String>()
    val packageGroup = objects.property<String>().convention(
        project.provider {
            project.group.toString()
        })
    val stagingProfileId = objects.property<String>()
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
    val svnStagingUri: URI,
    val nexusRepositoryUri: URI
) {
    val shortGitSha
        get() = gitSha.subSequence(0, 10)

    val tlpUrl
        get() = tlp.toLowerCase()
}

enum class RepositoryType {
    PROD, TEST
}
