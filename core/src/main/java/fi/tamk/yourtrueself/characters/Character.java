package fi.tamk.yourtrueself.characters;

import fi.tamk.yourtrueself.Stats;

public abstract class Character {

    private Stats.Stat mainStat = Stats.Stat.NONE;
    private String id;

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
