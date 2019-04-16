package fi.tamk.yourtrueself;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.I18NBundleLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import fi.tamk.yourtrueself.screens.CharacterSelectScreen;
import fi.tamk.yourtrueself.screens.MainScreen;

/**
 * Container class for the game's challenges
 */
class YTSChallenges {
    /**
     * List of challenges in the game.
     */
    static final Challenge[] CHALLENGES = {
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
     * List of daily challenges in the game.
     */
    static final DailyChallenge[] DAILY_CHALLENGES = {
            new DailyChallenge("dchlStairs", Player.Stat.NONE, false),
            new DailyChallenge("dchlLongWalk", Player.Stat.NONE, true),
            new DailyChallenge("dchlNewSport", Player.Stat.NONE, true),
            new DailyChallenge("dchlWalkSchool", Player.Stat.NONE, true),
            new DailyChallenge("dchlStand", Player.Stat.NONE, false),
            new DailyChallenge("dchlStretch", Player.Stat.NONE, false),
            new DailyChallenge("dchlWaterBottle", Player.Stat.NONE, false),
            new DailyChallenge("dchlShoulders", Player.Stat.NONE, false),
            new DailyChallenge("dchlSkipping", Player.Stat.NONE, false),
            new DailyChallenge("dchlFace2Face", Player.Stat.NONE, false),
            new DailyChallenge("dchlNextWeek", Player.Stat.NONE, true),
            new DailyChallenge("dchlOutside", Player.Stat.NONE, false),
            new DailyChallenge("dchlCleaning", Player.Stat.NONE, false),
            new DailyChallenge("dchlSquatting", Player.Stat.NONE, false),
            new DailyChallenge("dchlMorning", Player.Stat.NONE, true),
            new DailyChallenge("dchlAskFriend", Player.Stat.NONE, true),
            new DailyChallenge("dchlSleep", Player.Stat.NONE, false),
            new DailyChallenge("dchlDance", Player.Stat.NONE, false),
            new DailyChallenge("dchlShopping", Player.Stat.NONE, true),
            new DailyChallenge("dchlPomodoro", Player.Stat.NONE, false)
    };
}

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
     * List of achievements.
     */
    public static final Achievement[] ACHIEVEMENTS = {
            new TieredAchievement("ach.Challenge", 100, new int[]{1, 5, 15, 25, 50, 100}),
            new TieredAchievement("ach.Daily", 10, new int[]{1, 3, 6, 10}),
    };

    /**
     * Chance of picking an off-stat challenge.
     */
    private static final float OFF_STAT_CHANCE = 0.3f;

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

    private YTSTimerHelper alarmHelper;

    /**
     * Set to true to use longer challenge delays. Default is false.
     */
    private boolean releaseMode = false;

    /**
     * Achievement manager.
     */
    private final AchievementManager achievementManager = new AchievementManager(ACHIEVEMENTS);

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
     * Time in milliseconds (System.currentTimeMillis()) when the next challenge appears.
     */
    private long nextChallengeTime;

    /**
     * Time in milliseconds (System.currentTimeMillis()) when the next daily appears.
     */
    private long nextDailyTime;

    /**
     * Current daily challenge.
     */
    private DailyChallenge currentDaily;

    /**
     * Time in milliseconds (System.currentTimeMillis()) when the current daily started.
     */
    private long currentDailyStartTime;

    /**
     * Callback used after a challenge is completed.
     */
    private ChallengeCompletedListener challengeCompletedListener;
    private Music mainTheme;
    private Map<String, Sound> soundMap = new HashMap<String, Sound>();
    private float soundVolume = DefaultPreferences.PREF_SOUND_DEFAULT;

    /**
     * Set release mode. Release mode increases challenge delays to proper values.
     * Default is false.
     *
     * @param releaseMode true to enable release mode
     */
    public void setReleaseMode(boolean releaseMode) {
        this.releaseMode = releaseMode;
    }

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

