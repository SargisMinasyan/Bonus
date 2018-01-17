package com.example.minas.bonus;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;


public class App extends Application {
    private static App instance;
    public SharedPreferences sPref;
    public SharedPreferences.Editor ed;

    @Override
    public void onCreate() {
        sPref = getApplicationContext().getSharedPreferences("app", MODE_PRIVATE);
        ed = sPref.edit();
        super.onCreate();
        instance = this;

    }

    public static App getInstance() {

        return instance;
    }


}
