package com.demotxt.droidsrce.homedashboard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.demotxt.droidsrce.homedashboard.Model.Categorie;
import com.demotxt.droidsrce.homedashboard.Model.User;
import com.demotxt.droidsrce.homedashboard.Storage.ShareUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {


    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    EditText textMail, textPassword;

    static String token = "";


    String url = "https://ckoipapa.me/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        // Set up the login form.


        textMail= (EditText) findViewById(R.id.editEmail);
        textPassword= (EditText) findViewById(R.id.editPassword);

        Button button1=(Button)findViewById(R.id.BoutonLogin);

        textMail.setText("nicolas.lamanna2@wanadoo.fr");
        textPassword.setText("password");

        button1.setOnClickListener(new OnClickListener(){ // Notre classe anonyme
            public void onClick(View view){ // et sa méthode !
                if (view.getId()==R.id.BoutonLogin){
                    getCleApi();
                }
            }
        });
    }

    void getCleApi() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitImageAPI service = retrofit.create(RetrofitImageAPI.class);

        Call<User> call = service.getCleApi(textMail.getText().toString(), textPassword.getText().toString());


        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Response<User> response, Retrofit retrofit) {

                if(response.body() != null) {
                    ShareUtils.sauvegarderUser(getApplicationContext(),response.body());
                    Intent secondeActivite = new Intent(LoginActivity.this, Home2.class);
                    startActivity(secondeActivite);
                }else{
                    Toast.makeText(getApplicationContext(),"Utilisateur non valide !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("Failure");
            }

        });

    }

}

