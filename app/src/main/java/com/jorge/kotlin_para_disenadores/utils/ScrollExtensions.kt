package com.jorge.kotlin_para_disenadores.utils

import android.view.View
import android.widget.ScrollView

fun ScrollView.scrollHastaArribaDelTodo() {
    post {
        fullScroll(View.FOCUS_UP)
        smoothScrollTo(0, 0)
    }
}