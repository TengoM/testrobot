package com.tengo.easyrobot.ui.activity.main

import com.aldebaran.qi.sdk.RobotLifecycleCallbacks

interface QiPresenter: RobotLifecycleCallbacks {
    fun tellAJoke()
    fun sayHello()
    fun doDogAnimation()
}