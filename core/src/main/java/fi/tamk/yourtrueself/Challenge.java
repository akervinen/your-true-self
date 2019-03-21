package fi.tamk.yourtrueself;

public class Challenge {
    private String id;
    private Player.Stat mainStat;

    private int modifier;

    public Challenge(String id, Player.Stat mainStat) {
        this(id, mainStat, 0);
    }

    public Challenge(String id, Player.Stat mainStat, int modifier) {
        this.id = id;
        this.mainStat = mainStat;
        this.modifier = modifier;
    }

    public String getId() {
        return id;
    }

    public int getModifier() {
        return modifier;
    }

    public void complete(Player player) {
        player.train(mainStat, 2f);
    }
}
