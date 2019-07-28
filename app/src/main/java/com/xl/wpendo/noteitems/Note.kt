package com.xl.wpendo.noteitems

import android.arch.persistence.room.Entity
import com.xl.wpendo.R
import com.xl.wpendo.database.NoteEntity
import java.lang.StringBuilder

/**
 * 这个类估计不全
 */
class Note(
    private val time: String,
    private val noteKind: NoteKind,
    private val title: String,
    private val detail: String,
    private val picUrl: MutableList<String>?
) {
    fun getKind() = noteKind
    fun getTitle() = title
    fun getTime() = time
    fun getDaily() = detail
    fun getPic() = picUrl
    fun getBitmapFromNote(noteKind: NoteKind) =
        when (noteKind) {
            NoteKind.item_agenda -> R.drawable.ic_item_agenda
            NoteKind.item_daily -> R.drawable.ic_item_daily
            NoteKind.item_essay -> R.drawable.ic_item_essay
            NoteKind.item_todo -> R.drawable.ic_item_todo
        }

    fun changeIntoEntity():NoteEntity{
        var t: String =""
        for (i in time.split("/")){
                println("$t+????+$i")
                t+=i
            }
        var pics:String?= null
        if (picUrl!=null) {
            for (i in picUrl) {
                if (i != picUrl.last())
                    i.plus(",")
               pics+=i
            }
        }

println("$t+$title+$detail+$noteKind+$pics")
        return NoteEntity(t.toLong(),this.title,this.detail,this.noteKind.toString(), pics.toString())
    }
}