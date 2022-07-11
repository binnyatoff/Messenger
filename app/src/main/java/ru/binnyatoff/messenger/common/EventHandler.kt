package ru.binnyatoff.messenger.common

interface EventHandler<T> {
    fun obtainEvent(event: T)
}

interface ActionHandler<T>{
    fun obtainAction(action: T)
}