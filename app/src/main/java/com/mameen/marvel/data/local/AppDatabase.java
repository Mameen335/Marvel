package com.mameen.marvel.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.mameen.marvel.data.local.daos.CharactersDao;
import com.mameen.marvel.data.local.daos.ComicsDao;
import com.mameen.marvel.data.local.daos.EventsDao;
import com.mameen.marvel.data.local.daos.SeriesDao;
import com.mameen.marvel.data.local.daos.StoriesDao;
import com.mameen.marvel.data.local.entities.Characters;
import com.mameen.marvel.data.local.entities.Comics;
import com.mameen.marvel.data.local.entities.Events;
import com.mameen.marvel.data.local.entities.Series;
import com.mameen.marvel.data.local.entities.Stories;

@Database(entities = {Characters.class
        , Comics.class
        , Events.class
        , Series.class
        , Stories.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CharactersDao charactersDao();

    public abstract ComicsDao comicsDao();

    public abstract EventsDao eventsDao();

    public abstract SeriesDao seriesDao();

    public abstract StoriesDao storiesDao();
}
