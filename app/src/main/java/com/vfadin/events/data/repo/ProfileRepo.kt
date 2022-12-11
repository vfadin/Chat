package com.vfadin.events.data.repo

import com.vfadin.events.data.datasource.HttpChatRemoteDataSource
import com.vfadin.events.data.entity.login.toProfile
import com.vfadin.events.domain.RequestResult
import com.vfadin.events.domain.entity.Profile
import com.vfadin.events.domain.repo.IProfileRepo
import com.vfadin.events.util.SharedPrefs

class ProfileRepo(
    private val dataSource: HttpChatRemoteDataSource,
    private val sharedPreferences: SharedPrefs
) : IProfileRepo {
    override suspend fun getProfile(): RequestResult<Profile> {
        return when (val response = dataSource.getProfile(
            token = "${sharedPreferences.restoreTokenType()} ${sharedPreferences.restoreToken()}"
        )) {
            is RequestResult.Success -> {
                RequestResult.Success(response.result.data?.toProfile() ?: Profile())
            }
            is RequestResult.Error -> RequestResult.Error(response.exception)
        }
    }

    override suspend fun changeNickname(nickname: String): RequestResult<Unit> {
        return when (val response = dataSource.changeNickname(
            token = "${sharedPreferences.restoreTokenType()} ${sharedPreferences.restoreToken()}",
            nickname = nickname
        )) {
            is RequestResult.Success -> {
                RequestResult.Success(Unit)
            }
            is RequestResult.Error -> RequestResult.Error(response.exception)
        }
    }

    override suspend fun changeName(name: String): RequestResult<Unit> {
        return when (val response = dataSource.changeName(
            token = "${sharedPreferences.restoreTokenType()} ${sharedPreferences.restoreToken()}",
            name = name
        )) {
            is RequestResult.Success -> RequestResult.Success(Unit)
            is RequestResult.Error -> RequestResult.Error(response.exception)
        }
    }
}