package com.xl.wpendo.chenyang.text

import android.animation.LayoutTransition
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.xl.wpendo.R

/**
 *created by chenyang
 *on 2019/7/20
 */

class RichText(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : ScrollView(context, attrs, defStyleAttr) {

    private val EDIT_PADDING = 10.0f
    private val EDIT_FIRST_PADDING_TOP = 10.0f

    private var viewTagIndex = 1
    private var linearLayout: LinearLayout? = null //所有子view的容器，scrollView内部唯一的ViewGroup
    private var inflater: LayoutInflater? = null
    private var keyListener: OnKeyListener? = null //EditText的软件监听器
    private var btnListener: OnClickListener? = null //图片上的删除按钮的监听器
    private var focusListener: OnFocusChangeListener? = null //所有EdiiText的焦点监听器
    private var lasFocusEdit: EditText? = null //最近被聚焦的EditText
    private var translationer: LayoutTransition? = null //在图片添加或者删除时，出发transtion

    private var editNomalPadding = 0
    private var disappearingImageIndex = 0


    constructor(context: Context, attrs: AttributeSet) :this(context,attrs,0)
    constructor(context: Context):this(context, null, 0)
    init {

        inflater = LayoutInflater.from(context)

        linearLayout = LinearLayout(context)
        linearLayout!!.orientation = LinearLayout.VERTICAL
        linearLayout!!.setBackgroundColor(Color.BLUE)
        setupLayoutTransitions()
        val layoutParams = ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        addView(linearLayout, layoutParams)
        //linearLayout.addView()

        //初始化键盘退格监听
        //用来处理点击删除按钮时，view的一系列操作
        keyListener = object : OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if (event?.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_DEL) {
                    val edit = v as EditText
                    onBackspacePress(edit)
                }
                return false
            }
        }

        //图片删除处理
        btnListener = object : OnClickListener {
            override fun onClick(v: View?) {
                val parentView = v?.parent as RelativeLayout
                onImageCloseClick(parentView)
            }
        }

        focusListener = object : OnFocusChangeListener {
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                if (hasFocus) lasFocusEdit = v as EditText
            }
        }

        val firEditParam = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        editNomalPadding = dip2px(EDIT_PADDING)
        val firstEdit = creatEditText("input here", dip2px(EDIT_FIRST_PADDING_TOP))
        linearLayout!!.addView(firstEdit, firEditParam)
        lasFocusEdit = firstEdit
    }

    /**
     * 初始化transition动画
     */
    private fun setupLayoutTransitions() {

        translationer = LayoutTransition()
        linearLayout?.layoutTransition = translationer

        translationer?.addTransitionListener(object : LayoutTransition.TransitionListener {
            override fun startTransition(
                transition: LayoutTransition?,
                container: ViewGroup?,
                view: View?,
                transitionType: Int
            ) {}
            override fun endTransition(
                transition: LayoutTransition?,
                container: ViewGroup?,
                view: View?,
                transitionType: Int
            ) {
                if (!transition?.isRunning!! && transitionType == LayoutTransition.CHANGE_DISAPPEARING) {
                    //transition动画结束，合并EditText
                    mergeEditText()
                }
            }
        })

        translationer?.setDuration(300)
    }

    /**
     * 删除图片时，如果上下方都是EditText，则合并处理
     */
    private fun mergeEditText() {
        val preView = linearLayout?.getChildAt(disappearingImageIndex - 1)
        val nextView = linearLayout?.getChildAt(disappearingImageIndex)
        if (preView != null && preView is EditText
            && nextView != null && nextView is EditText
        ) {
            val str1 = preView.text.toString()
            val str2 = nextView.text.toString()
            val mergeStr: String

            if (str2.length > 0) mergeStr = str1 + "\n" + str2
            else mergeStr = str1


            linearLayout?.layoutTransition = null
            linearLayout?.removeView(nextView)
            preView.setText(mergeStr)
            preView.requestFocus()
            preView.setSelection(str1.length, str2.length)
            linearLayout?.layoutTransition = translationer
        }

    }


    /**
     * 生成文本框
     */
    @SuppressLint("InflateParams")
    private fun creatEditText(hint: String, paddingTop: Int): EditText {

        val editText = inflater?.inflate(R.layout.edit_item, null) as EditText
        editText.setOnKeyListener(keyListener)
        editText.setTag(viewTagIndex++)
        editText.setPadding(editNomalPadding, paddingTop, editNomalPadding, 0)
        editText.hint = hint
        editText.setOnFocusChangeListener(focusListener)
        return editText
    }

    /**
     * 处理软键盘backSpace回退事件
     *
     * @param editText 光标所在的EditText
     */
    @SuppressLint("SetTextI18n")
    private fun onBackspacePress(editText: EditText) {
        val startSelection = editText.selectionStart
        //当且仅当光标到达文本框的最前端，在判断是否删除之前的图片，或者合并两个view
        if (startSelection == 0) {
            val editIndex = linearLayout?.indexOfChild(editText)
            val preView = linearLayout?.getChildAt(editIndex!! - 1)

            //如果editIndex - 1 < 0 ,则不进行下面的操作
            if (null != preView) {
                if (preView is RelativeLayout)
                //光标EditText的上一个view对应的是图片,相当于点击删除
                    onImageCloseClick(preView)
                else if (preView is EditText) {
                    //光标EditText的上一个view对应的是EditText
                    val str1 = editText.text.toString()
                    val preEdit = preView
                    val str2 = preEdit.text.toString()

                    //合并文本view时，不需要transition动画
                    linearLayout?.layoutTransition = null
                    linearLayout?.removeView(editText)
                    //恢复transition动画
                    linearLayout?.layoutTransition = translationer


                    //文本合并
                    preEdit.setText(str2 + str1)
                    preEdit.requestFocus()
                    preEdit.setSelection(str2.length, str1.length)
                    lasFocusEdit = preEdit
                }
            }
        }
    }


    fun insertImage(imagePath: String) {
        val bitmap = getScaledBitmap(imagePath, width)
        insertImage(bitmap, imagePath)
    }

