package fi.tamk.yourtrueself;

/**
 * Daily challenge.
 */
public class DailyChallenge extends Challenge {
    private boolean canBeCompletedEarly = false;

    /**
     * Create a new daily challenge with given ID and stat. ID must exist in I18N bundle.
     *
     * @param id                  ID of the challenge
     * @param mainStat            which stat the challenge increases
     * @param canBeCompletedEarly whether the challenge can be completed any time of the day
     */
    public DailyChallenge(String id, Player.Stat mainStat, boolean canBeCompletedEarly) {
        super(id, mainStat);

        this.canBeCompletedEarly = canBeCompletedEarly;
    }

    /**
     * Create a new daily challenge with given ID and stat. ID must exist in I18N bundle.
     *
     * @param id ID of the challenge
     * @param mainStat which stat the challenge increases
     * @param modifier stat's numeric modifier
     * @param canBeCompletedEarly whether the challenge can be completed any time of the day
     */
    public DailyChallenge(String id, Player.Stat mainStat, int modifier, boolean canBeCompletedEarly) {
        super(id, mainStat, modifier);

        this.canBeCompletedEarly = canBeCompletedEarly;
    }

    /**
     * Whether challenge can be completed any time of the day. False means it can only be completed
     * close to the start of DND period or early next day.
     *
     * @return true if challenge can be completed early
     */
    public boolean canBeCompletedEarly() {
        return canBeCompletedEarly;
    }

    /**
     * Called when challenge is completed.
     *
     * @param player player that completed the challenge
     */
    @Override
    public void complete(Player player) {
        for (Player.Stat stat : Player.STAT_ENUMS) {
            player.train(stat, 1f);
        }
    }
}
