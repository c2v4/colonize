package com.c2v4.colonize.domain.action

sealed class Expression {

    abstract fun evaluate():Int

    class Count : Expression() {
        override fun evaluate(): Int {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    class Have : Expression() {
        override fun evaluate(): Int {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    class None : Expression(){
        override fun evaluate(): Int {
            return 1
        }
    }

}

