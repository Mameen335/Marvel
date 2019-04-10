package com.mameen.marvel.data.local.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.mameen.marvel.data.local.entities.Stories;

import java.util.List;

@Dao
public interface StoriesDao {

    @Query("SELECT * FROM Stories WHERE charId = :id")
    List<Stories> getAll(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(Stories... stories);

    @Insert
    void insert(Stories stories);

    @Delete
    void delete(Stories stories);

    @Update
    void update(Stories stories);
}
