fun main() {
    val opening = setOf('(', '[', '{', '<')
    val closing = setOf(')', ']', '}', '>')
    val mapping = mapOf(')' to '(', ']' to '[', '}' to '{', '>' to '<')

    fun part1(input: List<String>): Int {
        val points = mapOf(
            ')' to 3,
            ']' to 57,
            '}' to 1197,
            '>' to 25137
        )
        val parens = mutableListOf<Char>()
        var result = 0
        checkLine@
        for (line in input) {
            parens.clear()
            for (paren in line) {
                if (opening.contains(paren)) parens.add(paren)
                if (closing.contains(paren)) {
                    if (mapping[paren] != parens.last()) {
                        result += points[paren] as Int
                        continue@checkLine
                    }
                    parens.removeLast()
                }
            }
        }
        return result
    }

    fun part2(input: List<String>): Long {
        val points = mapOf(
            '(' to 1,
            '[' to 2,
            '{' to 3,
            '<' to 4
        )
        val parens = mutableListOf<Char>()
        val resultList = mutableListOf<Long>()
        checkLine@
        for (line in input) {
            parens.clear()
            var result = 0L
            for (paren in line) {
                if (opening.contains(paren)) parens.add(paren)
                if (closing.contains(paren)) {
                    if (mapping[paren] != parens.last()) {
                        continue@checkLine
                    }
                    parens.removeLast()
                }
            }
            for (paren in parens.reversed()) {
                result = result * 5 + points[paren] as Int
            }
            resultList.add(result)
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
