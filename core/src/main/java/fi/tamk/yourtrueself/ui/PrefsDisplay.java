package fi.tamk.yourtrueself.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
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

        addMusicSlider();
        addSoundSlider();
        addStartSelect();
        addEndSelect();
        addLanguageSelect();
        addCharacterButton();
        addCancelButton();
        addOKButton();
    }

    private void addMusicSlider() {
        this.add(new Label(game.getBundle().get("musicSlider"), skin));
        final Slider slider = new Slider(0, 10, 1f, false, skin);
        slider.setValue(music);
        this.add(slider).grow();
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                music = (int) slider.getValue();
            }
        });
        this.row();
    }

    private void addSoundSlider() {
        this.add(new Label(game.getBundle().get("soundSlider"), skin));
        final Slider slider = new Slider(0, 10, 1f, false, skin);
        slider.setValue(sound);
        this.add(slider).grow();
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                sound = (int) slider.getValue();
            }
        });
        this.row();
    }

    private void addStartSelect() {
        this.add(new Label(game.getBundle().get("noBother"), skin));
        this.row();
        this.add(new Label(game.getBundle().get("noBotherStart"), skin));
        final SelectBox select = new SelectBox(skin);
        String[] times = new String[24];
        for (int i = 0; i < times.length; i++) {
            times[i] = Integer.toString(i);
        }
        select.setItems(times);
        select.setSelected(times[noBotherStart - 1]);
        this.add(select).grow();
        select.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                noBotherStart = Integer.parseInt(select.getSelected().toString());
            }
        });
    }

    private void addEndSelect() {
        this.add(new Label(game.getBundle().get("noBotherEnd"), skin));
        final SelectBox select = new SelectBox(skin);
        String[] times = new String[24];
        for (int i = 0; i < times.length; i++) {
            times[i] = Integer.toString(i);
        }
        select.setItems(times);
        select.setSelected(times[noBotherEnd - 1]);
        this.add(select).grow();
        select.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                noBotherEnd = Integer.parseInt(select.getSelected().toString());
            }
        });
        this.row();
    }

    private void addLanguageSelect() {
        this.add(new Label(game.getBundle().get("language"), skin));
        final SelectBox select = new SelectBox(skin);
        String[] languages = {"FI", "EN"};
        select.setItems(languages);
        select.setSelected(lang);
        this.add(select).grow();
        this.row();
        select.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                lang = select.getSelected().toString();
            }
        });
    }

    private void addCharacterButton() {
        TextButton button = new TextButton(game.getBundle().get("changeCharacter"), skin);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.goToCharacterSelect();
            }
        });
        this.add(button).grow();
        this.row();
    }

    private void addCancelButton() {
        TextButton button = new TextButton(game.getBundle().get("cancel"), skin);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                remove();
            }
        });
        this.add(button).grow();
    }

    private void addOKButton() {
        TextButton button = new TextButton(game.getBundle().get("ok"), skin);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                prefs.putString("lang", lang);
                prefs.putInteger("sound", sound);
                prefs.putInteger("music", music);
                prefs.putInteger("noBotherStart", noBotherStart);
                prefs.putInteger("noBotherEnd", noBotherEnd);
                prefs.flush();
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
