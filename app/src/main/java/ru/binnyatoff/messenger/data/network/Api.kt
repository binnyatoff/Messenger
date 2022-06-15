package ru.binnyatoff.messenger.data.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.binnyatoff.messenger.data.network.models.query.UserLogin
import ru.binnyatoff.messenger.data.network.models.query.UserRegister
import ru.binnyatoff.messenger.data.network.models.query.getListUsers
import ru.binnyatoff.messenger.data.network.models.response.ListUsers
import ru.binnyatoff.messenger.data.network.models.response.Token


interface Api {

    @POST("login")
    suspend fun loginUser(@Body user: UserLogin): Response<Token>

    @POST("register")
    suspend fun registerUser(@Body user: UserRegister): Response<Token>

    @GET("list-user")
    suspend fun getListUsers(@Query(value = "login") login: String): Response<ListUsers>

    @GET("/test")
    suspend fun test(): Response<String>

}