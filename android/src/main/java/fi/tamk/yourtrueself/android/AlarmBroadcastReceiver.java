package fi.tamk.yourtrueself.android;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import fi.tamk.yourtrueself.R;

public class AlarmBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, new Intent(context, AndroidLauncher.class), PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "your-true-self")
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("New Challenge!")
                .setContentText("You got a new challenge in Your True Self!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(contentIntent);

        NotificationManager am = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "YTS";
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Your True Self",
                    NotificationManager.IMPORTANCE_DEFAULT);
            am.createNotificationChannel(channel);
            builder.setChannelId(channelId);
        }

        am.notify(0, builder.build());
    }
}
