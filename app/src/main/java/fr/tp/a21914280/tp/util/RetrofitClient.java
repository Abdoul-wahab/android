package fr.tp.a21914280.tp.util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ApiUtils.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
