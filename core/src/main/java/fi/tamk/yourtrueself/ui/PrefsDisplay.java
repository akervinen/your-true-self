package fi.tamk.yourtrueself.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.I18NBundle;

import fi.tamk.yourtrueself.YTSGame;

public class PrefsDisplay extends Window {

    Preferences prefs;
    Skin skin;
    String id;
    String lang;
    int sound;
    int music;
    int noBotherStart;
    int noBotherEnd;
    YTSGame game;

    public PrefsDisplay(Preferences prefs, Skin skin, YTSGame game) {
        super("", skin);
        this.defaults();
        this.game = game;
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
        addSlider(sound, "otsikko");
        addList("otsikko");
        addList("otsikko");
        addButton(game.getBundle().get("changeCharacter"));
    }

    private void addSlider(int value, String name) {
        this.add(new Label(name, skin)).left().padRight(2);
        Slider slider = new Slider(0, 10, 1f, false, skin);
        slider.setValue(value);
        this.add(slider).grow();
        this.row();
    }

    private void addList(String name) {
        this.add(new Label(name, skin)).left().padRight(2);
        List list = new List(skin);
        int[] times = new int[24];
        for (int i = 0; i < times.length; i++) {
            times[i] = i;
        }
        list.setItems(times);
        this.add(list).grow();
        this.row();
    }

    private void addButton(String name) {
        this.add(new Label(name, skin)).left().padRight(2);
        TextButton button = new TextButton(game.getBundle().get("train"), skin);
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
