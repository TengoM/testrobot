package com.tengo.mylibrary

import com.tengo.mylibrary.api.JokeGenerator
import com.tengo.mylibrary.api.JokeRetrofitService

object JokeFactory {
    fun getJokeGenerator(): JokeGenerator{
        return JokeRetrofitService(BuildConfig.BASE_URL)
    }
}