package fi.tamk.yourtrueself.android;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.badlogic.gdx.backends.android.AndroidApplication;

import fi.tamk.yourtrueself.YTSAlarmHelper;

/**
 * Helper class to access platform-specific timer functions.
 * This Android version uses AlarmManager for the timer, so it doesn't depend on the game running.
 */
public final class YTSAndroidHelper implements YTSAlarmHelper {
    private AndroidApplication app;

    YTSAndroidHelper(AndroidApplication app) {
        this.app = app;
    }

    @Override
    public void startTimer(int seconds) {
        AlarmManager am = (AlarmManager) app.getSystemService(Context.ALARM_SERVICE);
        Intent in = new Intent(app, AlarmBroadcastReceiver.class);
        PendingIntent pin = PendingIntent.getBroadcast(app, 0, in, 0);

        am.set(
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + seconds * 1000,
                pin
        );
    }
}
