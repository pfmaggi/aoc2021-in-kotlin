/*
 * Copyright 2021 Pietro F. Maggi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pietromaggi.aoc2021.day21

import com.pietromaggi.aoc2021.readInput

data class Player(var position: Int, var score: Int)

fun part1(input: List<String>): Int {
    val player = Array(2) {
        Player(input[it].removePrefix("Player ${it + 1} starting position: ").toInt() - 1, 0)
    }

    val dice = generateSequence(1) { if (it < 100) it + 1 else 1 } // `it` is the previous element
    var count = 0
    val result: Int
    while (true) {
        var increment = dice.drop(3 * count).take(3).sum()
        count++
        player[0].position = (player[0].position + increment) % 10
        player[0].score = player[0].position + 1 + player[0].score
        if (player[0].score >= 1000) {
            result = count * 3 * player[1].score
            break
        }
        increment = dice.drop(3 * count).take(3).sum()
        count++
        player[1].position = (player[1].position + increment) % 10
        player[1].score = player[1].position + 1 + player[1].score
        if (player[1].score >= 1000) {
            result = count * 3 * player[0].score
            break
        }
    }
    return result
}

fun part2(input: List<String>): Long {
    val p = IntArray(2)
    for (i in 0..1) {
        p[i] = input[i].removePrefix("Player ${i + 1} starting position: ").toInt()
    }

    data class WC(var w1: Long, var w2: Long)

    val dp = Array(11) { Array(11) { Array(21) { arrayOfNulls<WC>(21) } } }

    fun find(p1: Int, p2: Int, s1: Int, s2: Int): WC {
        dp[p1][p2][s1][s2]?.let { return it }
        val c = WC(0, 0)
        for (d1 in 1..3) for (d2 in 1..3) for (d3 in 1..3) {
            val p1n = (p1 + d1 + d2 + d3 - 1) % 10 + 1
            val s1n = s1 + p1n
            if (s1n >= 21) {
                c.w1++
            } else {
                val cn = find(p2, p1n, s2, s1n)
                c.w1 += cn.w2
                c.w2 += cn.w1
            }
        }
        dp[p1][p2][s1][s2] = c
        return c
    }

    val c = find(p[0], p[1], 0, 0)
    return maxOf(c.w1, c.w2)
}

fun main() {
    val input = readInput("Day21")
    println("### DAY 21 ###")
    println("what do you get if you multiply the score of the losing player by the number of times the die was rolled during the game?")
    println("My puzzle answer is: ${part1(input)}")
    println("Find the player that wins in more universes; in how many universes does that player win?")
    println("My puzzle answer is: ${part2(input)}")
}