package com.xl.wpendo.activities.chenyang.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.v4.app.FragmentActivity
import com.xl.wpendo.activities.chenyang.text.RichTextActivity


/**
 *created by chenyang
 *on 2019/7/19
 */

class RealPathFromUrlUtil (val context: Context, val uri:Uri){
    private val  CHOOSE_PHOTO = 2
    companion object {
        fun getRealPathFromUri(context: Context, uri: Uri): String {
            return ""
        }
    }

    fun RichTextActivity.openAlbum(){
            val intent = Intent("android.intent.action.GET_CONTENT")
            intent.setType("image/*")
            startActivityForResult(intent, CHOOSE_PHOTO)
    }




}
