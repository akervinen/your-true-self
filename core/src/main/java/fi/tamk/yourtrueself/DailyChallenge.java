package fi.tamk.yourtrueself;

public class DailyChallenge extends Challenge {
    public DailyChallenge(String id, Player.Stat mainStat) {
        super(id, mainStat);
    }

    public DailyChallenge(String id, Player.Stat mainStat, int modifier) {
        super(id, mainStat, modifier);
    }

    @Override
    public void complete(Player player) {
        player.train(mainStat, 4f);
    }
}
