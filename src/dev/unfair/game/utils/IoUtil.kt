package dev.unfair.game.utils

import java.io.File

object IoUtil {

    fun fileToList(file: File): List<String>
            = file.useLines { it.toList() }

}