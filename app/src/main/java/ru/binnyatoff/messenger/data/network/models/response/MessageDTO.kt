package ru.binnyatoff.messenger.data.network.models.response

data class MessageDTO(
    val dialog_key: String,
    val message_owner: String,
    val message: String
)