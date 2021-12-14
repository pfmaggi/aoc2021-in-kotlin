fun main() {
    data class Rule(val a: Char, val b: Char)

    fun parseInput(input: List<String>): Pair<String, Map<Rule, Char>> {
        val template = input[0].trim()
        val rules = mutableMapOf<Rule, Char>()
        input.takeLastWhile { it.isNotBlank() }
            .map { line ->
                val (a, b) = line.split(" -> ")
                rules[Rule(a[0], a[1])] = b[0]
            }

        return Pair(template, rules)
    }

    // This builds the actual list... to slow for part2
//    fun part1(input: List<String>, steps: Int): Int {
//        val (template, rules) = parseInput(input)
//        var partial = template
//        for (step in 1..steps) {
//            val temp = listOf(partial[0].toString()) + partial.zipWithNext()
//                .map { (first, second) -> "${rules[Rule(first, second)]}$second" }
//            partial = temp.joinToString("")
//        }
//        val finalPolymer = partial.toList()
//        val polymerFrequency = mutableListOf<Int>()
//        for (element in finalPolymer.distinct()) {
//            polymerFrequency.add(frequency(finalPolymer, element))
//        }
//        polymerFrequency.sort()
//        return polymerFrequency.last() - polymerFrequency.first()
//    }

    fun compute(input: List<String>, steps: Int): Long {
        val (template, rules) = parseInput(input)
        var polymer = mutableMapOf<Rule, Long>()
        template.zipWithNext().forEach { (first, second) ->
            val rule = Rule(first, second)
            polymer[rule] = polymer.getOrDefault(rule, 0) + 1
        }
        for (step in 1..steps) {
            val temp = mutableMapOf<Rule, Long>()
            polymer.forEach { (rule, count) ->
                val newElement = rules[rule]!!
                val rule1 = Rule(rule.a, newElement)
                val rule2 = Rule(newElement, rule.b)
                temp[rule1] = temp.getOrDefault(rule1, 0) + count
                temp[rule2] = temp.getOrDefault(rule2, 0) + count
            }
            polymer = temp
        }
        val polymerFrequency = mutableMapOf<Char, Long>()
        polymer.forEach { (rule, value) ->
            polymerFrequency[rule.a] = polymerFrequency.getOrDefault(rule.a, 0) + value
            polymerFrequency[rule.b] = polymerFrequency.getOrDefault(rule.b, 0) + value
        }
        polymerFrequency[template.first()] = polymerFrequency[template.first()]!! + 1
        polymerFrequency[template.last()] = polymerFrequency[template.last()]!! + 1
        val finalCount = polymerFrequency.map { (_, value) -> value / 2 }.sorted()
        return finalCount.last() - finalCount.first()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day14_test")
    check(compute(testInput, 10) == 1588L)
    check(compute(testInput, 40) == 2188189693529L)

    val input = readInput("Day14")
    println(compute(input, 10))
    println(compute(input, 40))
}
