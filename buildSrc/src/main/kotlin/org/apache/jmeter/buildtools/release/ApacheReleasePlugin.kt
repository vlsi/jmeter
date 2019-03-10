package org.apache.jmeter.buildtools.release

import de.marcphilipp.gradle.nexus.InitializeNexusStagingRepository
import de.marcphilipp.gradle.nexus.NexusPublishExtension
import io.codearte.gradle.nexus.BaseStagingTask
import io.codearte.gradle.nexus.NexusStagingExtension
import org.ajoberstar.grgit.Grgit
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin
import org.gradle.api.publish.plugins.PublishingPlugin
import org.gradle.api.tasks.TaskProvider
import org.gradle.internal.reflect.Instantiator
import org.gradle.kotlin.dsl.*
import java.net.URI
import javax.inject.Inject

class ApacheReleasePlugin @Inject constructor(private val instantiator: Instantiator) :
    Plugin<Project> {
    companion object {
        const val EXTENSION_NAME = "apacheRelease"

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

        // Save stagingRepoId. We don't know which
        val releaseExt = project.extensions.create<ApacheReleaseExtension>(EXTENSION_NAME, project)

        // We don't know which project will be the first to initialize the staging repository,
        // so we watch all the projects
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

        val stageSvnDist = project.tasks.register<StageToSvnTask>(STAGE_SVN_DIST_TASK_NAME) {
            description = "Stage release artifacts to SVN dist.apache.org repository"
            group = "release"
            files.from(releaseExt.archives.get())
//            project.files(releaseExt.archives.get())
//            dependsOn.addAll(releaseExt.archives.get())
        }

        val publishSvnDist = project.tasks.register<PromoteSvnRelease>(PUBLISH_SVN_DIST_TASK_NAME) {
            description = "Publish release artifacts to SVN dist.apache.org repository"
            group = "release"
            files.from(releaseExt.archives.get())
        }

        val closeRepository = project.tasks.named<BaseStagingTask>("closeRepository")

        val stageDist = project.tasks.register(STAGE_DIST_TASK_NAME) {
            description = "Stage release artifacts to SVN and Nexus"
            group = "release"
            dependsOn(stageSvnDist)
            dependsOn(closeRepository)
        }

        project.allprojects {
            plugins.withType<PublishingPlugin> {
                closeRepository.configure {
                    dependsOn(tasks.named(PublishingPlugin.PUBLISH_LIFECYCLE_TASK_NAME))
                }
            }
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
        val generateVote = prepareVote(project, stageDist, releaseExt)
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

    private fun prepareVote(
        project: Project,
        prepareDist: TaskProvider<*>,
        releaseExt: ApacheReleaseExtension
    ): TaskProvider<Task> {
        return project.tasks.register(GENERATE_VOTE_TEXT_TASK_NAME) {
            dependsOn(prepareDist)

            val projectVersion = project.version.toString()
            inputs.property("version", projectVersion)
            inputs.files(releaseExt.archives.get())

            val voteMailFile = "${project.buildDir}/$PREPARE_VOTE_TASK_NAME/mail.txt"
            outputs.file(project.file(voteMailFile))
            doLast {
                val tag = "v$projectVersion"

                val nexusPublish = project.the<NexusPublishExtension>()
                val nexusRepoName = nexusPublish.repositoryName.get()
                val repositoryId = releaseExt.repositoryIdStore[nexusRepoName]

                val repoUri = nexusPublish.serverUrl.get()
                    .run {
                        URI(
                            scheme,
                            null,
                            host,
                            port,
                            "/content/repositories/$repositoryId",
                            null,
                            null
                        )
                    }

                val grgit: Grgit by project

                val releaseParams = ReleaseParams(
                    tlp = releaseExt.tlp.get(),
                    version = projectVersion,
                    gitSha = grgit.head().id,
                    tag = tag,
                    artifacts = project.files(releaseExt.archives.get())
                        .sortedBy { it.name }
                        .map {
                            ReleaseArtifact(
                                it.name,
                                project.file(it.absolutePath + ".sha512").readText().trim()
                            )
                        },
                    stagingRepositoryUri = repoUri,
                    stagingRepositoryId = repositoryId ?: "unknownStagingRepositoryId"
                )
                val voteText = releaseExt.voteText.get().invoke(releaseParams)
                project.file(voteMailFile).writeText(voteText)
            }
        }
    }
}
