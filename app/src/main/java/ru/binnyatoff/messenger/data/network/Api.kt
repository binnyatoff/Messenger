package ru.binnyatoff.messenger.data.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.binnyatoff.messenger.data.network.models.query.DialogWithOutKey
import ru.binnyatoff.messenger.data.network.models.query.UserLogin
import ru.binnyatoff.messenger.data.network.models.query.UserRegister
import ru.binnyatoff.messenger.data.network.models.response.Dialog
import ru.binnyatoff.messenger.data.network.models.response.ListUsers
import ru.binnyatoff.messenger.data.network.models.response.MessageDTO
import ru.binnyatoff.messenger.data.network.models.response.Token


interface Api {

    @POST("login")
    suspend fun loginUser(@Body user: UserLogin): Response<Token>

    @POST("register")
    suspend fun registerUser(@Body user: UserRegister): Response<Token>

    @GET("list-user")
    suspend fun getListUsers(@Query(value = "login") login: String): Response<ListUsers>

    @POST("add-dialog")
    suspend fun addDialog(@Body dialog: DialogWithOutKey)

    @GET("get-dialogs")
    suspend fun getDialogs(@Query(value = "login") login: String): Response<List<Dialog>>

    @GET("get-messages")
    suspend fun getMessages(@Query(value = "dialog_key") dialog_key: String): Response<List<MessageDTO>>

    @POST("send-message")
    suspend fun sendMessage(@Body messageDTO: MessageDTO): Response<String>

    @GET("/test")
    suspend fun test(): Response<String>

}