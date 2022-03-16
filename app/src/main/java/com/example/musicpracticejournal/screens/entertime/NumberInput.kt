package com.example.musicpracticejournal.screens.entertime

import android.content.Context
import android.util.AttributeSet
import android.widget.GridLayout
import com.example.musicpracticejournal.databinding.ViewNumberInputBinding


class NumberInput(context: Context, attrs: AttributeSet) : GridLayout(context, attrs) {

    private var _binding: ViewNumberInputBinding? = null
    private val binding get() = _binding!!

    var onInputListener: OnInputListener? = null

    interface OnInputListener {
        fun onInput(input: String)
        fun onDelete()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        _binding = ViewNumberInputBinding.bind(this)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        with(binding) {
            btn1.setOnClickListener { onButtonClick("1") }
            btn2.setOnClickListener { onButtonClick("2") }
            btn3.setOnClickListener { onButtonClick("3") }
            btn4.setOnClickListener { onButtonClick("4") }
            btn5.setOnClickListener { onButtonClick("5") }
            btn6.setOnClickListener { onButtonClick("6") }
            btn7.setOnClickListener { onButtonClick("7") }
            btn8.setOnClickListener { onButtonClick("8") }
            btn9.setOnClickListener { onButtonClick("9") }
            btn0.setOnClickListener { onButtonClick("0") }
            btn00.setOnClickListener { onButtonClick("00") }
            btnDelete.setOnClickListener { onDeleteClick() }
        }
    }

    private fun onButtonClick(value: String) {
        onInputListener?.onInput(value)
    }

    private fun onDeleteClick() {
        onInputListener?.onDelete()
    }
}