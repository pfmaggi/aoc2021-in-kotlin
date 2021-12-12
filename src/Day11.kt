fun main() {
    fun part1(input: List<String>, iterations: Int): Int {
        data class Cell(val value: Int, val flashed: Boolean)

        val numbersMap = mutableMapOf<Pair<Int, Int>, Cell>()
        val sizeX = input[0].length - 1
        val sizeY = input.size - 1
        input.forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->
                numbersMap[Pair(x, y)] = Cell(char.code - 48, false)
            }
        }
        var result = 0

        repeat(
            iterations,
        ) {
            for ((key, cell) in numbersMap) {
                numbersMap[key] = Cell(cell.value + 1, false)
            }
            var thereIsStillWorkToDo = true
            while (thereIsStillWorkToDo) {
                thereIsStillWorkToDo = false
                // Now we ckeck for for values>9
                for ((key, cell) in numbersMap) {
                    val (x, y) = key
                    if (cell.value > 9) {
                        result++
                        thereIsStillWorkToDo = true
                        numbersMap[key] = Cell(0, true)
                        if (x > 0) {
                            if (y > 0) {
                                val currentCell = numbersMap[Pair(x - 1, y - 1)] as Cell
                                if (!currentCell.flashed) numbersMap[Pair(x - 1, y - 1)] =
                                    Cell(currentCell.value + 1, false)
                            }
                            if (y < sizeY) {
                                val currentCell = numbersMap[Pair(x - 1, y + 1)] as Cell
                                if (!currentCell.flashed) numbersMap[Pair(x - 1, y + 1)] =
                                    Cell(currentCell.value + 1, false)
                            }
                            val currentCell = numbersMap[Pair(x - 1, y)] as Cell
                            if (!currentCell.flashed) numbersMap[Pair(x - 1, y)] =
                                Cell(currentCell.value + 1, false)
                        }
                        if (x < sizeX) {
                            if (y > 0) {
                                val currentCell = numbersMap[Pair(x + 1, y - 1)] as Cell
                                if (!currentCell.flashed) numbersMap[Pair(x + 1, y - 1)] =
                                    Cell(currentCell.value + 1, false)
                            }
                            if (y < sizeY) {
                                val currentCell = numbersMap[Pair(x + 1, y + 1)] as Cell
                                if (!currentCell.flashed) numbersMap[Pair(x + 1, y + 1)] =
                                    Cell(currentCell.value + 1, false)
                            }
                            val currentCell = numbersMap[Pair(x + 1, y)] as Cell
                            if (!currentCell.flashed) numbersMap[Pair(x + 1, y)] =
                                Cell(currentCell.value + 1, false)
                        }
                        if (y > 0) {
                            val currentCell = numbersMap[Pair(x, y - 1)] as Cell
                            if (!currentCell.flashed) numbersMap[Pair(x, y - 1)] =
                                Cell(currentCell.value + 1, false)
                        }
                        if (y < sizeY) {
                            val currentCell = numbersMap[Pair(x, y + 1)] as Cell
                            if (!currentCell.flashed) numbersMap[Pair(x, y + 1)] =
                                Cell(currentCell.value + 1, false)
                        }
                    }
                }
            }
        }

        return result
    }

    fun part2(input: List<String>): Int {
        data class Cell(val value: Int, val flashed: Boolean)

        val numbersMap = mutableMapOf<Pair<Int, Int>, Cell>()
        val sizeX = input[0].length - 1
        val sizeY = input.size - 1
        input.forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->
                numbersMap[Pair(x, y)] = Cell(char.code - 48, false)
            }
        }
        var result = 0
        var count = 0

        while (count < 100) {
            count = 0
            result++

            for ((key, cell) in numbersMap) {
                numbersMap[key] = Cell(cell.value + 1, false)
            }
            var thereIsStillWorkToDo = true
            while (thereIsStillWorkToDo) {
                thereIsStillWorkToDo = false
                // Now we ckeck for for values>9
                for ((key, cell) in numbersMap) {
                    val (x, y) = key
                    if (cell.value > 9) {
                        count++
                        thereIsStillWorkToDo = true
                        numbersMap[key] = Cell(0, true)
                        if (x > 0) {
                            if (y > 0) {
                                val currentCell = numbersMap[Pair(x - 1, y - 1)] as Cell
                                if (!currentCell.flashed) numbersMap[Pair(x - 1, y - 1)] =
                                    Cell(currentCell.value + 1, false)
                            }
                            if (y < sizeY) {
                                val currentCell = numbersMap[Pair(x - 1, y + 1)] as Cell
                                if (!currentCell.flashed) numbersMap[Pair(x - 1, y + 1)] =
                                    Cell(currentCell.value + 1, false)
                            }
                            val currentCell = numbersMap[Pair(x - 1, y)] as Cell
                            if (!currentCell.flashed) numbersMap[Pair(x - 1, y)] =
                                Cell(currentCell.value + 1, false)
                        }
                        if (x < sizeX) {
                            if (y > 0) {
                                val currentCell = numbersMap[Pair(x + 1, y - 1)] as Cell
                                if (!currentCell.flashed) numbersMap[Pair(x + 1, y - 1)] =
                                    Cell(currentCell.value + 1, false)
                            }
                            if (y < sizeY) {
                                val currentCell = numbersMap[Pair(x + 1, y + 1)] as Cell
                                if (!currentCell.flashed) numbersMap[Pair(x + 1, y + 1)] =
                                    Cell(currentCell.value + 1, false)
                            }
                            val currentCell = numbersMap[Pair(x + 1, y)] as Cell
                            if (!currentCell.flashed) numbersMap[Pair(x + 1, y)] =
                                Cell(currentCell.value + 1, false)
                        }
                        if (y > 0) {
                            val currentCell = numbersMap[Pair(x, y - 1)] as Cell
                            if (!currentCell.flashed) numbersMap[Pair(x, y - 1)] =
                                Cell(currentCell.value + 1, false)
                        }
                        if (y < sizeY) {
                            val currentCell = numbersMap[Pair(x, y + 1)] as Cell
                            if (!currentCell.flashed) numbersMap[Pair(x, y + 1)] =
                                Cell(currentCell.value + 1, false)
                        }
                    }
                }
            }
        }

        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(part1(testInput, 100) == 1656)
    check(part2(testInput) == 195)

    val input = readInput("Day11")
    println(part1(input, 100))
    println(part2(input))
}
