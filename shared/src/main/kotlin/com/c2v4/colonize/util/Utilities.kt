package com.c2v4.colonize.util

fun checkArgument(condition: Boolean) {
    if (!condition) throw IllegalArgumentException()
}

operator fun <T> Map<T, Int>.plus(other: Map<T, Int>) = keys.plus(other.keys).map {
    it to (getOrDefault(it,
            0) + other.getOrDefault(it, 0))
}.toMap()

operator fun <T> Map<T, Int>.minus(other: Map<T, Int>) = keys.plus(other.keys).map {
    it to (getOrDefault(it,
            0) - other.getOrDefault(it, 0))
}.toMap()
