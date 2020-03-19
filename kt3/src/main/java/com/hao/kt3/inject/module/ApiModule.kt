package com.hao.kt3.inject.module

import com.hao.kt3.data.Api
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * @author Yang Shihao
 * @date 2018/10/23
 */
@Module(includes = arrayOf(NetworkModule::class))
class ApiModule {

    @Provides
    @Singleton
    internal fun provideApi(retrofit: Retrofit): Api = retrofit.create(Api::class.java)
}