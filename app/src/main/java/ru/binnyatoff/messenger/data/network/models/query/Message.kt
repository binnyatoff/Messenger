package ru.binnyatoff.messenger.data.network.models.query

data class Message(
    val message: String,
    val dateSend: String,
    val owner: String
)