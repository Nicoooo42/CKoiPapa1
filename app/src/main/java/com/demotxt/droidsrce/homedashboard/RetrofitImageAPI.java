package com.demotxt.droidsrce.homedashboard;

import com.demotxt.droidsrce.homedashboard.Model.Categorie;
import com.squareup.okhttp.ResponseBody;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by navneet on 17/6/16.
 */
public interface RetrofitImageAPI {

    @GET("storage/category/Zoo/lion.jpg")
    Call<ResponseBody> getImageDetails();


    @GET("api/v1/categories")
    Call<List<Categorie>> getCategories();
}