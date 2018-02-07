package com.c2v4.colonize.domain

data class Card(val cost:Int = 0, val requirements: List<Requirement<Comparable<>>>)