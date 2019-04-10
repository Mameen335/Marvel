package com.mameen.marvel.data.local.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.mameen.marvel.data.local.entities.Series;

import java.util.List;

@Dao
public interface SeriesDao {

    @Query("SELECT * FROM Series WHERE charId = :id")
    List<Series> getAll(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(Series... series);

    @Insert
    void insert(Series series);

    @Delete
    void delete(Series series);

    @Update
    void update(Series series);
}
