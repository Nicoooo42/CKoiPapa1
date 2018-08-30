package com.demotxt.droidsrce.homedashboard;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.demotxt.droidsrce.homedashboard.Model.Categorie;
import com.demotxt.droidsrce.homedashboard.Model.Prediction;
import com.demotxt.droidsrce.homedashboard.Storage.ShareUtils;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CameraActivity extends AppCompatActivity {

    private Uri file;
    private TextView text;

    public static String pred = "lion";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        text = (TextView) findViewById(R.id.textView9);

        ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }

        takePicture();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            }
        }
    }

    public void takePicture() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = Uri.fromFile(getOutputMediaFile());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file);

        startActivityForResult(intent, 100);
    }

    private static File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "CameraDemo");

        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                Log.d("CameraDemo", "failed to create directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                //imageView.setImageURI(file);

                File videoFile = new File(file.getPath());

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://159.89.25.189:5000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), videoFile);

                MultipartBody.Part body = MultipartBody.Part.createFormData("image", "image.jpg", requestFile);

                Log.d("onResponse", videoFile.getName());

                RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

                Call<Prediction> call =  retrofitInterface.uploadImage(body);

                call.enqueue(new Callback<Prediction>() {
                    @Override
                    public void onResponse(Call<Prediction> call, retrofit2.Response<Prediction> response) {
                        pred = response.body().getPredictions();
                        ShareUtils.sauvegarderPrediction(getApplicationContext(), response.body());

                        setContentView(R.layout.activity_objet);

                        Intent camera = new Intent(CameraActivity.this, Object.class);

                        camera.putExtra("ID_Image", pred);

                        // Puis on lance l'intent !
                        startActivity(camera);
                    }

                    @Override
                    public void onFailure(Call<Prediction> call, Throwable t) {
                        Log.d("onFailure6", t.toString());
                        text.setText("Connection Failed");
                    }
                });
            }
        }


    }
}