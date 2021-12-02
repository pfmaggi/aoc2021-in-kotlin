fun main() {
    fun part1(input: List<String>) : Int {
        var position = 0
        var depth = 0
        input.forEach { line ->
            if (line.startsWith("forward")) {
                position += line.substring(8).toInt()
            } else if (line.startsWith("down")) {
                depth += line.substring(5).toInt()
            } else if (line.startsWith("up")) {
                depth -= line.substring(3).toInt()
            }

        }
        return depth * position
    }

    fun part2(input: List<String>) : Int {
        var position = 0
        var aim = 0
        var depth = 0
        input.forEach { line ->
            if (line.startsWith("forward")) {
                val value = line.substring(8).toInt()
                position += value
                depth += value * aim
            } else if (line.startsWith("down")) {
                aim += line.substring(5).toInt()
            } else if (line.startsWith("up")) {
                aim -= line.substring(3).toInt()
            }

        }
        return depth * position
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
