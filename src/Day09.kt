fun main() {
    fun part1(input: List<String>): Int {
        val numbersMap = mutableMapOf<Pair<Int, Int>, Int>()
        val sizeX = input[0].length - 1
        val sizeY = input.size - 1
        input.forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->
                numbersMap[Pair(x, y)] = char.code - 48
            }
        }

        var result = 0
        checkMap@
        for ((key, value) in numbersMap) {
            val (x,y) = key
            if (x > 0) if (value >= numbersMap[Pair(x - 1, y)] as Int) continue@checkMap
            if (x < sizeX) if (value >= numbersMap[Pair(x + 1, y)] as Int) continue@checkMap
            if (y > 0) if (value >= numbersMap[Pair(x, y - 1)] as Int) continue@checkMap
            if (y < sizeY) if (value >= numbersMap[Pair(x, y + 1)] as Int) continue@checkMap
            result += value + 1
        }

        return result
    }

    fun part2(input: List<String>): Int {
        data class Cell(val value: Int, val counted: Boolean)

        val numbersMap = mutableMapOf<Pair<Int, Int>, Cell>()
        val sizeX = input[0].length - 1
        val sizeY = input.size - 1
        input.forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->
                numbersMap[Pair(x, y)] = Cell(char.code - 48, false)
            }
        }
        val result = mutableListOf<Int>()

        fun countIt(x: Int, y: Int, cell: Cell): Int {
            if ((cell.value == 9) or (cell.counted)) return 0
            var partialCount = 1
            numbersMap[Pair(x, y)] = Cell(cell.value, true)
            if (x > 0) partialCount += countIt(x - 1, y, numbersMap[Pair(x - 1, y)]!!)
            if (x < sizeX) partialCount += countIt(x + 1, y, numbersMap[Pair(x + 1, y)]!!)
            if (y > 0) partialCount += countIt(x, y - 1, numbersMap[Pair(x, y - 1)]!!)
            if (y < sizeY) partialCount += countIt(x, y + 1, numbersMap[Pair(x, y + 1)]!!)

            return partialCount
        }

        checkMap@
        for ((key, cell) in numbersMap) {
            if ((cell.value == 9) or (cell.counted)) continue@checkMap
            val (x, y) = key
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
