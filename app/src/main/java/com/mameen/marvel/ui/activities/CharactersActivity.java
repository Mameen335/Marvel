package com.mameen.marvel.ui.activities;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mameen.marvel.R;
import com.mameen.marvel.app.Constants;
import com.mameen.marvel.data.models.Character;
import com.mameen.marvel.listeners.EndlessRecyclerOnScrollLinear;
import com.mameen.marvel.listeners.OnItemClickListener;
import com.mameen.marvel.presenters.CharactersActivityPresenter;
import com.mameen.marvel.ui.adapters.CharactersAdapter;
import com.mameen.marvel.util.ParentActivity;
import com.mameen.marvel.util.PermissionChecker;

import java.util.ArrayList;
import java.util.List;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.android.annotations.BindClick;
import io.github.sporklibrary.android.annotations.BindView;

public class CharactersActivity extends ParentActivity implements CharactersActivityPresenter.View {

    static final String TAG = CharactersActivity.class.getSimpleName();

    private CharactersActivityPresenter presenter;

    private int offset = 0;
    private int total = 0;

    private CharactersAdapter adapter;
    private List<Character> characterList = new ArrayList<>();

    private List<Character> searchList = new ArrayList<>();

    @BindView(R.id.srl)
    private SwipeRefreshLayout srl;

    @BindView(R.id.rv)
    private RecyclerView rv;

    @BindView(R.id.progress)
    private ProgressBar progress;

    private PermissionChecker permissionChecker = new PermissionChecker();

    private static final String[] RequiredPermissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE
            , Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_characters);

        Spork.bind(this);
        srl.setOnRefreshListener(srlListener);

        presenter = new CharactersActivityPresenter(this, this);

        initList();
        try{
        presenter.loadAllCharacters(offset);
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    SwipeRefreshLayout.OnRefreshListener srlListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            characterList.clear();
            offset = 0;
            presenter.loadAllCharacters(offset);
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();

        Constants.isFirstScreen = true;
        srl.setRefreshing(false);
        checkPermissions(RequiredPermissions);
    }

    private void checkPermissions(@NonNull String[] permissions) {
        Log.e(TAG, "oncheckPermissions");
        permissionChecker.verifyPermissions(CharactersActivity.this, permissions, new PermissionChecker.VerifyPermissionsCallback() {

            @Override
            public void onPermissionAllGranted() {
            }

            @Override
            public void onPermissionDeny(String[] permissions) {
                Toast.makeText(CharactersActivity.this
                        , "Please grant required permissions.", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();

        Constants.isFirstScreen = false;
    }

    private void initList() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CharactersActivity.this);
        adapter = new CharactersAdapter(CharactersActivity.this, characterList, R.layout.row_character);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(adapter);
        rv.setOnScrollListener(new EndlessRecyclerOnScrollLinear(linearLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                if (offset < total) {
                    offset += 20;
                    presenter.loadAllCharacters(offset);
                }
            }
        });
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                try {
                    Intent intent = new Intent(CharactersActivity.this, CharacterDetailsActivity.class);
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

    @Override
    public void loadAllCharacter(List<Character> characterList) {
        adapter.InsertAll(characterList);
    }

    @Override
    public void setRefreshing(boolean enable) {
        if (offset > 0) {
            if (enable) {
                progress.setVisibility(View.VISIBLE);
            } else {
                progress.setVisibility(View.GONE);
            }
        } else {
            srl.setRefreshing(enable);
        }
    }

    @Override
    public void showError(String message) {
        Log.e(TAG, "Error: " + message);
        showLongSnackbar(R.id.parent, message);
    }

    @Override
    public void setTotal(int total) {
        this.total = total;
    }

    @BindClick(R.id.imgSearch)
    private void gotoSearch(){
        gotoActivity(SearchActivity.class);
    }
}
