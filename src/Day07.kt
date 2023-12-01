fun main() {
    val input = readInput("Day07")
    val log = input.lines()

    val rootDirectory = Dir(null, "/")
    var currentDirectory = rootDirectory
    val fs = mutableListOf<FsEntity>()

    fs.add(rootDirectory)

    fun flatten(list: List<FsEntity>): List<FsEntity> = buildList {
        list.forEach { entity ->
            add(entity)
            if (entity is Dir) addAll(flatten(entity.contents))
        }
    }

    fun part1(): Long {
        log.forEachIndexed { index, line ->
            if (line.startsWith("$")) {
                val commandArgs = line.removePrefix("$ ").split(" ")

                val command = commandArgs.first()
                val args = commandArgs.drop(1)

                when (command) {
                    "cd" -> {
                        val directory = args.first()

                        currentDirectory = when {
                            directory == ".." -> currentDirectory.parent!!
                            directory.startsWith("/") -> rootDirectory

                            else -> {
                                currentDirectory.contents
                                    .filterIsInstance<Dir>()
                                    .first { it.name == directory }
                            }
                        }
                    }

                    "ls" -> {
                        val contents = log.drop(index + 1).takeWhile { !it.startsWith("$") }

                        currentDirectory.contents.addAll(
                            contents.map { entity ->
                                when {
                                    entity.startsWith("dir") -> {
                                        val name = entity.substringAfter("dir ")

                                        Dir(currentDirectory, name)
                                    }

                                    else -> {
                                        val (size, name) = entity.split(" ")

                                        File(currentDirectory, name, size.toLong())
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }

        val flattened = flatten(rootDirectory.contents)

        return flattened.filterIsInstance<Dir>()
            .filter { it.size <= 100000 }
            .sumOf { it.size }
    }

    fun part2(): Long {
        val min = 30000000 - (70000000 - rootDirectory.size)
        val flattened = flatten(rootDirectory.contents)

        return flattened.filterIsInstance<Dir>()
            .filter { it.size > min }
            .minBy { it.size }.size
    }

    println(part1())
    println(part2())
}

sealed interface FsEntity {
    val parent: Dir?
    val size: Long
}

data class File(
    override val parent: Dir?,
    val name: String,
    override val size: Long,
) : FsEntity

data class Dir(
    override val parent: Dir?,
    val name: String,
    val contents: MutableList<FsEntity> = mutableListOf()
) : FsEntity {
    override fun toString(): String = name
    override val size get() = contents.sumOf(FsEntity::size)
}