package com.tengo.easyrobot.ui.activity.main

import android.os.Bundle
import android.widget.Toast
import com.aldebaran.qi.sdk.QiSDK
import com.aldebaran.qi.sdk.design.activity.RobotActivity
import com.tengo.easyrobot.R
import com.tengo.easyrobot.presenter.PresenterFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : RobotActivity(), MainView {

    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupPresenter()
        setupClickListeners()
        QiSDK.register(this, presenter)
    }

    override fun onDestroy() {
        super.onDestroy()
        QiSDK.unregister(this, presenter)
    }

    override fun displayText(reason: String) = Toast.makeText(this, reason, Toast.LENGTH_SHORT).show()

    private fun setupPresenter() {
        presenter = PresenterFactory.getMainPresenter(this)
    }

    private fun setupClickListeners(){
        uJokeButton.setOnClickListener {
            presenter.tellAJoke()
        }
        uHelloButton.setOnClickListener {
            presenter.sayHello()
        }
        uDogAnimationButton.setOnClickListener {
            presenter.doDogAnimation()
        }
    }

}
