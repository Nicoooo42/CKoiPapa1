package com.demotxt.droidsrce.homedashboard;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.demotxt.droidsrce.homedashboard.Model.Item;
import com.demotxt.droidsrce.homedashboard.Model.User;
import com.demotxt.droidsrce.homedashboard.Storage.ShareUtils;
import com.squareup.okhttp.ResponseBody;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class Object extends AppCompatActivity {

    String url = "https://ckoipapa.me/";
    TextView textview1;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objet);

        textview1= (TextView) findViewById(R.id.person_age);
        imageView= (ImageView) findViewById(R.id.photoItem);

        getCleApi();

    }

    void getCleApi() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitImageAPI service = retrofit.create(RetrofitImageAPI.class);

        Call<Item> call = service.getItem(1);


        call.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Response<Item> response, Retrofit retrofit) {

                if(response.body() != null) {
                    textview1.setText(response.body().getDescription());

                    getRetrofitImage(response.body().getIllustration());

                }else{
                    Toast.makeText(getApplicationContext(),"Objet non trouvé !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("Failure");
            }

        });
    }

    void getRetrofitImage(String urlComp) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitImageAPI service = retrofit.create(RetrofitImageAPI.class);

        String[] parseUrlComplement = urlComp.split("/");

        Call<ResponseBody> call = service.getImageDetailsItem(parseUrlComplement[3], parseUrlComplement[5], parseUrlComplement[6]);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {

                try {
                    boolean FileDownloaded = DownloadImage(response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("onFailure", t.toString());
            }
        });
    }

    boolean DownloadImage(ResponseBody body) {

        try {
            Log.d("DownloadImage", "Reading and writing file");
            InputStream in = null;
            FileOutputStream out = null;

            try {
                in = body.byteStream();
                out = new FileOutputStream(getExternalFilesDir(null) + File.separator + "Android.jpg");
                int c;

                while ((c = in.read()) != -1) {
                    out.write(c);
                }
            } catch (IOException e) {
                Log.d("DownloadImage", e.toString());
                return false;
            } finally {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            }

            int width, height;

            Bitmap bMap = BitmapFactory.decodeFile(getExternalFilesDir(null) + File.separator + "Android.jpg");
            width = 2 * bMap.getWidth();
            height = 3 * bMap.getHeight();
            Bitmap bMap2 = Bitmap.createScaledBitmap(bMap, width, height, false);

            imageView.setImageBitmap(bMap2);
            return true;

        } catch (IOException e) {
            Log.d("DownloadImage", e.toString());
            return false;
        }
    }



}
