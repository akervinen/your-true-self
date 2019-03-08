package fi.tamk.yourtrueself;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Character {

    enum Stat {
        STRENGTH, FLEXIBILITY, AGILITY, STAMINA, BALANCE
    }

    private float strength;
    private float flexibility;
    private float agility;
    private float stamina;
    private float balance;
    private Stat mainStat;
    private Texture texture;

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
    public float getFlexibility() {
        return flexibility;
    }
    public float getAgility() { return agility; }
    public float getStamina() {
        return stamina;
    }
    public float getBalance() {
        return balance;
    }
    public Stat getMainStat() {
        return mainStat;
    }
    public Texture getTexture() {
        return texture;
    }

    public void setStrength(float strength) {
        if (strength >= 0 && strength <=100) {
            this.strength = strength;
        }
    }
    public void setFlexibility(float flexibility) {
        if (flexibility >= 0 && flexibility <=100) {
            this.flexibility = flexibility;
        }
    }
    public void setAgility(float agility) {
        if (agility >= 0 && agility <=100) {
            this.agility = agility;
        }
    }
    public void setStamina(float stamina) {
        if (stamina >= 0 && stamina <=100) {
            this.stamina = stamina;
        }
    }
    public void setBalance(float balance) {
        if (balance >= 0 && balance <=100) {
            this.balance = balance;
        }
    }
    public void setTexture(String path) {
        this.texture = new Texture(Gdx.files.internal(path));
    }
    public void setMainStat(Stat mainStat) {
        this.mainStat = mainStat;
    }

}
