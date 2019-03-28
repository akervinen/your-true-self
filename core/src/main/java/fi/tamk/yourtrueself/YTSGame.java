package fi.tamk.yourtrueself;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.I18NBundleLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Locale;

import fi.tamk.yourtrueself.screens.CharacterSelectScreen;
import fi.tamk.yourtrueself.screens.MainScreen;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class YTSGame extends Game {
    public static final String PREF_NAME = "YTSPreferences";

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

    public static final DailyChallenge[] DAILY_CHALLENGES = {
            new DailyChallenge("dchlStairs", Player.Stat.STAMINA)
    };

    /**
     * Path to the Scene2D skin to use.
     */
    private static final String SKIN_PATH = "ui/orange/skin.json";

    private final AssetManager assetManager = new AssetManager();
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
    private final Player player = new Player();

    /**
     * Last completed challenge, used to avoid generating the same challenge twice in a row.
     */
    private Challenge previousChallenge;

    /**
     * Player's current challenge.
     */
    private Challenge currentChallenge;

    /**
     * Time between challenges in seconds.
     */
    private int challengeDelay = 5;

    /**
     * Time in milliseconds (System.currentTimeMillis()) when the next challenge appears.
     */
    private long nextChallengeTime;

    /**
     * Current daily challenge.
     */
    private DailyChallenge currentDaily;

    /**
     * Callback used after a challenge is completed.
     */
    private ChallengeCompletedListener challengeCompletedListener;
    private Music mainTheme;

    /*
        UI Stuff
     */

    /**
     * Load game assets.
     */
    private void loadAssets() {
        // Add custom loaders
        FileHandleResolver resolver = new InternalFileHandleResolver();
        assetManager.setLoader(Skin.class, new YTSSkinLoader(resolver));

        // Add assets to load
        assetManager.load("characters.atlas", TextureAtlas.class);
        assetManager.load(SKIN_PATH, Skin.class);

        assetManager.load("YourTrueSelf_MainthemeOGG.ogg", Music.class);

        // Actually load assets
        // This can be split up if we need a loading screen
        assetManager.finishLoading();

        uiSkin = assetManager.get(SKIN_PATH);
        uiSkin.addRegions(assetManager.get("characters.atlas", TextureAtlas.class));
    }

    /**
     * Set helper for platform-specific background timer functions.
     *
     * @param alarmHelper helper instance
     */
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
        setCurrentChallenge((Challenge) null);
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
     * Set current challenge and save it in preferences. If given challenge is null,
     * currentChallenge is removed from preferences.
     *
     * @param challenge new current challenge
     */
    private void setCurrentChallenge(Challenge challenge) {
        currentChallenge = challenge;
        if (challenge != null) {
            prefs.putString("currentChallenge", challenge.getId());
        } else {
            prefs.remove("currentChallenge");
        }
        prefs.flush();
    }

    /**
     * Set current challenge by its ID.
     *
     * @param challengeId ID of the new current challenge
     */
    private void setCurrentChallenge(String challengeId) {
        if (challengeId != null) {
            for (Challenge chl : CHALLENGES) {
                if (chl.getId().equals(challengeId)) {
                    setCurrentChallenge(chl);
                }
            }
        }
    }

    /**
     * Set the delay between challenges.
     *
     * @param challengeDelay new delay in seconds
     */
    public void setChallengeDelay(int challengeDelay) {
        this.challengeDelay = challengeDelay;
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
    private void setNextChallengeTime(long nextChallengeTime) {
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
        int nextTime = challengeDelay;

        setNextChallengeTime(System.currentTimeMillis() + nextTime * 1000);

        if (alarmHelper != null) {
            alarmHelper.startTimer(nextTime);
        }
    }

    public DailyChallenge getCurrentDaily() {
        return currentDaily;
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

    /**
     * Load player stats from preferences.
     */
    private void loadStats() {
        for (int i = 0; i < Player.STAT_NAMES.length; i++) {
            player.setByEnum(Player.STAT_ENUMS[i], prefs.getFloat(Player.STAT_NAMES[i], 0f));
        }
    }

    /**
     * Save player stats to preferences.
     */
    private void saveStats() {
        for (int i = 0; i < Player.STAT_NAMES.length; i++) {
            prefs.putFloat(Player.STAT_NAMES[i], player.getByEnum(Player.STAT_ENUMS[i]));
        }
        prefs.flush();
    }

    /**
     * Create a Locale instance from preferences language code.
     *
     * @param lang language code (en or fi)
     * @return a Locale instance of given language
     */
    public static Locale localeFromPref(String lang) {
        if (lang != null && lang.toLowerCase().equals("fi")) {
            return new Locale("fi");
        } else {
            return new Locale("en");
        }
    }

    /**
     * Load language file of given Locale.
     *
     * @param locale Locale of bundle to load
     */
    private void loadLanguage(Locale locale) {
        if (assetManager.contains("i18n/YourTrueSelf")) {
            assetManager.unload("i18n/YourTrueSelf");
        }
        assetManager.load("i18n/YourTrueSelf", I18NBundle.class, new I18NBundleLoader.I18NBundleParameter(locale));
        assetManager.finishLoadingAsset("i18n/YourTrueSelf");
        bundle = assetManager.get("i18n/YourTrueSelf");

        uiSkin.add("i18n-bundle", bundle, I18NBundle.class);
    }

    /**
     * Change language according to given language code and refresh screens.
     *
     * @param lang language code
     */
    public void changeLanguage(String lang) {
        Locale locale = localeFromPref(lang);
        loadLanguage(locale);

        mainScreen = new MainScreen(this);
        selectScreen = new CharacterSelectScreen(this);

        if (player.getCurrentCharacter() == null) {
            setScreen(selectScreen);
        } else {
            setScreen(mainScreen);
        }
    }

    /**
     * Initialize game.
     */
    @Override
    public void create() {
        prefs = Gdx.app.getPreferences(PREF_NAME);

        if (prefs.contains("playerCharacter")) {
            setPlayerCharacter(prefs.getString("playerCharacter"));
        }

        // If no language is set, set it according to platform's default locale
        Locale locale;
        if (!prefs.contains("lang")) {
            if (Locale.getDefault().getLanguage().equals(new Locale("fi").getLanguage())) {
                locale = localeFromPref("fi");
                prefs.putString("lang", "fi");
            } else {
                locale = localeFromPref("en");
                prefs.putString("lang", "en");
            }
            prefs.flush();
        } else {
            locale = localeFromPref(prefs.getString("lang", "en"));
        }

        loadAssets();
        loadLanguage(locale);

        loadStats();
        refreshChallenges();
        currentDaily = DAILY_CHALLENGES[0];

        uiViewport = new ScreenViewport();

        mainScreen = new MainScreen(this);
        selectScreen = new CharacterSelectScreen(this);

        mainTheme = assetManager.get("YourTrueSelf_MainthemeOGG.ogg");
        mainTheme.setLooping(true);
        mainTheme.setVolume(prefs.getFloat("music", 0.5f));
        mainTheme.play();

        Gdx.graphics.setContinuousRendering(false);
        Gdx.graphics.requestRendering();

        if (player.getCurrentCharacter() == null) {
            setScreen(selectScreen);
        } else {
            setScreen(mainScreen);
        }
    }

    @Override
    public void resize(int width, int height) {
        getUiViewport().update(width, height, true);

        super.resize(width, height);
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