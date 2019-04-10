package com.mameen.marvel.data.local.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.mameen.marvel.data.local.entities.Characters;

import java.util.List;

@Dao
public interface CharactersDao {

    @Query("SELECT * FROM Characters")
    List<Characters> getAll();

    @Query("SELECT * FROM Characters WHERE name LIKE  :search || '%'")
    List<Characters> search(String search);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(Characters... characters);

    @Insert
    void insert(Characters characters);

    @Delete
    void delete(Characters characters);

    @Update
    void update(Characters characters);
}
