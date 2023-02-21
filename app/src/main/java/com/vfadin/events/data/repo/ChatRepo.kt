package com.vfadin.events.data.repo

import com.squareup.moshi.Moshi
import com.vfadin.events.data.datasource.HttpChatRemoteDataSource
import com.vfadin.events.data.entity.home.ApiMessage
import com.vfadin.events.data.entity.login.toProfile
import com.vfadin.events.domain.RequestResult
import com.vfadin.events.domain.entity.Message
import com.vfadin.events.domain.entity.Profile
import com.vfadin.events.domain.entity.User
import com.vfadin.events.domain.entity.toMessage
import com.vfadin.events.domain.repo.IChatRepo
import com.vfadin.events.util.SharedPrefs
import io.socket.client.Socket
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import org.json.JSONObject

class ChatRepo(
    private val dataSource: HttpChatRemoteDataSource,
    private val sharedPreferences: SharedPrefs,
    private val socket: Socket,
) : IChatRepo {

    private val dataFlow = MutableSharedFlow<Message>(extraBufferCapacity = 128)

    override suspend fun getUser(roomId: Int): RequestResult<User> {
        return RequestResult.Success(User(-1, "", "", true))
    }

    override suspend fun getCurrentUser(): RequestResult<Profile> {
        return when(val response = dataSource.getProfile(
            token = "${sharedPreferences.restoreTokenType()} ${sharedPreferences.restoreToken()}"
        )) {
            is RequestResult.Success -> RequestResult.Success(response.result.data?.toProfile() ?: Profile())
            is RequestResult.Error -> RequestResult.Error(response.exception)
        }
    }

    override suspend fun getAllMessages(roomId: Int): RequestResult<List<Message>> {
        return when (val response = dataSource.getMessages(
            token = "${sharedPreferences.restoreTokenType()} ${sharedPreferences.restoreToken()}",
            chatId = roomId
        )) {
            is RequestResult.Success -> {
                RequestResult.Success(response.result.data?.map {
                    it.toMessage()
                } ?: emptyList())
            }
            is RequestResult.Error -> RequestResult.Error(response.exception)
        }
    }

    override suspend fun startSocket(roomId: Int): Flow<Message> {
        val jsonAdapter = Moshi.Builder().build().adapter(ApiMessage::class.java)
        socket.connect()
        socket.emit("join", roomId)
        socket.on("message") {
            println(it.first() as JSONObject)
            jsonAdapter.fromJson(it.first().toString())?.toMessage()?.let { message ->
                dataFlow.tryEmit(message)
            }
        }
        socket.on("error") { args ->
            println("Received event in my-room: ${args[0]}")
        }
        return dataFlow
    }
}