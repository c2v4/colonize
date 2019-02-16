package com.c2v4.colonize.domain.action.expectance

import com.c2v4.colonize.domain.action.Action

interface ExpectedAction {
    fun isValid(action: Action): Boolean
}

