package com.mameen.marvel.presenters;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.mameen.marvel.app.AppAsistant;
import com.mameen.marvel.app.Constants;
import com.mameen.marvel.data.apis.responses.CharactersResponse;
import com.mameen.marvel.data.apis.services.CharactersService;
import com.mameen.marvel.data.apis.services.Imple.CharactersServiceImple;
import com.mameen.marvel.data.local.DatabaseClient;
import com.mameen.marvel.data.local.entities.Characters;
import com.mameen.marvel.data.models.Character;
import com.mameen.marvel.data.models.Thumbnail;
import com.mameen.marvel.util.HashCreator;

import java.util.ArrayList;
import java.util.List;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindComponent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivityPresenter {

    static final String TAG = SearchActivityPresenter.class.getSimpleName();

    private View view;
    private Context context;

    @BindComponent(CharactersServiceImple.class)
    CharactersService charactersService;

    public SearchActivityPresenter(View view, Context context) {
        this.view = view;
        this.context = context;
        Spork.bind(this);
    }

    public void loadAllCharacters(String nameStartsWith) {

        String timeStamp = HashCreator.getTimeStamp();
        String apiKey = Constants.PU_KEY;
        String hash = HashCreator.getMd5(timeStamp + Constants.PR_KEY + Constants.PU_KEY);

        if (AppAsistant.isConnected(context)) {
            charactersService.searchCharacters(timeStamp, apiKey, hash, nameStartsWith, new Callback<CharactersResponse>() {
                @Override
                public void onResponse(Call<CharactersResponse> call, Response<CharactersResponse> response) {
                    Log.e(TAG, response.body().toString());
                    if (response.isSuccessful()) {
                        view.loadAllCharacter(response.body().getCharacters());
                    } else {

                    }
                }

                @Override
                public void onFailure(Call<CharactersResponse> call, Throwable t) {
                    Log.e(TAG, "onFailure Cause: " + t.getCause());
                }
            });
        } else {
            // no internet
            new GetCharacters().execute(nameStartsWith);
        }
    }

    private class GetCharacters extends AsyncTask<String, Void, List<Character>> {

        @Override
        protected List<Character> doInBackground(String... prams) {
            List<Characters> characters = DatabaseClient
                    .getInstance(context)
                    .getAppDatabase()
                    .charactersDao()
                    .search(prams[0]);

            List<Character> characterList = new ArrayList<>();
            for (Characters c : characters) {
                Character character = new Character();

                character.setId(c.getRemoteId());
                character.setName(c.getName());
                character.setDescription(c.getDescription());

                Thumbnail t = new Thumbnail();

                t.setPath(c.getThumbnailPath());
                t.setExtension(c.getThumbnailExtension());

                character.setThumbnail(t);

                characterList.add(character);
            }

            return characterList;
        }

        @Override
        protected void onPostExecute(List<Character> characters) {
            super.onPostExecute(characters);

            if (characters.size() > 0) {
                view.loadAllCharacter(characters);
            } else {
                view.showError("Check your Internet Connection");
            }
        }
    }

    public interface View {
        void loadAllCharacter(List<Character> characterList);

        void showError(String message);
    }
}
