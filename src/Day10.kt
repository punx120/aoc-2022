import java.lang.StringBuilder

fun main() {

    class Node(val length: Int, val arg: Int) {
        var startCycle: Int = -1
    }

    fun parseInstructions(input: List<String>): ArrayDeque<Node> {
        val instructions = ArrayDeque<Node>()
        input.map { it.split(" ") }
            .map { instructions.add(Node(if (it[0] == "noop") 1 else 2,
                                         if (it.size > 1) it[1].toInt() else 0))
                }
        return instructions
    }

    fun part1(instructions: ArrayDeque<Node>): Int {
        var strength = 0
        var cycle = 1
        var register = 1

        val crt = Array(6) { _ -> StringBuilder(" ".repeat(40)) }

        while (!instructions.isEmpty()) {
            val n = instructions.first()
            if (n.startCycle == -1) {
                n.startCycle = cycle
            }

            val col = (cycle -1) % 40
            val line = crt[(cycle -1) / 40]
            line[col] = if (col >= register -1 && col <= register +1) '#' else '.'

            if (cycle == 20 || (cycle - 20) % 40 == 0) {
                strength += cycle * register
            }

            ++cycle

            if (cycle - n.startCycle == n.length) {
                register += n.arg
                instructions.removeFirst()
            }
        }

        for (l in crt) {
            println(l)
        }

        return strength
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test2")
    println(part1(parseInstructions(testInput)))

    val input = readInput("Day10")
    println(part1(parseInstructions(input)))
}
