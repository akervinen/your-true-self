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
    /**
     * List of characters in the game.
     */
    public static final Character[] CHARACTERS = {
            new Character("couchpotato", Player.Stat.NONE),
            new Character("stronkman", Player.Stat.STRENGTH),
            new Character("enlightened", Player.Stat.FLEXIBILITY),
            new Character("thespider", Player.Stat.AGILITY),
            new Character("marathon", Player.Stat.STAMINA),
            new Character("graceful", Player.Stat.BALANCE)
    };

    /**
     * List of challenges in the game.
     */
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

    /**
     * Path to the Scene2D skin to use.
     */
    private static final String SKIN_PATH = "ui/orange/skin.json";

    private AssetManager assetManager = new AssetManager();
    private I18NBundle bundle;
    private Viewport uiViewport;
    private Skin uiSkin;
    private MainScreen mainScreen;
    private CharacterSelectScreen selectScreen;
    private Preferences prefs;

    private YTSAlarmHelper alarmHelper;

    /**
     * Player's information and stats.
     */
    private Player player = new Player();

    /**
     * Last completed challenge, used to avoid generating the same challenge twice in a row.
     */
    private Challenge previousChallenge;

    /**
     * Player's current challenge.
     */
    private Challenge currentChallenge;

    /**
     * Time in milliseconds (System.currentTimeMillis()) when the next challenge appears.
     */
    private long nextChallengeTime;

    /**
     * Callback used after a challenge is completed.
     */
    private ChallengeCompletedListener challengeCompletedListener;
    private Music mainTheme;

    /*
        UI Stuff
     */

    /**
     * Convert font points (72pt = inch) to pixels using the screen's vertical pixel density.
     *
     * @param pt typographical points to convert
     * @return given pt value in pixels
     */
    private int getPointInPixels(float pt) {
        return (int) (pt * (Gdx.graphics.getPpiY() / 72f));
    }

    /**
     * Load game assets.
     */
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

    public void setAlarmHelper(YTSAlarmHelper alarmHelper) {
        this.alarmHelper = alarmHelper;
    }

    /**
     * Get the current language's I18nBundle
     *
     * @return bundle containing the current i18n properties
     */
    public I18NBundle getBundle() {
        return bundle;
    }

    /**
     * Game-wide UI viewport.
     *
     * @return game-wide UI viewport
     */
    public Viewport getUiViewport() {
        return uiViewport;
    }

    /**
     * Game-wide Scene2D skin.
     *
     * @return game-wide scene2d skin
     */
    public Skin getSkin() {
        return uiSkin;
    }

    /**
     * Switch to main game screen.
     */
    public void goToMainScreen() {
        setScreen(mainScreen);
    }

    /**
     * Switch to character selection screen.
     */
    public void goToCharacterSelect() {
        setScreen(selectScreen);
    }

    /*
        Gameplay Stuff
     */

    /**
     * Get the player object.
     *
     * @return current player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Set the player's character.
     *
     * @param chr character to change to
     */
    public void setPlayerCharacter(Character chr) {
        player.setCurrentCharacter(chr);
        prefs.putString("playerCharacter", chr.getId());
        prefs.flush();
    }

    /**
     * Set the player's character by character ID.
     *
     * @param chr ID of the character to change to
     */
    public void setPlayerCharacter(String chr) {
        for (Character c : CHARACTERS) {
            if (chr.equals(c.getId())) {
                setPlayerCharacter(c);
            }
        }
    }

    /**
     * Set volume of the background music.
     *
     * @param volume new music volume
     */
    public void setMusicVolume(float volume) {
        mainTheme.setVolume(volume);
    }

    /**
     * Get saved preferences.
     *
     * @return saved preferences object
     */
    public Preferences getPrefs() {
        return prefs;
    }

    /**
     * Set the preferences object.
     *
     * @param prefs new preferences object
     */
    public void setPrefs(Preferences prefs) {
        this.prefs = prefs;
    }

    /**
     * Set a callback for completed challenges.
     *
     * @param listener callback object to call
     */
    public void setChallengeCompletedListener(ChallengeCompletedListener listener) {
        challengeCompletedListener = listener;
    }

    /**
     * Complete the given active challenge. Checks if the challenge is valid,
     * i.e. if it's one of the currently active challenges.
     *
     * @param chl challenge to complete
     */
    public void completeChallenge(Challenge chl) {
        // Check if challenge is valid (either normal, daily or MP challenge)
        if (chl != currentChallenge) {
            // Player tried to complete an old or invalid challenge?
            return;
        }

        previousChallenge = chl;
        currentChallenge = null;
        chl.complete(getPlayer());
        saveStats();

        if (challengeCompletedListener != null) {
            challengeCompletedListener.challengeCompleted(chl);
        }

        startNextChallengeTimer();
    }

    /**
     * Get the currently active challenge.
     *
     * @return current challenge
     */
    public Challenge getCurrentChallenge() {
        return currentChallenge;
    }

    /**
     * Set current challenge by its ID.
     *
     * @param challengeId ID of the new current challenge
     */
    public void setCurrentChallenge(String challengeId) {
        if (challengeId != null) {
            for (Challenge chl : CHALLENGES) {
                if (chl.getId().equals(challengeId)) {
                    setCurrentChallenge(chl);
                }
            }
        }
    }

    /**
     * Set current challenge and save it in preferences. If given challenge is null,
     * currentChallenge is removed from preferences.
     *
     * @param challenge new current challenge
     */
    public void setCurrentChallenge(Challenge challenge) {
        currentChallenge = challenge;
        if (challenge != null) {
            prefs.putString("currentChallenge", challenge.getId());
        } else {
            prefs.remove("currentChallenge");
        }
        prefs.flush();
    }

    /**
     * Get the time when the next challenge appears in milliseconds according to
     * System.currentTimeMillis
     *
     * @return time for the next challenge
     */
    public long getNextChallengeTime() {
        return nextChallengeTime;
    }

    /**
     * Set the time when the next challenge appears in milliseconds according to
     * System.currentTimeMillis.
     *
     * @param nextChallengeTime time for the next challenge
     */
    public void setNextChallengeTime(long nextChallengeTime) {
        this.nextChallengeTime = nextChallengeTime;

        if (nextChallengeTime == 0) {
            prefs.remove("nextChallengeTime");
        } else {
            prefs.putLong("nextChallengeTime", nextChallengeTime);
        }
        prefs.flush();
    }

    /**
     * Get a random(-ish) challenge. Excludes the most recently completed challenge.
     *
     * @return random challenge excluding last completed one
     */
    private Challenge getNextChallenge() {
        int idx;

        // Avoid getting the exact same challenge as last
        do {
            idx = MathUtils.random(0, CHALLENGES.length - 1);
        } while (previousChallenge != null &&
                CHALLENGES[idx].getId().equals(previousChallenge.getId()));

        return CHALLENGES[idx];
    }

    /**
     * Start timer until next challenge.
     */
    private void startNextChallengeTimer() {
        int nextTime = 10;

        setNextChallengeTime(System.currentTimeMillis() + nextTime * 1000);

        if (alarmHelper != null) {
            alarmHelper.startTimer(nextTime);
        }
    }

    /**
     * Re-read preferences for current challenge, check if next challenge needs to be given
     * and make sure that the player has a challenge if there's no active timer.
     */
    private void refreshChallenges() {
        setCurrentChallenge(prefs.getString("currentChallenge"));

        nextChallengeTime = prefs.getLong("nextChallengeTime", 0);
        if (currentChallenge == null && nextChallengeTime != 0) {
            if (System.currentTimeMillis() >= nextChallengeTime) {
                setNextChallengeTime(0);
                setCurrentChallenge(getNextChallenge());
            }
        }

        if (currentChallenge == null && !prefs.contains("nextChallengeTime")) {
            setCurrentChallenge(getNextChallenge());
        }
    }

    /**
     * Check if any new challenges should be added, return true if any were added.
     *
     * @return true if new challenges were added, otherwise false
     */
    public boolean checkChallenges() {
        if (currentChallenge == null && nextChallengeTime != 0) {
            if (System.currentTimeMillis() >= nextChallengeTime) {
                setNextChallengeTime(0);
                setCurrentChallenge(getNextChallenge());
                return true;
            }
        }

        if (currentChallenge == null && nextChallengeTime == 0) {
            setCurrentChallenge(getNextChallenge());
            return true;
        }

        return false;
    }

    private void loadStats() {
        for (int i = 0; i < Player.STAT_NAMES.length; i++) {
            player.setByEnum(Player.STAT_ENUMS[i], prefs.getFloat(Player.STAT_NAMES[i], 0f));
        }
    }

    public void saveStats() {
        for (int i = 0; i < Player.STAT_NAMES.length; i++) {
            prefs.putFloat(Player.STAT_NAMES[i], player.getByEnum(Player.STAT_ENUMS[i]));
        }
        prefs.flush();
    }

    /**
     * Initialize game.
     */
    @Override
    public void create() {
        loadAssets();
        prefs = Gdx.app.getPreferences("YTSPreferences");

        if (prefs.contains("playerCharacter")) {
            setPlayerCharacter(prefs.getString("playerCharacter"));
        }

        loadStats();
        refreshChallenges();

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

    /**
     * Called when coming back to focus or switching back to the app on Android.
     * Refreshes game challenges.
     */
    @Override
    public void resume() {
        super.resume();

        refreshChallenges();
    }

    /**
     * Dispose game assets.
     */
    @Override
    public void dispose() {
        assetManager.dispose();
    }

}