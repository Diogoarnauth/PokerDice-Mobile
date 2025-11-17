package com.example.chelasmultiplayerpokerdice.game

import com.example.chelasmultiplayerpokerdice.domain.DiceFace

object GameLogic {
    fun calculateHandScore(diceFaces: List<DiceFace>): PlayerHand {
        require(diceFaces.size == 5) { "A mão tem de ter 5 dados" }

        val values = diceFaces.map { it.value }.sortedDescending()

        val counts = values.groupingBy { it }.eachCount()

        val groups =
            counts.entries
                .sortedWith(
                    compareByDescending<Map.Entry<Int, Int>> { it.value }
                        .thenByDescending { it.key },
                )

        val isStraight = isStraight(values)

        return when {
            groups.first().value == 5 -> PlayerHand(
                "Five of a Kind",
                900.0 + groups.first().key
            )

            groups.first().value == 4 -> {
                val quad = groups.first().key
                val kicker = groups.find { it.value == 1 }?.key ?: 0
                PlayerHand("Four of a Kind", 800.0 + quad * 10 + kicker)
            }

            groups.first().value == 3 && groups[1].value == 2 -> {
                val trio = groups.first().key
                val pair = groups[1].key
                PlayerHand("Full House", 700.0 + trio * 10 + pair)
            }

            isStraight -> PlayerHand("Straight", 600.0 + values.max())
            groups.first().value == 3 -> {
                val trio = groups.first().key
                val kickers = groups.filter { it.value == 1 }.map { it.key }.sortedDescending()
                PlayerHand("Three of a Kind", 500.0 + trio * 10 + kickers.sum())
            }

            groups.first().value == 2 && groups[1].value == 2 -> {
                val pairHigh = maxOf(groups[0].key, groups[1].key)
                val pairLow = minOf(groups[0].key, groups[1].key)
                val kicker = groups.find { it.value == 1 }?.key ?: 0
                PlayerHand("Two Pair", 400.0 + pairHigh * 10 + pairLow + kicker / 10.0)
            }

            groups.first().value == 2 -> {
                val pair = groups.first().key
                val kickers = groups.filter { it.value == 1 }.map { it.key }.sortedDescending()
                PlayerHand("One Pair", 300.0 + pair * 10 + kickers.sum())
            }

            else -> PlayerHand("Bust (High Card)", 200.0 + values.first())
        }
    }

    private fun isStraight(values: List<Int>): Boolean {
        val sorted = values.distinct().sorted()
        if (sorted.size != 5) return false
        val isLowStraight = sorted == listOf(9, 10, 11, 12, 13)
        val isHighStraight = sorted == listOf(10, 11, 12, 13, 14)
        return isLowStraight || isHighStraight
    }
}