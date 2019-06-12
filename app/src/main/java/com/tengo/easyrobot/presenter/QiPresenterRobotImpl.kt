package com.tengo.easyrobot.presenter

import com.aldebaran.qi.sdk.QiContext
import com.tengo.easyrobot.R
import com.tengo.easyrobot.ui.activity.main.QiPresenter
import com.tengo.easyrobot.ui.activity.main.MainView
import com.tengo.easyrobot.utils.RobotStateMachine
import com.tengo.mylibrary.JokeFactory
import com.tengo.mylibrary.api.JokeGenerator
import com.tengo.mylibrary.model.Joke


class QiPresenterRobotImpl(private val view: MainView) : QiPresenter {

    private var qiContext: QiContext? = null
    private val jokeGenerator: JokeGenerator = JokeFactory.getJokeGenerator()
    private var robot = RobotStateMachine.WAITING_FOR_ROBOT

    override fun tellAJoke() {
        jokeGenerator.generate(object : JokeGenerator.Callback {
            override fun onSuccess(joke: Joke) {
                robot.say(joke.description, qiContext)
            }
            override fun onFailure(errorMessage: String) {
                view.displayText(errorMessage)
            }
        })
    }

    override fun sayHello() {
        robot.say(view.getString(R.string.hello), qiContext)
    }

    override fun doDogAnimation() {
        robot.doDogAnimation(qiContext)
    }

    override fun onRobotFocusGained(qiContext: QiContext?) {
        this.qiContext = qiContext
        robot = RobotStateMachine.CONNECTED_TO_ROBOT
    }

    override fun onRobotFocusLost() {
        qiContext = null
        robot = RobotStateMachine.WAITING_FOR_ROBOT
    }

    override fun onRobotFocusRefused(reason: String?) {
        view.displayText(reason ?: "")
        robot = RobotStateMachine.WAITING_FOR_ROBOT
    }
}
