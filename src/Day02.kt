enum class Command {
    FORWARD,
    DOWN,
    UP
}

fun main() {
    data class Instruction(val command: Command, val value: Int)

    fun String.toCommand() : Command {
        val command = when(this) {
            "forward" -> Command.FORWARD
            "down" -> Command.DOWN
            "up" -> Command.UP
            else -> throw IllegalArgumentException("Invalid command")
        }
        return command
    }

    fun List<String>.toInstructions() : List<Instruction> {
        return this.map { line ->
            val (command, value) = line.split(" ")
            Instruction(command.toCommand(), value.toInt())
        }
    }

    fun part1(input: List<String>) : Int {
        val instructions = input.toInstructions()
        var position = 0
        var depth = 0
        for ((command, value) in instructions) {
            when (command) {
                Command.FORWARD -> position += value
                Command.DOWN -> depth += value
                Command.UP -> depth -= value
            }
        }
        return depth * position
    }

    fun part2(input: List<String>) : Int {
        val instructions = input.toInstructions()
        var position = 0
        var aim = 0
        var depth = 0
        for ((command, value) in instructions) {
            when (command) {
                Command.FORWARD -> {
                    position += value
                    depth += value * aim
                }
                Command.DOWN -> aim += value
                Command.UP -> aim -= value
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
