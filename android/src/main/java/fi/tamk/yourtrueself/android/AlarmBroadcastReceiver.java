package fi.tamk.yourtrueself.android;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.backends.android.AndroidFiles;
import com.badlogic.gdx.backends.android.AndroidPreferences;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Locale;

import fi.tamk.yourtrueself.R;
import fi.tamk.yourtrueself.YTSGame;

public class AlarmBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, new Intent(context, AndroidLauncher.class), PendingIntent.FLAG_UPDATE_CURRENT);

        Preferences pref = new AndroidPreferences(context.getSharedPreferences(YTSGame.PREF_NAME, Context.MODE_PRIVATE));
        Locale locale;
        if (pref.contains("lang")) {
            String lang = pref.getString("lang");
            locale = YTSGame.localeFromPref(lang);
        } else {
            locale = Locale.getDefault();
        }

        Gdx.files = new AndroidFiles(context.getAssets(), context.getFilesDir().getAbsolutePath());
        I18NBundle bundle = I18NBundle.createBundle(Gdx.files.internal("i18n/YourTrueSelf"), locale);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "your-true-self")
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(bundle.get("chlNotificationTitle"))
                .setContentText(bundle.get("chlNotificationBody"))
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
