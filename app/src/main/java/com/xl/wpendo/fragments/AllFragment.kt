package com.xl.wpendo.fragments

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.persistence.room.Room
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xl.wpendo.activities.TimeLineAdapter
import com.xl.wpendo.database.DataBase
import com.xl.wpendo.livedatabus.LiveDataBus
import com.xl.wpendo.noteitems.Note
import com.xl.wpendo.noteitems.NoteKind
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AllFragment : BaseFragment() {

    override var adapter: TimeLineAdapter? = super.adapter
    override var notes: MutableList<Note>? = super.notes
    override var titles: MutableList<String>? = super.titles

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        getFromRoom()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    @SuppressLint("CheckResult")
    private fun getFromRoom(){
        val dataBase = Room.databaseBuilder(
            context!!,
            DataBase::class.java, "DataBase"
        ).allowMainThreadQueries()
            .build()
        val noteDao = dataBase.noteDao
        noteDao.all.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe {
            println("after saving+${it[0].title}")
        }
        dataBase.close()
    }
    override fun initView(view: View) {
////这里需要从用户获得，此处只是例子
//        for (i in 0 until 5) {
//            val note = Note("2017/7/${i + 1}", NoteKind.item_agenda, "初始化", i.toString(), null)
//            notes?.add(note)
//            titles?.add(note.getTitle())
//        }

    }

    override fun registerForNewNote() {

    }
}
