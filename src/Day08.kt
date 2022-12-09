fun main() {

    fun buildArray(input: List<String>): Array<IntArray> {
        val n = input[0].length
        val array = Array(n) { IntArray(n) }
        for (i in 0 until n) {
            for (j in 0 until n) {
                array[i][j] = input[i][j].digitToInt()
            }
        }
        return array
    }

    fun part1(array: Array<IntArray>): Int {
        val n = array.size
        val visibleTrees = BooleanArray(n * n)
        var count = 0

        for (r in array.indices) {
            var max = -1
            for (c in array.indices) {
                if (array[r][c] > max) {
                    if (!visibleTrees[r * n + c]) {
                        visibleTrees[r * n + c] = true
                        ++count
                    }
                    max = array[r][c]
                }
            }

            max = -1
            for (c in n - 1 downTo 0) {
                if (array[r][c] > max) {
                    if (array[r][c] > max) {
                        if (!visibleTrees[r * n + c]) {
                            visibleTrees[r * n + c] = true
                            ++count
                        }
                        max = array[r][c]
                    }
                }
            }
        }

        for (c in array.indices) {
            var max = -1
            for (r in array.indices) {
                if (array[r][c] > max) {
                    if (!visibleTrees[r * n + c]) {
                        visibleTrees[r * n + c] = true
                        ++count
                    }
                    max = array[r][c]
                }
            }

            max = -1
            for (r in n - 1 downTo 0) {
                if (array[r][c] > max) {
                    if (!visibleTrees[r * n + c]) {
                        visibleTrees[r * n + c] = true
                        ++count
                    }
                    max = array[r][c]
                }
            }
        }

        return count
    }

    fun computeScenicScore(array: Array<IntArray>, r: Int, c: Int): Int {
        var left = 0
        var right = 0
        var top = 0
        var bottom = 0

        val h = array[r][c]

        for (i in r-1 downTo 0) {
            ++left
            if (array[i][c] >= h) {
                break
            }
        }

        for (i in r+1 until array.size) {
            ++right
            if (array[i][c] >= h) {
                break
            }
        }

        for (i in c+1 until  array.size) {
            ++bottom
            if (array[r][i] >= h) {
                break
            }
        }

        for (i in c-1 downTo  0) {
            ++top
            if (array[r][i] >= h) {
                break
            }
        }

        return left * right * top * bottom
    }

    fun part2(array: Array<IntArray>): Int {
        var maxScenicScore = 0

        for (r in array.indices) {
            for (c in array.indices) {
                val score = computeScenicScore(array, r, c)
                if (score > maxScenicScore) {
                    maxScenicScore = score
                }
            }
        }
        return maxScenicScore
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    val testArray = buildArray(testInput)
    println(part1(testArray))
    println(part2(testArray))

    val input = readInput("Day08")
    val array = buildArray(input)

    println(part1(array))
    println(part2(array))
}
