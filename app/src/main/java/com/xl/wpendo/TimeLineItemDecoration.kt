package com.xl.wpendo

import android.content.Context
import android.graphics.*
import android.support.v7.widget.RecyclerView
import android.view.View
import com.xl.wpendo.noteitems.Note

class TimeLineItemDecoration(context: Context, notes: MutableList<Note>) : RecyclerView.ItemDecoration() {
    private var _paint1: Paint //画竖线
    private var _paint2: Paint //画文字
    private var _paint3: Paint //画横线
    private var icon: Bitmap
    //    private  var icon2: Bitmap
//    private  var icon3: Bitmap
//    private  var icon4: Bitmap
    private val _context = context
    private var _itemViewLeftinterval: Int = 0
    private var _itemViewtopinterval: Int = 0
    private val _notes = notes


    init {
        //配置画笔
        _paint1 = Paint()
        _paint1.color = Color.RED
        _paint2 = Paint()
        _paint2.textSize = 30F
        _paint2.color = Color.BLUE
        _paint3 = Paint()
        _paint3.color = Color.BLACK
        //设置item偏移量
        _itemViewLeftinterval = 300
        _itemViewtopinterval = 50
//        //配置图片
        icon = BitmapFactory.decodeResource(_context.resources, R.drawable.ic_item_todo)
//        icon2 = BitmapFactory.decodeResource(_context.resources, R.drawable.ic_item_agenda)
//        icon3 = BitmapFactory.decodeResource(_context.resources, R.drawable.ic_item_daily)
//        icon4 = BitmapFactory.decodeResource(_context.resources, R.drawable.ic_item_essay)
        //向下偏移量设为图标的一般
        _itemViewtopinterval = icon.height / 2

    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        //设置item偏移量
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val index = parent.getChildAdapterPosition(parent.getChildAt(i))
            //判断在adapter中的奇偶
            when (index % 2) {
                //奇左移
                1 -> outRect.set(_itemViewLeftinterval, _itemViewtopinterval, 0, 0)
                //偶右移
                0 -> outRect.set(0, _itemViewtopinterval, _itemViewLeftinterval, 0)
            }
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        sortByTime()
        val firstNote=_notes[0]
        //遍历子view
        val childCount = parent.childCount
        for (i in 0 until childCount-1) {
            //对应的子view
            val child = parent.getChildAt(i)
            //对应在adapter的下标
            val index = parent.getChildAdapterPosition(child)
            //对应在数据中的内容
            val note = _notes[index+1]
            //判断在adapter中的序号奇偶
            when (index % 2) {
                //交叉的方式排列
                1 -> {
                    //X中心坐标
                    val middleX = child.left + child.width / 2 - _itemViewLeftinterval / 2
                    //处理的是adapter中的第一个item
                    if (index == 0)
                    //第一个item在中心画图标，X中心坐标-图标一半,特殊处理
                    {
                        c.drawBitmap(
                            BitmapFactory.decodeResource(_context.resources, note.getBitmapFromNote(firstNote.noteKind)),
                            middleX.toFloat() - icon.width / 2,
                            child.top.toFloat() - _itemViewtopinterval,
                            _paint1
                        )
                    }
                    c.drawLine(
                        middleX.toFloat() - icon.width / 2,
                        child.bottom.toFloat() + _itemViewtopinterval,
                        middleX.toFloat() - icon.width / 2 - 25,
                        child.bottom.toFloat() + _itemViewtopinterval,
                        _paint3
                    )
                    c.drawText(
                        note.time,
                        (middleX).toFloat() + icon.width / 2 + 25,
                        child.bottom.toFloat() + _itemViewtopinterval / 2 + _itemViewtopinterval,
                        _paint2
                    )


                    //画上链接线
                    c.drawLine(
                        middleX.toFloat(),
                        child.top.toFloat() + _itemViewtopinterval,
                        middleX.toFloat(),
                        child.bottom.toFloat(),
                        _paint1
                    )
                    //排除最后一次，画图
                    if (i != childCount - 1) c.drawBitmap(
                        BitmapFactory.decodeResource(_context.resources, note.getBitmapFromNote(note.noteKind)),
                        middleX.toFloat() - icon.width / 2,
                        child.bottom.toFloat(),
                        _paint1
                    )


                }
                0 -> {
                    val middleX = child.left + child.width / 2 + _itemViewLeftinterval / 2
                    c.drawLine(
                        middleX.toFloat() + icon.width / 2,
                        child.bottom.toFloat() + _itemViewtopinterval,
                        middleX.toFloat() +icon.width / 2 + 25,
                        child.bottom.toFloat() + _itemViewtopinterval,
                        _paint3
                    )
                    c.drawText(
                        note.time,
                        200F,
                        _itemViewtopinterval / 2 + child.bottom.toFloat() + _itemViewtopinterval / 2 + _itemViewtopinterval,
                        _paint2
                    )
                    if (i == 0 && index == 0) {
                        //特殊处理第一个item，这里包含图标和文字
                        c.drawBitmap(
                            BitmapFactory.decodeResource(_context.resources, note.getBitmapFromNote(firstNote.noteKind)),
                            middleX.toFloat() - icon.width / 2,
                            child.top.toFloat() - _itemViewtopinterval,
                            _paint1
                        )
                        c.drawLine(
                            middleX.toFloat() - icon.width / 2,
                            child.top.toFloat(),
                            middleX.toFloat() - icon.width / 2 - 25,
                            child.top.toFloat(),
                            _paint3
                        )
                        c.drawText(
                            firstNote.time,
                            (middleX).toFloat() + icon.width / 2 + 25,
                            child.top.toFloat() + _itemViewtopinterval / 2,
                            _paint2
                        )
                    }
                    c.drawLine(
                        middleX.toFloat(),
                        child.top.toFloat() + _itemViewtopinterval,
                        middleX.toFloat(),
                        child.bottom.toFloat(),
                        _paint1
                    )
                    if (i != childCount - 1) c.drawBitmap(
                        BitmapFactory.decodeResource(_context.resources, note.getBitmapFromNote(note.noteKind)),
                        middleX.toFloat() - icon.width / 2,
                        child.bottom.toFloat(),
                        _paint1
                    )
                }
            }
        }

    }

    private fun sortByTime(){
        //这部分需要先转化成时间戳
        _notes.sortBy { it.time }
    }
}
