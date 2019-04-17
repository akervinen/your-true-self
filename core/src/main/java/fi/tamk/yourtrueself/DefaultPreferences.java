package fi.tamk.yourtrueself;

/**
 * Container class for preferences related constants
 */
public class DefaultPreferences {
    /**
     * Name of the preferences file.
     */
    public static final String PREF_NAME = "YTSPreferences";

    /**
     * Language preference key.
     */
    public static final String PREF_LANGUAGE = "lang";

    /**
     * Music volume preference key.
     */
    public static final String PREF_MUSIC = "music";

    /**
     * Default value of music volume.
     */
    public static final float PREF_MUSIC_DEFAULT = 1.0f;

    /**
     * Sound volume preference key.
     */
    public static final String PREF_SOUND = "sound";

    /**
     * Default value of sound volume.
     */
    public static final float PREF_SOUND_DEFAULT = 1.0f;

    /**
     * Notification toggle preference key.
     */
    public static final String PREF_NOTIFICATIONS = "notifications";

    /**
     * Default value of notification toggle.
     */
    public static final boolean PREF_NOTIFICATIONS_DEFAULT = true;

    /**
     * DND start time preference key.
     */
    public static final String PREF_DND_START = "noBotherStart";

    /**
     * Default value of DND start time.
     */
    public static final int PREF_DND_START_DEFAULT = 22;

    /**
     * DND end time preference key.
     */
    public static final String PREF_DND_END = "noBotherEnd";

    /**
     * DND end time default value.
     */
    public static final int PREF_DND_END_DEFAULT = 8;

    /**
     * Player character preference key.
     */
    static final String PREF_CHARACTER = "playerCharacter";

    /**
     * Current challenge preference key.
     */
    static final String PREF_CHL_CURRENT = "currentChallenge";

    /**
     * Next challenge time preference key.
     */
    static final String PREF_CHL_NEXT = "nextChallengeTime";

    /**
     * Current daily challenge ID preference key.
     */
    static final String PREF_DAILY_CURRENT = "currentDaily";

    /**
     * Current daily challenge start time preference key.
     */
    static final String PREF_DAILY_STARTED = "currentDailyStart";

    /**
     * Next daily challenge start time preference key.
     */
    static final String PREF_DAILY_NEXT = "nextDailyTime";
}
