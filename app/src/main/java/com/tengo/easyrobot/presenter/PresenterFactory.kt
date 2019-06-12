package com.tengo.easyrobot.presenter

import com.tengo.easyrobot.ui.activity.main.MainPresenter
import com.tengo.easyrobot.ui.activity.main.MainView

object PresenterFactory {

    fun getMainPresenter(view: MainView): MainPresenter{
        return PlainMainPresenter(view)
    }

}