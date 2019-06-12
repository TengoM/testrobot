package com.tengo.easyrobot.presenter

import com.tengo.easyrobot.ui.activity.main.QiPresenter
import com.tengo.easyrobot.ui.activity.main.MainView

object PresenterFactory {

    fun getMainPresenter(view: MainView): QiPresenter{
        return QiPresenterRobotImpl(view)
    }

}