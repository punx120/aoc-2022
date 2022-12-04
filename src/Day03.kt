fun main() {

    fun priority(c : Char): Int {
        return if (c.code >= 'a'.code) {
            c.code - 'a'.code + 1
        } else {
            26 + c.code - 'A'.code + 1
        }
    }

    fun part1(input: List<String>): Int {
        var sum = 0
        for (line in input) {
            val first = mutableSetOf<Char>()
            val middle = line.length / 2
            for (i in 0 until middle) {
                first.add(line[i])
            }

            val second = mutableSetOf<Char>()
            for (i in middle until line.length) {
                val item = line[i]
                if (first.contains(item) && second.add(item)) {
                    sum += priority(item)
                }
            }
        }

        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        for (i in input.indices step 3) {
            val map = mutableMapOf<Char, MutableSet<Int>>()

            for (k in input[i].indices) {
                map.computeIfAbsent(input[i][k]) { mutableSetOf() }.add(i)
            }

            for (k in input[i+1].indices) {
                map.computeIfAbsent(input[i+1][k]) { mutableSetOf() }.add(i+1)
            }

            for (k in input[i+2].indices) {
                map.computeIfAbsent(input[i+2][k]) { mutableSetOf() }.add(i+2)
            }

            map.forEach{e ->
                if (e.value.size == 3) {
                   sum += priority(e.key)
                }
            }
        }

        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    println(part1(testInput))
    println(part2(testInput))

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
