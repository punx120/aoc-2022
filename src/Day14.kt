import kotlin.math.max

fun main() {

    class Waterfall(val map: Array<CharArray>, val depth: Int)

    fun parseInput(input: List<String>): Waterfall {
        val map = Array(1000) { CharArray(200) { '.' } }
        var depth = 0
        for (line in input) {
            val l = line.split("->").map { it.trim().split(",") }.map { Pair(it[0].toInt(), it[1].toInt()) }
            var p = l[0]
            map[p.first][p.second] = '#'
            for (i in 1 until l.size) {
                val n = l[i]
                if (n.first < p.first) {
                    for (x in n.first..p.first) {
                        map[x][p.second] = '#'
                        depth = max(p.second, depth)
                    }
                } else if (n.first > p.first) {
                    for (x in p.first..n.first) {
                        map[x][p.second] = '#'
                        depth = max(p.second, depth)
                    }
                } else if (n.second < p.second) {
                    for (y in n.second..p.second) {
                        map[n.first][y] = '#'
                        depth = max(y, depth)
                    }
                } else if (n.second > p.second) {
                    for (y in p.second..n.second) {
                        map[n.first][y] = '#'
                        depth = max(y, depth)
                    }
                }
                p = n
            }
        }
        return Waterfall(map, depth)
    }

    fun move(map: Array<CharArray>, pair: Pair<Int, Int>): Pair<Int, Int> {
        if (map[pair.first][pair.second + 1] == '.') {
            return Pair(pair.first, pair.second+1)
        } else if (map[pair.first][pair.second + 1] != '.') {
            if (map[pair.first - 1][pair.second + 1] == '.') {
                return Pair(pair.first - 1, pair.second + 1)
            } else if (map[pair.first + 1][pair.second + 1] == '.') {
                return Pair(pair.first + 1, pair.second + 1)
            }
        }

        return pair
    }

    fun drop(map: Waterfall) : Boolean {
        var p = Pair(500, 0)
        while (true) {
            val n = move(map.map, p)
            if (n.second > map.depth + 2) {
                return true
            }

            if (n == p) {
                break
            } else {
                p = n
            }
        }
        if (map.map[p.first][p.second] != '.') {
            return true
        }

        map.map[p.first][p.second] = 'o'
        return false
    }

    fun part1(input: List<String>, floor: Boolean): Int {
        val map = parseInput(input)
        if (floor) {
            for (i in map.map.indices) {
                map.map[i][map.depth + 2] = '#'
            }
        }

        var i = 0
        while (true) {
            if (drop(map)) {
                break
            }
            ++i
        }

        return i
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day14_test")
    println(part1(testInput, false))
    println(part1(testInput, true))

    val input = readInput("Day14")
    println(part1(input, false))
    println(part1(input, true))
}
