package com.beust.kobalt.intellij.config

import com.beust.kobalt.intellij.Constants
import com.beust.kobalt.intellij.KobaltApplicationComponent
import com.intellij.openapi.roots.libraries.LibraryKind
import com.intellij.openapi.roots.libraries.LibraryPresentationProvider
import com.intellij.openapi.vfs.VfsUtilCore
import com.intellij.openapi.vfs.VirtualFile
import icons.KobaltIcons

/**
 * @author Dmitry Zhuravlev
 *         Date:  26.04.2016
 */
class KobaltLibraryPresentationProvider : LibraryPresentationProvider<KobaltLibraryProperties>(KOBALT_KIND) {

    companion object {
        private val KOBALT_KIND = LibraryKind.create(Constants.KOBALT_LIBRARY_KIND)
    }

    override fun getDescription(properties: KobaltLibraryProperties): String? {
        val version = properties.version
        return "Kobalt library" + if (version != null) " of version " + version else ":"
    }


    override fun getIcon() = KobaltIcons.Kobalt

    override fun detect(classesRoots: MutableList<VirtualFile>): KobaltLibraryProperties? {
        val libraryFiles = VfsUtilCore.toVirtualFileArray(classesRoots)
        val kobaltVersion = KobaltApplicationComponent.version
        if (containsKobaltJar(libraryFiles, kobaltVersion)) {
            return KobaltLibraryProperties(kobaltVersion)
        }
        return null
    }

    private fun containsKobaltJar(libraryFiles: Array<out VirtualFile>, kobaltVersion: String) = libraryFiles.any { it.name == "kobalt-$kobaltVersion.jar" }
}