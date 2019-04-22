package fi.tamk.yourtrueself.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import fi.tamk.yourtrueself.YTSGame;

/**
 * Launches the desktop (LWJGL) application.
 */
public class DesktopLauncher {
    /**
     * First method called when the game runs. Creates the LWJGL application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        createApplication();
    }

    /**
     * Creates the LWJGL application.
     *
     * @return {@link LwjglApplication} object for the game
     */
    private static LwjglApplication createApplication() {
        return new LwjglApplication(new YTSGame(), getDefaultConfiguration());
    }

    /**
     * Create the default app configuration.
     *
     * @return {@link LwjglApplicationConfiguration} object with default settings
     */
    private static LwjglApplicationConfiguration getDefaultConfiguration() {
        LwjglApplicationConfiguration configuration = new LwjglApplicationConfiguration();
        configuration.title = "Your True Self";
        configuration.width = 360;
        configuration.height = 640;
        /*for (int size : new int[]{128, 64, 32, 16}) {
            configuration.addIcon("libgdx" + size + ".png", FileType.Internal);
        }*/
        return configuration;
    }
}