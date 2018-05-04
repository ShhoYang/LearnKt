package com.hao.kt2.domain.commands

/**
 * @author Yang Shihao
 */
interface Command<out T> {
    fun execute(): T
}