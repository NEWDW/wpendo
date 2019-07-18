package com.xl.wpendo.activities

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.xl.wpendo.R
import com.xl.wpendo.TimeLineItemDecoration
import com.xl.wpendo.noteitems.Note
import com.xl.wpendo.noteitems.NoteKind

class MainActivity : BaseActivity() {
    private var notes: MutableList<Note>? = ArrayList<Note>()
    private var titles: MutableList<String>? = ArrayList<String>()
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initNotes()
        recyclerView = findViewById(R.id.rv_timeline)
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        val adapter = TimeLineAdapter(this.titles!!)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(TimeLineItemDecoration(this, this.notes!!))
    }

    private fun initNotes() {
        //这里需要从用户获得，此处只是例子
        for (i in 0 until 10) {
            val note = when (i % 2) {
                1 -> Note("2017/7/${i + 1}", NoteKind.item_agenda, "真的好难啊", i.toString())
                0 -> Note("2017/7/${i + 20}", NoteKind.item_essay, "好难啊", i.toString())
                else -> null
            }
            notes?.add(note!!)
            titles?.add(note!!.title)
        }

    }
}
