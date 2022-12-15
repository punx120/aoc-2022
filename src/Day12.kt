fun main() {

    fun buildMap(input: List<String>): Array<CharArray> {
        val n = input[0].length
        val map = Array(n) { CharArray(n) }
        for (r in input.indices) {
            for (c in input[r].indices)
                map[r][c] = input[r][c]
        }
        return map
    }

    fun findStartAndEnd(map: Array<CharArray>): Pair<Pair<Int, Int>, Pair<Int, Int>> {
        var end: Pair<Int, Int>? = null
        var start: Pair<Int, Int>? = null
        val n = map.size

        // find E
        for (r in 0 until n) {
            for (c in 0 until n) {
                if (map[r][c] == 'E') {
                    end = Pair(r, c)
                    if (start != null) {
                        return Pair(start, end)
                    }
                } else if (map[r][c] == 'S') {
                    start = Pair(r, c)
                    if (end != null) {
                        return Pair(start, end)
                    }
                }
            }
        }
        throw RuntimeException("Invalid input")
    }

    fun part1(map: Array<CharArray>, multipleStart : Boolean): Int {
        val n = map.size
        val visited = Array(n) { IntArray(n) { -1 } }
        val to_visit = mutableSetOf<Pair<Int, Int>>()
        val (start, end) = findStartAndEnd(map)
        map[end.first][end.second] = 'z'
        map[start.first][start.second] = 'a'

        visited[end.first][end.second] = 0
        to_visit.add(end)

        val directions = setOf(Pair(0, 1), Pair(0, -1), Pair(-1, 0), Pair(1, 0))
        var d = 1
        while (to_visit.isNotEmpty()) {
            val next = mutableSetOf<Pair<Int, Int>>()
            for (p in to_visit) {
                val height = map[p.first][p.second]

                for (dir in directions) {
                    val x = p.first + dir.first
                    val y = p.second + dir.second
                    if (x >= 0 && x < n && y >= 0 && y < n) {
                        if (visited[x][y] == -1 && height <= map[x][y] + 1) {
                            visited[x][y] = d
                            next.add(Pair(x,y))
                        }
                    }
                }
            }

            to_visit.clear()
            to_visit.addAll(next)
            ++d

            if (!multipleStart && visited[start.first][start.second] >= 0) {
                return visited[start.first][start.second]
            }
        }

        var min = Int.MAX_VALUE
        for (i in 0 until n) {
            for (j in 0 until n) {
                if (map[i][j] == 'a' && visited[i][j] < min && visited[i][j] > 0) {
                    min = visited[i][j]
                }
            }
        }
        return min
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day12_test")
    println(part1(buildMap(testInput), false))
    println(part1(buildMap(testInput), true))

    val input = readInput("Day12")
    println(part1(buildMap(input), false))
    println(part1(buildMap(input), true))

}
