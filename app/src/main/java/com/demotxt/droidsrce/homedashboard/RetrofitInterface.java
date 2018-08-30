package com.demotxt.droidsrce.homedashboard;

import com.demotxt.droidsrce.homedashboard.Model.Prediction;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface RetrofitInterface {
    @Multipart
    @POST("/predict")
    Call<Prediction> uploadImage(@Part MultipartBody.Part image);
}