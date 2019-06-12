package com.tengo.easyrobot.utils

import com.aldebaran.qi.sdk.QiContext

interface Robot{
    fun say(sentence: String, qiContext: QiContext?)
    fun doDogAnimation(qiContext: QiContext?)
}