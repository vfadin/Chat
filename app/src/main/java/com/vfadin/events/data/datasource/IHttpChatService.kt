package com.vfadin.events.data.datasource

import com.vfadin.events.data.entity.*
import com.vfadin.events.data.entity.forgotPassword.*
import com.vfadin.events.data.entity.home.*
import com.vfadin.events.data.entity.login.*
import com.vfadin.events.data.entity.profile.ApiChangeName
import com.vfadin.events.data.entity.profile.ApiChangeSurname
import com.vfadin.events.data.entity.profile.ApiChangeUsername
import retrofit2.http.*

interface IHttpChatService {

    @POST("/api/registration")
    suspend fun registration(@Body registration: ApiRegistration): ApiRegistrationResponse

    @POST("/api/login")
    suspend fun auth(@Body authPost: ApiAuth): ApiAuthResponse

    @POST("api/forgotpass")
    suspend fun sendResetPasswordEmail(@Body resetPassword: ApiResetPasswordSendEmail): ApiResetPasswordSendEmailResponse

    @PUT("newpass")
    suspend fun resetPassword(@Body resetPassword: ApiResetPassword): ApiResetPasswordResponse

    @GET("api/profile")
    suspend fun getProfile(@Header("authorization") token: String): ApiProfileResponse

    @GET("/api/getmessages/{id}")
    suspend fun getMessages(
        @Header("authorization") token: String,
        @Path("id") chatId: Int
    ): ApiMessagesResponse

    @POST("/api/startchat")
    suspend fun startChat(
        @Header("authorization") token: String,
        @Body startChat: ApiStartChatBody
    ): ApiStartChatResponse

    @GET("/api/getrooms")
    suspend fun getRooms(
        @Header("authorization") token: String
    ): ApiRoomsResponse

    @GET("/api/users")
    suspend fun getUsers(
        @Header("authorization") token: String
    ): ApiGetUsers

    @POST("/api/changeusername")
    suspend fun changeUsername(
        @Header("authorization") token: String,
        @Body changeUsername: ApiChangeUsername
    )

    @POST("/api/changename")
    suspend fun changeName(
        @Header("authorization") token: String,
        @Body name: ApiChangeName
    )

    @POST("/api/changesurname")
    suspend fun changeSurname(
        @Header("authorization") token: String,
        @Body name: ApiChangeSurname
    )
}
