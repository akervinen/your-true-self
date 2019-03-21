package fi.tamk.yourtrueself;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

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

    public static final Challenge[] CHALLENGES = {
            new Challenge("chlStrPushups", Player.Stat.STRENGTH, 8),
            new Challenge("chlStrSquats", Player.Stat.STRENGTH, 6),
            new Challenge("chlStrBagCurl", Player.Stat.STRENGTH, 10),
            new Challenge("chlFlxStretch", Player.Stat.FLEXIBILITY),
            new Challenge("chlFlxToes", Player.Stat.FLEXIBILITY, 8),
            new Challenge("chlFlxClaspHands", Player.Stat.FLEXIBILITY, 5),
            new Challenge("chlAgiHang", Player.Stat.AGILITY),
            new Challenge("chlStaPlank", Player.Stat.STAMINA, 10),
            new Challenge("chlBalOneFoot", Player.Stat.BALANCE, 10),
            new Challenge("chlBalHeelToe", Player.Stat.BALANCE, 15)
    };

    private static final String SKIN_PATH = "ui/orange/skin.json";

    private AssetManager assetManager = new AssetManager();
    private I18NBundle bundle;
    private Viewport uiViewport;
    private Skin uiSkin;
    private MainScreen mainScreen;
    private CharacterSelectScreen selectScreen;
    private Preferences prefs;
    private Player player = new Player();
    private Challenge previousChallenge;
    private Challenge currentChallenge;
    private ChallengeCompletedListener challengeCompletedListener;
    private Music mainTheme;

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

        // Add generated fonts here, so that the skin can use them
        ObjectMap<String, Object> fontMap = new ObjectMap<String, Object>();
        fontMap.put("font", fontSmall);
        fontMap.put("font-title", fontTitle);

        SkinLoader.SkinParameter skinParam = new SkinLoader.SkinParameter(fontMap);

        // Add assets to load

        assetManager.load("i18n/YourTrueSelf", I18NBundle.class);
//        assetManager.load("i18n/YourTrueSelf", I18NBundle.class, new I18NBundleLoader.I18NBundleParameter(new Locale("fi", "FI")));
        assetManager.load("characters.atlas", TextureAtlas.class);
        assetManager.load(SKIN_PATH, Skin.class, skinParam);

        assetManager.load("YourTrueSelf_MainthemeOGG.ogg", Music.class);

        // Actually load assets
        // This can be split up if we need a loading screen
        assetManager.finishLoading();

        bundle = assetManager.get("i18n/YourTrueSelf", I18NBundle.class);

        uiSkin = assetManager.get(SKIN_PATH);
        uiSkin.addRegions(assetManager.get("characters.atlas", TextureAtlas.class));

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
        prefs.putString("playerCharacter", chr.getId());
        prefs.flush();
    }

    public void setPlayerCharacter(String chr) {
        for (Character c : CHARACTERS) {
            if (chr.equals(c.getId())) {
                setPlayerCharacter(c);
            }
        }
    }

    public void setMusicVolume(float volume) {
        mainTheme.setVolume(volume);
    }

    public Preferences getPrefs() {
        return prefs;
    }

    public void setPrefs(Preferences prefs) {
        this.prefs = prefs;
    }

    public void setChallengeCompletedListener(ChallengeCompletedListener listener) {
        challengeCompletedListener = listener;
    }

    public void completeChallenge(Challenge chl) {
        // Check if challenge is valid (either normal, daily or MP challenge)
        if (chl != currentChallenge) {
            // Player tried to complete an old or invalid challenge?
            return;
        }

        previousChallenge = chl;
        currentChallenge = null;
        chl.complete(getPlayer());

        if (challengeCompletedListener != null) {
            challengeCompletedListener.challengeCompleted(chl);
        }
    }

    public Challenge getCurrentChallenge() {
        if (currentChallenge == null) {
            currentChallenge = getNextChallenge();
        }
        return currentChallenge;
    }

    public Challenge getNextChallenge() {
        int idx;

        // Avoid getting the exact same challenge as last
        do {
            idx = MathUtils.random(0, CHALLENGES.length - 1);
        } while (previousChallenge != null &&
                CHALLENGES[idx].getId().equals(previousChallenge.getId()));

        return CHALLENGES[idx];
    }

    @Override
    public void create() {
        loadAssets();
        prefs = Gdx.app.getPreferences("YTSPreferences");

        if (prefs.contains("playerCharacter")) {
            setPlayerCharacter(prefs.getString("playerCharacter"));
        }

        uiViewport = new ScreenViewport();

        mainScreen = new MainScreen(this);
        selectScreen = new CharacterSelectScreen(this);

        mainTheme = assetManager.get("YourTrueSelf_MainthemeOGG.ogg");
        mainTheme.setLooping(true);
        mainTheme.setVolume(prefs.getFloat("music", 0.5f));
        mainTheme.play();

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