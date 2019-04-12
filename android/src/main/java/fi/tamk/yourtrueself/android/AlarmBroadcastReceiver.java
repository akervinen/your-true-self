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

/**
 * Class to receive Android's Alarm. Creates a notification with game's language.
 */
public class AlarmBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, new Intent(context, AndroidLauncher.class), PendingIntent.FLAG_UPDATE_CURRENT);

        // Load preferences manually, since Gdx.app isn't set
        Preferences pref = new AndroidPreferences(context.getSharedPreferences(YTSGame.PREF_NAME, Context.MODE_PRIVATE));

        // Use preferences locale or default if none is set
        Locale locale;
        if (pref.contains("lang")) {
            String lang = pref.getString("lang");
            locale = YTSGame.localeFromPref(lang);
        } else {
            locale = Locale.getDefault();
        }

        // Set Gdx.files to an instance of AndroidFiles we create, because it isn't set otherwise
        // and causes I18NBundle.createBundle to crash.
        Gdx.files = new AndroidFiles(context.getAssets(), context.getFilesDir().getAbsolutePath());
        I18NBundle bundle = I18NBundle.createBundle(Gdx.files.internal("i18n/YourTrueSelf"), locale);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "your-true-self")
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle(bundle.get("chlNotificationTitle"))
                .setContentText(bundle.get("chlNotificationBody"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(contentIntent)
                .setAutoCancel(true);

        NotificationManager am = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Android Oreo and onwards requires notification channel to be set
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
