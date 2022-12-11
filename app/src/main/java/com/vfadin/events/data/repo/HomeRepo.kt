package com.vfadin.events.data.repo

import com.vfadin.events.data.datasource.HttpChatRemoteDataSource
import com.vfadin.events.data.entity.home.ApiStartChatBody
import com.vfadin.events.domain.RequestResult
import com.vfadin.events.domain.entity.Chat
import com.vfadin.events.domain.entity.User
import com.vfadin.events.domain.entity.toChat
import com.vfadin.events.domain.repo.IHomeRepo
import com.vfadin.events.util.SharedPrefs

class HomeRepo(
    private val dataSource: HttpChatRemoteDataSource,
    private val sharedPreferences: SharedPrefs
) : IHomeRepo {
    override suspend fun getChats(): RequestResult<List<Chat>> {
        return when (val response = dataSource.getRooms(
            token = "${sharedPreferences.restoreTokenType()} ${sharedPreferences.restoreToken()}"
        )) {
            is RequestResult.Success -> {
                RequestResult.Success(response.result.data?.map { it.toChat() } ?: emptyList())
            }
            is RequestResult.Error -> {
                RequestResult.Error(response.exception)
            }
        }
    }

    override suspend fun createChat(list: List<Int>): RequestResult<Unit> {
        return when (val response = dataSource.startChat(
            token = "${sharedPreferences.restoreTokenType()} ${sharedPreferences.restoreToken()}",
            startChat = ApiStartChatBody(users = list),
        )) {
            is RequestResult.Success -> {
                RequestResult.Success(Unit)
            }
            is RequestResult.Error -> {
                RequestResult.Error(response.exception)
            }
        }
    }

    override suspend fun getUsers(): RequestResult<List<User>> {
        return RequestResult.Success(
            listOf(
                User(
                    id = 2,
                    name = "Vladislav",
                    avatar = "https://avatars.githubusercontent.com/u/1016365?v=4"
                ),
                User(
                    id = 1,
                    name = "Grisha",
                    avatar = "https://avatars.githubusercontent.com/u/1016365?v=4"
                ),
            )
        )
    }
}