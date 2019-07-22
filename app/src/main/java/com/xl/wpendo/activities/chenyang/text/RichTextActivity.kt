package com.xl.wpendo.activities.chenyang.text

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentUris
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.text.*
import android.text.style.*
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.xl.wpendo.R
import com.xl.wpendo.activities.BaseActivity
import com.xl.wpendo.activities.chenyang.myenums.typeface
import kotlinx.android.synthetic.main.activity_rich_text.*
import java.io.Flushable
import java.util.jar.Manifest

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class RichTextActivity :  BaseActivity(), View.OnClickListener {

    private var etSelectedStart :Int = 0
    private var inputString:String = ""
    private var etSelectedEnd :Int = 0
    private var spannable:SpannableString ?= null
    private var textInstanceChange:Boolean = true
    private val  CHOOSE_PHOTO = 2
    private var imagePath = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rich_text)

        btn_openAlbum.setOnClickListener(this)
        tv_overstriking.setOnClickListener(this)
        tv_deleteline.setOnClickListener(this)
        tv_underline.setOnClickListener(this)
        tv_italic.setOnClickListener(this)

        et_test.addTextChangedListener(EdiWatcher())
    }


    override fun onClick(v: View?) {

        if(textInstanceChange){
            inputString = et_test.text.toString()
            spannable = SpannableString(inputString)
            textInstanceChange = false
        }

        etSelectedStart = et_test.selectionStart
        etSelectedEnd = et_test.selectionEnd

        when(v?.id) {
            R.id.btn_openAlbum ->
            {
                if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)!=
                        PackageManager.PERMISSION_GRANTED){

                    val s = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    ActivityCompat.requestPermissions(this,
                        s,1)
                }else{
                    openAlbum()
                }
            }

            R.id.tv_overstriking ->
            {

                val  styleSpan = StyleSpan(Typeface.BOLD)
                spannable!!.setSpan(styleSpan,etSelectedStart,etSelectedEnd,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            }

            R.id.tv_deleteline ->
            {
                val  strikethroughSpan = StrikethroughSpan()
                spannable!!.setSpan(strikethroughSpan,etSelectedStart,etSelectedEnd,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }

            R.id.tv_underline ->
            {
                val  underlineSpan = UnderlineSpan()
                spannable!!.setSpan(underlineSpan,etSelectedStart,etSelectedEnd,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }

            R.id.tv_italic ->
            {
                val  italic = StyleSpan(Typeface.ITALIC)
                spannable!!.setSpan(italic,etSelectedStart,etSelectedEnd,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }
        et_test.setText(spannable)

    }
private inner class EdiWatcher : TextWatcher {

    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }
}

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            1 ->
                when(grantResults.size>0&&grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    true ->
                        openAlbum()
                }

        }
    }

    private fun openAlbum(){
        val intent = Intent("android.intent.action.GET_CONTENT")
        intent.setType("image/*")
        startActivityForResult(intent, CHOOSE_PHOTO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        when(requestCode){
            CHOOSE_PHOTO ->
            {
                if(resultCode.equals(Activity.RESULT_OK)){
                    if(Build.VERSION.SDK_INT >= 19) handleImageOnKitKat(data)
                    else handleImageBeforeKitkat(data)
                }
                richText.insertImage(imagePath)
            }
        }
    }


    @SuppressLint("NewApi")
    private fun handleImageOnKitKat(data: Intent?) {
        val uri = data?.data

        if (DocumentsContract.isDocumentUri(this, uri)){
            //如果是document类型的uri， 则通过document id进行处理
            val docId = DocumentsContract.getDocumentId(uri)
            when(uri?.authority){
                "com.android.providers.media.documents" ->
                {
                    //解析出数字格式的id
                    val id = docId.split(":")[1]
                    val selection = MediaStore.Images.Media._ID + "=" + id
                    setImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection)

                }

                "com.android.providers.downloads.documents" ->
                {
                    val contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), docId as Long)
                    setImagePath(contentUri, "")
                }
            }
        }else if("content".equals(uri?.authority, true))
            //content类型的Uri，采用普通方式处理
            setImagePath(uri!!, "")

        else if("file".equals(uri?.authority, true))
            //file类型的Uri，直接获取图片路径即可
            imagePath = uri!!.path

    }

    private fun handleImageBeforeKitkat(data: Intent?) {
        val uri = data?.data
        setImagePath(uri!!, "")
    }

    private fun setImagePath(uri: Uri, selection: String) {
        var path = ""
        //通过Uri和selection来获取真实的图片路径
        val coursor = contentResolver.query(uri,null,selection,null,null)

        if(coursor!=null){
            if(coursor.moveToFirst()){
                path = coursor.getString(coursor.getColumnIndex(MediaStore.Images.Media.DATA))
            }
            coursor.close()
        }
        imagePath = path
    }
}