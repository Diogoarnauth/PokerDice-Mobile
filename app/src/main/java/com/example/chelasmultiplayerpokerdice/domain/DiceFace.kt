package com.example.chelasmultiplayerpokerdice.domain

sealed class DiceFace(val value: Int, val label: String) {
    data object Nine : DiceFace(9, "9")
    data object Ten : DiceFace(10, "10")
    data object Jack : DiceFace(11, "J")
    data object Queen : DiceFace(12, "Q")
    data object King : DiceFace(13, "K")
    data object Ace : DiceFace(14, "A")

    companion object {
        val allFaces: List<DiceFace> = listOf(Nine, Ten, Jack, Queen, King, Ace)
        fun random(): DiceFace = allFaces.random()
        fun fromLabel(label: String): DiceFace = allFaces.first { it.label == label }
    }
}

data class Die(
    val id: Int,
    val face: DiceFace,
    val isHeld: Boolean
)