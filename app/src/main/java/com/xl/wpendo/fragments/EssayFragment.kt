package com.xl.wpendo.fragments

import android.arch.lifecycle.Observer
import android.support.v7.widget.RecyclerView
import android.view.View
import com.xl.wpendo.activities.TimeLineAdapter
import com.xl.wpendo.livedatabus.LiveDataBus
import com.xl.wpendo.noteitems.Note
import com.xl.wpendo.noteitems.NoteKind

class EssayFragment : BaseFragment() {
    override fun initView(view: View) {
//        //这里需要从用户获得，此处只是例子
//        for (i in 0 until 5) {
//            val note = Note("2017/7/${i + 10}", NoteKind.item_essay, "初始化", i.toString(), null)
//
//            notes?.add(note)
//            titles?.add(note.getTitle())
//        }
    }

    override var adapter: TimeLineAdapter? =super.adapter
    override var notes: MutableList<Note>? = super.notes?.filter { it.getKind()==NoteKind.item_essay }?.toMutableList()
    override var titles: MutableList<String>? = super.titles
    override fun registerForNewNote() {
//        LiveDataBus.get()
//            .with(NoteKind.item_essay.toString(), Note::class.java)
//            .observe(activity!!, Observer<Note> {
//                if (it != null) {
//                    notes!!.add(it)
//                    titles!!.add(it.getTitle())
//                    println("do it?${adapter?.itemCount}")
//                    adapter!!.notifyDataSetChanged()
////                    adapter!!.notifyItemChanged(adapter!!.itemCount)
//                }
//            })
    }


}