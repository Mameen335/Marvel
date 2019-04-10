package com.mameen.marvel.app;

public class Constants {

    public static boolean isFirstScreen = false;  // flag to mange back stack

    //////////////////////////////////////////////////////////////////////////////

    private static String host = "http://gateway.marvel.com";

    public static String APP_BASE_URL = host + "/v1/public/";

    public final static String PU_KEY = "da9fe57452219f3cc9485096f44f740a";
    public final static String PR_KEY = "e38e2b4df34564ea673a101a31d885961eda0688";


    public final static String SECTION_COIMCS = "comics";
    public final static String SECTION_SERIES = "series";
    public final static String SECTION_STORIES = "stories";
    public final static String SECTION_EVENTS = "events";
}
