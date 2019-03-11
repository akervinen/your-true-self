package fi.tamk.yourtrueself;

import com.badlogic.gdx.math.MathUtils;

public final class Stats {
    public static final String[] STAT_NAMES = {
            "statStrength",
            "statFlexibility",
            "statAgility",
            "statStamina",
            "statBalance"
    };

    public static final Stat[] STAT_ENUMS = {
            Stat.STRENGTH,
            Stat.FLEXIBILITY,
            Stat.AGILITY,
            Stat.STAMINA,
            Stat.BALANCE
    };

    private float strength;
    private float flexibility;
    private float agility;
    private float stamina;
    private float balance;

    public Stats() {
    }

    public Stats(float strength, float flexibility, float agility, float stamina, float balance) {
        this.strength = strength;
        this.flexibility = flexibility;
        this.agility = agility;
        this.stamina = stamina;
        this.balance = balance;
    }

    public float getByEnum(Stat stat) {
        switch (stat) {
        case STRENGTH:
            return strength;
        case FLEXIBILITY:
            return flexibility;
        case AGILITY:
            return agility;
        case STAMINA:
            return stamina;
        case BALANCE:
            return balance;
        }

        return 0;
    }

    public float setByEnum(Stat stat, float val) {
        switch (stat) {
        case STRENGTH:
            setStrength(val);
            break;
        case FLEXIBILITY:
            setFlexibility(val);
            break;
        case AGILITY:
            setAgility(val);
            break;
        case STAMINA:
            setStamina(val);
            break;
        case BALANCE:
            setBalance(val);
            break;
        }

        return 0;
    }

    public float getStrength() {
        return strength;
    }

    public void setStrength(float strength) {
        this.strength = MathUtils.clamp(strength, 0, 100);
    }

    public float getFlexibility() {
        return flexibility;
    }

    public void setFlexibility(float flexibility) {
        this.flexibility = MathUtils.clamp(flexibility, 0, 100);
    }

    public float getAgility() {
        return agility;
    }

    public void setAgility(float agility) {
        this.agility = MathUtils.clamp(agility, 0, 100);
    }

    public float getStamina() {
        return stamina;
    }

    public void setStamina(float stamina) {
        this.stamina = MathUtils.clamp(stamina, 0, 100);
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = MathUtils.clamp(balance, 0, 100);
    }

    public enum Stat {
        NONE, STRENGTH, FLEXIBILITY, AGILITY, STAMINA, BALANCE
    }
}
