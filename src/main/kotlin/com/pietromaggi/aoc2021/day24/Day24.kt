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

package com.pietromaggi.aoc2021.day24

import com.pietromaggi.aoc2021.readInput

enum class Op {
    INP,
    ADD_NUM,
    ADD_REG,
    MUL_NUM,
    MUL_REG,
    DIV_NUM,
    DIV_REG,
    MOD_NUM,
    MOD_REG,
    EQL,
    NEQ,
    LDR,
    CDM,
    YEX
}

data class Instruction(val op: Op, val reg: Int, val reg2: Int, val value: Int)

fun String.toReg(): Int =
    if (this.isEmpty()) 4 else
        when (this.lowercase().trimStart()[0]) {
            'w' -> 0
            'x' -> 1
            'y' -> 2
            'z' -> 3
            else -> 4
        }

fun String.decrement(): String {
    var newString = ""
    var carry = false
    var decrement = true
    var pos = 0
    for (i in this.length - 1 downTo 0) {
        val char = this[i]
        pos++
        var digit = char.digitToInt()
        if (decrement) digit -= 1
        if (carry) {
            carry = false
            if (pos == 9) println(this)
            digit -= 1
        }

        decrement = false

        if (digit == 0) {
            carry = true
            digit = 9
        }
        newString = digit.toString() + newString
        if (!carry) break
    }
    return substring(0, length - pos) + newString
}

fun String.increment(): String {
    val value = this.toLong() + 1
    if ((value % 100000000) == 0L) println(value)
    return value.toString().map { if (it == '0') '1' else it }.joinToString("")
}

fun parse(input: List<String>) = buildList {
    for (line in input) {
        if (line.isNotEmpty())
            when (line.substring(0..2)) {
                "inp" -> {
                    this.add(Instruction(Op.INP, line.substring(4).toReg(), 5, 0))
                }
                "add" -> {
                    if (line.substring(6).toReg() == 4) {
                        this.add(Instruction(Op.ADD_NUM,
                            line.substring(4..6).toReg(),
                            4,
                            line.substring(6).toInt()))
                    } else {
                        this.add(Instruction(Op.ADD_REG,
                            line.substring(4..6).toReg(),
                            line.substring(6).toReg(),
                            0))
                    }
                }
                "mul" -> {
                    if (line.substring(6).toReg() == 4) {
                        this.add(Instruction(Op.MUL_NUM,
                            line.substring(4..6).toReg(),
                            4,
                            line.substring(6).toInt()))
                    } else {
                        this.add(Instruction(Op.MUL_REG,
                            line.substring(4..6).toReg(),
                            line.substring(6).toReg(),
                            0))
                    }
                }
                "div" -> {
                    if (line.substring(6).toReg() == 4) {
                        this.add(Instruction(Op.DIV_NUM,
                            line.substring(4..6).toReg(),
                            4,
                            line.substring(6).toInt()))
                    } else {
                        this.add(Instruction(Op.DIV_REG,
                            line.substring(4..6).toReg(),
                            line.substring(6).toReg(),
                            0))
                    }
                }
                "mod" -> {
                    if (line.substring(6).toReg() == 4) {
                        this.add(Instruction(Op.MOD_NUM,
                            line.substring(4..6).toReg(),
                            4,
                            line.substring(6).toInt()))
                    } else {
                        this.add(Instruction(Op.MOD_REG,
                            line.substring(4..6).toReg(),
                            line.substring(6).toReg(),
                            0))
                    }
                }
                "eql" -> {
                    this.add(Instruction(Op.EQL,
                        line.substring(4..6).toReg(),
                        line.substring(6).toReg(),
                        if (line.substring(6).toReg() == 4) line.substring(6).toInt() else 0))
                }
                "neq" -> {
                    this.add(Instruction(Op.NEQ,
                        line.substring(4..6).toReg(),
                        line.substring(6).toReg(),
                        if (line.substring(6).toReg() == 4) line.substring(6).toInt() else 0))
                }
                "ldr" -> {
                    this.add(Instruction(Op.LDR,
                        line.substring(4..6).toReg(),
                        line.substring(6).toReg(),
                        if (line.substring(6).toReg() == 4) line.substring(6).toInt() else 0))
                }
                "cdm" -> {
                    this.add(Instruction(Op.CDM, line.substring(4..6).toReg(), line.substring(6).toReg(), 0))
                }
                "yex" -> {
                    this.add(Instruction(Op.YEX, line.substring(4..6).toReg(), line.substring(6).toReg(), 0))
                }
                else -> continue
            }
    }
}.toMutableList()

