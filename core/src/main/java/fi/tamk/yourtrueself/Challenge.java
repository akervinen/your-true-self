package fi.tamk.yourtrueself;

/**
 * Challenge data.
 */
public class Challenge {
    /**
     * Internal ID of the challenge. Mostly used for finding translations from the
     * i18n bundle.
     */
    private final String id;

    /**
     * Challenge's main stat. Completing the challenge raises this stat.
     */
    private final Player.Stat mainStat;

    /**
     * Challenge's modifier, for example "walk (x) steps" or "do (x) squats". Gets passed
     * to the translation bundle.
     */
    private int modifier;

    /**
     * Create a new challenge with given ID and stat. Must have a translation with the same ID.
     *
     * @param id       internal ID of the challenge
     * @param mainStat stat that the challenge increases
     */
    public Challenge(String id, Player.Stat mainStat) {
        this(id, mainStat, 0);
    }

    /**
     * Create a new challenge with given ID, stat and modifier. Must have a translation with the
     * same ID.
     *
     * @param id       internal ID of the challenge
     * @param mainStat stat that the challenge increases
     * @param modifier challenge modifier, e.g. "(x) steps" or "(x) seconds"
     */
    public Challenge(String id, Player.Stat mainStat, int modifier) {
        this.id = id;
        this.mainStat = mainStat;
        this.modifier = modifier;
    }

    /**
     * Get the challenge's internal ID.
     *
     * @return internal ID
     */
    public String getId() {
        return id;
    }

    /**
     * Get the challenge's modifier value, for example "walk (x) steps" or "do (x) squats".
     *
     * @return modifier
     */
    public int getModifier() {
        return modifier;
    }

    /**
     * Complete the challenge and increase player stats according to main stat.
     *
     * @param player player that completed the challenge
     */
    public void complete(Player player) {
        player.train(mainStat, 2f);
    }
}
