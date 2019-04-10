package com.mameen.marvel.data.local.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.mameen.marvel.data.local.entities.Events;

import java.util.List;

@Dao
public interface EventsDao {

    @Query("SELECT * FROM Events WHERE charId = :id")
    List<Events> getAll(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(Events... events);

    @Insert
    void insert(Events events);

    @Delete
    void delete(Events events);

    @Update
    void update(Events events);
}
