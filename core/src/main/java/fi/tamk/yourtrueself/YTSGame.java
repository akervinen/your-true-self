package fi.tamk.yourtrueself;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.I18NBundleLoader;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Locale;

import fi.tamk.yourtrueself.screens.CharacterSelectScreen;
import fi.tamk.yourtrueself.screens.MainScreen;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class YTSGame extends Game {
    public static final Character[] CHARACTERS = {
            new Character("couchpotato", Player.Stat.NONE),
            new Character("stronkman", Player.Stat.STRENGTH),
            new Character("enlightened", Player.Stat.FLEXIBILITY),
            new Character("thespider", Player.Stat.AGILITY),
            new Character("marathon", Player.Stat.STAMINA),
            new Character("graceful", Player.Stat.BALANCE)
    };

    private static final String SKIN_PATH = "ui/orange/skin.json";

    private static final AssetDescriptor<TextureAtlas> characterAtlasAsset =
            new AssetDescriptor<TextureAtlas>("characters.atlas", TextureAtlas.class);
    private static final AssetDescriptor<I18NBundle> bundleAsset =
            new AssetDescriptor<I18NBundle>("i18n/YourTrueSelf", I18NBundle.class);
    private static final AssetDescriptor<I18NBundle> bundleAssetFI =
            new AssetDescriptor<I18NBundle>("i18n/YourTrueSelf", I18NBundle.class, new I18NBundleLoader.I18NBundleParameter(new Locale("fi", "FI")));

    private AssetManager assetManager = new AssetManager();
    private I18NBundle bundle;
    private Viewport uiViewport;
    private Skin uiSkin;
    private MainScreen mainScreen;
    private CharacterSelectScreen selectScreen;
    private Player player = new Player();

    /*
        UI Stuff
     */

    private int getPointInPixels(float pt) {
        return (int) (pt * (Gdx.graphics.getPpiY() / 72f));
    }

    private void loadAssets() {
        FreeTypeFontGenerator.FreeTypeFontParameter parameter;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/Roboto-Regular.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = getPointInPixels(10);
        BitmapFont fontSmall = generator.generateFont(parameter);

        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = getPointInPixels(18);
        BitmapFont fontTitle = generator.generateFont(parameter);
        generator.dispose();

        ObjectMap<String, Object> fontMap = new ObjectMap<String, Object>();
        fontMap.put("font", fontSmall);
        fontMap.put("font-title", fontTitle);

        SkinLoader.SkinParameter skinParam = new SkinLoader.SkinParameter(fontMap);

        assetManager.load(bundleAsset);
//        assetManager.load(bundleAssetFI);
        assetManager.load(characterAtlasAsset);
        assetManager.load(SKIN_PATH, Skin.class, skinParam);

        assetManager.finishLoading();

        bundle = assetManager.get(bundleAsset);

        uiSkin = assetManager.get(SKIN_PATH);
        uiSkin.addRegions(assetManager.get(characterAtlasAsset));

        uiSkin.add("i18n-bundle", bundle, I18NBundle.class);
    }

    public I18NBundle getBundle() {
        return bundle;
    }

    public Viewport getUiViewport() {
        return uiViewport;
    }

    public Skin getSkin() {
        return uiSkin;
    }

    public void goToMainScreen() {
        setScreen(mainScreen);
    }

    /*
        Gameplay Stuff
     */

    public void goToCharacterSelect() {
        setScreen(selectScreen);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayerCharacter(Character chr) {
        player.setCurrentCharacter(chr);
    }

    public void setPlayerCharacter(String chr) {
        for (Character c : CHARACTERS) {
            if (chr.equals(c.getId())) {
                setPlayerCharacter(c);
            }
        }
    }

    @Override
    public void create() {
        loadAssets();

        uiViewport = new ScreenViewport();

        mainScreen = new MainScreen(this);
        selectScreen = new CharacterSelectScreen(this);

        if (player.getCurrentCharacter() == null) {
            setScreen(selectScreen);
        } else {
            setScreen(mainScreen);
        }
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }
}