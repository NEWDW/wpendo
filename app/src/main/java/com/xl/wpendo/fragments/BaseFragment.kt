package com.xl.wpendo.fragments

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
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





class BaseFragment : Fragment() {
    private var adapter: TimeLineAdapter? =null
    private var notes: MutableList<Note>? = null
    private var titles: MutableList<String>? = null
    private lateinit var recyclerView: RecyclerView

    init {

    }
    @SuppressLint("InflateParams")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(com.xl.wpendo.R.layout.fragment_base, container, false)
        initView(view)
        registerForNewNote()
        return view

    }

    /**
     * 这个地方，需要拿出去，这里做个例子，留个坑。。。
     */
    private fun registerForNewNote() {
        LiveDataBus.get()
            .with("new_note",Note::class.java)
            .observe(activity!!, Observer<Note> {
                if (it != null) {
                    notes!!.add(it)
                    titles!!.add(it.title)
                    println("do it?${adapter?.itemCount}")
                    adapter!!.notifyDataSetChanged()
//                    adapter!!.notifyItemChanged(adapter!!.itemCount)
                }
            })
    }

    override fun onPause() {
        super.onPause()
        println("diaoton")

    }

    private fun initView(view: View) {
        notes = ArrayList<Note>() as MutableList<Note>?
        titles = ArrayList<String>()
        recyclerView = view.findViewById(com.xl.wpendo.R.id.rv_timeline)
        val linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager
        adapter = TimeLineAdapter(this.titles!!)
        recyclerView.adapter = adapter
        recyclerView.itemAnimator = null
        recyclerView.addItemDecoration(TimeLineItemDecoration(context!!, this.notes!!))
        //这里需要从用户获得，此处只是例子
        for (i in 0 until 5) {
            val note = when (i % 2) {
                1 -> Note("2017/7/${i + 1}", NoteKind.item_agenda, "真的好难啊", i.toString(),null)
                0 -> Note("2017/7/${i + 20}", NoteKind.item_essay, "好难啊", i.toString(),null)
                else -> null
            }
            notes?.add(note!!)
            titles?.add(note!!.title)
        }

    }
}