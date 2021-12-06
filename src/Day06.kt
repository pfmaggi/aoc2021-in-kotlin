fun main() {
    fun compute(input: List<String>, days: Int) : Long {
        val firstGeneration = input[0].split(",").map(String::toInt)
        var fishMap = mutableMapOf<Int, Long>()
        firstGeneration.forEach { fish ->
            fishMap[fish] = fishMap.getOrDefault(fish, 0)+1
        }
        repeat(days) {
            val newGeneration = mutableMapOf<Int, Long>()
            fishMap.forEach { fish ->
                if (fish.key == 0) {
                    newGeneration[8] = fish.value
                    newGeneration[6] = newGeneration.getOrDefault(6, 0)+fish.value
                } else {
                    newGeneration[fish.key - 1] = newGeneration.getOrDefault(fish.key-1, 0)+fish.value
                }
            }
            fishMap = newGeneration
        }
        var result = 0L
        fishMap.forEach {
            result += it.value
        }
        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(compute(testInput, 18) == 26L)
    check(compute(testInput, 80) == 5934L)
    check(compute(testInput, 256) == 26984457539L)

    val input = readInput("Day06")
    println(compute(input, 80))
    println(compute(input, 256))
}
