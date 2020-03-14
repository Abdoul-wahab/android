package fr.tp.a21914280.tp.service;

import fr.tp.a21914280.tp.util.ListeResponse;
import fr.tp.a21914280.tp.util.Response;
import fr.tp.a21914280.tp.util.ResponseAPI;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    //Details Annonce
    @Headers("Content-Type: application/json")
    @GET("?apikey=21914280&method=details")
    Call<ResponseAPI> getAnnonce(@Query("id") String id);


    //Liste Annonces
    @Headers("Content-Type: application/json")
    @GET("?apikey=21914280&method=listAll")
    Call<ListeResponse> getListAnnonce();

    //Liste By Pseudo
    @Headers("Content-Type: application/json")
    @GET("?apikey=21914280&method=listByPseudo")
    Call<ListeResponse> myList(@Query("pseudo") String pseudo);

    // Supprimer
    @Headers("Content-Type: application/json")
    @GET("?apikey=21914280&method=delete")
    Call<Response> deleteAnnonce(@Query("id") String id);


    // Annonce au hasart
    @Headers("Content-Type: application/json")
    @GET("?apikey=21914280&method=randomAd")
    Call<ResponseAPI> randomAnnonce();

    //Enrégistrement d'annonces
    @POST("?apikey=21914280&method=save")
    @FormUrlEncoded
    Call<ResponseAPI> saveAnnonce(@Field("titre") String titre,
                                  @Field("description") String desc,
                                  @Field("pseudo") String pseudo,
                                  @Field("prix") int prix,
                                  @Field("emailContact") String emailContact,
                                  @Field("telContact") String telContact,
                                  @Field("ville") String ville,
                                  @Field("cp") String cp);

    // Modification d'annonce
    @POST("?apikey=21914280&method=update")
    @FormUrlEncoded
    Call<ResponseAPI> updateAnnonce(@Field("id") String id,
                                    @Field("titre") String titre,
                                    @Field("description") String description,
                                    @Field("pseudo") String pseudo,
                                    @Field("prix") int prix,
                                    @Field("emailContact") String emailContact,
                                    @Field("telContact") String telContact,
                                    @Field("ville") String ville,
                                    @Field("cp") String cp);

    // Upload d'images
    @Multipart
    @POST("?apikey=21914280&method=addImage")
    Call<ResponseAPI> uploadImage(@Part MultipartBody.Part photo,
                                  @Part("id") RequestBody id);

}
