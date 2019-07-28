package com.xl.wpendo.fragments

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.persistence.room.Room
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xl.wpendo.activities.TimeLineAdapter
import com.xl.wpendo.itemdecoration.TimeLineItemDecoration
import com.xl.wpendo.livedatabus.LiveDataBus
import com.xl.wpendo.noteitems.Note
import com.xl.wpendo.noteitems.NoteKind
import android.support.v7.widget.DefaultItemAnimator
import com.xl.wpendo.database.DataBase
import com.xl.wpendo.database.NoteEntity
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


abstract class BaseFragment : Fragment() {
    protected open  var adapter: TimeLineAdapter?=null
    protected open var notes: MutableList<Note> ?= ArrayList<Note>() as MutableList<Note>
    protected open var titles: MutableList<String>?= ArrayList<String>()
    private lateinit var recyclerView: RecyclerView



    @SuppressLint("InflateParams")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(com.xl.wpendo.R.layout.fragment_base, container, false)

        getFragmentView(view)
        return view

    }

    private fun getFragmentView(view: View) {
        adapter = TimeLineAdapter(this.titles!!)
        recyclerView = view.findViewById(com.xl.wpendo.R.id.rv_timeline)
        val linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager as RecyclerView.LayoutManager?
        recyclerView.adapter = adapter
        recyclerView.itemAnimator = null
        recyclerView.addItemDecoration(TimeLineItemDecoration(context!!, this.notes!!))

    }

    /**
     * 这个地方，需要拿出去
     */
    abstract fun registerForNewNote()
//    {
//        LiveDataBus.get()
//            .with("new_note",Note::class.java)
//            .observe(activity!!, Observer<Note> {
//                if (it != null) {
//                    notes!!.add(it)
//                    titles!!.add(it.title)
//                    println("do it?${adapter?.itemCount}")
//                    adapter!!.notifyDataSetChanged()
////                    adapter!!.notifyItemChanged(adapter!!.itemCount)
//                }
//            })
//    }


    abstract fun initView(view: View)
    @SuppressLint("CheckResult")
    protected fun saveByRoom(note: Note) {
        val dataBase = Room.databaseBuilder(
            context!!,
            DataBase::class.java, "DataBase"
        ).allowMainThreadQueries()
            .build()
        val noteDao = dataBase.noteDao
        Completable.fromAction {
            print(noteDao.insertAll(note.changeIntoEntity()))
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        noteDao.all.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe {
            println("after saving+${it.size}")
        }
        dataBase.close()
    }

    @SuppressLint("CheckResult")
    protected fun deleteFromRoom(note: Note){
        val dataBase = Room.databaseBuilder(
            context!!,
            DataBase::class.java, "DataBase"
        ).allowMainThreadQueries()
            .build()
        val noteDao = dataBase.noteDao
        Completable.fromAction {
            noteDao.delete(note.changeIntoEntity())
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        dataBase.noteDao.insertAll(note.changeIntoEntity())
        dataBase.noteDao.all.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe {
            println("after deleting+${it.size}")
        }
        dataBase.close()
    }

}