package fi.tamk.yourtrueself.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.I18NBundle;

import fi.tamk.yourtrueself.Stats;

public class PrefsDisplay extends Table {

    Preferences prefs;
    Skin skin;
    String id;
    String lang;
    int sound;
    int music;
    int noBotherStart;
    int noBotherEnd;

    private ProgressBar[] bars = new ProgressBar[Stats.STAT_ENUMS.length];

    public PrefsDisplay(Preferences prefs, Skin skin) {
        super(skin);
        this.defaults();
        I18NBundle bundle = skin.get("i18n-bundle", I18NBundle.class);

        this.skin = skin;
        this.prefs = prefs;
        id = prefs.getString("id", "couchpotato");
        lang = prefs.getString("lang", "fi");
        sound = prefs.getInteger("sound", 5);
        music = prefs.getInteger("music", 5);
        noBotherStart = prefs.getInteger("noBotherStart", 22);
        noBotherEnd = prefs.getInteger("noBotherEnd", 8);

        addSlider(music, "otsikko");


    }

    private void addSlider(int value, String name) {
        this.add(new Label(name, skin)).left().padRight(2);
        Slider slider = new Slider(0, 10, 1f, false, skin);
        slider.setValue(value);
        this.add(slider).grow();
        this.row();
    }

    @Override
    public float getPrefWidth() {
        return Gdx.graphics.getPpiX() * 1;
    }

    @Override
    public float getPrefHeight() {
        return Gdx.graphics.getPpiY() * 2;
    }
}
