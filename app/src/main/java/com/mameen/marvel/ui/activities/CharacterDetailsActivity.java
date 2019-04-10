package com.mameen.marvel.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mameen.marvel.R;
import com.mameen.marvel.app.Constants;
import com.mameen.marvel.data.models.Character;
import com.mameen.marvel.data.models.Section;
import com.mameen.marvel.listeners.EndlessRecyclerOnScrollLinear;
import com.mameen.marvel.listeners.OnItemClickListener;
import com.mameen.marvel.presenters.CharactersDetailsActivityPresenter;
import com.mameen.marvel.ui.adapters.CharactersAdapter;
import com.mameen.marvel.ui.adapters.SectionAdapter;
import com.mameen.marvel.ui.dialogs.SectionDialog;
import com.mameen.marvel.util.ParentActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.android.annotations.BindClick;
import io.github.sporklibrary.android.annotations.BindView;

public class CharacterDetailsActivity extends ParentActivity implements CharactersDetailsActivityPresenter.View {

    static final String TAG = CharacterDetailsActivity.class.getSimpleName();

    private int id;
    private String name;
    private String desc;
    private String image;

    List<Section> comicsList = new ArrayList<>();
    List<Section> seriesList = new ArrayList<>();
    List<Section> storiesList = new ArrayList<>();
    List<Section> eventsList = new ArrayList<>();

    private SectionAdapter comicsAdapter;
    private SectionAdapter seriesAdapter;
    private SectionAdapter storiesAdapter;
    private SectionAdapter eventsAdapter;

    private CharactersDetailsActivityPresenter presenter;

    @BindView(R.id.imgCharacter)
    ImageView imgCharacter;

    @BindView(R.id.tvName)
    TextView tvName;

    @BindView(R.id.tvDescription)
    TextView tvDescription;

    @BindView(R.id.rvComics)
    RecyclerView rvComics;

    @BindView(R.id.rvSeries)
    RecyclerView rvSeries;

    @BindView(R.id.rvStories)
    RecyclerView rvStories;

    @BindView(R.id.rvEvents)
    RecyclerView rvEvents;

    @BindView(R.id.progress)
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_details);

        hideSystemUI();
        try {
            Bundle bundle = getIntent().getExtras();
            id = bundle.getInt("id");
            name = bundle.getString("name");
            desc = bundle.getString("desc");
            image = bundle.getString("image");
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        Spork.bind(this);

        presenter = new CharactersDetailsActivityPresenter(this, this);
        try {
            comicsAdapter = new SectionAdapter(CharacterDetailsActivity.this, comicsList, R.layout.row_section);
            initList(comicsAdapter, rvComics, Constants.SECTION_COIMCS);

            seriesAdapter = new SectionAdapter(CharacterDetailsActivity.this, seriesList, R.layout.row_section);
            initList(seriesAdapter, rvSeries, Constants.SECTION_SERIES);

            storiesAdapter = new SectionAdapter(CharacterDetailsActivity.this, storiesList, R.layout.row_section);
            initList(storiesAdapter, rvStories, Constants.SECTION_STORIES);

            eventsAdapter = new SectionAdapter(CharacterDetailsActivity.this, eventsList, R.layout.row_section);
            initList(eventsAdapter, rvEvents, Constants.SECTION_EVENTS);
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        loadCharacterData();
    }

    private void loadCharacterData() {

        tvName.setText(name);
        tvDescription.setText(desc);

        try {
            Picasso.get()
                    .load(image)
                    .placeholder(R.color.black)
                    .error(R.color.black)
                    .into(imgCharacter, new Callback() {
                        @Override
                        public void onSuccess() {
                            progress.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(Exception e) {
                            progress.setVisibility(View.GONE);
                        }
                    });

        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    private void initList(final SectionAdapter adapter, RecyclerView rv, String section) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CharacterDetailsActivity.this
                , LinearLayoutManager.HORIZONTAL, false);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                try {
                    Log.e(TAG, "size: " + adapter.getData().size());
                    new SectionDialog(CharacterDetailsActivity.this, adapter.getData()).show();
                } catch (Exception e) {
                    Log.e(TAG, "Exception: " + e.getMessage());
                }
            }
        });

        presenter.loadSection(id + "", section);
    }


    @BindClick(R.id.imgBack)
    private void goBack() {
        onBackPressed();
    }

    private void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    @Override
    public void loadComics(List<Section> comicsList) {
        comicsAdapter.InsertAll(comicsList);
    }

    @Override
    public void loadSeries(List<Section> seriesList) {
        seriesAdapter.InsertAll(seriesList);
    }

    @Override
    public void loadStories(List<Section> storiesList) {
        storiesAdapter.InsertAll(storiesList);
    }

    @Override
    public void loadEvents(List<Section> eventsList) {
        eventsAdapter.InsertAll(eventsList);
    }

    @Override
    public void showError(String message) {

    }
}
