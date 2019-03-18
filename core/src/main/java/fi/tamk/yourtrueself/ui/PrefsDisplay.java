package fi.tamk.yourtrueself.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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

        addSlider(music, game.getBundle().get("musicSlider"));
        addSlider(sound, game.getBundle().get("soundSlider"));
        this.add(new Label(game.getBundle().get("noBother"), skin));
        this.row();
        addSelect(noBotherStart, game.getBundle().get("noBotherStart"));
        addSelect(noBotherEnd, game.getBundle().get("noBotherEnd"));
        this.row();
        addLanguage(lang, game.getBundle().get("noBotherEnd"));
        addCharacterButton(game.getBundle().get("changeCharacter"));
        this.row();
        addCancelButton(game.getBundle().get("cancel"));
        addOKButton(game.getBundle().get("ok"));
    }

    private void addSlider(int value, String name) {
        this.add(new Label(name, skin));
        Slider slider = new Slider(0, 10, 1f, false, skin);
        slider.setValue(value);
        this.add(slider).grow();
        this.row();
    }

    private void addSelect(int time, String name) {
        this.add(new Label(name, skin));
        SelectBox select = new SelectBox(skin);
        String[] times = new String[24];
        for (int i = 0; i < times.length; i++) {
            times[i] = Integer.toString(i);
        }
        select.setItems(times);
        select.setSelected(time);
        this.add(select).grow();
    }

    private void addLanguage(String lang, String name) {
        this.add(new Label(name, skin));
        SelectBox select = new SelectBox(skin);
        String[] languages = {"FI", "EN"};
        select.setItems(languages);
        select.setSelected(lang);
        this.add(select).grow();
        this.row();
    }

    private void addCharacterButton(String name) {
        TextButton button = new TextButton(name, skin);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.goToCharacterSelect();
            }
        });
        this.add(button).grow();
    }

    private void addCancelButton(String name) {
        TextButton button = new TextButton(name, skin);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                remove();
            }
        });
        this.add(button).grow();
    }

    private void addOKButton(String name) {
        TextButton button = new TextButton(name, skin);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //save preferences before closing
                remove();
            }
        });
        this.add(button).grow();
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
