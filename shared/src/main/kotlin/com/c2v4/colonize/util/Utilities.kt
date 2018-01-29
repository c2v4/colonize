package com.c2v4.colonize.util

fun checkArgument(condition:Boolean) {
    if(!condition) throw IllegalArgumentException()
}