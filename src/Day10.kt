fun main() {
    val opening = setOf('(', '[', '{', '<')
    val closing = setOf(')', ']', '}', '>')
    val mapping = mapOf(')' to '(', ']' to '[', '}' to '{', '>' to '<')

    fun part1(input: List<String>): Int {
        val points = mapOf<Char, Int>(
            ')' to 3,
            ']' to 57,
            '}' to 1197,
            '>' to 25137
        )
        val parens = mutableListOf<Char>()
        var result = 0
        input.map { line ->
            var notFoundYet = true
            line.chunked(1).map { paren ->
                if (notFoundYet) {
                    if (opening.contains(paren[0])) parens.add(paren[0])
                    if (closing.contains(paren[0])) {
                        if (mapping[paren[0]] == parens.last()) {
                            parens.removeLast()
                        } else {
                            points[paren[0]]?.apply { result += this }
                            notFoundYet = false
                        }
                    }
                }
            }
            parens.clear()
        }
        return result
    }

    fun part2(input: List<String>): Long {
        val points = mapOf<Char, Int>(
            '(' to 1,
            '[' to 2,
            '{' to 3,
            '<' to 4
        )
        val parens = mutableListOf<Char>()
        val resultList = mutableListOf<Long>()
        input.map { line ->
            var isValidLine = true
            var result = 0L
            line.chunked(1).map { paren ->
                if (opening.contains(paren[0])) parens.add(paren[0])
                if (closing.contains(paren[0])) {
                    if (mapping[paren[0]] == parens.last()) {
                        parens.removeLast()
                    } else {
                        isValidLine = false
                    }
                }
            }
            if (isValidLine) {
                parens.reversed().chunked(1).map { paren ->
                    points[paren[0]]?.apply { result = result * 5 + this }
                }
                resultList.add(result)
            }
            parens.clear()
        }
        resultList.sort()
        return resultList[resultList.size / 2]

    }
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 26397)
    check(part2(testInput) == 288957L)

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}
