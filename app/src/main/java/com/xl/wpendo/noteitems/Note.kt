package com.xl.wpendo.noteitems

import com.xl.wpendo.R

/**
 * 这个类估计不全
 */
class Note(
    val time:String,
    val noteKind:NoteKind,
    val title:String,
    val detail:String,
    val picUrl: MutableList<String>?
) {
    fun getBitmapFromNote(noteKind: NoteKind)=
            when(noteKind){
                NoteKind.item_agenda-> R.drawable.ic_item_agenda
                NoteKind.item_daily -> R.drawable.ic_item_daily
                NoteKind.item_essay -> R.drawable.ic_item_essay
                NoteKind.item_todo -> R.drawable.ic_item_todo
            }
}