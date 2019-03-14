package fi.tamk.yourtrueself;

public class Character {

    private final Player.Stat mainStat;
    private final String id;

    public Character(String id, Player.Stat mainStat) {
        this.id = id;
        this.mainStat = mainStat;
    }

    public Player.Stat getMainStat() {
        return mainStat;
    }

    public String getId() {
        return id;
    }

}
