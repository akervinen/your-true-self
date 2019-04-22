package fi.tamk.yourtrueself;

import com.badlogic.gdx.math.MathUtils;

/**
 * Represents the player of the game. Contains their current character and stats.
 */
public final class Player {
    /**
     * Internal names of stats. Used for translation purposes.
     */
    public static final String[] STAT_NAMES = {
            "stat.strength",
            "stat.flexibility",
            "stat.agility",
            "stat.stamina",
            "stat.balance"
    };

    /**
     * Stat enums list in the same order as internal names for slightly more convenient usage.
     */
    public static final Stat[] STAT_ENUMS = {
            Stat.STRENGTH,
            Stat.FLEXIBILITY,
            Stat.AGILITY,
            Stat.STAMINA,
            Stat.BALANCE
    };

    /**
     * Current character of the player. Can be null if the player hasn't chosen a character yet.
     */
    private Character currentCharacter = null;

    /**
     * Game's achievement manager.
     */
    private AchievementManager achievementManager;

    /**
     * Player's strength stat.
     */
    private float strength;

    /**
     * Player's flexibility stat.
     */
    private float flexibility;

    /**
     * Player's agility stat.
     */
    private float agility;

    /**
     * Player's stamina stat.
     */
    private float stamina;

    /**
     * Player's balance stat.
     */
    private float balance;

    /**
     * Create a new player with all default values (no character and all stats 0).
     *
     * @param achievementManager achievement manager object for easier access from player's methods
     */
    Player(AchievementManager achievementManager) {
        this.achievementManager = achievementManager;
    }

    /**
     * Increase given stat by given value.
     *
     * @param stat  stat to increase
     * @param value value to increase stat by
     */
    void train(Stat stat, float value) {
        if (getByEnum(stat) < 100 && (getByEnum(stat) + value) >= 100) {
            achievementManager.increaseProgress("ach.Stat", 1);
        }

        setByEnum(stat, getByEnum(stat) + value);
    }

    /**
     * Get the player's current character, or null if none.
     *
     * @return current character
     */
    public Character getCurrentCharacter() {
        return currentCharacter;
    }

    /**
     * Set the player's current character, or null to set to none.
     *
     * @param currentCharacter player's new character or null
     */
    void setCurrentCharacter(Character currentCharacter) {
        this.currentCharacter = currentCharacter;
    }

    /**
     * Get a stat by enum.
     *
     * @param stat enum representing the wanted stat
     * @return wanted stat or 0 if invalid
     */
    public float getByEnum(Stat stat) {
        switch (stat) {
        case STRENGTH:
            return strength;
        case FLEXIBILITY:
            return flexibility;
        case AGILITY:
            return agility;
        case STAMINA:
            return stamina;
        case BALANCE:
            return balance;
        }

        return 0;
    }

    /**
     * Set player's stat by enum.
     *
     * @param stat stat to set
     * @param val  value to set to
     */
    void setByEnum(Stat stat, float val) {
        val = MathUtils.clamp(val, 0, 100);

        switch (stat) {
        case STRENGTH:
            strength = val;
            break;
        case FLEXIBILITY:
            flexibility = val;
            break;
        case AGILITY:
            agility = val;
            break;
        case STAMINA:
            stamina = val;
            break;
        case BALANCE:
            balance = val;
            break;
        }
    }

    /**
     * Enum representing all the stats and "none" for no stat.
     */
    public enum Stat {
        /**
         * No specific stat. Used by daily challenges, as they increase all of the player's stats.
         * Also the main stat of Potato and Nuck, since they're not tied to any specific stats.
         */
        NONE,
        /**
         * Strength stat.
         */
        STRENGTH,
        /**
         * Flexibility stat.
         */
        FLEXIBILITY,
        /**
         * Agility stat.
         */
        AGILITY,
        /**
         * Stamina stat.
         */
        STAMINA,
        /**
         * Balance stat.
         */
        BALANCE
    }
}
