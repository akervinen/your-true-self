package fi.tamk.yourtrueself;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class YTSGame extends Game {
    private AssetManager assetManager = new AssetManager();

    private void loadAssets() {
        assetManager.load("ui/skin.json", Skin.class);

        MainScreen.loadAssets(assetManager);

        assetManager.finishLoading();
    }

    AssetManager getAssetManager() {
        return assetManager;
    }

    @Override
    public void create() {
        loadAssets();

        setScreen(new MainScreen(this));
    }

    @Override
    public void resume() {
        assetManager.finishLoading();

        super.resume();
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }
}