fun main() {
    fun part1(input: List<String>): Int {
        val numbers = input.map { line ->
            line.chunked(1).map(String::toInt)
        }
        val numbersMap = mutableMapOf<Pair<Int, Int>, Int>()
        numbers.forEachIndexed { idx1, list ->
            list.forEachIndexed { idx2, number ->
                numbersMap[Pair(idx1, idx2)] = number
            }
        }
        val sizeX = numbers[0].size - 1
        val sizeY = numbers.size - 1
        var result = 0
        numbersMap.forEach() { (a, b), value ->
            var small = true
            if (a > 0) if (value >= numbersMap[Pair(a - 1, b)]!!) small = false
            if (a < sizeY) if (value >= numbersMap[Pair(a + 1, b)]!!) small = false
            if (b > 0) if (value >= numbersMap[Pair(a, b - 1)]!!) small = false
            if (b < sizeX) if (value >= numbersMap[Pair(a, b + 1)]!!) small = false
            if (small) result += value + 1
        }

        return result
    }

    fun part2(input: List<String>): Int {
        data class Cell(val value: Int, val counted: Boolean)

        val numbers = input.map { line ->
            line.chunked(1).map(String::toInt)
        }
        val numbersMap = mutableMapOf<Pair<Int, Int>, Cell>()
        numbers.forEachIndexed { idx1, list ->
            list.forEachIndexed { idx2, number ->
                numbersMap[Pair(idx1, idx2)] = Cell(number, false)
            }
        }
        val sizeX = numbers[0].size - 1
        val sizeY = numbers.size - 1
        val result = mutableListOf<Int>()

        fun countIt(x: Int, y: Int, cell: Cell): Int {
            if ((cell.value == 9) or (cell.counted)) return 0
            var partialCount = 1
            numbersMap[Pair(x, y)] = Cell(cell.value, true)
            if (x > 0) partialCount += countIt(x - 1, y, numbersMap[Pair(x - 1, y)]!!)
            if (x < sizeY) partialCount += countIt(x + 1, y, numbersMap[Pair(x + 1, y)]!!)
            if (y > 0) partialCount += countIt(x, y - 1, numbersMap[Pair(x, y - 1)]!!)
            if (y < sizeX) partialCount += countIt(x, y + 1, numbersMap[Pair(x, y + 1)]!!)

            return partialCount
        }

        numbersMap.forEach() { (x, y), cell ->
            result.add(countIt(x, y, cell))
        }
        result.sortDescending()
        return result.take(3).fold(1) { sum, element -> sum * element }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 1134)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}
