package com.xl.wpendo.fragments

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xl.wpendo.activities.TimeLineAdapter
import com.xl.wpendo.itemdecoration.TimeLineItemDecoration
import com.xl.wpendo.livedatabus.LiveDataBus
import com.xl.wpendo.noteitems.Note
import com.xl.wpendo.noteitems.NoteKind

class AgendaFragment : BaseFragment() {


    override var adapter: TimeLineAdapter?= super.adapter
    override var notes: MutableList<Note>? = super.notes?.filter { it.getKind()==NoteKind.item_agenda }?.toMutableList()
    override var titles: MutableList<String>?=super.titles

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        registerForNewNote()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun registerForNewNote() {
        LiveDataBus.get()
            .with(NoteKind.item_agenda.toString(), Note::class.java)
            .observe(activity!!, Observer<Note> {
                if (it != null) {
                    notes!!.add(it)
                    titles!!.add(it.getTitle())
                    println("do it?${adapter?.itemCount}")
                    adapter!!.notifyDataSetChanged()
//                    adapter!!.notifyItemChanged(adapter!!.itemCount)

                }
            })
    }

    override fun initView(view: View) {
//        //这里需要从用户获得，此处只是例子
//        for (i in 0 until 5) {
//            val note =Note("2017/7/${i + 1}", NoteKind.item_agenda, "初始化", i.toString(), null)
//
//            notes?.add(note)
//            titles?.add(note.getTitle())
//        }
    }


}