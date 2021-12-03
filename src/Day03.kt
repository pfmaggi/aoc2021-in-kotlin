import java.lang.Integer.parseInt

fun main() {
    data class DigitsCount(var zeros: Int, var ones: Int)

    fun digitsCounts(input: List<String>): MutableList<DigitsCount> {
        val counts: MutableList<DigitsCount> = mutableListOf()

        input.forEach { line ->
            line.forEachIndexed() { idx, digit ->
                if (counts.size <= idx) counts.add(idx, DigitsCount(0, 0))
                if ('0' == digit) {
                    counts[idx].zeros++
                } else counts[idx].ones++
            }
        }
        return counts
    }

    fun part1(input: List<String>): Int {
        var gamma = ""
        var epsilon = ""
        val counts: MutableList<DigitsCount> = digitsCounts(input)
        counts.iterator().forEach { digit ->
            if (digit.zeros > digit.ones) {
                gamma += "0"
                epsilon += "1"
            } else {
                gamma += "1"
                epsilon += "0"
            }
        }

        return parseInt(gamma, 2) * parseInt(epsilon, 2)
    }

    fun part2(input: List<String>): Int {
        val co2Scrubber: Int
        val oxigenGenerator : Int
        var counts: MutableList<DigitsCount> = digitsCounts(input)
        var numbers: MutableList<String> = input.toMutableList()
        while (numbers.size > 1) {
            counts.forEachIndexed { idx, _ ->
                counts = digitsCounts(numbers)
                for (line_idx in numbers.lastIndex downTo 0) {
                    val line = numbers[line_idx]

                    if (counts[idx].zeros > counts[idx].ones) {
                        if ('1' == line[idx]) {
                            numbers.removeAt(line_idx)
                        }
                    } else {
                        if ('0' == line[idx]) {
                            numbers.removeAt(line_idx)
                        }
                    }
                }
                if (numbers.size == 1) return@forEachIndexed
            }

        }
        oxigenGenerator = parseInt(numbers[0], 2)

        counts = digitsCounts(input)
        numbers = input.toMutableList()

        while (numbers.size > 1) {
            counts.forEachIndexed { idx, _ ->
                if (numbers.size == 1) return@forEachIndexed
                counts = digitsCounts(numbers)
                for (line_idx in numbers.lastIndex downTo 0) {
                    val line = numbers[line_idx]

                    if (counts[idx].zeros > counts[idx].ones) {
                        if ('0' == line[idx]) {
                            numbers.removeAt(line_idx)
                        }
                    } else {
                        if ('1' == line[idx]) {
                            numbers.removeAt(line_idx)
                        }
                    }
                }
            }

        }

        co2Scrubber = parseInt(numbers[0], 2)
        return oxigenGenerator * co2Scrubber
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
