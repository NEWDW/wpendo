package com.xl.wpendo.fragments

import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xl.wpendo.activities.TimeLineAdapter
import com.xl.wpendo.livedatabus.LiveDataBus
import com.xl.wpendo.noteitems.Note
import com.xl.wpendo.noteitems.NoteKind

class DailyFragment : BaseFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        registerForNewNote()
        super.onCreate(savedInstanceState)
    }

    override fun initView(view: View) {
//        //这里需要从用户获得，此处只是例子
//        for (i in 0 until 5) {
//            val note =  Note("2017/7/${i + 20}", NoteKind.item_daily, "初始化", i.toString(), null)
//            notes?.add(note)
//            titles?.add(note.getTitle())
//        }
    }
    override var adapter: TimeLineAdapter? =super.adapter
    override var notes: MutableList<Note>? = super.notes?.filter { it.getKind()==NoteKind.item_daily }?.toMutableList()
    override var titles: MutableList<String>? = super.titles
    private lateinit var observer:Observer<Note>
    override fun registerForNewNote() {
       observer = Observer<Note> {
            if (it != null) {
                notes!!.add(it)
                titles!!.add(it.getTitle())
                println("do it?${adapter?.itemCount}")
                    saveByRoom(it)
                adapter!!.notifyDataSetChanged()


//                    saveByRoom(it)
//                    adapter!!.notifyItemChanged(adapter!!.itemCount)
            }
        }
        LiveDataBus.get()
            .with(NoteKind.item_daily.toString(), Note::class.java)
            .observe(activity!!,observer)
        println("我被执行了")
    }


}