package com.example.minas.bonus.register;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.minas.bonus.App;
import com.example.minas.bonus.LoginActivity;
import com.example.minas.bonus.R;

import java.util.Locale;

import static com.example.minas.bonus.LoginActivity.Language;

public class RegistretionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("");
        setContentView(R.layout.activity_registretion);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.help:
                android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(this);

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
            case R.id.language:CharSequence[] items = {" English "," Russian "," Armenian"};

                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);

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
                                LoginActivity.levelDialog.dismiss();
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
                                LoginActivity.levelDialog.dismiss();
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
                                LoginActivity.levelDialog.dismiss();
                                finish();
                                startActivity(getIntent());
                                break;

                        }

                    }
                });

                LoginActivity.levelDialog = builder.create();
                LoginActivity.levelDialog.show();
                break;

            case R.id.logout:
                final Intent intent=new Intent(RegistretionActivity.this,LoginActivity.class);

                AlertDialog.Builder builderForMenu = new AlertDialog.Builder(this);
                builderForMenu.setTitle("Warning")
                        .setMessage("Do you want to live as?")
                        .setIcon(R.mipmap.scanner)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        try {
                                            App.getInstance().ed.remove(LoginActivity.SELLERUSERNAME);
                                            App.getInstance().ed.remove(LoginActivity.SELLERPASSWORD);
                                            App.getInstance().ed.commit();

                                        }
                                        catch (Exception e){

                                            System.out.println(e.getMessage());
                                        }
                                        finally {
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                })
                        .setNegativeButton("No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                    }
                                });
                AlertDialog alert = builderForMenu.create();

                alert.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void Scan(View view) {
        Intent intent =new Intent(this, SimpleScannerActivity.class);
        startActivity(intent);
        finish();
    }

    public void Registration(View view) {
        Intent intent=new Intent(this,RegistrateYourClient.class);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    @Override
    public void onBackPressed() {
        final Intent intent=new Intent(RegistretionActivity.this,LoginActivity.class);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Warning")
                .setMessage("Do you want to live as?")
                .setIcon(R.mipmap.scanner)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                try {
                                    App.getInstance().ed.remove(LoginActivity.SELLERUSERNAME);
                                    App.getInstance().ed.remove(LoginActivity.SELLERPASSWORD);
                                    App.getInstance().ed.commit();

                                }
                                catch (Exception e){

                                    System.out.println(e.getMessage());
                                }
                                finally {
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        })
                .setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
        AlertDialog alert = builder.create();

        alert.show();

    }
}
