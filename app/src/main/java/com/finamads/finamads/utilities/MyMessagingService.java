package com.finamads.finamads.utilities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.finamads.finamads.R;
import com.finamads.finamads.activities.HomeActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;



public class MyMessagingService extends FirebaseMessagingService {

    //Uri uri = Uri.parse("http://www.google.com");
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        //showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());


        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d("Firebase", "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d("Firebase", "Message data payload: " + remoteMessage.getData());

            /*if (*//* Check if data needs to be processed by long running job *//* true) {
                // For long-running tasks (10 seconds or more) use WorkManager.
                scheduleJob();
            } else {
                // Handle message within 10 seconds
                handleNow();
            }*/

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
            Log.d("Firebase", "##################Message Notification Body: " + remoteMessage.getNotification().getBody());
            Log.d("Firebase", "################Message Notification title: " + remoteMessage.getNotification().getTitle());
        }

        // Also if you intend on generating your own notify as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.

    }

    public void showNotification(String title, String message) {
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, HomeActivity.class), 0);
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, "MyNotifications")
                        .setContentTitle(title)
                        .setLargeIcon(largeIcon)
                        .setSmallIcon(getNotificationIcon())
                        .setAutoCancel(true)
                        .setContentText(message)
                        .setContentIntent(pi);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(999, builder.build());


    }

    private int getNotificationIcon() {
        boolean useWhiteIcon = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M);
        return useWhiteIcon ? R.mipmap.ic_launcher : R.mipmap.ic_launcher;
    }

}
