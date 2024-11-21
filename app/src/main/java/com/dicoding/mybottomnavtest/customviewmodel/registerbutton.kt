package com.dicoding.mybottomnavtest.customviewmodel

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.dicoding.mybottomnavtest.R

class registerbutton : AppCompatButton {

    private fun init() {
        txtColor = ContextCompat.getColor(context, android.R.color.background_light)
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private var txtColor: Int = 0

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        setTextColor(txtColor)
        textSize = 18f
        gravity = Gravity.CENTER
        text = if(isEnabled) resources.getString(R.string.register) else resources.getString(R.string.register)
    }


}