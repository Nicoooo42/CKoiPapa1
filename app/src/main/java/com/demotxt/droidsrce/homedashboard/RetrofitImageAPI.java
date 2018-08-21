package com.demotxt.droidsrce.homedashboard;

import com.demotxt.droidsrce.homedashboard.Model.Categorie;
import com.demotxt.droidsrce.homedashboard.Model.Item;
import com.demotxt.droidsrce.homedashboard.Model.User;
import com.squareup.okhttp.ResponseBody;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;


/**
 * Created by navneet on 17/6/16.
 */
public interface RetrofitImageAPI {

    @GET("storage/category/{IdCategorie}/{NameImage}")
    Call<ResponseBody> getImageDetails(@Path("IdCategorie") String IdCategorie, @Path("NameImage") String NameImage);

    @GET("storage/category/{IdCategorie}/items/{Nameid}/{NameImage}")
    Call<ResponseBody> getImageDetailsItem(@Path("IdCategorie") String IdCategorie, @Path("Nameid") String Nameid, @Path("NameImage") String NameImage);

    @POST("api/v1/categories")
    @FormUrlEncoded
    Call<List<Categorie>> getCategories(@Field("token") String token);

    @Multipart
    @POST("/images/upload")
    Call<okhttp3.ResponseBody> uploadImage(@Part("image") MultipartBody.Part image);

    @POST("api/v1/user/login")
    @FormUrlEncoded
    Call<User> getCleApi(@Field("email") String email, @Field("password") String password);

    @POST("api/v1/items")
    @FormUrlEncoded
    Call<Item> getItem(@Field("id") Integer id);
}