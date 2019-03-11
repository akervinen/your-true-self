package fi.tamk.yourtrueself.characters;

public class Character {

    private float strength;
    private float flexibility;
    private float agility;
    private float stamina;
    private float balance;
    private Stat mainStat = Stat.NONE;
    private String id;
    Character() {
        setStrength(0);
        setFlexibility(0);
        setAgility(0);
        setStamina(0);
        setBalance(0);
    }

    public float getStrength() {
        return strength;
    }

    public void setStrength(float strength) {
        if (strength >= 0 && strength <= 100) {
            this.strength = strength;
        }
    }

    public float getFlexibility() {
        return flexibility;
    }

    public void setFlexibility(float flexibility) {
        if (flexibility >= 0 && flexibility <= 100) {
            this.flexibility = flexibility;
        }
    }

    public float getAgility() {
        return agility;
    }

    public void setAgility(float agility) {
        if (agility >= 0 && agility <= 100) {
            this.agility = agility;
        }
    }

    public float getStamina() {
        return stamina;
    }

    public void setStamina(float stamina) {
        if (stamina >= 0 && stamina <= 100) {
            this.stamina = stamina;
        }
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        if (balance >= 0 && balance <= 100) {
            this.balance = balance;
        }
    }

    public Stat getMainStat() {
        return mainStat;
    }

    public void setMainStat(Stat mainStat) {
        this.mainStat = mainStat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    enum Stat {
        NONE, STRENGTH, FLEXIBILITY, AGILITY, STAMINA, BALANCE
    }

}
