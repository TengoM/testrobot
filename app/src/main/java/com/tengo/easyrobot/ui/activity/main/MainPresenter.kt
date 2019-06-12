package com.tengo.easyrobot.ui.activity.main

import com.aldebaran.qi.sdk.RobotLifecycleCallbacks

interface MainPresenter: RobotLifecycleCallbacks {
    fun tellAJoke()
    fun sayHello()
    fun doDogAnimation()
}