//****************************************************************************************
    /**
     * 插入图片
     */
    private fun insertImage(bitmap: Bitmap, imagePath: String) {
        val lastEditstr = lasFocusEdit!!.text.toString()
        val cursorIndex: Int = lasFocusEdit!!.selectionStart
        val editStr1 = lastEditstr.substring(0, cursorIndex).trim() //EditText的开头到光标位置的String
        val lastEditIndex = linearLayout!!.indexOfChild(lasFocusEdit)

        if (lastEditstr.length == 0 || editStr1.length == 0) {
            //如果EditText已经为空，或者光标到了editText的最前面，则直接插入图片，并下移EditText
            addImageViewAtIndex(lastEditIndex, bitmap, imagePath)
        } else {
            //如果EditText非空且光标不在最顶端， 则需要添加新的imageView和EditText
            lasFocusEdit!!.setText(editStr1)
            val editStr2 = lastEditstr.substring(cursorIndex).trim()

            if (linearLayout!!.childCount - 1 == lastEditIndex || editStr2.length > 0) {
                addEditTextAtIndex(lastEditIndex + 1, editStr2)
            }

            addImageViewAtIndex(lastEditIndex + 1, bitmap, imagePath)
            lasFocusEdit!!.requestFocus()
            lasFocusEdit!!.setSelection(editStr1.length, editStr2.length)

        }

        hideKeyBoard()
    }

    /**
     * 隐藏小键盘
     */

    private fun hideKeyBoard() {
        val inputMethodMannager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodMannager.hideSoftInputFromWindow(lasFocusEdit!!.windowToken, 0)
    }


//**************************************************************************************

    /**
     * 在特定位置插入EditText
     */
    private fun addEditTextAtIndex(index: Int, editStr: String) {
        val editText = creatEditText("", resources.getDimensionPixelSize(R.dimen.edit_padding_top))
        editText.setText(editStr)

        //EditText添加或者删除，不触碰Transition动画
        linearLayout!!.layoutTransition = null
        linearLayout!!.addView(editText, index)
        //remove以后恢复transition动画
        linearLayout!!.layoutTransition = translationer

    }


    /**
     * 在特定的位置添加图片
     */
    private fun addImageViewAtIndex(lastEditIndex: Int, bitmap: Bitmap, imagePath: String) {

        val imageLayout = creatImageLayout()
        val imageview = imageLayout.findViewById<ImageView>(R.id.iv_editImage)
        imageview.setImageBitmap(bitmap)
        imageview.setTag(imagePath)

        //调整imageView的高度
        val imageHeight = width * bitmap.height / bitmap.width
        val layoutParams = RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, imageHeight)
        imageview.layoutParams = layoutParams

        //onActivityResult无法触发动画， 此处post处理
        linearLayout?.postDelayed(object : Runnable {
            override fun run() {
                linearLayout!!.addView(imageLayout, lastEditIndex)
            }

        }, 200)
    }

    /**
     * 创建图片View
     */
    @SuppressLint("InflateParams")
    private fun creatImageLayout(): RelativeLayout {
        val relativeLayout = inflater?.inflate(R.layout.edit_imageview, null) as RelativeLayout
        relativeLayout.setTag(viewTagIndex++)
        val closeView = relativeLayout.findViewById<ImageView>(R.id.iv_imageClose)
        closeView.setTag(relativeLayout.getTag())
        closeView.setOnClickListener(btnListener)
        return relativeLayout
    }


    /**
     * 根据View的宽度，缩放bitmap的尺寸
     */
    private fun getScaledBitmap(imagePath: String, width: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(imagePath, options)
        val sampleSize = if (options.outWidth > width) options.outWidth / width + 1 else 1
        options.inJustDecodeBounds = false
        options.inSampleSize = sampleSize

        return BitmapFactory.decodeFile(imagePath, options)
    }


    private fun onImageCloseClick(preView: View) {
        if (!translationer?.isRunning!!) {
            disappearingImageIndex = linearLayout!!.indexOfChild(preView)
            linearLayout?.removeView(preView)
        }
    }


    /**
     * dp和pixel转换模块
     *
     * @return 像素值
     */
    private fun dip2px(ediT_PADDING: Float): Int {
        val m = context.resources.displayMetrics.density
        return (ediT_PADDING * m + 0.5f).toInt()
    }

}


