import java.lang.StringBuilder

fun main() {

    fun parseMove(line: String) : Triple<Int, Int, Int> {
        val regex = "move (\\d+) from (\\d+) to (\\d+)".toRegex()
        val (cnt, from, to) = regex.find(line)!!.destructured
        return Triple(cnt.toInt(), from.toInt(), to.toInt()
        )
    }

    fun buildFromInput(input: List<String>): Pair<Int, Array<ArrayList<Char>>> {
        var br = 0
        for(i in input.indices) {
            if (input[i].trim() == "") {
                br = i
                break
            }
        }

        val n = input[br - 1].split(' ').filter { c -> c != " " && c != ""}.size
        val stacks = Array(n)  { _ -> ArrayList<Char>()}

        for(i in br - 2 downTo 0) {
            for (j in 0 until n) {
                val idx = 1 + 4 * j
                if (idx < input[i].length && input[i][idx] != ' ') {
                    stacks[j].add(input[i][idx])
                }
            }
        }

        return Pair(br, stacks)
    }

    fun printOutput(stacks: Array<ArrayList<Char>>): String {
        val sb = StringBuilder()
        for (stack in stacks) {
            sb.append(stack.last())
        }
        return sb.toString()
    }

    fun part1(input: List<String>): String {
        val (br, stacks) = buildFromInput(input)

        for(l in br + 1 until input.size) {
            val (cnt, from, to) = parseMove(input[l])
            for (i in 0 until cnt) {
                if (stacks[from-1].isNotEmpty()) {
                    stacks[to-1].add(stacks[from-1].removeLast())
                } else {
                    break
                }
            }
        }

       return printOutput(stacks)
    }

    fun part2(input: List<String>): String {
        val (br, stacks) = buildFromInput(input)

        for (l in br+1 until input.size) {
            val (cnt, from, to) = parseMove(input[l])

            val fromIdx = stacks[from-1].size - cnt
            stacks[to-1].addAll(stacks[from-1].subList(fromIdx, stacks[from-1].size))

            for (j in 0 until cnt) {
                stacks[from-1].removeLast()
            }
        }

        return printOutput(stacks)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    println(part1(testInput))
    println(part2(testInput))

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
