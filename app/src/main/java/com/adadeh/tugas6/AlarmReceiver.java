package com.adadeh.tugas6;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //builder yang akan membuat notifikasi tampil
        PendingIntent pendingIntent =
                PendingIntent.getActivity(context, 0 ,i, 0);
        NotificationCompat.Builder builder = new
                NotificationCompat.Builder(context, "AlarmManager")
                .setSmallIcon(R.drawable.ic_baseline_baby_changing_station_24)
                .setContentTitle("ALARAM BERBUNYI")
                .setContentText("Tugas Praktikum 6 Boss")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);
        NotificationManagerCompat notificationManagerCompat =
                NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(123, builder.build());
    }
}
