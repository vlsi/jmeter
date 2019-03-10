package org.apache.jmeter.buildtools.release

import java.io.File

class ParentFilesCollector {
    private val parentFolders = mutableSetOf<String>()

    val parents: Set<String> = parentFolders

    fun add(fileName: String) {
        val file = File(fileName)
        collect(file.parentFile)
    }

    private fun collect(file: File?) {
        if (file == null) {
            return
        }
        val path = file.path
        if (parentFolders.contains(path)) {
            return
        }
        collect(file.parentFile)
        parentFolders.add(path)
    }
}
