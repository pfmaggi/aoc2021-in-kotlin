fun main() {
    var connections = mapOf<String, MutableList<String>>()

    fun buildPaths(path: List<String>, validPaths: MutableSet<List<String>>, canVisitTwice: String) {
        val currentCave = path.last()
        if (currentCave == "end") {
            validPaths += path
            return
        }
        val nextCavesToVisit = connections.getValue(currentCave).filter { cave ->
            (cave.isLargeCave) or
                    (cave !in (path)) or
                    ((cave == canVisitTwice) and (path.count { it == canVisitTwice } <= 1))
        }
        for (nextCave in nextCavesToVisit) {
            buildPaths(path + nextCave, validPaths, canVisitTwice)
        }
    }

    fun part1(): Int {
        val results = mutableSetOf<List<String>>()

        buildPaths(listOf("start"), results, "")
        return results.count()
    }

    fun part2(): Int {
        val results = mutableSetOf<List<String>>()

        for (smallCave in connections.keys.filter { cave -> (!cave.isLargeCave) && cave !in listOf("start", "end") }) {
            buildPaths(listOf("start"), results, smallCave)
        }
        return results.count()
    }

    // test if implementation meets criteria from the description, like:
    connections = buildConnections("Day12_test1")
    check(part1() == 19)
    check(part2() == 103)

    connections = buildConnections("Day12_test2")
    check(part1() == 226)
    check(part2() == 3509)

    connections = buildConnections("Day12")
    println(part1())
    println(part2())
}

private fun buildConnections(fileName: String) : Map<String, MutableList<String>>  {
    return buildMap {
        readInput(fileName).forEach { line ->
            val (cave1, cave2) = line.split("-")
            this.getOrPut(cave1) { mutableListOf() }.add(cave2)
            this.getOrPut(cave2) { mutableListOf() }.add(cave1)
        }
    }
}

val String.isLargeCave get() = this != lowercase()

