package com.dev.nightfury.alarmnotification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder;

        String name = "Reminder";
        String id = "Reminder"; // The user-visible name of the channel.
        String description = "Reminder"; // The user-visible description of the channel.

        Intent repeatingIntent = new Intent(context, RepeatingActivity.class);
            repeatingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 100, repeatingIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notificationManager.getNotificationChannel(id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, name, importance);
                mChannel.setDescription(description);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notificationManager.createNotificationChannel(mChannel);
            }
            builder = new NotificationCompat.Builder(context, id);

            builder.setContentTitle("Custom Alarm")  // required
                    .setSmallIcon(android.R.drawable.ic_popup_reminder) // required
                    .setContentText("Reminder")  // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker("Custom Alarm")
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        }else{
            builder = new NotificationCompat.Builder(context);

            builder.setContentTitle("Custom Alarm")                           // required
                    .setSmallIcon(android.R.drawable.ic_popup_reminder) // required
                    .setContentText("Reminder")  // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker("Custom Alarm")
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                    .setPriority(Notification.PRIORITY_HIGH);

        }

        Notification notification = builder.build();
        notificationManager.notify(100, notification);
    }
}
