package com.protexcreative.freemind;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;
import com.protexcreative.freemind.Model.Notification;

import static com.protexcreative.freemind.App.CHANNEL_1_ID;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String messageTitle = remoteMessage.getNotification().getTitle();
        String messagebody = remoteMessage.getNotification().getBody();
        String click_action = remoteMessage.getNotification().getClickAction();

        String dataMessage = remoteMessage.getData().get("message");

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this, CHANNEL_1_ID)
                        .setSmallIcon(R.mipmap.ic_app_icon_5)
                .setContentTitle(messageTitle)
                .setColor(Color.rgb(29, 120, 116))
                .setContentText(messagebody)
                .setAutoCancel(true);

        Intent resultIntent = new Intent(click_action);
        resultIntent.putExtra("dataMessage", dataMessage);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0,  resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(resultPendingIntent);

        final int mNotificationID = (int) System.currentTimeMillis();

        final NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        mNotifyMgr.notify(mNotificationID, mBuilder.build());
    }
}
