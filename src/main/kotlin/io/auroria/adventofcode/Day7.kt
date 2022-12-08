package io.auroria.adventofcode

object Day7 {

    abstract class FilesystemNode(val name: String, val parent: Directory?) {
        abstract fun calculateSize(): Int
    }

    class Directory(name: String, parent: Directory?, val children: MutableList<FilesystemNode> = mutableListOf()) :
        FilesystemNode(name, parent) {
        override fun calculateSize() = children.sumOf { it.calculateSize() }

        override fun toString(): String {
            return "$name (dir)"
        }
    }

    class File(name: String, parent: Directory, val size: Int) : FilesystemNode(name, parent) {
        override fun calculateSize() = size

        override fun toString(): String {
            return "$name (file, size=$size)"
        }
    }

    fun noSpaceLeftOnDevice() {
        val data = this::class.java.getResourceAsStream("/day7/input")!!
            .bufferedReader()
            .readLines()

        val tree = generateTree(data)


        val smallDirectories = findSmallerDirectories(tree, 100000)
        println("Part 1: " + smallDirectories.sumOf { it.calculateSize() })

        val totalDiskSize = 70000000
        val totalUsedDiskSpace = tree.calculateSize()

        val currFreeSpace = totalDiskSize - totalUsedDiskSpace
        val minSpaceStillNeeded = 30000000 - currFreeSpace

        val unrolledDirectories = unrollTree(tree)
        val directoryToDelete = unrolledDirectories
            .filter { it.calculateSize() >= minSpaceStillNeeded }
            .minBy { it.calculateSize() }

        println("Part 2: " + directoryToDelete.calculateSize())
    }

    private fun unrollTree(tree: Directory): List<Directory> {
        val folders = tree.children
            .filterIsInstance<Directory>()

        return tree.children
            .filterIsInstance<Directory>()
            .flatMap { unrollTree(it) }
            .plus(folders)
    }

    private fun findSmallerDirectories(tree: Directory, sizeLimit: Int): List<Directory> {
        val folders = tree.children
            .filterIsInstance<Directory>()
            .filter { it.calculateSize() <= sizeLimit }

        return tree.children
            .filterIsInstance<Directory>()
            .flatMap { findSmallerDirectories(it, sizeLimit) }
            .plus(folders)
    }

    private fun generateTree(data: List<String>): Directory {
        var root: Directory? = null
        var currState: Directory? = root

        data.forEach { line ->
            val cdFolderRegex = "\\$ cd ([\\w\\d]+)".toRegex()
            val dirFolderRegex = "dir ([\\w\\d]+)".toRegex()
            val fileRegex = "(\\d+) ([\\w\\d\\.]+)".toRegex()

            when {
                line == "$ cd /" -> {
                    if (root == null) {
                        root = Directory("/", null)
                    }
                    currState = root
                }

                line == "$ cd .." -> currState = currState!!.parent
                line.matches(cdFolderRegex) -> {
                    val folderName = cdFolderRegex.matchEntire(line)!!.groupValues[1]
                    val foundExistingFolder = currState!!.children
                        .filterIsInstance<Directory>()
                        .find { c -> c.name == folderName }

                    if (foundExistingFolder != null) {
                        currState = foundExistingFolder
                    } else {
                        val newDirectory = Directory(folderName, currState)
                        currState!!.children.add(newDirectory)
                    }
                }

                line.matches(dirFolderRegex) -> {
                    val folderName = dirFolderRegex.matchEntire(line)!!.groupValues[1]

                    val foundExistingFolder = currState!!.children
                        .filterIsInstance<Directory>()
                        .any { c -> c.name == folderName }

                    if (!foundExistingFolder) {
                        val newDirectory = Directory(folderName, currState)
                        currState!!.children.add(newDirectory)
                    }
                }

                line.matches(fileRegex) -> {
                    val fileMatches = fileRegex.matchEntire(line)!!.groupValues
                    val fileSize = fileMatches[1].toInt()
                    val fileName = fileMatches[2]

                    val foundExistingFile = currState!!.children
                        .filterIsInstance<File>()
                        .any { c -> c.name == fileName }

                    if (!foundExistingFile) {
                        val newFile = File(fileName, currState!!, fileSize)
                        currState!!.children.add(newFile)
                    }
                }

                line == "$ ls" -> Unit
                else -> throw RuntimeException("What happened?")
            }
        }

        return root!!
    }


}

fun main() {
    Day7.noSpaceLeftOnDevice()
}