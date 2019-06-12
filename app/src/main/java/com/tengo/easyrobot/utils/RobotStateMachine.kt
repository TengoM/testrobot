package com.tengo.easyrobot.utils

import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.builder.AnimateBuilder
import com.aldebaran.qi.sdk.builder.AnimationBuilder
import com.aldebaran.qi.sdk.builder.SayBuilder
import com.aldebaran.qi.sdk.core.QiThreadPool
import com.tengo.easyrobot.R

enum class RobotStateMachine : Robot {

    WAITING_FOR_ROBOT {
        override fun say(sentence: String, qiContext: QiContext?) {}

        override fun doDogAnimation(qiContext: QiContext?) {}
    },

    CONNECTED_TO_ROBOT {
        override fun say(sentence: String, qiContext: QiContext?) {
            executeInsideQi(qiContext) {
                val sayCommand = SayBuilder.with(qiContext).withText(sentence).build()
                sayCommand.async().run()
            }
        }

        override fun doDogAnimation(qiContext: QiContext?) {
            executeInsideQi(qiContext) {
                val animation = AnimationBuilder.with(qiContext)
                    .withResources(R.raw.dog_a001)
                    .build()
                AnimateBuilder.with(it).withAnimation(animation).build().run()
            }
        }
    };

    fun executeInsideQi(qiContext: QiContext?, func: (context: QiContext) -> Unit) {
        qiContext?.let {
            QiThreadPool.run<Any> {
                func(it)
            }
        }
    }


}