package com.xl.wpendo.activities

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.view.WindowManager
import com.readystatesoftware.systembartint.SystemBarTintManager
import android.graphics.Color
import android.view.View


@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {
    private lateinit var tintManager: SystemBarTintManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initWindow()
    }
    private fun initWindow() {
        val window: Window = window
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            //解决4.0-5.0版本之间，页面包含EditText无法配适问题
            // create our manager instance after the content view is set
            tintManager = SystemBarTintManager(this)
            // enable status bar tint
            tintManager.isStatusBarTintEnabled = true
            // enable navigation bar tint
            tintManager.setNavigationBarTintEnabled(true)
            // 自定义状态栏的颜色
            tintManager.setStatusBarTintColor(resources.getColor(com.xl.wpendo.R.color.tintColor))
        }
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            // 解决[5.0-5.1.1]版本状态栏没有全透明的系统适配问题
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }
}