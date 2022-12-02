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

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    println(part1(testInput))

    val input = readInput("Day01")
    println(part1(input))
}
