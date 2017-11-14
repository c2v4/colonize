package com.c2v4.colonize.domain.action

sealed class Target {
    class Self : Target()
    class Others : Target()
    class All : Target()
}