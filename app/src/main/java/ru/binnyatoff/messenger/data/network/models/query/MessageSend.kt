package ru.binnyatoff.messenger.data.network.models.query

data class MessageSend(
    val message: String,
    val dateSend: String,
    val owner: String
)