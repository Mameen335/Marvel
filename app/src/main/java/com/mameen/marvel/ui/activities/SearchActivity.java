package com.mameen.marvel.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.mameen.marvel.R;
import com.mameen.marvel.data.models.Character;
import com.mameen.marvel.listeners.EndlessRecyclerOnScrollLinear;
import com.mameen.marvel.listeners.OnItemClickListener;
import com.mameen.marvel.presenters.SearchActivityPresenter;
import com.mameen.marvel.ui.adapters.CharactersAdapter;
import com.mameen.marvel.util.ParentActivity;

import java.util.ArrayList;
import java.util.List;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.android.annotations.BindClick;
import io.github.sporklibrary.android.annotations.BindView;

public class SearchActivity extends ParentActivity implements SearchActivityPresenter.View {

    static final String TAG = SearchActivity.class.getSimpleName();

    private SearchActivityPresenter presenter;

    private CharactersAdapter adapter;
    private List<Character> searchList = new ArrayList<>();

    @BindView(R.id.etSearch)
    private EditText etSearch;

    @BindView(R.id.rv)
    private RecyclerView rv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Spork.bind(this);

        presenter = new SearchActivityPresenter(this, this);
        initList();

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.loadAllCharacters(etSearch.getText().toString().trim());
            }
        });
    }

    private void initList() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchActivity.this);
        adapter = new CharactersAdapter(SearchActivity.this, searchList, R.layout.row_character);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                try {
                    Intent intent = new Intent(SearchActivity.this, CharacterDetailsActivity.class);
                    Character character = adapter.getData().get(position);

                    intent.putExtra("id", character.getId());
                    intent.putExtra("name", character.getName());
                    intent.putExtra("desc", character.getDescription());
                    intent.putExtra("image", character.getThumbnail().getFullPathPortrait());
                    startActivity(intent);
                } catch (Exception e) {
                    Log.e(TAG, "Exception: " + e.getMessage());
                }
            }
        });
    }

    @BindClick(R.id.tvCancel)
    private void closeMe(){
        onBackPressed();
    }

    @Override
    public void loadAllCharacter(List<Character> characterList) {
        adapter.InsertAll(characterList);
    }

    @Override
    public void showError(String message) {
        Log.e(TAG, "Error: " + message);
        showLongSnackbar(R.id.parent, message);
    }
}
