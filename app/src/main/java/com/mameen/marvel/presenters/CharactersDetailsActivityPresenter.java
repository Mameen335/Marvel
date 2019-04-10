package com.mameen.marvel.presenters;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.mameen.marvel.app.AppAsistant;
import com.mameen.marvel.app.Constants;
import com.mameen.marvel.data.apis.responses.CharactersResponse;
import com.mameen.marvel.data.apis.responses.SectionResponse;
import com.mameen.marvel.data.apis.services.CharactersService;
import com.mameen.marvel.data.apis.services.Imple.CharactersServiceImple;
import com.mameen.marvel.data.local.DatabaseClient;
import com.mameen.marvel.data.local.entities.Comics;
import com.mameen.marvel.data.local.entities.Events;
import com.mameen.marvel.data.local.entities.Series;
import com.mameen.marvel.data.local.entities.Stories;
import com.mameen.marvel.data.models.Character;
import com.mameen.marvel.data.models.Section;
import com.mameen.marvel.data.models.Thumbnail;
import com.mameen.marvel.util.HashCreator;

import java.util.ArrayList;
import java.util.List;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindComponent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharactersDetailsActivityPresenter {

    static final String TAG = CharactersDetailsActivityPresenter.class.getSimpleName();

    private View view;
    private Context context;

    @BindComponent(CharactersServiceImple.class)
    CharactersService charactersService;

    public CharactersDetailsActivityPresenter(View view, Context context) {
        this.view = view;
        this.context = context;
        Spork.bind(this);
    }

    public void loadSection(String resourceId, final String section) {

        String timeStamp = HashCreator.getTimeStamp();
        String apiKey = Constants.PU_KEY;
        String hash = HashCreator.getMd5(timeStamp + Constants.PR_KEY + Constants.PU_KEY);

        if (AppAsistant.isConnected(context)) {
            charactersService.getSection(resourceId, section, timeStamp, apiKey, hash, new Callback<SectionResponse>() {
                @Override
                public void onResponse(Call<SectionResponse> call, Response<SectionResponse> response) {
                    Log.e(TAG, response.body().toString());
                    if (response.isSuccessful()) {

                        switch (section) {
                            case Constants.SECTION_COIMCS:
                                view.loadComics(response.body().getCharacters());
//                                new SaveComics().execute(response.body().getCharacters());
                                break;
                            case Constants.SECTION_SERIES:
                                view.loadSeries(response.body().getCharacters());
//                                new SaveSeries().execute(response.body().getCharacters());
                                break;
                            case Constants.SECTION_STORIES:
                                view.loadStories(response.body().getCharacters());
//                                new SaveStories().execute(response.body().getCharacters());
                                break;
                            case Constants.SECTION_EVENTS:
                                view.loadEvents(response.body().getCharacters());
//                                new SaveEvents().execute(response.body().getCharacters());
                                break;
                        }
                    } else {

                    }
                }

                @Override
                public void onFailure(Call<SectionResponse> call, Throwable t) {
                    Log.e(TAG, "onFailure Cause: " + t.getCause());
                }
            });
        } else {
            // no internet
//            new GetSection().execute(new SectionType(Integer.parseInt(resourceId), section));
        }
    }

    /*private class SaveComics extends AsyncTask<List<Section>, Void, Void> {

        @Override
        protected Void doInBackground(List<Section>... parms) {

            Comics[] comics = new Comics[parms[0].size()];

            for (int i=0; i<parms[0].size();i++){
                Section s = parms[0].get(i);
                Comics c = new Comics();

                c.setCharId(s.getId());
                c.setName(s.getTitle());
                c.setThumbnailPath(s.getThumbnail().getPath());
                c.setThumbnailExtension(s.getThumbnail().getExtension());

                comics[i] = c;
            }

            DatabaseClient.getInstance(context).getAppDatabase()
                    .comicsDao()
                    .insertAll(comics);
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    private class SaveSeries extends AsyncTask<List<Section>, Void, Void> {

        @Override
        protected Void doInBackground(List<Section>... parms) {

            Series[] series = new Series[parms[0].size()];

            for (int i=0; i<parms[0].size();i++){
                Section s = parms[0].get(i);
                Series c = new Series();

                c.setCharId(s.getId());
                c.setName(s.getTitle());
                c.setThumbnailPath(s.getThumbnail().getPath());
                c.setThumbnailExtension(s.getThumbnail().getExtension());

                series[i] = c;
            }

            DatabaseClient.getInstance(context).getAppDatabase()
                    .seriesDao()
                    .insertAll(series);
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    private class SaveStories extends AsyncTask<List<Section>, Void, Void> {

        @Override
        protected Void doInBackground(List<Section>... parms) {

            Stories[] stories = new Stories[parms[0].size()];

            for (int i=0; i<parms[0].size();i++){
                Section s = parms[0].get(i);
                Stories c = new Stories();

                c.setCharId(s.getId());
                c.setName(s.getTitle());
                c.setThumbnailPath(s.getThumbnail().getPath());
                c.setThumbnailExtension(s.getThumbnail().getExtension());

                stories[i] = c;
            }

            DatabaseClient.getInstance(context).getAppDatabase()
                    .storiesDao()
                    .insertAll(stories);
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    private class SaveEvents extends AsyncTask<List<Section>, Void, Void> {

        @Override
        protected Void doInBackground(List<Section>... parms) {

            Events[] events = new Events[parms[0].size()];

            for (int i=0; i<parms[0].size();i++){
                Section s = parms[0].get(i);
                Events c = new Events();

                c.setCharId(s.getId());
                c.setName(s.getTitle());
                c.setThumbnailPath(s.getThumbnail().getPath());
                c.setThumbnailExtension(s.getThumbnail().getExtension());

                events[i] = c;
            }

            DatabaseClient.getInstance(context).getAppDatabase()
                    .eventsDao()
                    .insertAll(events);
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    private class GetSection extends AsyncTask<SectionType, Void, List<Section>> {

        private SectionType sectionType;

        @Override
        protected List<Section> doInBackground(SectionType... prams) {

            this.sectionType = prams[0];

            List<Section> sections = new ArrayList<>();
            switch (prams[0].getName()) {
                case Constants.SECTION_COIMCS:
                    List<Comics> comics = DatabaseClient
                            .getInstance(context)
                            .getAppDatabase()
                            .comicsDao()
                            .getAll(prams[0].getId());

                    for (Comics c : comics) {
                        Section s = new Section();

                        s.setId(c.getId());
                        s.setTitle(c.getName());

                        Thumbnail t = new Thumbnail();

                        t.setPath(c.getThumbnailPath());
                        t.setExtension(c.getThumbnailExtension());
                        s.setThumbnail(t);

                        sections.add(s);
                    }
                    break;
                case Constants.SECTION_SERIES:
                    List<Series> series = DatabaseClient
                            .getInstance(context)
                            .getAppDatabase()
                            .seriesDao()
                            .getAll(prams[0].getId());

                    for (Series c : series) {
                        Section s = new Section();

                        s.setId(c.getId());
                        s.setTitle(c.getName());

                        Thumbnail t = new Thumbnail();

                        t.setPath(c.getThumbnailPath());
                        t.setExtension(c.getThumbnailExtension());
                        s.setThumbnail(t);

                        sections.add(s);
                    }
                    break;
                case Constants.SECTION_STORIES:
                    List<Stories> stories = DatabaseClient
                            .getInstance(context)
                            .getAppDatabase()
                            .storiesDao()
                            .getAll(prams[0].getId());

                    for (Stories c : stories) {
                        Section s = new Section();

                        s.setId(c.getId());
                        s.setTitle(c.getName());

                        Thumbnail t = new Thumbnail();

                        t.setPath(c.getThumbnailPath());
                        t.setExtension(c.getThumbnailExtension());
                        s.setThumbnail(t);

                        sections.add(s);
                    }
                    break;
                case Constants.SECTION_EVENTS:
                    List<Events> events = DatabaseClient
                            .getInstance(context)
                            .getAppDatabase()
                            .eventsDao()
                            .getAll(prams[0].getId());

                    for (Events c : events) {
                        Section s = new Section();

                        s.setId(c.getId());
                        s.setTitle(c.getName());

                        Thumbnail t = new Thumbnail();

                        t.setPath(c.getThumbnailPath());
                        t.setExtension(c.getThumbnailExtension());
                        s.setThumbnail(t);

                        sections.add(s);
                    }
                    break;
            }

            return sections;
        }

        @Override
        protected void onPostExecute(List<Section> sections) {
            super.onPostExecute(sections);

            if (sections.size() > 0) {
                switch (sectionType.getName()) {
                    case Constants.SECTION_COIMCS:
                        view.loadComics(sections);
                        break;
                    case Constants.SECTION_SERIES:
                        view.loadSeries(sections);
                        break;
                    case Constants.SECTION_STORIES:
                        view.loadStories(sections);
                        break;
                    case Constants.SECTION_EVENTS:
                        view.loadEvents(sections);
                        break;
                }
            } else {
                view.showError("Check your Internet Connection");
            }
        }
    }

    private class SectionType {
        private int id;
        private String name;

        public SectionType(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }*/

    public interface View {
        void loadComics(List<Section> comicsList);

        void loadSeries(List<Section> seriesList);

        void loadStories(List<Section> storiesList);

        void loadEvents(List<Section> eventsList);

        void showError(String message);

    }
}
