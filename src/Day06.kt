fun main() {

    fun part1(input: List<String>, cnt: Long) {
        val elements = ArrayList<Char>()
        for (line in input) {
            elements.clear()
            for (i in line.indices) {
                elements.add(line[i])
                if (elements.size == cnt.toInt()) {
                    if (cnt == elements.stream().distinct().count()) {
                        println(i+1)
                        break
                    } else {
                        elements.removeFirst()
                    }
                }
            }
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    part1(testInput, 4)
    part1(testInput, 14L)

    val input = readInput("Day06")
    part1(input, 4L)
    println(part1(input, 14L))
}
