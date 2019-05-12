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

import de.marcphilipp.gradle.nexus.InitializeNexusStagingRepository
import de.marcphilipp.gradle.nexus.NexusPublishExtension
import io.codearte.gradle.nexus.NexusStagingExtension
import org.ajoberstar.grgit.Grgit
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin
import org.gradle.api.publish.plugins.PublishingPlugin
import org.gradle.api.tasks.Sync
import org.gradle.api.tasks.TaskProvider
import org.gradle.internal.reflect.Instantiator
import org.gradle.kotlin.dsl.*
import java.io.File
import java.net.URI
import javax.inject.Inject

class StageVoteReleasePlugin @Inject constructor(private val instantiator: Instantiator) :
    Plugin<Project> {
    companion object {
        const val EXTENSION_NAME = "releaseParams"

        const val GENERATE_VOTE_TEXT_TASK_NAME = "generateVoteText"
        const val PREPARE_VOTE_TASK_NAME = "prepareVote"
        const val STAGE_SVN_DIST_TASK_NAME = "stageSvnDist"
        const val PUBLISH_SVN_DIST_TASK_NAME = "publishSvnDist"
        const val STAGE_DIST_TASK_NAME = "stageDist"
        const val PUBLISH_DIST_TASK_NAME = "publishDist"
    }

    override fun apply(project: Project) {
        if (project.parent == null) {
            configureRoot(project)
        }
    }

    private fun configureRoot(project: Project) {
        project.pluginManager.apply("org.ajoberstar.grgit")

        // Save stagingRepoId. We don't know which
        val releaseExt = project.extensions.create<ReleaseExtension>(EXTENSION_NAME, project)

        configureNexusPublish(project, releaseExt)

        configureNexusStaging(project, releaseExt)

        project.addPreviewSiteTasks(releaseExt)

        val stageSvnDist = project.tasks.register<StageToSvnTask>(STAGE_SVN_DIST_TASK_NAME) {
            description = "Stage release artifacts to SVN dist repository"
            group = "release"
            files.from(releaseExt.archives.get())
//            project.files(releaseExt.archives.get())
//            dependsOn.addAll(releaseExt.archives.get())
        }

        val publishSvnDist = project.tasks.register<PromoteSvnRelease>(PUBLISH_SVN_DIST_TASK_NAME) {
            description = "Publish release artifacts to SVN dist repository"
            group = "release"
            files.from(releaseExt.archives.get())
        }

        val closeRepository = project.tasks.named("closeRepository")

        // closeRepository does not wait all publications by default, so we add that dependency
        project.allprojects {
            plugins.withType<PublishingPlugin> {
                closeRepository.configure {
                    dependsOn(tasks.named(PublishingPlugin.PUBLISH_LIFECYCLE_TASK_NAME))
                }
            }
        }

        val stageDist = project.tasks.register(STAGE_DIST_TASK_NAME) {
            description = "Stage release artifacts to SVN and Nexus"
            group = "release"
            dependsOn(stageSvnDist)
            dependsOn(closeRepository)
        }

        val publishDist = project.tasks.register(PUBLISH_DIST_TASK_NAME) {
            description = "Publish release artifacts to SVN and Nexus"
            group = "release"
            dependsOn(publishSvnDist)
            // Task from NexusStagingPlugin
            dependsOn(project.tasks.named("closeAndReleaseRepository"))
        }

        // prepareVote depends on all the publish tasks
        // prepareVote depends on publish SVN
        val generateVote = generateVoteText(project, stageDist, releaseExt)
        val prepareVote = project.tasks.register(PREPARE_VOTE_TASK_NAME) {
            description = "Prepare text for vote mail"
            group = "release"
            dependsOn(generateVote)
            doLast {
                val voteText = generateVote.get().outputs.files.singleFile.readText()
                println(voteText)
            }
        }
    }

    private fun Project.addPreviewSiteTasks(releaseExt: ReleaseExtension) {
        val preparePreviewSiteRepo by tasks.registering(GitPrepareRepo::class) {
            repository.set(releaseExt.sitePreview)
        }

        val syncPreviewSiteRepo by tasks.registering(Sync::class) {
            dependsOn(preparePreviewSiteRepo)

            val repo = releaseExt.sitePreview.get()
            val repoDir = File(buildDir, repo.name)
            into(repoDir)
            // Just reuse .gitattributes for text/binary and crlf/lf attributes
            from("${rootProject.rootDir}/.gitattributes")
        }

        // previewSiteContents can be populated from different places, so we defer to afterEvaluate
        afterEvaluate {
            syncPreviewSiteRepo.configure {
                for (c in releaseExt.previewSiteContents.get()) {
                    with(c)
                }
            }
        }

        val pushPreviewSite by tasks.registering(GitCommitAndPush::class) {
            group = PublishingPlugin.PUBLISH_TASK_GROUP
            description = "Builds and publishes site preview"
            commitMessage.set("Update preview for ${rootProject.version}")
            repository.set(rootProject.the<ReleaseExtension>().sitePreview)

            dependsOn(syncPreviewSiteRepo)
        }
    }

    private fun configureNexusStaging(
        project: Project,
        releaseExt: ReleaseExtension
    ) {
        // The fields of releaseExt are not configured yet (the extension is not yet used in build scripts),
        // so we populate NexusStaging properties after the project is configured
        project.afterEvaluate {
            project.configure<NexusStagingExtension> {
                val nexus = releaseExt.nexus
                packageGroup = nexus.packageGroup.get()
                username = nexus.credentials.username.get()
                password = nexus.credentials.password.get()
                val nexusPublish = project.the<NexusPublishExtension>()
                serverUrl =
                    nexusPublish.run { if (useStaging.get()) serverUrl else snapshotRepositoryUrl }
                        .get().toString()
            }
        }
    }

    private fun URI.replacePath(path: String) = URI(
        scheme,
        userInfo,
        host,
        port,
        path,
        query,
        fragment
    )

    private fun configureNexusPublish(
        project: Project,
        releaseExt: ReleaseExtension
    ) {
        project.configure<NexusPublishExtension> {
            serverUrl.set(releaseExt.nexus.url.map { it.replacePath("/service/local/") })
            snapshotRepositoryUrl.set(releaseExt.nexus.url.map { it.replacePath("/content/repositories/snapshots/") })
        }
        // Use the same settings for all subprojects that apply MavenPublishPlugin
        project.subprojects {
            plugins.withType<MavenPublishPlugin> {
                apply(plugin = "de.marcphilipp.nexus-publish")

                configure<NexusPublishExtension> {
                    rootProject.the<NexusPublishExtension>().let {
                        serverUrl.set(it.serverUrl)
                        snapshotRepositoryUrl.set(it.snapshotRepositoryUrl)
                        useStaging.set(it.useStaging)
                    }
                }
            }
        }

        // We don't know which project will be the first to initialize the staging repository,
        // so we watch all the projects
        // The goal of this block is to fetch and save the Id of newly created staging repository
        project.allprojects {
            plugins.withId("de.marcphilipp.nexus-publish") {
                tasks.withType<InitializeNexusStagingRepository>().configureEach {
                    doLast {
                        // nexus-publish puts stagingRepositoryId to NexusStagingExtension so we get it from there
                        val repoName = this@configureEach.repositoryName.get()
                        val stagingRepositoryId =
                            rootProject.the<NexusStagingExtension>().stagingRepositoryId.get()
                        releaseExt.repositoryIdStore[repoName] = stagingRepositoryId
                    }
                }
            }
        }
    }

    private fun generateVoteText(
        project: Project,
        prepareDist: TaskProvider<*>,
        releaseExt: ReleaseExtension
    ): TaskProvider<Task> {
        return project.tasks.register(GENERATE_VOTE_TEXT_TASK_NAME) {
            dependsOn(prepareDist)

            val projectVersion = project.version.toString()
            inputs.property("version", projectVersion)
            inputs.files(releaseExt.archives.get())

            val voteMailFile = "${project.buildDir}/$PREPARE_VOTE_TASK_NAME/mail.txt"
            outputs.file(project.file(voteMailFile))
            doLast {
                val nexusPublish = project.the<NexusPublishExtension>()
                val nexusRepoName = nexusPublish.repositoryName.get()
                val repositoryId = releaseExt.repositoryIdStore[nexusRepoName]

                val repoUri =
                    nexusPublish.serverUrl.get().replacePath("/content/repositories/$repositoryId")

                val grgit: Grgit by project

                val svnDist = releaseExt.svnDist

                val releaseParams = ReleaseParams(
                    tlp = releaseExt.tlp.get(),
                    version = projectVersion,
                    gitSha = grgit.head().id,
                    tag = releaseExt.tag.get(),
                    artifacts = project.files(releaseExt.archives.get())
                        .sortedBy { it.name }
                        .map {
                            ReleaseArtifact(
                                it.name,
                                project.file(it.absolutePath + ".sha512").readText().trim()
                            )
                        },
                    svnStagingUri = svnDist.url.get().let { it.replacePath(it.path + "/" + svnDist.stageFolder.get()) },
                    nexusRepositoryUri = repoUri
                )
                val voteText = releaseExt.voteText.get().invoke(releaseParams)
                project.file(voteMailFile).writeText(voteText)
            }
        }
    }
}
