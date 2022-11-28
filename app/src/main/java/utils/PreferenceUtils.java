package utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.alfred.ui.Espacios.SalaPrincipal;
import com.example.alfred.ui.login.LoginActivity;
import com.example.alfred.ui.ui.home.HomeActivity;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

public class PreferenceUtils {

    public PreferenceUtils(){

    }

    public static boolean saveEmail(String email, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_EMAIL, email);
        prefsEditor.apply();
        return true;
    }

    public static String getEmail (Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_EMAIL, null);
    }

    public static boolean savePassword (String password, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_PASSWORD, password);
        prefsEditor.apply();
        return true;
    }

    public static String getPassword (Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_PASSWORD, null);
    }

    public  static boolean deleteSharedPre (Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
        return true;
    }
    public static boolean saveHome (String home, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_HOME, home);
        prefsEditor.apply();
        return true;
    }

    public static String getHome (Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_HOME, null);
    }
}
