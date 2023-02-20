package com.vfadin.events.di

import android.content.Context
import com.vfadin.events.data.datasource.IHttpChatService
import com.vfadin.events.data.datasource.HttpChatRemoteDataSource
import com.vfadin.events.data.network.INetwork
import com.vfadin.events.data.network.Network
import com.vfadin.events.data.network.SupportInterceptor
import com.vfadin.events.data.repo.*
import com.vfadin.events.domain.repo.*
import com.vfadin.events.util.SharedPrefs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.components.SingletonComponent
import io.socket.client.IO
import io.socket.client.Socket
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPrefModule {

    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPrefs {
        return SharedPrefs(context)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideSupportInterceptor(): SupportInterceptor {
        return SupportInterceptor()
    }

    @Provides
    fun provideNetwork(supportInterceptor: SupportInterceptor): INetwork {
        return Network(supportInterceptor)
    }

    @Provides
    fun providePfdoService(network: INetwork): IHttpChatService {
        return network.retrofit.create(
            IHttpChatService::class.java
        )
    }

}

@Module
@InstallIn(SingletonComponent::class)
object SocketModule {

    @Provides
    fun provideSocket(@ApplicationContext context: Context): Socket {
        val options = IO.Options.builder()
            .setForceNew(true)
            .setReconnection(true)
            .setExtraHeaders(mapOf("token" to listOf(SharedPrefs(context).restoreToken())))
            .build()
        return IO.socket("http://194.147.115.205:3000", options)
    }
}

@Module
@InstallIn(ActivityRetainedComponent::class)
object DataSourceModule {

    @ActivityRetainedScoped
    fun provideHttpChatRemoteDataSource(api: IHttpChatService): HttpChatRemoteDataSource {
        return provideHttpChatRemoteDataSource(api)
    }
}

@Module
@InstallIn(ActivityRetainedComponent::class)
object RepositoryModule {

    @Provides
    @ActivityRetainedScoped
    fun provideLoginRepo(
        dataSource: HttpChatRemoteDataSource,
        sharedPrefs: SharedPrefs,
    ): ILoginRepo {
        return LoginRepo(dataSource, sharedPrefs)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideForgotPasswordRepo(dataSource: HttpChatRemoteDataSource): IForgotPasswordRepo {
        return ForgotPasswordRepo(dataSource)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideHomeRepo(dataSource: HttpChatRemoteDataSource, sharedPrefs: SharedPrefs): IHomeRepo {
        return HomeRepo(dataSource, sharedPrefs)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideChatRepo(
        socket: Socket,
        dataSource: HttpChatRemoteDataSource,
        sharedPrefs: SharedPrefs,
    ): IChatRepo {
        return ChatRepo(dataSource, sharedPrefs, socket)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideProfileRepo(
        dataSource: HttpChatRemoteDataSource,
        sharedPrefs: SharedPrefs,
    ): IProfileRepo {
        return ProfileRepo(dataSource, sharedPrefs)
    }
}