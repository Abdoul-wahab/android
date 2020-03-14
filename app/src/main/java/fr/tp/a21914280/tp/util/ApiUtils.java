package fr.tp.a21914280.tp.util;

import fr.tp.a21914280.tp.service.ApiService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiUtils {

    public static String API_URL = "https://ensweb.users.info.unicaen.fr/android-api/";

    public static ApiService getAPIService() {
        return RetrofitClient.retrofit.create(ApiService.class);

    }

}
