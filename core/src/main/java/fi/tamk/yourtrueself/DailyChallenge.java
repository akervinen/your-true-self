package fi.tamk.yourtrueself;

public class DailyChallenge extends Challenge {
    private boolean canBeCompletedEarly = false;

    public DailyChallenge(String id, Player.Stat mainStat, boolean canBeCompletedEarly) {
        super(id, mainStat);

        this.canBeCompletedEarly = canBeCompletedEarly;
    }

    public DailyChallenge(String id, Player.Stat mainStat, int modifier, boolean canBeCompletedEarly) {
        super(id, mainStat, modifier);

        this.canBeCompletedEarly = canBeCompletedEarly;
    }

    public boolean canBeCompletedEarly() {
        return canBeCompletedEarly;
    }

    @Override
    public void complete(Player player) {
        for (Player.Stat stat : Player.STAT_ENUMS) {
            player.train(stat, 1f);
        }
    }
}
