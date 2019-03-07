package fi.tamk.yourtrueself;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class YTSGame extends Game {
    private AssetManager assetManager = new AssetManager();

    private Viewport uiViewport;

    private MainScreen mainScreen;
    private CharacterSelectScreen selectScreen;

    private void loadAssets() {
        assetManager.load("ui/skin.json", Skin.class);
        assetManager.load("characters/couchpotato.png", Texture.class);

        MainScreen.loadAssets(assetManager);

        assetManager.finishLoading();
    }

    AssetManager getAssetManager() {
        return assetManager;
    }

    Viewport getUiViewport() {
        return uiViewport;
    }

    public void goToMainScreen() {
        setScreen(mainScreen);
    }

    @Override
    public void create() {
        loadAssets();

        uiViewport = new ScreenViewport();

        mainScreen = new MainScreen(this);
        selectScreen = new CharacterSelectScreen(this);

        setScreen(selectScreen);
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