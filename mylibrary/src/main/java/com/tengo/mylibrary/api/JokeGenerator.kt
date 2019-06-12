package com.tengo.mylibrary.api

import com.tengo.mylibrary.model.Joke
import retrofit2.Callback

interface JokeGenerator {
    fun generate(callback: Callback)

    interface Callback{
        fun onSuccess(joke: Joke)
        fun onFailure(errorMessage: String)
    }
}