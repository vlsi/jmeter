package org.apache.jmeter.buildtools.release

import org.gradle.api.Project
import java.util.concurrent.ConcurrentHashMap

class NexusRepositoryIdStore(private val project: Project) {
    private val savedIds = ConcurrentHashMap<String, String>()

    private fun storeDir() = "${project.buildDir}/stagingProfiles"

    private fun filePath(repositoryName: String) = "${storeDir()}/$repositoryName.txt"

    operator fun get(name: String) = savedIds[name]

    operator fun set(name: String, id: String) {
        if (savedIds.putIfAbsent(name, id) == null) {
            project.logger.info("Saving stagingRepositoryId for repository $name -> $id")
            val file = project.file(filePath(name))
            file.parentFile.mkdirs()
            file.writeText(id)
        }
    }

    fun load() {
        for (f in project.file(storeDir()).listFiles({ f -> f.name.endsWith("*.txt") })) {
            savedIds[f.name.removeSuffix(".txt")] = f.readText()
        }
    }
}