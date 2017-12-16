package com.qt.basep.domain.commands

interface Command<out T> {
    fun execute(): T
}