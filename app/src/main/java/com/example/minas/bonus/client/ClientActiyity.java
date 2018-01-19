package com.example.minas.bonus.client;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.minas.bonus.APIService;
import com.example.minas.bonus.ApiUtils;
import com.example.minas.bonus.App;
import com.example.minas.bonus.LoginActivity;
import com.example.minas.bonus.R;
import com.example.minas.bonus.notifications.NotificationPublisher;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientActiyity extends AppCompatActivity {

    private static final String EMAIL = "EMAIL";
    private static final String USERNAME = "USERNAME";
    private static final String PHONE = "PHONE";
    private static final String BONUS = "BONUS";
    private ImageView imageView;
    private TextView username;
    private TextView email;
    private TextView phone;
    private ListView bonus_list;
    ClientAdapter clientAdapter;
    public static final String Length = "length";
    List<Bonus> list;
    Client client=new Client();
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("");
        progressDialog = new ProgressDialog(ClientActiyity.this);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setMax(100); // Progress Dialog Max Value

        progressDialog.setTitle("Progressing"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);
        setContentView(R.layout.activity_client);
        imageView = (ImageView) findViewById(R.id.barCode_image);
        username = (TextView) findViewById(R.id.username_client);
        email = (TextView) findViewById(R.id.email_client);
        phone = (TextView) findViewById(R.id.phone_client);
        bonus_list = (ListView) findViewById(R.id.bonuslist_id);

        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {


        } else if (conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED
                || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) {

            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false)
                    .setTitle("Error")
                    .setMessage("Please check your internet connection")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                            startActivity(getIntent());
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        APIService apiService = ApiUtils.getAPIService();

        String clientString= (String)App.getInstance().sPref.getString(LoginActivity.CLIENT,null);
        Client client1=new Gson().fromJson(clientString,Client.class);

        apiService.getClientInfo(client1.getUsername()).enqueue(new Callback<ResponsePOJO<Client>>() {
            @Override
            public void onResponse(Call<ResponsePOJO<Client>> call, Response<ResponsePOJO<Client>> response) {
                if (response.body().getStatus().equals("200")) {

                    client=response.body().getObject();
                    list = client.getBonuses();
                    username.setText(client.getUsername());
                    phone.setText(client.getPhone());
                    email.setText(client.getEmail());
                    List<UserDataAndAmount> userDataAndAmountList = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        userDataAndAmountList.add(new UserDataAndAmount(list.get(i).getData() + "", list.get(i).getAmount() + " bonus"));
                    }
                    clientAdapter = new ClientAdapter(ClientActiyity.this, userDataAndAmountList);
                    bonus_list.setAdapter(clientAdapter);
                    QRCodeWriter writer = new QRCodeWriter();
                    try {
                        BitMatrix bitMatrix = writer.encode(username.getText().toString(), BarcodeFormat.QR_CODE, 512, 512);
                        int width = bitMatrix.getWidth();
                        int height = bitMatrix.getHeight();
                        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
                        for (int x = 0; x < width; x++) {
                            for (int y = 0; y < height; y++) {
                                bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                            }
                        }
                        imageView.setImageBitmap(bmp);

                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                    scheduleNotification(getNotification("check your page"), 170000000);

                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponsePOJO<Client>> call, Throwable t) {

            }
        });

    }

    public void logout(View view) {
        Intent intent = new Intent(ClientActiyity.this, LoginActivity.class);

        try {
            App.getInstance().ed.remove(LoginActivity.USERNAME);
            App.getInstance().ed.remove(LoginActivity.PASSWORD);
            App.getInstance().ed.remove(BONUS);
            App.getInstance().ed.commit();

        } catch (Exception e) {

            System.out.println(e.getMessage());
        } finally {
            startActivity(intent);
            finish();
        }


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
        switch (item.getItemId()) {
            case R.id.help:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);

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
                CharSequence[] items = {" English ", " Russian ", " Armenian"};

                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setTitle(R.string.Select);
                builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {

                        String languageToLoad; // your language
                        Locale locale;
                        Configuration config = new Configuration();
                        switch (item) {
                            case 0:
                                languageToLoad = "en"; // your language
                                locale = new Locale(languageToLoad);
                                Locale.setDefault(locale);
                                config.locale = locale;
                                getBaseContext().getResources().updateConfiguration(config,
                                        getBaseContext().getResources().getDisplayMetrics());
                                App.getInstance().ed.putString(LoginActivity.Language, languageToLoad);
                                App.getInstance().ed.commit();
                                LoginActivity.levelDialog.dismiss();
                                finish();
                                startActivity(getIntent());
                                break;
                            case 1:
                                languageToLoad = "ru"; // your language
                                locale = new Locale(languageToLoad);
                                Locale.setDefault(locale);
                                config.locale = locale;
                                getBaseContext().getResources().updateConfiguration(config,
                                        getBaseContext().getResources().getDisplayMetrics());
                                App.getInstance().ed.putString(LoginActivity.Language, languageToLoad);
                                App.getInstance().ed.commit();
                                LoginActivity.levelDialog.dismiss();
                                finish();
                                startActivity(getIntent());
                                break;
                            case 2:
                                languageToLoad = "hy"; // your language
                                locale = new Locale(languageToLoad);
                                Locale.setDefault(locale);
                                config.locale = locale;
                                getBaseContext().getResources().updateConfiguration(config,
                                        getBaseContext().getResources().getDisplayMetrics());
                                App.getInstance().ed.putString(LoginActivity.Language, languageToLoad);
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
                Intent intent = new Intent(ClientActiyity.this, LoginActivity.class);

                try {
                    App.getInstance().ed.remove(LoginActivity.USERNAME);
                    App.getInstance().ed.remove(LoginActivity.PASSWORD);
                    App.getInstance().ed.remove(BONUS);
                    App.getInstance().ed.commit();

                } catch (Exception e) {

                    System.out.println(e.getMessage());
                } finally {
                    startActivity(intent);
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    private void scheduleNotification(Notification notification, int delay) {

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    private Notification getNotification(String content) {
        Intent intent = new Intent(this, ClientActiyity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Scheduled Notification");
        builder.setContentText(content);
        builder.setContentIntent(pIntent);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        return builder.build();
    }
}
