package com.demotxt.droidsrce.homedashboard.Storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.demotxt.droidsrce.homedashboard.Model.User;
import com.demotxt.droidsrce.homedashboard.Model.Prediction;
import com.google.gson.Gson;

public class ShareUtils {

    public static String FICHIER_XML = "MonFichier.xml";
    public static String CleUser = "User";
    public static String ClePred = "Prediction";

    private static Gson gson = new Gson();

    public static void sauvegarderUser(Context context, User user){
        SharedPreferences sharedPreferences = context.getSharedPreferences(FICHIER_XML, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = gson.toJson(user);
        editor.putString(CleUser, json);
        editor.apply();
    }

    public static void sauvegarderPrediction(Context context, Prediction prediction){
        SharedPreferences sharedPreferences = context.getSharedPreferences(FICHIER_XML, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = gson.toJson(prediction);
        editor.putString(ClePred, json);
        editor.apply();
    }

    public static User recuperationUser(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(FICHIER_XML, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(CleUser,null);

        if(json != null)
            return gson.fromJson(json, User.class);
        else
            return null;
    }

    public static Prediction recuperationPrediction(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(FICHIER_XML, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(ClePred,null);

        if(json != null)
            return gson.fromJson(json, Prediction.class);
        else
            return null;
    }


}
