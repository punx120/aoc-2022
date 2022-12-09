import java.lang.RuntimeException
import kotlin.math.abs

fun main() {

    fun moveHead(pos : Pair<Int, Int>, direction : Pair<Int, Int>) : Pair<Int, Int> {
        return Pair(pos.first + direction.first, pos.second + direction.second)
    }

    fun moveTail(hp: Pair<Int, Int>, tp: Pair<Int, Int>): Pair<Int, Int> {
        if (abs(hp.first - tp.first) < 2 && abs(hp.second - tp.second) < 2)
            return tp

        return if (hp.first == tp.first) { // Same y axis
            val second = if (tp.second < hp.second) hp.second -1 else hp.second +1
            Pair(hp.first, second)
        } else if (hp.second == tp.second) { // same x axis
            val first = if (tp.first < hp.first) hp.first -1 else hp.first +1
            Pair(first, hp.second)
        } else {
            val first = if (tp.first < hp.first) tp.first + 1 else tp.first - 1
            val second = if (tp.second < hp.second) tp.second + 1 else tp.second -1
            Pair(first, second)
        }
    }

    fun part1(input: List<String>): Int {
        val position = mutableSetOf<Pair<Int, Int>>()

        var hp = Pair(0, 0)
        var tp = Pair(0, 0)

        for (line in input) {
            val dir = when (line[0]) {
                'R' -> Pair(1, 0)
                'L' -> Pair(-1, 0)
                'U' -> Pair(0, 1)
                'D' -> Pair(0, -1)
                else -> throw RuntimeException("invalid $line")
            }
            val steps = line.substring(2, line.length).toInt()

            for (i in 0 until steps) {
                hp = moveHead(hp, dir)
                tp = moveTail(hp, tp)
                position.add(tp)
            }

        }
        return position.size
    }

    fun part2(input: List<String>): Int {
        val position = mutableSetOf<Pair<Int, Int>>()

        val ropes = Array(10) { _ -> Pair(0, 0)}

        for (line in input) {
            val dir = when (line[0]) {
                'R' -> Pair(1, 0)
                'L' -> Pair(-1, 0)
                'U' -> Pair(0, 1)
                'D' -> Pair(0, -1)
                else -> throw RuntimeException("invalid $line")
            }
            val steps = line.substring(2, line.length).toInt()

            for (i in 0 until steps) {
                ropes[0] = moveHead(ropes[0], dir)
                for (j in 1 until 10) {
                    ropes[j] = moveTail(ropes[j - 1], ropes[j])
                }
                position.add(ropes[9])
            }
        }
        return position.size
    }

    val testInput = readInput("Day09_test")
    println(part1(testInput))
    println(part2(testInput))

    println(part2(readInput("Day09_test2")))

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}
