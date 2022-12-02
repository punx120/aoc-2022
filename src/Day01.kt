fun main() {
    fun part1(input: List<String>): Int {
        var max = 0
        var current = 0

        for (row: String in input) {
            if (row.isEmpty()) {
                if (current > max) {
                    max = current
                }
                current = 0
            } else {
                current += row.toInt()
            }
        }
        return max
    }

    fun part2(input: List<String>): Int {
        var current = 0
        val top = mutableListOf<Int>()

        for (row: String in input) {
            if (row.isEmpty()) {
                top.add(current)
                if (top.size > 3) {
                    top.sort()
                    top.removeAt(0)
                }
                current = 0
            } else {
                current += row.toInt()
            }
        }
        return top.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    println(part1(testInput))

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
