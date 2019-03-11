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

import fi.tamk.yourtrueself.characters.Character;
import fi.tamk.yourtrueself.characters.CouchPotato;
import fi.tamk.yourtrueself.characters.Enlightened;
import fi.tamk.yourtrueself.characters.Graceful;
import fi.tamk.yourtrueself.characters.MaraThon;
import fi.tamk.yourtrueself.characters.StronkMan;
import fi.tamk.yourtrueself.characters.TheSpider;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class YTSGame extends Game {
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

    private Character[] characters = {
            new CouchPotato(),
            new StronkMan(),
            new Enlightened(),
            new TheSpider(),
            new MaraThon(),
            new Graceful()
    };

    private Character currentCharacter = null;
    private Stats currentStats = new Stats(0, 20, 40, 60, 80);

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

    I18NBundle getBundle() {
        return bundle;
    }

    Viewport getUiViewport() {
        return uiViewport;
    }

    Skin getSkin() {
        return uiSkin;
    }

    public Character[] getCharacters() {
        return characters;
    }

    public Character getCharacter() {
        return currentCharacter;
    }

    public void setCharacter(Character chr) {
        currentCharacter = chr;
    }

    public void setCharacter(String chr) {
        for (Character c : characters) {
            if (chr.equals(c.getId())) {
                currentCharacter = c;
            }
        }
    }

    public Stats getCurrentStats() {
        return currentStats;
    }

    public void train() {
        if (currentCharacter == null) {
            return;
        }

        Stats.Stat mainStat = currentCharacter.getMainStat();

        if (mainStat == Stats.Stat.NONE) {
            for (Stats.Stat stat : Stats.STAT_ENUMS) {
                currentStats.setByEnum(stat, currentStats.getByEnum(stat) + .5f);
            }
        } else {
            currentStats.setByEnum(mainStat, currentStats.getByEnum(mainStat) + 2);
        }
    }

    public void goToMainScreen() {
        setScreen(mainScreen);
    }

    public void goToCharacterSelect() {
        setScreen(selectScreen);
    }

    @Override
    public void create() {
        loadAssets();

        uiViewport = new ScreenViewport();

        mainScreen = new MainScreen(this);
        selectScreen = new CharacterSelectScreen(this);

        if (getCharacter() == null) {
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