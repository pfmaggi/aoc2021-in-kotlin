fun main() {
    /*
     * Count number of increases in the List<Int> passed to the function.
     */
    fun countIncreases(numbers: List<Int>): Int {
        return numbers.zipWithNext().count  { (first, second) -> second > first }
    }

    fun part1(input: List<String>) =
        countIncreases(input.map(String::toInt))

    fun part2(input: List<String>) =
        countIncreases(input.map(String::toInt).windowed(3) { it.sum() })

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
