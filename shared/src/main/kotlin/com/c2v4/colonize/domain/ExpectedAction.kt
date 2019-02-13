package com.c2v4.colonize.domain

sealed class ExpectedAction {
    abstract fun isValid(action: Action): Boolean
}

class PlaceOcean(): ExpectedAction() {
    override fun isValid(action: Action): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
