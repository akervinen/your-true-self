package fi.tamk.yourtrueself;

public class Character {

    private Player.Stat mainStat;
    private String id;

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
