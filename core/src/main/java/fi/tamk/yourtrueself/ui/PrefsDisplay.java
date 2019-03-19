package fi.tamk.yourtrueself.ui;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
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
    private Stage stage;

    public PrefsDisplay(Preferences prefs, Skin skin, YTSGame game) {
        super(game.getBundle().get("prefs"), skin);
        this.defaults().space(5).padTop(20).growX();
        this.game = game;

        stage = this.getStage();

        this.setMovable(false);

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
        addCreditsButton();
        addCancelButton();
        addOKButton();

        this.pack();
    }

    private void addMusicSlider() {
        this.add(new Label(game.getBundle().get("musicSlider"), skin));
        final Slider slider = new Slider(0, 10, 1f, false, skin);
        slider.setValue(music);
        this.add(slider);
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
        this.add(slider);
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
        final SelectBox<String> select = new SelectBox<String>(skin);
        Array<String> times = new Array<String>(24);
        for (int i = 0; i < 24; i++) {
            times.add(Integer.toString(i));
        }
        select.setItems(times);
        select.setSelected(times.get(noBotherStart));
        this.add(select).row();
        select.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                noBotherStart = Integer.parseInt(select.getSelected().toString());
            }
        });
    }

    private void addEndSelect() {
        this.add(new Label(game.getBundle().get("noBotherEnd"), skin));
        final SelectBox<String> select = new SelectBox<String>(skin);
        Array<String> times = new Array<String>(24);
        for (int i = 0; i < 24; i++) {
            times.add(Integer.toString(i));
        }
        select.setItems(times);
        select.setSelected(times.get(noBotherEnd));
        this.add(select);
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
        final SelectBox<String> select = new SelectBox<String>(skin);
        select.setItems("FI", "EN");
        select.setSelected(lang);
        this.add(select);
        this.row().padTop(20);
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
    }

    private void addCreditsButton() {
        final PrefsDisplay self = this;
        TextButton button = new TextButton(game.getBundle().get("credits"), skin);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                CreditsDisplay credits = new CreditsDisplay(skin, game);
                credits.setPosition(self.getStage().getWidth() / 2, self.getStage().getHeight() / 2, Align.center);
                self.getStage().addActor(credits);
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

//    @Override
//    public float getPrefWidth() {
//        return Gdx.graphics.getPpiX() * 1;
//    }
//
//    @Override
//    public float getPrefHeight() {
//        return Gdx.graphics.getPpiY() * 2;
//    }
}
