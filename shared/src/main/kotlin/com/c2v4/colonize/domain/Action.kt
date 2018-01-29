package com.c2v4.colonize.domain

data class Action(val prerequisite: Effect, val effect:Effect)