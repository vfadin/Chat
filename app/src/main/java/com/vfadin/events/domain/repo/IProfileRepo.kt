package com.vfadin.events.domain.repo

import com.vfadin.events.domain.RequestResult
import com.vfadin.events.domain.entity.Profile

interface IProfileRepo {
    suspend fun getProfile(): RequestResult<Profile>
    suspend fun changeNickname(nickname: String): RequestResult<Unit>
    suspend fun changeName(name: String): RequestResult<Unit>
}