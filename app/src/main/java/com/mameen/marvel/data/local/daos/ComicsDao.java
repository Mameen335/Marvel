package com.mameen.marvel.data.local.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.mameen.marvel.data.local.entities.Comics;

import java.util.List;

@Dao
public interface ComicsDao {

    @Query("SELECT * FROM Comics WHERE charId = :id")
    List<Comics> getAll(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(Comics... comics);

    @Insert
    void insert(Comics comics);

    @Delete
    void delete(Comics comics);

    @Update
    void update(Comics comics);
}
