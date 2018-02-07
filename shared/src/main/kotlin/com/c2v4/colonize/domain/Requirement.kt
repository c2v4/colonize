package com.c2v4.colonize.domain

class  Requirement<in T : Comparable<T>>(private val selector: Selector<T>, private val condition: Condition<T>)