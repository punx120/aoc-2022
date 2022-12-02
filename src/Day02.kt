fun main() {
    fun score(opp: String, me:String) : Int {
        if (opp == "A") {
            if (me == "X") {
                return 3
            } else if (me == "Y") {
                return 6
            } else if (me == "Z") {
                return 0
            }
        } else if (opp == "B") {
            if (me == "X") {
                return 0
            } else if (me == "Y") {
                return 3
            } else if (me == "Z") {
                return 6
            }
        } else if (opp == "C") {
            if (me == "X") {
                return 6
            } else if (me == "Y") {
                return 0
            } else if (me == "Z") {
                return 3
            }
        }
        throw Exception("Invalid combination ($opp, $me)")
    }

    fun moveScore(move: String) = when (move) {
        "X" -> 1
        "A" -> 1
        "Y" -> 2
        "B" -> 2
        "Z" -> 3
        "C" -> 3
        else -> throw Exception("Invalid move $move")
    }

    fun scoreFromResult(result: String) = when (result) {
        "X" -> 0
        "Y" -> 3
        "Z" -> 6
        else -> throw Exception("Invalid move $result")
    }

    fun getMoveScoreForGivenResult(move: String, result: String) : Int {
        if (result == "X") {
            if (move == "A") {
                return moveScore("C")
            } else if (move == "B") {
                return moveScore("A")
            } else if (move == "C") {
                return moveScore("B")
            }
        } else if (result == "Y") {
            return moveScore(move)
        } else if (result == "Z") {
            if (move == "A") {
                return moveScore("B")
            } else if (move == "B") {
                return moveScore("C")
            } else if (move == "C") {
                return moveScore("A")
            }
        }
        throw Exception("Invalid result $result")
    }

    fun part1(input: List<String>): Int {
        var res = 0
        for (row : String in input) {
            val parts = row.split(' ')
            res += score(parts[0], parts[1]) + moveScore(parts[1])
        }
        return res
    }

    fun part2(input: List<String>): Int {
        var res = 0
        for (row : String in input) {
            val parts = row.split(' ')
            res += scoreFromResult(parts[1]) + getMoveScoreForGivenResult(parts[0], parts[1])
        }
        return res
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    println(part1(testInput))
    println(part2(testInput))

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
