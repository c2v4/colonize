package com.c2v4.colonize.domain

import java.util.*

class Orchestrator(private var state: State = State(),private val actions:Deque<Action> = LinkedList()){

}