import java.util.*

fun main() {

    val opRegex = "Operation: new = old ([+*]) ([a-z|0-9]+)".toRegex()

    class Monkey(val op: (ULong) -> ULong, val div: ULong, val test: (Boolean) -> Int) {
        var gcd = 1u.toULong()
        var count = 0L
        val items: LinkedList<ULong> = LinkedList<ULong>()
        fun processItems(monkeys: Map<Int, Monkey>, divideBy3: Boolean) {
            for (item in items) {
                ++count
                var value = op.invoke(item)
                if (divideBy3) {
                    value /= 3u
                } else {
                    value %= gcd
                }
                monkeys[test.invoke(value % div == 0u.toULong())]!!.items.add(value)
            }
            items.clear()
        }
    }

    fun parseOperation(line: String): (ULong) -> ULong {
        val match = opRegex.find(line)
        val (op, nb) = match!!.destructured
        return if (nb == "old") {
            if (op == "+") { i: ULong -> i + i } else { i: ULong -> i * i }
        } else {
            val n = nb.toULong()
            if (op == "+") { i: ULong -> i + n } else { i: ULong -> i * n }
        }
    }

    fun buildMonkeys(input: List<String>) : Map<Int, Monkey> {
        val monkeys = mutableMapOf<Int, Monkey>()

        val monkeyIdxRegex = "Monkey (\\d+):".toRegex()

        for (i in input.indices) {
            val find = monkeyIdxRegex.matchEntire(input[i])
            if (find != null) {
                val idx = find.groupValues[1].toInt()
                val elements = input[i + 1].split(':')[1].trim().split(',')
                val operation = parseOperation(input[i + 2])
                val div = input[i + 3].replace("Test: divisible by ", "").trim().toULong()
                val trueMonkey = input[i + 4].replace("If true: throw to monkey ", "").trim().toInt()
                val falseMonkey = input[i + 5].replace("If false: throw to monkey ", "").trim().toInt()

                val monkey = Monkey(operation, div) { b -> if (b) trueMonkey else falseMonkey }
                monkey.items.addAll(elements.map { it.trim().toULong() })
                monkeys[idx] = monkey
            }
        }
        return monkeys
    }

    fun part1(monkeys: Map<Int, Monkey>, round: Int, divideBy3: Boolean): Long {
        var mut = 1u.toULong()
        monkeys.values.map { mut *= it.div }
        monkeys.values.forEach{ it.gcd = mut}

        for (r in 1..round) {
            for (i in 0 until monkeys.size) {
                monkeys[i]!!.processItems(monkeys, divideBy3)
            }
        }

        val sorted = monkeys.values.map { it.count }.sortedDescending()
        return sorted[0] * sorted[1]
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    println(part1(buildMonkeys(testInput), 20, true))
    println(part1(buildMonkeys(testInput), 10000, false))

    val input = readInput("Day11")
    println(part1(buildMonkeys(input), 20, true))
    println(part1(buildMonkeys(input), 10000, false))
}
