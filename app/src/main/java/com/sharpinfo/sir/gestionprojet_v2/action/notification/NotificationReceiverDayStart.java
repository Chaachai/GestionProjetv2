package com.sharpinfo.sir.gestionprojet_v2.action.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;

import com.sharpinfo.sir.gestionprojet_v2.R;
import com.sharpinfo.sir.gestionprojet_v2.action.menu.SideMenuActivity;

public class NotificationReceiverDayStart extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent repeating_intent = new Intent(context, SideMenuActivity.class);

        repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 4242, repeating_intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentIntent(pendingIntent);

        builder.setSmallIcon(R.drawable.ic_notifications_black_24dp);

        builder.setAutoCancel(true);
//        builder.setLargeIcon();

        builder.setContentTitle("Rappel");
        builder.setContentText("N'oubliez pas de saisir vos depenses et le temps alloué a chaque projet ou tache");
        builder.setSubText("Utilisation journalière");
        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        builder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        builder.setLights(Color.RED, 3000, 3000);
        assert notificationManager != null;
        notificationManager.notify(4242, builder.build());
    }
}
