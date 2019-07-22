package com.xl.wpendo.activities

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.xl.wpendo.R

class TimeLineAdapter(notes: List<String>) : RecyclerView.Adapter<TimeLineAdapter.ViewHolder>() {
    private val _notes = notes


    private lateinit var inflater: LayoutInflater
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.note_item, p0, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val note = _notes[p1]
        p0.textView.text = note
    }

    override fun getItemCount(): Int {
        return _notes.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView = itemView.findViewById<TextView>(R.id.tv_note)!!
    }
}