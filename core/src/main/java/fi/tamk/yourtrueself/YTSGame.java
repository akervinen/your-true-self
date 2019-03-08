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

    private Viewport uiViewport;
    private Skin uiSkin;

    private MainScreen mainScreen;
    private CharacterSelectScreen selectScreen;

    private String currentCharacter = "couchpotato";

    private int getPointInPixels(float pt) {
        return (int) (pt * (Gdx.graphics.getPpiY() / 72f));
    }

    private void loadSkin() {
        uiSkin = new Skin();

        FreeTypeFontGenerator.FreeTypeFontParameter parameter;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/Roboto-Regular.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = getPointInPixels(10);
        BitmapFont fontSmall = generator.generateFont(parameter);


        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = getPointInPixels(18);
        BitmapFont fontTitle = generator.generateFont(parameter);
        generator.dispose();

        uiSkin.add("font", fontSmall, BitmapFont.class);
        uiSkin.add("font-title", fontTitle, BitmapFont.class);

        uiSkin.addRegions(new TextureAtlas("ui/orange/skin.atlas"));
        uiSkin.addRegions(new TextureAtlas("characters.atlas"));
        uiSkin.load(Gdx.files.internal(SKIN_PATH));
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
        loadSkin();

        uiViewport = new ScreenViewport();

        mainScreen = new MainScreen(this);
        selectScreen = new CharacterSelectScreen(this);

        setScreen(selectScreen);
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void dispose() {
        uiSkin.dispose();
    }
}