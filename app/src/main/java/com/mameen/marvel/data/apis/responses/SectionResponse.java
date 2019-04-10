package com.mameen.marvel.data.apis.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mameen.marvel.data.models.Section;

import java.util.ArrayList;

public class SectionResponse {

    @SerializedName("code")
    @Expose
    private int code;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("data")
    @Expose
    private SectionsData sections;

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
        return sections.offset;
    }

    public void setOffset(int offset) {
        this.sections.offset = offset;
    }

    public int getLimit() {
        return sections.limit;
    }

    public void setLimit(int limit) {
        this.sections.limit = limit;
    }

    public int getTotal() {
        return sections.total;
    }

    public void setTotal(int total) {
        this.sections.total = total;
    }

    public int getCount() {
        return sections.count;
    }

    public void setCount(int count) {
        this.sections.count = count;
    }

    public ArrayList<Section> getCharacters() {
        return sections.sections;
    }

    public void setCharacters(ArrayList<Section> comics) {
        this.sections.sections = comics;
    }

    private class SectionsData {

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
        private ArrayList<Section> sections;
    }
}
