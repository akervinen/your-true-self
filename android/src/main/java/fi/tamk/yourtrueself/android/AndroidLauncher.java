package fi.tamk.yourtrueself.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import fi.tamk.yourtrueself.YTSGame;

/**
 * Launches the Android application.
 */
public class AndroidLauncher extends AndroidApplication {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration configuration = new AndroidApplicationConfiguration();

        YTSAndroidHelper yan = new YTSAndroidHelper(this);

        YTSGame game = new YTSGame();
        game.setAlarmHelper(yan);

        initialize(game, configuration);
    }
}