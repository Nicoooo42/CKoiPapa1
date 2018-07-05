package com.demotxt.droidsrce.homedashboard.Storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.demotxt.droidsrce.homedashboard.Model.User;
import com.google.gson.Gson;

public class ShareUtils {

    public static String FICHIER_XML = "MonFichier.xml";
    public static String CleUser = "User";

    private static Gson gson = new Gson();

    public static void sauvegarderUser(Context context, User user){
        SharedPreferences sharedPreferences = context.getSharedPreferences(FICHIER_XML, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = gson.toJson(user);
        editor.putString(CleUser, json);
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


}
