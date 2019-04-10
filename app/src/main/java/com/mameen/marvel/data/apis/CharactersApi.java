package com.mameen.marvel.data.apis;

import com.mameen.marvel.data.apis.responses.CharactersResponse;
import com.mameen.marvel.data.apis.responses.SectionResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CharactersApi {

    @GET("characters")
    Call<CharactersResponse> getCharacters(@Query("ts") String timeStamp
            , @Query("apikey") String apiKey
            , @Query("hash") String hash
            , @Query("offset") int offset);

    @GET("characters")
    Call<CharactersResponse> searchCharacters(@Query("ts") String timeStamp
            , @Query("apikey") String apiKey
            , @Query("hash") String hash
            , @Query("nameStartsWith") String nameStartsWith);

    @GET("characters/{resourceId}/{section}")
    Call<SectionResponse> getSection(@Path("resourceId") String resourceId
            , @Path("section") String section
            , @Query("ts") String timeStamp
            , @Query("apikey") String apiKey
            , @Query("hash") String hash);
}