fun compute(program: List<Instruction>, number: String): Int {
    val regs = IntArray(4)
    var pos = 0

    fun eql(reg1: Int, reg2: Int, value: Int) {
        if (4 == reg2) {
            regs[reg1] = if (regs[reg1] == value) 1 else 0
        } else {
            regs[reg1] = if (regs[reg1] == regs[reg2]) 1 else 0
        }
    }

    fun neq(reg1: Int, reg2: Int, value: Int) {
        if (4 == reg2) {
            regs[reg1] = if (regs[reg1] == value) 0 else 1
        } else {
            regs[reg1] = if (regs[reg1] == regs[reg2]) 0 else 1
        }
    }

    fun ldr(reg1: Int, reg2: Int, value: Int) {
        if (4 == reg2) {
            regs[reg1] = value
        } else {
            regs[reg1] = regs[reg2]
        }
    }

    for (instruction in program) {
        when (instruction.op) {
            Op.INP -> regs[instruction.reg] = number[pos++].digitToInt()
            Op.ADD_NUM -> regs[instruction.reg] = regs[instruction.reg] + instruction.value
            Op.ADD_REG -> regs[instruction.reg] = regs[instruction.reg] + regs[instruction.reg2]
            Op.MUL_NUM -> regs[instruction.reg] = regs[instruction.reg] * instruction.value
            Op.MUL_REG -> regs[instruction.reg] = regs[instruction.reg] * regs[instruction.reg2]
            Op.DIV_NUM -> regs[instruction.reg] = regs[instruction.reg] / instruction.value
            Op.DIV_REG -> regs[instruction.reg] = regs[instruction.reg] / regs[instruction.reg2]
            Op.MOD_NUM -> regs[instruction.reg] = regs[instruction.reg] % instruction.value
            Op.MOD_REG -> regs[instruction.reg] = regs[instruction.reg] % regs[instruction.reg2]
            Op.EQL -> eql(instruction.reg, instruction.reg2, instruction.value)
            Op.NEQ -> neq(instruction.reg, instruction.reg2, instruction.value)
            Op.LDR -> ldr(instruction.reg, instruction.reg2, instruction.value)
            Op.CDM -> {
                regs[instruction.reg] = regs[instruction.reg2] % 26
                regs[instruction.reg2] = regs[instruction.reg2] / 26
            }
            Op.YEX -> {
                regs[instruction.reg] = 25 * regs[instruction.reg2] + 1
            }
        }
    }

    return regs[3]
}

fun part1(input: List<String>): Long {
    val program = parse(input)

    var number = "99999999999999"

    while (compute(program, number) != 0) {
        number = number.decrement()
    }
    return number.toLong()
}

fun part2(input: List<String>): Long {
    val program = parse(input)

    var number = "11111111111111"

    while (compute(program, number) != 0) {
        number = number.increment()
    }
    return number.toLong()
}


fun computeDirect(n: String) : Int {
    var z = 26 * (n[0].digitToInt() + 6)
    z += n[1].digitToInt() + 12
    z *= 26
    z += n[2].digitToInt() + 5
    var x = n[3].digitToInt() - 6
    var w = n[4].digitToInt()
    if (x != w) {
        z *= 26
        z += w + 7
    }
    z *= 26
    z += n[5].digitToInt()
    x = n[6].digitToInt()
    w = n[7].digitToInt()
    if (x != w) {
        z *= 26
        z += w + 12
    }
    x = n[8].digitToInt() + 7
    w = n[9].digitToInt()
    if (x != w) {
        z *= 26
        z += w + 13
    }
    w = n[10].digitToInt()
    x = z % 26 - 8
    z /= 26
    if (x != w) {
        z *= 26
        z += w + 10
    }
    w = n[11].digitToInt()
    x = z % 26 - 4
    z /= 26
    if (x != w) {
        z *= 26
        z += w + 11
    }
    w = n[12].digitToInt()
    x = z % 26 - 15
    z /= 26
    if (x != w) {
        z *= 26
        z += w + 9
    }
    w = n[13].digitToInt()
    x = z % 26 - 8
    z /= 26
    if (x != w) {
        z *= 26
        z += w + 9
    }

    return z
}

fun part2Fast() : Int {
    var number = "11111111111111"

    while (computeDirect(number) != 0) {
        number = number.increment()
    }
    println("My Solution is: $number")
    return number.toInt()
}

fun part1Fast(): Long {
    var number = "99999999999999"

    while (computeDirect(number) != 0) {
        number = number.decrement()
    }
    return number.toLong()
}

fun main() {
    val input = readInput("Day24_mod")

    println("""
    ### DAY 24 ###
    ==============
    What is the largest model number accepted by MONAD?
    --> ${part1Fast()}
    What is the smallest model number accepted by MONAD?
    --> ${part2Fast()}
    """)
}
