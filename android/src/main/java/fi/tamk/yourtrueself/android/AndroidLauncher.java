package fi.tamk.yourtrueself.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import fi.tamk.yourtrueself.BuildConfig;
import fi.tamk.yourtrueself.YTSGame;

/**
 * Launches the Android application.
 */
public class AndroidLauncher extends AndroidApplication {
    /**
     * Creates the Android activity, libGDX Game instance and platform-specific timer helper.
     * Also sets the game into release mode if BUILD_TYPE is "release".
     *
     * @param savedInstanceState {@inheritDoc}
     * @see AndroidApplication#onCreate(Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration configuration = new AndroidApplicationConfiguration();

        YTSAndroidHelper yan = new YTSAndroidHelper(this);

        YTSGame game = new YTSGame();
        game.setAlarmHelper(yan);

        if (BuildConfig.BUILD_TYPE.equals("release")) {
            game.setReleaseMode(true);
        }

        initialize(game, configuration);
    }
}