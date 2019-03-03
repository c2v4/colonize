package com.c2v4.colonize.domain

enum class Resource(val lowerLimitProduction:Int = 0) {
    HEAT, PLANTS, TITANIUM, STEEL, MEGA_CREDITS(-5), ENERGY;
}
