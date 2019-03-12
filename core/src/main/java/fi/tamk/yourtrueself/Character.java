package fi.tamk.yourtrueself;

public class Character {

    private Stats.Stat mainStat = Stats.Stat.NONE;
    private String id;

    public Character(String id, Stats.Stat mainStat) {
        this.id = id;
        this.mainStat = mainStat;
    }

    public Stats.Stat getMainStat() {
        return mainStat;
    }

    public void setMainStat(Stats.Stat mainStat) {
        this.mainStat = mainStat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
