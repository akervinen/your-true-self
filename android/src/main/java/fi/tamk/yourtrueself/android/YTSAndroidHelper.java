package fi.tamk.yourtrueself.android;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import com.badlogic.gdx.backends.android.AndroidApplication;

import fi.tamk.yourtrueself.YTSAlarmHelper;

public final class YTSAndroidHelper implements YTSAlarmHelper {
    private AndroidApplication app;

    public YTSAndroidHelper(AndroidApplication app) {
        this.app = app;
    }

    @Override
    public void startTimer(int seconds) {
        AlarmManager am = (AlarmManager) app.getSystemService(Context.ALARM_SERVICE);
        Intent in = new Intent(app, AlarmBroadcastReceiver.class);
        PendingIntent pin = PendingIntent.getBroadcast(app, 0, in, 0);

        am.set(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + seconds * 1000,
                pin
        );
    }
}
