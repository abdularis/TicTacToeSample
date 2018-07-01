package com.aar.app.tictactoe.game

const val BOARD_SIZE = 3
const val EMPTY_TOKEN = 0
private const val ARRAY_SIZE = BOARD_SIZE * BOARD_SIZE

class Board(val data: IntArray = IntArray(ARRAY_SIZE, { EMPTY_TOKEN })) {

    init {
        if (data.size != ARRAY_SIZE) {
            throw IllegalArgumentException(
                    "data array size must have exactly $ARRAY_SIZE elements, current size ${data.size}")
        }
    }

    val isFull: Boolean
        get() {
            for (cell in data) {
                if (cell == EMPTY_TOKEN) return false
            }
            return true
        }

    operator fun get(i: Int) = data[i]
    operator fun set(i: Int, newValue: Int) {
        data[i] = newValue
    }

    operator fun get(i: Int, j: Int) = data[getLinearIndex(i, j)]
    operator fun set(i: Int, j: Int, newValue: Int) {
        data[getLinearIndex(i, j)] = newValue
    }

    fun clear() {
        for (i in 0 until data.size) {
            data[i] = EMPTY_TOKEN
        }
    }

    fun emptyAt(i: Int, j: Int) = data[getLinearIndex(i, j)] == EMPTY_TOKEN

    private inline fun getLinearIndex(i: Int, j: Int) = (BOARD_SIZE * i) + j
}
