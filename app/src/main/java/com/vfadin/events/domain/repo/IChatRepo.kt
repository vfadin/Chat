package com.vfadin.events.domain.repo

import com.vfadin.events.domain.RequestResult
import com.vfadin.events.domain.entity.Message
import com.vfadin.events.domain.entity.User
import kotlinx.coroutines.flow.Flow

interface IChatRepo {
    suspend fun getUser(roomId: Int) : RequestResult<User>
    suspend fun startSocket(roomId: Int): Flow<Message>
    suspend fun getAllMessages(roomId: Int): RequestResult<List<Message>>
}