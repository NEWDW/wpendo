package com.xl.wpendo.database;

import android.arch.persistence.room.*;
import io.reactivex.Flowable;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("select *from NoteEntity")
    Flowable <List<NoteEntity>> getAll();
    @Query("select * from NoteEntity where id in(:noteIds)")
    Flowable <List<NoteEntity>> loadAllByIds(int[] noteIds);
    @Query("select * from Noteentity where title like :title")
    Flowable <NoteEntity> findByTitle(String title);
    @Query("select * from Noteentity where detail like :detail")
    Flowable <NoteEntity> findByDetail(String detail);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(NoteEntity... noteEntities);
    @Update
    void Update(NoteEntity... noteEntities);
    @Delete
    void delete(NoteEntity noteEntity);
}
