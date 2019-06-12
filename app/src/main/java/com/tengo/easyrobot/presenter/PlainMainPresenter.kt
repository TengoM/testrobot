package com.tengo.easyrobot.presenter

import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.builder.AnimateBuilder
import com.aldebaran.qi.sdk.builder.AnimationBuilder
import com.aldebaran.qi.sdk.builder.SayBuilder
import com.aldebaran.qi.sdk.core.QiThreadPool
import com.tengo.easyrobot.R
import com.tengo.easyrobot.ui.activity.main.MainPresenter
import com.tengo.easyrobot.ui.activity.main.MainView
import com.tengo.mylibrary.JokeFactory
import com.tengo.mylibrary.api.JokeGenerator
import com.tengo.mylibrary.model.Joke


class PlainMainPresenter(private val view: MainView) : MainPresenter {

    private var qiContext: QiContext? = null
    private val jokeGenerator: JokeGenerator = JokeFactory.getJokeGenerator()

    override fun tellAJoke() {
        jokeGenerator.generate(object : JokeGenerator.Callback{
            override fun onSuccess(joke: Joke) {
                executeInsideQi {
                    say(joke.description, it)
                }
            }

            override fun onFailure(errorMessage: String) {
                view.displayText(errorMessage)
            }
        })

    }

    override fun sayHello() {
        executeInsideQi {
            say(view.getString(R.string.hello), it)
        }
    }

    override fun doDogAnimation() {
        executeInsideQi {
            val animation = AnimationBuilder.with(qiContext)
                    .withResources(R.raw.dog_a001)
                    .build()
            AnimateBuilder.with(it).withAnimation(animation).build().run()
        }
    }

    override fun onRobotFocusGained(qiContext: QiContext?) {
        this.qiContext = qiContext
        sayHello()
    }

    override fun onRobotFocusLost() {
        qiContext = null
    }

    override fun onRobotFocusRefused(reason: String?) {
        view.displayText(reason ?: "")
    }

    private fun say(sentence: String, qiContext: QiContext){
        val sayCommand = SayBuilder.with(qiContext).withText(sentence).build()
        sayCommand.async().run()
    }

    private fun executeInsideQi(func: (context: QiContext) -> Unit) {
        qiContext?.let {
            QiThreadPool.run<Any> {
                func(it)
            }
        }
    }
}
