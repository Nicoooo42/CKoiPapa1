package com.demotxt.droidsrce.homedashboard;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface RetrofitInterface {
    @Multipart
    @POST("/images/upload")
    Call<String> uploadImage(@Part MultipartBody.Part image);
}