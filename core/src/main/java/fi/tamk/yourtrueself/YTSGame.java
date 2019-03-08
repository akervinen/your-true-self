package fi.tamk.yourtrueself;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class YTSGame extends Game {
    public static final String SKIN_PATH = "ui/orange/skin.json";
    private AssetManager assetManager = new AssetManager();

    private Viewport uiViewport;
    private Skin uiSkin;

    private MainScreen mainScreen;
    private CharacterSelectScreen selectScreen;

    private String currentCharacter = "couchpotato";

    private int getPointInPixels(float pt) {
        return (int) (pt * (Gdx.graphics.getPpiY() / 72f));
    }

    private void loadAssets() {
        FileHandleResolver resolver = new InternalFileHandleResolver();
        assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        assetManager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        FreeTypeFontLoaderParameter smallFont = new FreeTypeFontLoaderParameter();
        smallFont.fontFileName = "ui/Roboto-Regular.ttf";
        smallFont.fontParameters.size = getPointInPixels(10);
        assetManager.load("ui/Roboto-Regular.ttf", BitmapFont.class, smallFont);

        FreeTypeFontLoaderParameter titleFont = new FreeTypeFontLoaderParameter();
        titleFont.fontFileName = "ui/Roboto-Regular.ttf";
        titleFont.fontParameters.size = getPointInPixels(18);
        assetManager.load("ui/Roboto-Title.ttf", BitmapFont.class, titleFont);

        assetManager.load("characters/couchpotato.png", Texture.class);

        MainScreen.loadAssets(assetManager);

        assetManager.finishLoading();
    }

    private void loadSkin() {
        uiSkin = new Skin();

        uiSkin.add("char-couchpotato", assetManager.get("characters/couchpotato.png", Texture.class));

        uiSkin.add("font", assetManager.get("ui/Roboto-Regular.ttf", BitmapFont.class), BitmapFont.class);
        uiSkin.add("font-title", assetManager.get("ui/Roboto-Title.ttf", BitmapFont.class), BitmapFont.class);

        uiSkin.addRegions(new TextureAtlas("ui/orange/skin.atlas"));
        uiSkin.load(Gdx.files.internal(SKIN_PATH));
    }

    AssetManager getAssetManager() {
        return assetManager;
    }

    Viewport getUiViewport() {
        return uiViewport;
    }

    Skin getSkin() {
        return uiSkin;
    }

    public void goToMainScreen() {
        setScreen(mainScreen);
    }

    public void goToCharacterSelect() {
        setScreen(selectScreen);
    }

    public void chooseCharacter(String chr) {
        currentCharacter = chr;
    }

    @Override
    public void create() {
        loadAssets();
        loadSkin();

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
        uiSkin.dispose();
        assetManager.dispose();
    }
}