import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val crabList = input[0].split(",").map(String::toInt).sorted()

        // We use the median
        val median = crabList[crabList.size / 2]
        val resultList = mutableListOf<Int>()
        for (candidate in (median - 1)..(median + 1)) {
            resultList.add(crabList.fold(0) { sum, element -> sum + abs(element - candidate) })
        }
        return resultList.reduce { a: Int, b: Int -> a.coerceAtMost(b) }
    }

    fun movesToFuel(moves: Int) = (1..moves).sum()

    fun part2(input: List<String>): Int {
        val crabList = input[0].split(",").map(String::toInt).sorted()

        // We use the average
        val average = (0.5 + crabList.average()).toInt()
        val resultList = mutableListOf<Int>()
        for (candidate in (average - 1)..(average + 1)) {
            resultList.add(crabList.fold(0) { sum, element -> sum + movesToFuel(abs(element - candidate)) })
        }
        return resultList.reduce { a: Int, b: Int -> a.coerceAtMost(b) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
