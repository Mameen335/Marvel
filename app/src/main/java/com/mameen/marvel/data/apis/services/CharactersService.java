package com.mameen.marvel.data.apis.services;

import com.mameen.marvel.data.apis.responses.CharactersResponse;
import com.mameen.marvel.data.apis.responses.SectionResponse;

import retrofit2.Callback;

public interface CharactersService {

    void getCharacters(String timeStamp
            , String apiKey
            , String hash
            , int offset
            , Callback<CharactersResponse> callback);

    void searchCharacters(String timeStamp
            , String apiKey
            , String hash
            , String nameStartsWith
            , Callback<CharactersResponse> callback);

    void getSection(String resourceId
            , String section
            , String timeStamp
            , String apiKey
            , String hash
            , Callback<SectionResponse> callback);
}
