package com.example.minas.bonus;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.minas.bonus.client.Client;
import com.example.minas.bonus.client.ClientActiyity;
import com.example.minas.bonus.client.ResponsePOJO;
import com.example.minas.bonus.client.Seller;
import com.example.minas.bonus.register.RegistretionActivity;
import com.google.gson.Gson;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    public static AlertDialog levelDialog =null;
    public static  final String Language="Language";
    public static final String CLIENT = "CLIENT";
    public static final String SELLERUSERNAME = "SELLERUSERNAME";
    public static final String SELLERPASSWORD = "SELLERPASSWORD";
    public static final String SELLER = "SELLER";
    private EditText username;
    private EditText password;
    private RadioButton client;
    private RadioButton Customer;
    private APIService mAPIService;
    private CheckBox remember_me;


    public static String USERNAME="USERNAME";
    public static String PASSWORD="PASSWORD";
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("");

        if(App.getInstance().sPref.getString(Language,null)!=null){
            String languageToLoad; // your language
            Locale locale ;
            Configuration config = new Configuration();
            switch (App.getInstance().sPref.getString(Language,null)){
                case "en":
                    languageToLoad = "en"; // your language
                    locale = new Locale(languageToLoad);
                    Locale.setDefault(locale);
                    config.locale = locale;
                    getBaseContext().getResources().updateConfiguration(config,
                            getBaseContext().getResources().getDisplayMetrics());
                    break;
                case "ru":
                    languageToLoad = "ru"; // your language
                    locale = new Locale(languageToLoad);
                    Locale.setDefault(locale);
                    config.locale = locale;
                    getBaseContext().getResources().updateConfiguration(config,
                            getBaseContext().getResources().getDisplayMetrics());
                    break;
                case "hy":
                    languageToLoad = "hy"; // your language
                    locale = new Locale(languageToLoad);
                    Locale.setDefault(locale);
                    config.locale = locale;
                    getBaseContext().getResources().updateConfiguration(config,
                            getBaseContext().getResources().getDisplayMetrics());
                    break;
            }
        }


        setContentView(R.layout.activity_login);
        mAPIService = ApiUtils.getAPIService();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission();
            screen();
        } else {
            screen();
        }


    }

    private void screen() {
        username=(EditText)findViewById(R.id.username_login);
        password=(EditText)findViewById(R.id.password_login);
        Customer =(RadioButton)findViewById(R.id.Customer_login);
        client =(RadioButton)findViewById(R.id.client_login);
        remember_me =(CheckBox) findViewById(R.id.remember_me);


        String savedusername = App.getInstance().sPref.getString(USERNAME, null);
        String savedpassword = App.getInstance().sPref.getString(PASSWORD, null);
        String savedSellerusername = App.getInstance().sPref.getString(SELLERUSERNAME, null);
        String savedSellerpassword = App.getInstance().sPref.getString(SELLERPASSWORD, null);

        if (savedusername!=null){
            Intent intent = new Intent(this, ClientActiyity.class);
            startActivity(intent);
            finish();

        }else if (savedSellerusername!=null){
            Intent intent = new Intent(this, RegistretionActivity.class);
            startActivity(intent);
            finish();
        }



    }

    @Override
    public void onBackPressed() {
       finish();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.login, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.help:
                final AlertDialog.Builder builder1 = new AlertDialog.Builder(this);

                builder1.setTitle(R.string.help);
                builder1.setView(R.layout.help);
                builder1.setPositiveButton(R.string.help, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
                LoginActivity.levelDialog = builder1.create();
                LoginActivity.levelDialog.show();
                break;
            case R.id.language:

                CharSequence[] items = {" English "," Russian "," Armenian"};

                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setTitle(R.string.Select);
                builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {

                        String languageToLoad; // your language
                        Locale locale ;
                        Configuration config = new Configuration();
                        switch(item)
                        {
                            case 0:
                                languageToLoad = "en"; // your language
                                locale = new Locale(languageToLoad);
                                Locale.setDefault(locale);
                                config.locale = locale;
                                getBaseContext().getResources().updateConfiguration(config,
                                        getBaseContext().getResources().getDisplayMetrics());
                                App.getInstance().ed.putString(Language, languageToLoad);
                                App.getInstance().ed.commit();
                                levelDialog.dismiss();
                                finish();
                                startActivity(getIntent());
                                break;
                            case 1:
                                languageToLoad  = "ru"; // your language
                                locale = new Locale(languageToLoad);
                                Locale.setDefault(locale);
                                config.locale = locale;
                                getBaseContext().getResources().updateConfiguration(config,
                                        getBaseContext().getResources().getDisplayMetrics());
                                App.getInstance().ed.putString(Language, languageToLoad);
                                App.getInstance().ed.commit();
                                levelDialog.dismiss();
                                finish();
                                startActivity(getIntent());
                                break;
                            case 2:
                                languageToLoad  = "hy"; // your language
                                locale = new Locale(languageToLoad);
                                Locale.setDefault(locale);
                                config.locale = locale;
                                getBaseContext().getResources().updateConfiguration(config,
                                        getBaseContext().getResources().getDisplayMetrics());
                                App.getInstance().ed.putString(Language, languageToLoad);
                                App.getInstance().ed.commit();
                                levelDialog.dismiss();
                                finish();
                                startActivity(getIntent());
                                break;

                        }

                    }
                });

                levelDialog = builder.create();
                levelDialog.show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void sendPostLoginClient(String usernameforview, String passwordforview) {



        mAPIService.login(usernameforview, passwordforview).enqueue(new Callback<ResponsePOJO<Client>>() {
            @Override
            public void onResponse(Call<ResponsePOJO<Client>> call, Response<ResponsePOJO<Client>> response) {

                if(response.body().getStatus().equals("200")) {
                    if (remember_me.isChecked()){

                        App.getInstance().ed.putString(USERNAME, username.getText().toString());
                        App.getInstance().ed.putString(PASSWORD, password.getText().toString());
                        App.getInstance().ed.commit();
                    }
                    Gson gson=new Gson();
                    Intent intent = new Intent(LoginActivity.this, ClientActiyity.class);
                    intent.putExtra(CLIENT,gson.toJson(response.body().getObject()));
                    startActivity(intent);
                    finish();

                    Log.i("" ,"post submitted to API." + response.body().toString());
                }else {
                    username.setError(getResources().getString(R.string.wrongusername));
                    password.setError(getResources().getString(R.string.wrongpassword));

                }
            }
            @Override
            public void onFailure(Call<ResponsePOJO<Client>> call, Throwable t) {

                Log.e("", "Unable to submit post to API.");
            }
        });

    }
    public void sendPostLoginSeller(String usernameforview, String passwordforview) {



        mAPIService.loginSeller(usernameforview, passwordforview).enqueue(new Callback<ResponsePOJO<Seller>>() {
            @Override
            public void onResponse(Call<ResponsePOJO<Seller>> call, Response<ResponsePOJO<Seller>> response) {

                if(response.body().getStatus().equals("200")) {
                    Gson gson=new Gson();
                    if (remember_me.isChecked()){

                        App.getInstance().ed.putString(SELLERUSERNAME, username.getText().toString());
                        App.getInstance().ed.putString(SELLERPASSWORD, password.getText().toString());
                        App.getInstance().ed.commit();
                    }
                    Intent intent = new Intent(LoginActivity.this, RegistretionActivity.class);
                    intent.putExtra(SELLER,gson.toJson(response.body().getObject()));
                    startActivity(intent);
                    finish();

                    Log.i("" ,"post submitted to API." + response.body().toString());
                }else {
                    username.setError(getResources().getString(R.string.wrongusername));
                    password.setError(getResources().getString(R.string.wrongpassword));

                }

            }

            @Override
            public void onFailure(Call<ResponsePOJO<Seller>> call, Throwable t) {

                Log.e("", "Unable to submit post to API.");
            }
        });


    }

    public void login(View view) {

        if (username.getText().toString().isEmpty()
                ||password.getText().toString().isEmpty()) {
            if (username.getText().toString().isEmpty())
                username.setError(""+R.string.required);
            if (password.getText().toString().isEmpty())
                password.setError(""+R.string.required);

        }else {
            if (client.isChecked()) {
                String u = username.getText().toString().trim();
                String p = password.getText().toString().trim();
                sendPostLoginClient(u, p);
            } else if (Customer.isChecked()) {

                String u = username.getText().toString().trim();
                String p = password.getText().toString().trim();
                sendPostLoginSeller(u, p);


            }
        }





    }
    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermission() {


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                +ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                +ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                + ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {

                Snackbar.make(this.findViewById(android.R.id.content),
                        "Please Grant Permissions",
                        Snackbar.LENGTH_INDEFINITE).setActionTextColor(Color.WHITE).setAction("ENABLE",
                        new View.OnClickListener() {
                            @TargetApi(Build.VERSION_CODES.M)
                            @Override
                            public void onClick(View v) {
                                requestPermissions(
                                        new String[]{
                                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                                Manifest.permission.CAMERA,
                                                Manifest.permission.WRITE_EXTERNAL_STORAGE,},
                                        123);
                            }
                        }).show();
            } else {
                requestPermissions(
                        new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.CAMERA,
                                Manifest.permission.ACCESS_COARSE_LOCATION},
                        123);
            }
        }
    }
}
