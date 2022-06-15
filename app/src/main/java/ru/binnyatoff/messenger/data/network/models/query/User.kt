package ru.binnyatoff.messenger.data.network.models.query


data class UserLogin(
    val login: String,
    val password: String
)

data class UserRegister(
    val username:String,
    val login: String,
    val password: String
)