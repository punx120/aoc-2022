class Node(
    val name: String,
    var files: Long = 0,
    val parent: Node? = null,
    val children: MutableMap<String, Node> = mutableMapOf()
) {
    fun totalSize(): Long {
        return files + children.values.sumOf { it.totalSize() }
    }

    override fun toString(): String {
        return "Node(name='$name', totalSize=${totalSize()})"
    }
}

fun main() {

    fun explore(node: Node): Long {
        var total = 0L
        for (n in node.children.values) {
            if (n.totalSize() <= 100000) {
                total += n.totalSize()
            }
            total += explore(n)
        }

        return total
    }

    fun buildDirectoryStructure(input: List<String>): Node {
        val root = Node("/")
        var current = root

        for (line in input) {
            if (line.startsWith("$")) {
                if (line.startsWith("$ cd ")) {
                    val dest = line.subSequence(5, line.length)
                    if (dest == "/") {
                        current = root
                    } else if (dest == "..") {
                        current = current.parent!!
                    } else {
                        current = current.children[dest]!!
                    }
                }
            } else {
                if (line.startsWith("dir")) {
                    val dirname = line.substring(4, line.length)
                    current.children[dirname] = Node(dirname, parent = current)
                } else {
                    val e = line.split(" ")
                    current.files += e[0].toLong()
                }
            }
        }
        return root
    }

    fun part1(root: Node): Long {
        var total = 0L
        for (dir in root.children.values) {
            if (dir.totalSize() <= 100000) {
                total += dir.totalSize()
            }
            total += explore(dir)
        }
        return total
    }

    fun findDirectories(node: Node, minSize: Long): List<Node> {
        val ans = ArrayList<Node>()
        for (dir in node.children.values) {
            if (dir.totalSize() >= minSize) {
                ans.add(dir)
            }
            ans.addAll(findDirectories(dir, minSize))
        }
        return ans
    }

    fun part2(root: Node): Long {
        val free = 70000000 - root.totalSize()
        val toFree = 30000000 - free
        val candidates = findDirectories(root, toFree)
        return candidates.minBy { it.totalSize() }.totalSize()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    val testRoot = buildDirectoryStructure(testInput)
    println(part1(testRoot))
    println(part2(testRoot))

    val input = readInput("Day07")
    val root = buildDirectoryStructure(input)
    println(part1(root))
    println(part2(root))
}
