package com.master.projetmovies.misc

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes


//region * * * * * ViewGroup * * * * *

fun ViewGroup.inflate(@LayoutRes layout: Int): View {
    return LayoutInflater.from(context).inflate(layout, this, false)
}

//region ****** Duration for int ****
fun Int.toConversionDuration():String{
    val hours: Int = this / 60
    val minutes: Int = this.rem(60)
    return "${hours}h${minutes}"
}