package fi.tamk.yourtrueself.android;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.badlogic.gdx.backends.android.AndroidApplication;

import fi.tamk.yourtrueself.YTSTimerHelper;

/**
 * Helper class to access platform-specific timer functions.
 * This Android version uses AlarmManager for the timer, so it doesn't depend on the game running.
 */
public final class YTSAndroidHelper implements YTSTimerHelper {
    private AndroidApplication app;

    /**
     * Create an instance of timer helper class for Android.
     *
     * @param app instance of AndroidApplication provided by libGDX's AndroidLauncher
     */
    YTSAndroidHelper(AndroidApplication app) {
        this.app = app;
    }

    @Override
    public void startTimer(long time) {
        AlarmManager am = (AlarmManager) app.getSystemService(Context.ALARM_SERVICE);
        Intent in = new Intent(app, AlarmBroadcastReceiver.class);
        PendingIntent pin = PendingIntent.getBroadcast(app, 0, in, 0);

        am.set(
                AlarmManager.RTC_WAKEUP,
                time,
                pin
        );
    }
}
