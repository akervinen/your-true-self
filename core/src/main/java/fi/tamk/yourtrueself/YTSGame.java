package fi.tamk.yourtrueself;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.I18NBundleLoader;
import com.badlogic.gdx.assets.loaders.SkinLoader;
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
     * List of challenges for Couch Potato.
     */
    static final Challenge[] POTATO_CHALLENGES = {
            new Challenge("chl.Pot.Fridge", Player.Stat.STAMINA),
            new Challenge("chl.Pot.Vacuum", Player.Stat.STAMINA),
            new Challenge("chl.Pot.Chair", Player.Stat.AGILITY),
            new Challenge("chl.Pot.Crunch", Player.Stat.STRENGTH),
            new Challenge("chl.Pot.Pushup", Player.Stat.STRENGTH),
            new Challenge("chl.Pot.Squat", Player.Stat.STRENGTH),
            new Challenge("chl.Pot.LowerLegs", Player.Stat.STRENGTH),
            new Challenge("chl.Pot.Hands", Player.Stat.FLEXIBILITY),
            new Challenge("chl.Pot.Neck", Player.Stat.FLEXIBILITY),
            new Challenge("chl.Pot.Flap", Player.Stat.STRENGTH),
            new Challenge("chl.Pot.NextFloor", Player.Stat.STRENGTH),
            new Challenge("chl.Pot.Remote", Player.Stat.STAMINA),
            new Challenge("chl.Pot.Shoulders", Player.Stat.FLEXIBILITY),
            new Challenge("chl.Pot.Wrists", Player.Stat.FLEXIBILITY),
            new Challenge("chl.Pot.Shake", Player.Stat.AGILITY),
            new Challenge("chl.Pot.Laugh", Player.Stat.BALANCE),
            new Challenge("chl.Pot.Sway", Player.Stat.BALANCE),
            new Challenge("chl.Pot.Listen", Player.Stat.FLEXIBILITY),
            new Challenge("chl.Pot.Stand", Player.Stat.STAMINA),
            new Challenge("chl.Pot.Fruit", Player.Stat.STAMINA),
    };

    /**
     * List of challenges in the game.
     */
    static final Challenge[] CHALLENGES = {
            // Potato challenges
            // REMEMBER TO CHANGE THESE IF YOU CHANGE THE ONES ABOVE
            new Challenge("chl.Pot.Fridge", Player.Stat.STAMINA),
            new Challenge("chl.Pot.Vacuum", Player.Stat.STAMINA),
            new Challenge("chl.Pot.Chair", Player.Stat.AGILITY),
            new Challenge("chl.Pot.Crunch", Player.Stat.STRENGTH),
            new Challenge("chl.Pot.Pushup", Player.Stat.STRENGTH),
            new Challenge("chl.Pot.Squat", Player.Stat.STRENGTH),
            new Challenge("chl.Pot.LowerLegs", Player.Stat.STRENGTH),
            new Challenge("chl.Pot.Hands", Player.Stat.FLEXIBILITY),
            new Challenge("chl.Pot.Neck", Player.Stat.FLEXIBILITY),
            new Challenge("chl.Pot.Flap", Player.Stat.STRENGTH),
            new Challenge("chl.Pot.NextFloor", Player.Stat.STRENGTH),
            new Challenge("chl.Pot.Remote", Player.Stat.STAMINA),
            new Challenge("chl.Pot.Shoulders", Player.Stat.FLEXIBILITY),
            new Challenge("chl.Pot.Wrists", Player.Stat.FLEXIBILITY),
            new Challenge("chl.Pot.Shake", Player.Stat.AGILITY),
            new Challenge("chl.Pot.Laugh", Player.Stat.BALANCE),
            new Challenge("chl.Pot.Sway", Player.Stat.BALANCE),
            new Challenge("chl.Pot.Listen", Player.Stat.FLEXIBILITY),
            new Challenge("chl.Pot.Stand", Player.Stat.STAMINA),
            new Challenge("chl.Pot.Fruit", Player.Stat.STAMINA),

            // Normal challenges
            new Challenge("chl.Str.Pushups", Player.Stat.STRENGTH),
            new Challenge("chl.Str.Squats", Player.Stat.STRENGTH),
            new Challenge("chl.Str.Biceps", Player.Stat.STRENGTH),
            new Challenge("chl.Str.LiftBag", Player.Stat.STRENGTH),
            new Challenge("chl.Str.LiftFriend", Player.Stat.STRENGTH),
            new Challenge("chl.Str.LiftSelf", Player.Stat.STRENGTH),
            new Challenge("chl.Str.Dip", Player.Stat.STRENGTH),
            new Challenge("chl.Str.AbsHold", Player.Stat.STRENGTH, 1),
            new Challenge("chl.Str.PushPalms", Player.Stat.STRENGTH),
            new Challenge("chl.Str.Superhero", Player.Stat.STRENGTH),
            new Challenge("chl.Str.Lunges", Player.Stat.STRENGTH, 10),
            new Challenge("chl.Str.MoveWall", Player.Stat.STRENGTH),
            new Challenge("chl.Str.ElbowLift", Player.Stat.STRENGTH),
            new Challenge("chl.Str.BicycleCrunch", Player.Stat.STRENGTH),
            new Challenge("chl.Str.Sideways", Player.Stat.STRENGTH),
            new Challenge("chl.Str.Plank", Player.Stat.STRENGTH, 1),
            new Challenge("chl.Str.ThighSqueeze", Player.Stat.STRENGTH),
            new Challenge("chl.Str.LegHold", Player.Stat.STRENGTH, 1),
            new Challenge("chl.Str.LiftSide", Player.Stat.STRENGTH),
            new Challenge("chl.Str.ArmWrestle", Player.Stat.STRENGTH),
            new Challenge("chl.Flx.CatCow", Player.Stat.FLEXIBILITY),
            new Challenge("chl.Flx.BendForward", Player.Stat.FLEXIBILITY),
            new Challenge("chl.Flx.GrapHands", Player.Stat.FLEXIBILITY),
            new Challenge("chl.Flx.FloorRotation", Player.Stat.FLEXIBILITY),
            new Challenge("chl.Flx.Arch", Player.Stat.FLEXIBILITY),
            new Challenge("chl.Flx.SitStanding", Player.Stat.FLEXIBILITY),
            new Challenge("chl.Flx.Sway", Player.Stat.FLEXIBILITY),
            new Challenge("chl.Flx.SideStretch", Player.Stat.FLEXIBILITY),
            new Challenge("chl.Flx.SitRotation", Player.Stat.FLEXIBILITY),
            new Challenge("chl.Flx.SitStretch", Player.Stat.FLEXIBILITY),
            new Challenge("chl.Flx.ThighStretch", Player.Stat.FLEXIBILITY),
            new Challenge("chl.Flx.WallStretch", Player.Stat.FLEXIBILITY),
            new Challenge("chl.Flx.Split", Player.Stat.FLEXIBILITY),
            new Challenge("chl.Flx.BendDown", Player.Stat.FLEXIBILITY),
            new Challenge("chl.Flx.Meditation", Player.Stat.FLEXIBILITY),
            new Challenge("chl.Flx.BottomStretch", Player.Stat.FLEXIBILITY),
            new Challenge("chl.Flx.HandBreathing", Player.Stat.FLEXIBILITY),
            new Challenge("chl.Flx.Warrior", Player.Stat.FLEXIBILITY),
            new Challenge("chl.Flx.Cobra", Player.Stat.FLEXIBILITY),
            new Challenge("chl.Flx.DownwardDog", Player.Stat.FLEXIBILITY),
            new Challenge("chl.Agi.ClimbTree", Player.Stat.AGILITY),
            new Challenge("chl.Agi.PlankClapping", Player.Stat.AGILITY),
            new Challenge("chl.Agi.TwoSteps", Player.Stat.AGILITY),
            new Challenge("chl.Agi.Cartwheel", Player.Stat.AGILITY),
            new Challenge("chl.Agi.Hopscotch", Player.Stat.AGILITY),
            new Challenge("chl.Agi.RollForward", Player.Stat.AGILITY),
            new Challenge("chl.Agi.PrecisionJump", Player.Stat.AGILITY),
            new Challenge("chl.Agi.HandFootSwitch", Player.Stat.AGILITY),
            new Challenge("chl.Agi.PlayCatch", Player.Stat.AGILITY),
            new Challenge("chl.Agi.Letkajenkka", Player.Stat.AGILITY),
            new Challenge("chl.Agi.Zigzag", Player.Stat.AGILITY),
            new Challenge("chl.Agi.Spider", Player.Stat.AGILITY),
            new Challenge("chl.Agi.Fouette", Player.Stat.AGILITY),
            new Challenge("chl.Agi.WallJump", Player.Stat.AGILITY),
            new Challenge("chl.Agi.TowelPull", Player.Stat.AGILITY),
            new Challenge("chl.Agi.WallWalk", Player.Stat.AGILITY),
            new Challenge("chl.Agi.Crawl", Player.Stat.AGILITY),
            new Challenge("chl.Agi.RollBackward", Player.Stat.AGILITY),
            new Challenge("chl.Agi.Leap", Player.Stat.AGILITY),
            new Challenge("chl.Agi.Falling", Player.Stat.AGILITY),
            new Challenge("chl.Sta.Walk", Player.Stat.STAMINA),
            new Challenge("chl.Sta.SkipRope", Player.Stat.STAMINA, 5),
            new Challenge("chl.Sta.JumpingJack", Player.Stat.STAMINA, 20),
            new Challenge("chl.Sta.TopFloor", Player.Stat.STAMINA),
            new Challenge("chl.Sta.ButtocksRun", Player.Stat.STAMINA, 1),
            new Challenge("chl.Sta.KneeRun", Player.Stat.STAMINA, 1),
            new Challenge("chl.Sta.MountainClimber", Player.Stat.STAMINA),
            new Challenge("chl.Sta.ShadowBoxing", Player.Stat.STAMINA, 5),
            new Challenge("chl.Sta.Stick", Player.Stat.STAMINA),
            new Challenge("chl.Sta.StarJump", Player.Stat.STAMINA, 10),
            new Challenge("chl.Sta.Dance", Player.Stat.STAMINA),
            new Challenge("chl.Sta.Chair", Player.Stat.STAMINA, 20),
            new Challenge("chl.Sta.Burpee", Player.Stat.STAMINA, 10),
            new Challenge("chl.Sta.HandWalk", Player.Stat.STAMINA, 10),
            new Challenge("chl.Sta.Frog", Player.Stat.STAMINA),
            new Challenge("chl.Sta.Race", Player.Stat.STAMINA),
            new Challenge("chl.Sta.Kneeclap", Player.Stat.STAMINA, 1),
            new Challenge("chl.Sta.Gorilla", Player.Stat.STAMINA, 5),
            new Challenge("chl.Sta.Swim", Player.Stat.STAMINA, 5),
            new Challenge("chl.Sta.Twerk", Player.Stat.STAMINA, 5),
            new Challenge("chl.Bal.GrapLeg", Player.Stat.BALANCE, 1),
            new Challenge("chl.Bal.ToesHeels", Player.Stat.BALANCE, 15),
            new Challenge("chl.Bal.Shoulderstand", Player.Stat.BALANCE),
            new Challenge("chl.Bal.BookHead", Player.Stat.BALANCE),
            new Challenge("chl.Bal.HandLeg", Player.Stat.BALANCE),
            new Challenge("chl.Bal.Round", Player.Stat.BALANCE),
            new Challenge("chl.Bal.Tightrope", Player.Stat.BALANCE),
            new Challenge("chl.Bal.Pick", Player.Stat.BALANCE),
            new Challenge("chl.Bal.EyesClosed", Player.Stat.BALANCE),
            new Challenge("chl.Bal.Squat", Player.Stat.BALANCE),
            new Challenge("chl.Bal.ElbowKnee", Player.Stat.BALANCE),
            new Challenge("chl.Bal.Plank", Player.Stat.BALANCE),
            new Challenge("chl.Bal.Sock", Player.Stat.BALANCE),
            new Challenge("chl.Bal.Fingers", Player.Stat.BALANCE),
            new Challenge("chl.Bal.Backwards", Player.Stat.BALANCE),
            new Challenge("chl.Bal.Lunge", Player.Stat.BALANCE),
            new Challenge("chl.Bal.Tiptoe", Player.Stat.BALANCE),
            new Challenge("chl.Bal.Moonwalk", Player.Stat.BALANCE),
            new Challenge("chl.Bal.Crab", Player.Stat.BALANCE),
            new Challenge("chl.Bal.Juggling", Player.Stat.BALANCE),
    };

    /**
     * List of challenges for Nuck Chorris.
     */
    static final Challenge[] NUCK_CHALLENGES = {
            new Challenge("chl.Sec.Handwalk", Player.Stat.BALANCE),
            new Challenge("chl.Sec.Pullup", Player.Stat.STRENGTH),
            new Challenge("chl.Sec.LiftOverhead", Player.Stat.STRENGTH),
            new Challenge("chl.Sec.Lizard", Player.Stat.AGILITY),
            new Challenge("chl.Sec.Somersault", Player.Stat.AGILITY),
            new Challenge("chl.Sec.Dragon", Player.Stat.STRENGTH),
            new Challenge("chl.Sec.Peacock", Player.Stat.STRENGTH),
            new Challenge("chl.Sec.Locust", Player.Stat.FLEXIBILITY),
            new Challenge("chl.Sec.IronCross", Player.Stat.STRENGTH),
            new Challenge("chl.Sec.Crunch", Player.Stat.STRENGTH),
            new Challenge("chl.Sec.Flag", Player.Stat.STRENGTH),
            new Challenge("chl.Sec.Clap", Player.Stat.AGILITY),
            new Challenge("chl.Sec.Pushup", Player.Stat.STRENGTH),
            new Challenge("chl.Sec.Squat", Player.Stat.STRENGTH),
            new Challenge("chl.Sec.Run", Player.Stat.STAMINA),
            new Challenge("chl.Sec.Headstand", Player.Stat.BALANCE),
            new Challenge("chl.Sec.Lunge", Player.Stat.STAMINA),
            new Challenge("chl.Sec.Climb", Player.Stat.AGILITY),
            new Challenge("chl.Sec.Juggling", Player.Stat.AGILITY),
            new Challenge("chl.Sec.ExerciseBall", Player.Stat.BALANCE)
    };

    /**
     * List of daily challenges in the game.
     */
    static final DailyChallenge[] DAILY_CHALLENGES = {
            new DailyChallenge("dchl.Stairs", Player.Stat.NONE, false),
            new DailyChallenge("dchl.LongWalk", Player.Stat.NONE, true),
            new DailyChallenge("dchl.NewSport", Player.Stat.NONE, true),
            new DailyChallenge("dchl.WalkSchool", Player.Stat.NONE, true),
            new DailyChallenge("dchl.Stand", Player.Stat.NONE, false),
            new DailyChallenge("dchl.Stretch", Player.Stat.NONE, false),
            new DailyChallenge("dchl.WaterBottle", Player.Stat.NONE, false),
            new DailyChallenge("dchl.Shoulders", Player.Stat.NONE, false),
            new DailyChallenge("dchl.Skipping", Player.Stat.NONE, false),
            new DailyChallenge("dchl.Face2Face", Player.Stat.NONE, false),
            new DailyChallenge("dchl.NextWeek", Player.Stat.NONE, true),
            new DailyChallenge("dchl.Outside", Player.Stat.NONE, false),
            new DailyChallenge("dchl.Cleaning", Player.Stat.NONE, false),
            new DailyChallenge("dchl.Squatting", Player.Stat.NONE, false),
            new DailyChallenge("dchl.Morning", Player.Stat.NONE, true),
            new DailyChallenge("dchl.AskFriend", Player.Stat.NONE, true),
            new DailyChallenge("dchl.Sleep", Player.Stat.NONE, false),
            new DailyChallenge("dchl.Dance", Player.Stat.NONE, false),
            new DailyChallenge("dchl.Shopping", Player.Stat.NONE, true),
            new DailyChallenge("dchl.Pomodoro", Player.Stat.NONE, false)
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
            new Character("couchpotato", Player.Stat.NONE, true),
            new Character("nuckchorris", Player.Stat.NONE, false),
            new Character("stronkman", Player.Stat.STRENGTH, true),
            new Character("enlightened", Player.Stat.FLEXIBILITY, true),
            new Character("thespider", Player.Stat.AGILITY, true),
            new Character("marathon", Player.Stat.STAMINA, true),
            new Character("graceful", Player.Stat.BALANCE, true)
    };

    /**
     * List of achievements.
     */
    public static final Achievement[] ACHIEVEMENTS = {
            new TieredAchievement("ach.Challenge", 100, new int[]{1, 5, 15, 25, 50, 100}),
            new TieredAchievement("ach.Daily", 10, new int[]{1, 3, 6, 10}),
            new TieredAchievement("ach.Stat", 5, new int[]{1, 2, 3, 4, 5})
    };

    /**
     * Chance of picking an off-stat challenge.
     */
    private static final float OFF_STAT_CHANCE = 0.3f;

    /**
     * Path to the Scene2D skin to use.
     */
    private static final String SKIN_PATH = "ui/skin.json";

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
    private final Player player = new Player(achievementManager);

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

        String atlasPath = "ui/skin-mdpi.atlas";
        if (Gdx.graphics.getDensity() > 2.5) {
            atlasPath = "ui/skin-xxhdpi.atlas";
        } else if (Gdx.graphics.getDensity() > 1.5) {
            atlasPath = "ui/skin-xhdpi.atlas";
        }

        assetManager.load(SKIN_PATH, Skin.class, new SkinLoader.SkinParameter(atlasPath));

        assetManager.load("YourTrueSelf_MainthemeOGG.ogg", Music.class);

        assetManager.load("sounds/SelectedCouchpotato.ogg", Sound.class);
        assetManager.load("sounds/SelectedEnlightened.ogg", Sound.class);
        assetManager.load("sounds/SelectedGraceful.ogg", Sound.class);
        assetManager.load("sounds/SelectedSpider.ogg", Sound.class);
        assetManager.load("sounds/SelectedStronkperson.ogg", Sound.class);
        assetManager.load("sounds/SelectedCouchpotato.ogg", Sound.class);
        assetManager.load("sounds/SelectedMarathon.ogg", Sound.class);
        assetManager.load("sounds/SelectedNuckChorris.ogg", Sound.class);
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

        // Only re-pick challenge if it's not on cooldown already
        if (getCurrentChallenge() != null) {
            setCurrentChallenge((Challenge) null);
            setNextChallengeTime(0);
        }

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
    private void setPlayerCharacter(String chr) {
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
            if (!skipped) {
                startNextDailyTimer();
                achievementManager.increaseProgress("ach.Daily", 1);
            } else {
                setNextDailyTime(0);
            }
        } else {
            previousChallenge = chl;
            setCurrentChallenge((Challenge) null);
            if (!skipped) {
                startNextChallengeTimer();
                achievementManager.increaseProgress("ach.Challenge", 1);
            } else {
                setNextChallengeTime(0);
            }
        }

        if (!skipped) {
            chl.complete(getPlayer());

            saveStats();
            soundMap.get("completedChallenge").play(getSoundVolume());

            checkNuckProgress();

            if (challengeCompletedListener != null) {
                challengeCompletedListener.challengeCompleted(chl);
            }
        }
    }

    private void checkNuckProgress() {
        if (achievementManager.getProgress("ach.Stat") >= 2) {
            for (Character c : CHARACTERS) {
                if (c.getId().equals("nuckchorris")) {
                    c.setVisibility(true);
                }
            }
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

        Challenge[] chlList = YTSChallenges.CHALLENGES;

        // Couchpotato (stat = NONE) only picks its own challenges.
        if (chr.getId().equals("couchpotato")) {
            pickOffStat = false;
            chlList = YTSChallenges.POTATO_CHALLENGES;
        } else if (chr.getId().equals("nuckchorris") && !pickOffStat) {
            chlList = YTSChallenges.NUCK_CHALLENGES;
        }

        // If only one main stat challenge exists, force-enable off-stat challenges to avoid
        // an infinite loop.
        int mainStatChls = 0;
        for (Challenge chl : chlList) {
            if (chl.mainStat == chr.getMainStat()) {
                mainStatChls += 1;
            }
        }
        if (mainStatChls <= 1) {
            pickOffStat = true;
        }

        // Avoid getting the exact same challenge as last
        do {
            idx = MathUtils.random(0, chlList.length - 1);
            isOffStat = chlList[idx].mainStat != chr.getMainStat();
        } while ((isOffStat && !pickOffStat) ||
                (previousChallenge != null &&
                        chlList[idx].getId().equals(previousChallenge.getId())));

        return chlList[idx];
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

        if (releaseMode) {
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
        }

        setNextChallengeTime(c.getTimeInMillis());

        if (!prefs.getBoolean(DefaultPreferences.PREF_NOTIFICATIONS, DefaultPreferences.PREF_NOTIFICATIONS_DEFAULT)) {
            return;
        }

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
        soundMap.put("marathon", assetManager.get("sounds/SelectedMarathon.ogg", Sound.class));
        soundMap.put("nuckchorris", assetManager.get("sounds/SelectedNuckChorris.ogg", Sound.class));
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

        checkNuckProgress();

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