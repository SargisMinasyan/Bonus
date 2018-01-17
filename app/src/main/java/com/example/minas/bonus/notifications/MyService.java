package com.example.minas.bonus.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.minas.bonus.R;
import com.example.minas.bonus.client.ClientActiyity;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        NotificationManager nm;
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Integer lenth= intent.getExtras().getInt(ClientActiyity.Length);
        makeNotifi();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public void makeNotifi(){
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(18000000);
                    Intent intent = new Intent(MyService.this, ClientActiyity.class);
                    PendingIntent pIntent = PendingIntent.getActivity(MyService.this, 0, intent, 0);
                    // Build notification
                    // Actions are just fake
                    Notification noti = new Notification.Builder(MyService.this)
                            .setContentTitle(getResources().getString(R.string.checkyourbonuses))
                            .setContentText(getResources().getString(R.string.clickheretoread)).setSmallIcon(R.mipmap.ic_launcher)
                            .setContentIntent(pIntent)
                            .build();
                    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    // hide the notification after its selected
                    noti.flags |= Notification.FLAG_AUTO_CANCEL;

                    notificationManager.notify(0, noti);
                    makeNotifi();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

    }
}
