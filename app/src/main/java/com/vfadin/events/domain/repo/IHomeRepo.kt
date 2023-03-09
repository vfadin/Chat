package com.vfadin.events.domain.repo

import com.vfadin.events.domain.RequestResult
import com.vfadin.events.domain.entity.Chat
import com.vfadin.events.domain.entity.Profile
import com.vfadin.events.domain.entity.User

interface IHomeRepo {
    suspend fun getChats(): RequestResult<List<Chat>>
    suspend fun createChat(list: List<Int>): RequestResult<Unit>
    suspend fun getUsers(): RequestResult<List<User>>
}