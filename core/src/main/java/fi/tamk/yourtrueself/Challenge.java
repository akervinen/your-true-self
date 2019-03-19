package fi.tamk.yourtrueself;

public class Challenge {
    private String id;
    private Player.Stat mainStat;

    public Challenge(String id, Player.Stat mainStat) {
        this.id = id;
        this.mainStat = mainStat;
    }

    public String getId() {
        return id;
    }

    public void complete(Player player) {
        player.train(mainStat, 2f);
    }
}
