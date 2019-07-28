package com.xl.wpendo.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class NoteEntity {
    public NoteEntity(long id, String title, String detail, String noteKind, String  picUrls) {
        this.id = id;
        this.title = title;
        this.detail = detail;
        this.noteKind = noteKind;
        this.picUrls = picUrls;
    }
    @PrimaryKey
    private long id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "detail")
    private String detail;
    @ColumnInfo(name = "kind")
    private String noteKind;
    @ColumnInfo(name = "pics")
    private String picUrls;

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    String getDetail() {
        return detail;
    }

    void setDetail(String detail) {
        this.detail = detail;
    }

    String getNoteKind() {
        return noteKind;
    }

    void setNoteKind(String noteKind) {
        this.noteKind = noteKind;
    }


    String getPicUrls() {
        return picUrls;
    }


    void setPicUrls(String picUrls) {
        this.picUrls = picUrls;
    }
}
