fun main() {
    fun compute(input: List<String>, days: Int): Long {
        var fishArray = LongArray(9)

        input[0].split(",").map(String::toInt).map {fish ->
            fishArray[fish] = fishArray[fish] + 1}

        repeat(days) {
            val newGeneration = LongArray(9)
            for (idx in fishArray.indices) {
                if (idx == 0) {
                    newGeneration[8] = fishArray[idx]
                    newGeneration[6] = newGeneration[6] + fishArray[idx]
                } else {
                    newGeneration[idx - 1] = newGeneration[idx - 1] + fishArray[idx]
                }
            }
            fishArray = newGeneration
        }
        return fishArray.sum()
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
