package com.xl.wpendo.activities.chenyang

import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.Spanned
import android.text.TextWatcher
import android.text.style.BackgroundColorSpan
import android.text.style.StrikethroughSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.xl.wpendo.R
import com.xl.wpendo.activities.BaseActivity
import com.xl.wpendo.activities.chenyang.myenums.typeface
import kotlinx.android.synthetic.main.activity_rich_text.*
import java.io.Flushable

class RichTextActivity :  BaseActivity(), View.OnClickListener {

    private var etSelectedStart :Int = 0
    private var etSelectedEnd :Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rich_text)

        btn_swich.setOnClickListener(this)
        tv_overstriking.setOnClickListener(this)
        tv_deleteline.setOnClickListener(this)
        tv_underline.setOnClickListener(this)
        tv_italic.setOnClickListener(this)
        et_test.addTextChangedListener(EdiWatcher())
    }


    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_swich ->
            {
                val inputString = et_test.text.toString()
                Log.d("******input", inputString+"")
                val spannable = Spannable.Factory.getInstance().newSpannable(inputString)
                val  backgroundColorSpan = BackgroundColorSpan(Color.parseColor("#ff0000"))
                spannable.setSpan(backgroundColorSpan,etSelectedStart,etSelectedEnd,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                et_test.setText(spannable)
            }

            R.id.tv_overstriking ->
            {

                etSelectedStart = et_test.selectionStart
                etSelectedEnd = et_test.selectionEnd
                val inputString = et_test.text.toString()
                val spannable = Spannable.Factory.getInstance().newSpannable(inputString)
                val  styleSpan = StyleSpan(Typeface.BOLD)
                spannable.setSpan(styleSpan,etSelectedStart,etSelectedEnd,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                et_test.setText(spannable)

//                val selected = inputString.substring(etSelectedStart,etSelectedEnd)
//                Log.d("******selected", selected+"")
            }

            R.id.tv_deleteline ->
            {
                etSelectedStart = et_test.selectionStart
                etSelectedEnd = et_test.selectionEnd
                val inputString = et_test.text.toString()
                val spannable = Spannable.Factory.getInstance().newSpannable(inputString)
                val  strikethroughSpan = StrikethroughSpan()
                spannable.setSpan(strikethroughSpan,etSelectedStart,etSelectedEnd,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                et_test.setText(spannable)
            }

            R.id.tv_underline ->
            {
                etSelectedStart = et_test.selectionStart
                etSelectedEnd = et_test.selectionEnd
                val inputString = et_test.text.toString()
                val spannable = Spannable.Factory.getInstance().newSpannable(inputString)
                val  underlineSpan = UnderlineSpan()
                spannable.setSpan(underlineSpan,etSelectedStart,etSelectedEnd,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                et_test.setText(spannable)
            }

            R.id.tv_italic ->
            {
                etSelectedStart = et_test.selectionStart
                etSelectedEnd = et_test.selectionEnd
                val inputString = et_test.text.toString()
                val spannable = Spannable.Factory.getInstance().newSpannable(inputString)
                val  italic = StyleSpan(Typeface.ITALIC)
                spannable.setSpan(italic,etSelectedStart,etSelectedEnd,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                et_test.setText(spannable)
            }
        }

    }
private inner class EdiWatcher : TextWatcher {

    override fun afterTextChanged(s: Editable?) {
//        etSelectedStart = et_test.selectionStart
//        etSelectedEnd = et_test.selectionEnd
//        Log.d("****after",etSelectedStart.toString()+"")
//        Log.d("****after",etSelectedEnd.toString()+"")
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//        etSelectedStart = et_test.selectionStart
//        etSelectedEnd = et_test.selectionEnd
//        Log.d("****before",etSelectedStart.toString()+"")
//        Log.d("****before",etSelectedEnd.toString()+"")

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//        etSelectedStart = et_test.selectionStart
//        etSelectedEnd = et_test.selectionEnd
//        Log.d("****onchange",etSelectedStart.toString()+"")
//        Log.d("****onchange",etSelectedEnd.toString()+"")
    }
}
}