        assetManager.load("sounds/SelectedCouchpotato.ogg", Sound.class);
        assetManager.load("sounds/SelectedEnlightened.ogg", Sound.class);
        assetManager.load("sounds/SelectedGraceful.ogg", Sound.class);
        assetManager.load("sounds/SelectedSpider.ogg", Sound.class);
        assetManager.load("sounds/SelectedStronkperson.ogg", Sound.class);
        assetManager.load("sounds/SelectedCouchpotato.ogg", Sound.class);
        assetManager.load("sounds/ButtonPress.ogg", Sound.class);
        assetManager.load("sounds/CompletedChallenge.ogg", Sound.class);
        assetManager.load("sounds/Notification.ogg", Sound.class);
        assetManager.load("sounds/NotThere.ogg", Sound.class);
        assetManager.load("sounds/Success.ogg", Sound.class);

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
    public void setAlarmHelper(YTSTimerHelper alarmHelper) {
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
     * Get achievement manager.
     *
     * @return game achievement manager
     */
    public AchievementManager getAchievementManager() {
        return achievementManager;
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
        prefs.putString(DefaultPreferences.PREF_CHARACTER, chr.getId());
        prefs.flush();
        Sound soundEffect = soundMap.get(chr.getId());
        if (soundEffect != null) {
            soundEffect.play(getSoundVolume());
        }
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
     * @param volume new music volume, 0 = mute, 1 = full
     */
    public void setMusicVolume(float volume) {
        mainTheme.setVolume(volume);
    }

    /**
     * Set volume of sound effects.
     *
     * @param volume new sound volume, 0 = mute, 1 = full
     */
    public void setSoundVolume(float volume) {
        soundVolume = volume;
    }

    /**
     * Get current sound volume.
     *
     * @return current sound volume
     */
    public float getSoundVolume() {
        return soundVolume;
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
     * @param chl     challenge to complete
     * @param skipped whether challenge was skipped
     */
    public void completeChallenge(Challenge chl, boolean skipped) {
        // Check if challenge is valid (either normal, daily or MP challenge)
        if (chl != currentChallenge && chl != currentDaily) {
            // Player tried to complete an old or invalid challenge?
            return;
        }

        if (chl instanceof DailyChallenge) {
            setCurrentDaily((DailyChallenge) null);
            startNextDailyTimer();
            if (!skipped) {
                achievementManager.increaseProgress("ach.Daily", 1);
            }
        } else {
            previousChallenge = chl;
            setCurrentChallenge((Challenge) null);
            startNextChallengeTimer();
            if (!skipped) {
                achievementManager.increaseProgress("ach.Challenge", 1);
            }
        }

        if (!skipped) {
            chl.complete(getPlayer());
            saveStats();
            soundMap.get("completedChallenge").play(getSoundVolume());

            if (challengeCompletedListener != null) {
                challengeCompletedListener.challengeCompleted(chl);
            }
        } else {
            soundMap.get("notThere").play(getSoundVolume());
        }
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
            prefs.putString(DefaultPreferences.PREF_CHL_CURRENT, challenge.getId());
        } else {
            prefs.remove(DefaultPreferences.PREF_CHL_CURRENT);
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
            for (Challenge chl : YTSChallenges.CHALLENGES) {
                if (chl.getId().equals(challengeId)) {
                    setCurrentChallenge(chl);
                }
            }
        }
    }

    /**
     * Get the time when the next challenge appears in milliseconds according to
     * System.currentTimeMillis
     *
     * @return time for the next challenge
     */
    public long getNextDailyTime() {
        return nextDailyTime;
    }

    /**
     * Set the time when the next challenge appears in milliseconds according to
     * System.currentTimeMillis.
     *
     * @param nextDailyTime time for the next challenge
     */
    private void setNextDailyTime(long nextDailyTime) {
        this.nextDailyTime = nextDailyTime;

        if (nextDailyTime == 0) {
            prefs.remove(DefaultPreferences.PREF_DAILY_NEXT);
        } else {
            prefs.putLong(DefaultPreferences.PREF_DAILY_NEXT, nextDailyTime);
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
    private void setNextChallengeTime(long nextChallengeTime) {
        this.nextChallengeTime = nextChallengeTime;

        if (nextChallengeTime == 0) {
            prefs.remove(DefaultPreferences.PREF_CHL_NEXT);
        } else {
            prefs.putLong(DefaultPreferences.PREF_CHL_NEXT, nextChallengeTime);
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

        boolean pickOffStat = MathUtils.random() <= OFF_STAT_CHANCE;
        boolean isOffStat;

        Character chr = player.getCurrentCharacter();
        if (chr == null) {
            return null;
        }

        // Couchpotato (stat = NONE) only picks its own challenges.
        if (chr.getMainStat() == Player.Stat.NONE) {
            pickOffStat = false;
        }

        // If only one main stat challenge exists, force-enable off-stat challenges to avoid
        // an infinite loop.
        int mainStatChls = 0;
        for (Challenge chl : YTSChallenges.CHALLENGES) {
            if (chl.mainStat == chr.getMainStat()) {
                mainStatChls += 1;
            }
        }
        if (mainStatChls <= 1) {
            pickOffStat = true;
        }

        // Avoid getting the exact same challenge as last
        do {
            idx = MathUtils.random(0, YTSChallenges.CHALLENGES.length - 1);
            isOffStat = YTSChallenges.CHALLENGES[idx].mainStat != chr.getMainStat();
        } while ((isOffStat && !pickOffStat) ||
                (previousChallenge != null &&
                        YTSChallenges.CHALLENGES[idx].getId().equals(previousChallenge.getId())));

        return YTSChallenges.CHALLENGES[idx];
    }

    /**
     * Get a random(-ish) daily challenge.
     *
     * @return random daily challenge
     */
    private DailyChallenge getNextDaily() {
        int idx = MathUtils.random(0, YTSChallenges.DAILY_CHALLENGES.length - 1);
        return YTSChallenges.DAILY_CHALLENGES[idx];
    }

    /**
     * Start timer until next challenge.
     */
    private void startNextChallengeTimer() {
        int delay = 5;
        if (releaseMode) {
            delay = 90 * 60;
        }

        Calendar c = Calendar.getInstance();
        c.add(Calendar.SECOND, delay);

        int dndStart = prefs.getInteger(DefaultPreferences.PREF_DND_START, DefaultPreferences.PREF_DND_START_DEFAULT);
        int dndEnd = prefs.getInteger(DefaultPreferences.PREF_DND_END, DefaultPreferences.PREF_DND_END_DEFAULT);

        int possibleHour = c.get(Calendar.HOUR_OF_DAY);
        if (dndStart < dndEnd) {
            if (possibleHour >= dndStart && possibleHour < dndEnd) {
                c.set(Calendar.HOUR_OF_DAY, dndEnd);
                c.set(Calendar.MINUTE, 0);
                c.set(Calendar.SECOND, 0);
            }
        } else {
            if (possibleHour >= dndStart || possibleHour < dndEnd) {
                c.add(Calendar.DAY_OF_MONTH, 1);
                c.set(Calendar.HOUR_OF_DAY, dndEnd);
                c.set(Calendar.MINUTE, 0);
                c.set(Calendar.SECOND, 0);
            }
        }

        setNextChallengeTime(c.getTimeInMillis());

        if (alarmHelper != null) {
            alarmHelper.startTimer(c.getTimeInMillis());
        }
    }

    /**
     * Start timer until next challenge.
     */
    private void startNextDailyTimer() {
        if (releaseMode) {
            Calendar c = Calendar.getInstance();

            if (currentDailyStartTime != 0) {
                c.setTimeInMillis(currentDailyStartTime);
            }

            c.add(Calendar.DAY_OF_MONTH, 1);
            c.set(Calendar.HOUR_OF_DAY, prefs.getInteger(DefaultPreferences.PREF_DND_END, DefaultPreferences.PREF_DND_END_DEFAULT));
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);

            setNextDailyTime(c.getTimeInMillis());
        } else {
            setNextDailyTime(System.currentTimeMillis() + 15 * 1000);
        }
    }

    /**
     * Get current daily challenge.
     *
     * @return current daily challenge, or null
     */
    public DailyChallenge getCurrentDaily() {
        return currentDaily;
    }

    /**
     * Set current daily challenge. Can be null to remove daily.
     *
     * @param daily new daily challenge or null
     */
    private void setCurrentDaily(DailyChallenge daily) {
        currentDaily = daily;
        if (daily != null) {
            prefs.putString(DefaultPreferences.PREF_DAILY_CURRENT, daily.getId());
            if (!prefs.contains(DefaultPreferences.PREF_DAILY_STARTED)) {
                prefs.putLong(DefaultPreferences.PREF_DAILY_STARTED, System.currentTimeMillis());
            }
        } else {
            prefs.remove(DefaultPreferences.PREF_DAILY_CURRENT);
            prefs.remove(DefaultPreferences.PREF_DAILY_STARTED);
        }
        prefs.flush();
    }

    /**
     * Set current daily challenge by its ID.
     *
     * @param challengeId ID of the new daily challenge
     */
    private void setCurrentDaily(String challengeId) {
        if (challengeId != null) {
            for (DailyChallenge chl : YTSChallenges.DAILY_CHALLENGES) {
                if (chl.getId().equals(challengeId)) {
                    setCurrentDaily(chl);
                }
            }
        }
    }

    /**
     * Re-read preferences for current challenge, check if next challenge needs to be given
     * and make sure that the player has a challenge if there's no active timer.
     */
    private void refreshChallenges() {
        setCurrentChallenge(prefs.getString(DefaultPreferences.PREF_CHL_CURRENT));
        nextChallengeTime = prefs.getLong(DefaultPreferences.PREF_CHL_NEXT, 0);
        setCurrentDaily(prefs.getString(DefaultPreferences.PREF_DAILY_CURRENT));
        currentDailyStartTime = prefs.getLong(DefaultPreferences.PREF_DAILY_STARTED);
        nextDailyTime = prefs.getLong(DefaultPreferences.PREF_DAILY_NEXT);

        checkChallenges();
    }

    /**
     * Check if any new challenges should be added, return true if any were added.
     *
     * @return true if new challenges were added, otherwise false
     */
    public boolean checkChallenges() {
        boolean changed = false;

        if (currentChallenge == null) {
            if (System.currentTimeMillis() >= nextChallengeTime) {
                setNextChallengeTime(0);
                setCurrentChallenge(getNextChallenge());
                soundMap.get("notification").play(getSoundVolume());
                changed = true;
            }
        }

        if (currentDaily == null) {
            if (System.currentTimeMillis() >= nextDailyTime) {
                setCurrentDaily(getNextDaily());
                setNextDailyTime(0);
                soundMap.get("notification").play(getSoundVolume());
                changed = true;
            }
        }

        return changed;
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
            mainScreen.preferences();
        }
    }

    /**
     * Convert given pixel value to dp (Density Independent Pixel) value.
     *
     * @param px pixel value to convert
     * @return given pixel value in dp
     */
    public static float dp(float px) {
        return px * Gdx.graphics.getDensity();
    }

    private void createSounds() {
        soundMap.put("couchpotato", assetManager.get("sounds/SelectedCouchpotato.ogg", Sound.class));
        soundMap.put("enlightened", assetManager.get("sounds/SelectedEnlightened.ogg", Sound.class));
        soundMap.put("graceful", assetManager.get("sounds/SelectedGraceful.ogg", Sound.class));
        soundMap.put("thespider", assetManager.get("sounds/SelectedSpider.ogg", Sound.class));
        soundMap.put("stronkman", assetManager.get("sounds/SelectedStronkperson.ogg", Sound.class));
        soundMap.put("marathon", assetManager.get("sounds/SelectedCouchpotato.ogg", Sound.class));
        soundMap.put("buttonPress", assetManager.get("sounds/ButtonPress.ogg", Sound.class));
        soundMap.put("completedChallenge", assetManager.get("sounds/CompletedChallenge.ogg", Sound.class));
        soundMap.put("notification", assetManager.get("sounds/Notification.ogg", Sound.class));
        soundMap.put("notThere", assetManager.get("sounds/NotThere.ogg", Sound.class));
        soundMap.put("success", assetManager.get("sounds/Success.ogg", Sound.class));

        setSoundVolume(prefs.getFloat(DefaultPreferences.PREF_SOUND, DefaultPreferences.PREF_SOUND_DEFAULT));
    }

    public void playSound(String soundName) {
        Sound soundEffect = soundMap.get(soundName);
        if (soundEffect != null) {
            soundEffect.play(getSoundVolume());
        } else {
            soundMap.get("notThere").play(getSoundVolume());
        }
    }

    /**
     * Initialize game.
     */
    @Override
    public void create() {
        prefs = Gdx.app.getPreferences(DefaultPreferences.PREF_NAME);

        if (prefs.contains(DefaultPreferences.PREF_CHARACTER)) {
            setPlayerCharacter(prefs.getString(DefaultPreferences.PREF_CHARACTER));
        }

        // If no language is set, set it according to platform's default locale
        Locale locale;
        if (!prefs.contains(DefaultPreferences.PREF_LANGUAGE)) {
            if (Locale.getDefault().getLanguage().equals(new Locale("fi").getLanguage())) {
                locale = localeFromPref("fi");
                prefs.putString(DefaultPreferences.PREF_LANGUAGE, "fi");
            } else {
                locale = localeFromPref("en");
                prefs.putString(DefaultPreferences.PREF_LANGUAGE, "en");
            }
            prefs.flush();
        } else {
            locale = localeFromPref(prefs.getString(DefaultPreferences.PREF_LANGUAGE, "en"));
        }

        loadAssets();

        createSounds();

        loadLanguage(locale);

        loadStats();
        achievementManager.loadProgress(prefs);
        refreshChallenges();

        uiViewport = new ScreenViewport();

        mainScreen = new MainScreen(this);
        selectScreen = new CharacterSelectScreen(this);

        mainTheme = assetManager.get("YourTrueSelf_MainthemeOGG.ogg");
        mainTheme.setLooping(true);
        mainTheme.setVolume(prefs.getFloat(DefaultPreferences.PREF_MUSIC, DefaultPreferences.PREF_MUSIC_DEFAULT));
        mainTheme.play();

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

    @Override
    public void pause() {
        super.pause();

        saveStats();
        achievementManager.saveProgress(prefs);
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