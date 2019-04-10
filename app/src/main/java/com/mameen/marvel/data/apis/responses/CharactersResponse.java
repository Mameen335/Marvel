package com.mameen.marvel.data.apis.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mameen.marvel.data.models.Character;

import java.util.ArrayList;

public class CharactersResponse {

    @SerializedName("code")
    @Expose
    private int code;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("data")
    @Expose
    private CharactersData characters;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getOffset() {
        return characters.offset;
    }

    public void setOffset(int offset) {
        this.characters.offset = offset;
    }

    public int getLimit() {
        return characters.limit;
    }

    public void setLimit(int limit) {
        this.characters.limit = limit;
    }

    public int getTotal() {
        return characters.total;
    }

    public void setTotal(int total) {
        this.characters.total = total;
    }

    public int getCount() {
        return characters.count;
    }

    public void setCount(int count) {
        this.characters.count = count;
    }

    public ArrayList<Character> getCharacters() {
        return characters.characters;
    }

    public void setCharacters(ArrayList<Character> characters) {
        this.characters.characters = characters;
    }

    private class CharactersData {

        @SerializedName("offset")
        @Expose
        private int offset;

        @SerializedName("limit")
        @Expose
        private int limit;

        @SerializedName("total")
        @Expose
        private int total;

        @SerializedName("count")
        @Expose
        private int count;

        @SerializedName("results")
        @Expose
        private ArrayList<Character> characters;
    }
}
