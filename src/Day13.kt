fun main() {
    data class Dot(val x: Int, val y:Int)
    data class FoldInfo(val type: Char, val cord: Int)

    fun parseInput(input: List<String>): Pair<MutableSet<Dot>, MutableList<FoldInfo>> {
        val dots = mutableSetOf<Dot>()
        val folds = mutableListOf<FoldInfo>()
        var readDotsCoords = true
        for (line in input) {
            if (line.isBlank()) readDotsCoords = false else {
                if (readDotsCoords) {
                    val (x, y) = line.split(",").map(String::toInt)
                    dots.add(Dot(x, y))
                } else {
                    val (axes, value) = line.split("=")

                    folds.add(FoldInfo(axes.trimEnd().last(), value.toInt()))
                }
            }
        }
        return Pair(dots, folds)
    }

    fun doFold(foldInfo: FoldInfo, dots: Set<Dot>): Set<Dot> {
        return if (foldInfo.type == 'x') {
            // fold is vertical
            buildSet {
                for (dot in dots) {
                    this.add(
                        Dot(
                            (
                                    if (dot.x > foldInfo.cord) {
                                        foldInfo.cord * 2 - dot.x
                                    } else dot.x), dot.y
                        )
                    )
                }
            }
        } else {
            // fold is horizontal
            buildSet {
                for (dot in dots) {
                    this.add(
                        Dot(
                            dot.x,
                            if (dot.y > foldInfo.cord) {
                                foldInfo.cord * 2 - dot.y
                            } else dot.y
                        )
                    )
                }
            }
        }
    }

    fun buildCode(dots: Set<Dot>) : List<String> {
        val sizeX = dots.maxOf { it.x }
        val sizeY = dots.maxOf { it.y }
        return buildList {
            for (y in 0..sizeY) {
                var line = ""
                for (x in 0..sizeX) {
                    line += if (dots.contains(Dot(x, y))) "#" else " "
                }
                this.add(line)
            }
        }

    }

    fun part1(input: List<String>): Int {
        val (dots, folds) = parseInput(input)

        // evaluate just the first fold
        return doFold(folds[0], dots).size
    }

    fun part2(input: List<String>): List<String> {
        val (dots, folds) = parseInput(input)

        var foldedDots = dots as Set<Dot>
        folds.forEach { foldInfo ->
            foldedDots = doFold(foldInfo, foldedDots)
        }

        return buildCode(foldedDots)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day13_test")
    check(part1(testInput) == 17)
    val part2TestResult = listOf(
        "#####",
        "#   #",
        "#   #",
        "#   #",
        "#####"
    )
    check(part2(testInput) == part2TestResult)

    val input = readInput("Day13")
    println(part1(input))
    for (line in part2(input)) {
        println(line)
    }
}
