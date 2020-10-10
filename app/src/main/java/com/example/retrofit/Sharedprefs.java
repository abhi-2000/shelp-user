package com.example.retrofit;


import android.content.Context;
import android.content.SharedPreferences;

public class Sharedprefs {
    private static final String SHARED_PREFS="sharedpref";


    public static String readShared(Context ctx, String setname,String defvalue)
    {
        SharedPreferences sharedPreferences =ctx.getSharedPreferences(SHARED_PREFS,Context.MODE_PRIVATE);
        return sharedPreferences.getString(setname,defvalue);
    }

    public static void saveSharedsetting(Context ctx,String setname, String setvalue){
        SharedPreferences sharedPreferences =ctx.getSharedPreferences(SHARED_PREFS,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putString(setname,setvalue);
        editor.apply();
    }
    public static void sharedprefsave(Context ctx, String name,String token,String userId){
        SharedPreferences prefs= ctx.getSharedPreferences("Name",0);
        SharedPreferences.Editor prefedit= prefs.edit();
        prefedit.putString("Name", name);
        prefedit.commit();
        SharedPreferences prefs_token= ctx.getSharedPreferences("Token",0);
        SharedPreferences.Editor prefedit_token= prefs_token.edit();
        prefedit_token.putString("Token", token);
        prefedit_token.commit();
        SharedPreferences prefs_userID= ctx.getSharedPreferences("userId",0);
        SharedPreferences.Editor prefsedit_userID= prefs_userID.edit();
        prefsedit_userID.putString("userId", userId);
        prefsedit_userID.commit();
//        SharedPreferences prefs_email= ctx.getSharedPreferences("email",0);
//        SharedPreferences.Editor prefsedit_email= prefs_email.edit();
//        prefsedit_email.putString("email", email);
//        prefsedit_email.commit();

    }

}