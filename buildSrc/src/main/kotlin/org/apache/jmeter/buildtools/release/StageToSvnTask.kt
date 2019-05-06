package org.apache.jmeter.buildtools.release

import org.gradle.api.tasks.*
import org.gradle.kotlin.dsl.the
import org.gradle.work.ChangeType
import org.gradle.work.Incremental
import org.gradle.work.InputChanges

abstract class StageToSvnTask() : SvnmuccTask() {
    @get:Incremental
    @get:SkipWhenEmpty
    @get:InputFiles
    @get:PathSensitive(PathSensitivity.NAME_ONLY)
    val files = project.files()

    @get:Input
    val folder = project.the<ReleaseExtension>().svnDist.stageFolder

    init {
        dependsOn(files)
    }

    override fun message() =
        project.the<ReleaseExtension>().run {
            "Uploading release candidate ${tlp.get()} ${tag.get()} to dev area"
        }

    override fun operations(inputChanges: InputChanges): List<SvnOperation> {
        return mutableListOf<SvnOperation>().apply {
            val folderName = folder.get()
            add(SvnMkdir(folderName))
            for (f in inputChanges.getFileChanges(files)) {
                val destinationName = "$folderName/${f.file.name}"
                for (ext in listOf("", ".sha512")) {
                    add(
                        when (f.changeType) {
                            ChangeType.REMOVED -> SvnRm(destinationName + ext)
                            ChangeType.ADDED, ChangeType.MODIFIED -> SvnPut(
                                f.file,
                                destinationName + ext
                            )
                        }
                    )
                }
            }
        }
    }
}
