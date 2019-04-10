package com.mameen.marvel.data.apis.services.Imple;

import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mameen.marvel.app.Constants;
import com.mameen.marvel.data.apis.services.BaseService;

import java.io.File;
import java.util.concurrent.TimeUnit;

import io.github.sporklibrary.annotations.ComponentScope;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@ComponentScope(ComponentScope.Scope.SINGLETON)
public class BaseServiceImpl implements BaseService {

    static final String TAG = BaseServiceImpl.class.getSimpleName();

    private static final String BASE_URL = Constants.APP_BASE_URL;
    private Retrofit retrofit;

    public BaseServiceImpl() {

        try {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .build();

            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd")
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .create();

            retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    @Override
    public Retrofit getRetrofit() {
        return retrofit;
    }

    public MultipartBody.Part getFilePart(File file, String fileName) {
        if (file == null)
            return null;

        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData(fileName, file.getName(), reqFile);
        return body;
    }
}
