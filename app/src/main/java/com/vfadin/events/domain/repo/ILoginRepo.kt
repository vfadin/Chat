package com.vfadin.events.domain.repo

import com.vfadin.events.domain.RequestResult
import com.vfadin.events.domain.entity.Profile
import com.vfadin.events.domain.entity.login.LoginResponse
import com.vfadin.events.domain.entity.login.RegistrationResponse

interface ILoginRepo {
    suspend fun login(login: String, password: String): RequestResult<LoginResponse>
    suspend fun registration(
        login: String,
        password: String,
        passwordConfirmation: String,
        email: String
    ): RequestResult<RegistrationResponse>
}