fun main() {

    fun getRange(s : String) : Pair<Int, Int> {
        val parts = s.split("-")
        return Pair(parts[0].toInt(), parts[1].toInt())
    }

    fun part1(input: List<String>): Int {
        var count = 0
        for (line in input) {
            val split = line.split(',')
            val (r1, r2) = getRange(split[0])
            val (l1, l2) = getRange(split[1])

            if ((r1 <= l1 && l2 <= r2) || (l1 <= r1 && r2 <= l2)) {
                ++count
            }
        }
        return count
    }

    fun part2(input: List<String>): Int {
        var count = 0
        for (line in input) {
            val split = line.split(',')
            val (r1, r2) = getRange(split[0])
            val (l1, l2) = getRange(split[1])

            if (l1 in r1..r2 || l2 in r1 .. r2 || r1 in l1..l2 || r2 in l1..l2) {
                ++count
            }
        }
        return count
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    println(part1(testInput))
    println(part2(testInput))

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
