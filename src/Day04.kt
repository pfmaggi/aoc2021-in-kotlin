fun main() {
    fun compute(input: List<String>): List<Int> {
        val results: MutableList<Int> = mutableListOf()

        val draws = input[0].split(",").map { it.toInt() }
        val boards = input
            .drop(1) // Remove first line that contains the draws
            .chunked(6)
            .map { singleBoardLines ->
                singleBoardLines.filter { singleBoardLine ->
                    singleBoardLine.isNotBlank() // Remove empty lines
                }
            }.map { board ->
                board.map { line ->
                    line.split("  ", " ") // Double or Single space separator
                        .filter { it.isNotBlank() } // Leading whitespace
                        .map { it.toInt() }
                }
            }
        var bingoBoards = boards.map { BingoBoard.fromCollection(it) }

        for (currentDraw in draws) {
            for (board in bingoBoards) {
                board.mark(currentDraw)
                if (board.checkBoard()) {
                    val sumOfUnmarkedFields = board.unmarked().sum()
                    results.add(sumOfUnmarkedFields * currentDraw)
                    bingoBoards = bingoBoards - board
                }
            }
        }

        return results
    }

    // test if implementation meets criteria from the description, like:
    val testResult = compute(readInput("Day04_test"))
    check(testResult.first() == 4512)
    check(testResult.last() == 1924)

    val result = compute(readInput("Day04"))
    println(result.first())
    println(result.last())
}

data class BingoField(val value: Int, val marked: Boolean = false)

data class BingoBoard(val fields: List<MutableList<BingoField>>) {

    private val widthIndices = fields[0].indices
    private val heightIndices = fields.indices

    companion object {
        fun fromCollection(coll: List<List<Int>>): BingoBoard {
            return BingoBoard(coll.map { row -> row.map { field -> BingoField(field) }.toMutableList() })
        }
    }

    fun checkBoard() = checkRow() || checkColumn()
    private fun checkRow() = fields.any { row -> row.all { it.marked } }

    private fun checkColumn(): Boolean {
        for (column in widthIndices) {
            var columnOk = true
            for (row in heightIndices) {
                if (!fields[row][column].marked) {
                    columnOk = false
                    continue
                }
            }
            if (columnOk) return true
        }
        return false
    }

    fun mark(num: Int) {
        for (row in this.fields) {
            row.replaceAll {
                if (it.value == num) it.copy(marked = true) else it
            }
        }
    }

    fun unmarked() = fields.flatten().filter { !it.marked }.map { it.value }
}