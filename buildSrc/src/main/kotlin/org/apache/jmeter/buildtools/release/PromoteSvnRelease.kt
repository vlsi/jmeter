package org.apache.jmeter.buildtools.release

import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.kotlin.dsl.the
import org.gradle.work.InputChanges

abstract class PromoteSvnRelease : SvnmuccTask() {
    @get:InputFiles
    @get:PathSensitive(PathSensitivity.NAME_ONLY)
    val files = project.files()

    init {
        outputs.upToDateWhen { false }
    }

    override fun message() =
        project.the<ApacheReleaseExtension>().run {
            "Promoting release candidate ${tlp.get()} ${tag.get()} to release area"
        }

    override fun operations(inputChanges: InputChanges): List<SvnOperation> {
        return mutableListOf<SvnOperation>().apply {
            val ext = project.the<ApacheReleaseExtension>()
            val stageFolder = ext.releaseStageFolder.get()
            val releaseFolder = ext.releaseFinalFolder.get()

            val subfolders = ext.releaseSubfolder.get()
            for (f in files) {
                val stagedFile = "$stageFolder/${f.name}"
                val subfolder = subfolders.entries.firstOrNull { f.name.contains(it.key) }?.value
                val releasedFile =
                    "$releaseFolder/${if (subfolder.isNullOrEmpty()) "" else "$subfolder/"}${f.name}"
                for (fileExt in listOf("", ".sha512")) {
                    add(SvnMv(stagedFile + fileExt, releasedFile + fileExt))
                }
            }
            add(SvnRm(stageFolder))
        }
    }
}
