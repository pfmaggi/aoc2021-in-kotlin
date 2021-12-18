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

package com.pietromaggi.aoc2021.day15

import com.pietromaggi.aoc2021.readInput
import java.util.PriorityQueue

// Source: https://github.com/elizarov/AdventOfCode2021/blob/main/src/Day15_1.kt
fun part1(input: List<String>): Int {
    val a = input.map { line -> line.toCharArray().map(Char::digitToInt) }
    val n = a.size
    val m = a[0].size
    val d = Array(n) { IntArray(m) { Int.MAX_VALUE } }
    val v = Array(n) { BooleanArray(m) }
    fun relax(i: Int, j: Int, x: Int) {
        if (i !in 0 until n || j !in 0 until m) return
        d[i][j] = minOf(d[i][j], x + a[i][j])
    }
    d[0][0] = 0
    while (!v[n - 1][m - 1]) {
        var mx = Int.MAX_VALUE
        var mi = -1
        var mj = -1
        for (i in 0 until n) for (j in 0 until m) {
            if (!v[i][j] && d[i][j] < mx) {
                mx = d[i][j]
                mi = i
                mj = j
            }
        }
        v[mi][mj] = true
        relax(mi - 1, mj, mx)
        relax(mi + 1, mj, mx)
        relax(mi, mj - 1, mx)
        relax(mi, mj + 1, mx)
    }
    return d[n - 1][m - 1]
}

// Source: https://github.com/elizarov/AdventOfCode2021/blob/main/src/Day15_2_fast.kt
fun part2(input: List<String>): Int {
    val a0 = input.map { line -> line.toCharArray().map(Char::digitToInt) }
    val n0 = a0.size
    val m0 = a0[0].size
    val n = 5 * n0
    val m = 5 * m0
    val a = Array(n) { i ->
        IntArray(m) { j ->
            val k = i / n0 + j / m0
            (a0[i % n0][j % m0] + k - 1) % 9 + 1
        }
    }
    val d = Array(n) { IntArray(m) { Int.MAX_VALUE } }

    data class Pos(val i: Int, val j: Int, val x: Int)

    val v = Array(n) { BooleanArray(m) }
    val q = PriorityQueue(compareBy(Pos::x))
    fun relax(i: Int, j: Int, x: Int) {
        if (i !in 0 until n || j !in 0 until m || v[i][j]) return
        val xx = x + a[i][j]
        if (xx < d[i][j]) {
            d[i][j] = xx
            q += Pos(i, j, xx)
        }
    }
    d[0][0] = 0
    q.add(Pos(0, 0, 0))
    while (!v[n - 1][m - 1]) {
        val (i, j, x) = q.remove()
        if (v[i][j]) continue
        v[i][j] = true
        relax(i - 1, j, x)
        relax(i + 1, j, x)
        relax(i, j - 1, x)
        relax(i, j + 1, x)
    }
    return d[n - 1][m - 1]
}

fun main() {
    val input = readInput("Day15")
    println(part1(input))
    println(part2(input))
}
