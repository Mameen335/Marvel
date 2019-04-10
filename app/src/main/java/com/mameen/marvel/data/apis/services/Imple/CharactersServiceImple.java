package com.mameen.marvel.data.apis.services.Imple;

import android.util.Log;

import com.mameen.marvel.data.apis.CharactersApi;
import com.mameen.marvel.data.apis.responses.CharactersResponse;
import com.mameen.marvel.data.apis.responses.SectionResponse;
import com.mameen.marvel.data.apis.services.BaseService;
import com.mameen.marvel.data.apis.services.CharactersService;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindComponent;
import retrofit2.Callback;

public class CharactersServiceImple implements CharactersService {

    static final String TAG = CharactersServiceImple.class.getSimpleName();

    @BindComponent(BaseServiceImpl.class)
    BaseService baseService;

    CharactersApi charactersApi;

    public CharactersServiceImple() {
        Spork.bind(this);
        charactersApi = baseService.getRetrofit().create(CharactersApi.class);
    }

    @Override
    public void getCharacters(String timeStamp, String apiKey, String hash, int offset, Callback<CharactersResponse> callback) {
        try {
            charactersApi.getCharacters(timeStamp, apiKey, hash, offset).enqueue(callback);
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    @Override
    public void searchCharacters(String timeStamp, String apiKey, String hash, String nameStartsWith, Callback<CharactersResponse> callback) {
        try {
            charactersApi.searchCharacters(timeStamp, apiKey, hash, nameStartsWith).enqueue(callback);
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    @Override
    public void getSection(String resourceId, String section, String timeStamp, String apiKey, String hash, Callback<SectionResponse> callback) {
        try {
            charactersApi.getSection(resourceId, section, timeStamp, apiKey, hash).enqueue(callback);
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }
}
