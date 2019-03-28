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
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import fi.tamk.yourtrueself.YTSGame;

class VolumeSlider extends Slider {
    private final YTSGame game;
    private final Preferences preferences;
    private final String prefName;

    VolumeSlider(Preferences preferences, final String prefName, YTSGame ytsGame, Skin skin) {
        super(0, 1, 0.1f, false, skin);

        this.game = ytsGame;
        this.preferences = preferences;
        this.prefName = prefName;

        setValue(preferences.getFloat(prefName, 0.5f));

        this.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                updatePref();
                if (prefName.equals("music")) {
                    game.setMusicVolume(getValue());
                }
            }
        });
    }

    static void changeStyle(SliderStyle style) {
        // Change bar height (otherwise it's however many pixels the assets are high)
        Drawable[] knobs = new Drawable[]{
                style.knob, style.knobAfter, style.knobOver, style.knobDown, style.disabledKnob,
                style.disabledKnobAfter
        };

        Drawable[] sliderGfx = new Drawable[]{
                style.background, style.disabledBackground, style.knobBefore, style.disabledKnobBefore
        };

        for (Drawable d : knobs) {
            if (d == null) continue;
            float ratio = ((Gdx.graphics.getPpiY() * 0.125f) / d.getMinHeight());
            d.setMinHeight(d.getMinHeight() * ratio);
            d.setMinWidth(d.getMinWidth() * ratio);
        }
        for (Drawable d : sliderGfx) {
            if (d == null) continue;
            float ratio = ((Gdx.graphics.getPpiY() * 0.05f) / d.getMinHeight());
            d.setMinHeight(d.getMinHeight() * ratio);
//            d.setMinWidth(d.getMinWidth() * ratio);
        }
    }

    private void updatePref() {
        preferences.putFloat(prefName, getValue());
        preferences.flush();
    }

    @Override
    public float getMinHeight() {
        return Gdx.graphics.getPpiY() * 0.25f;
    }
}

public class PrefsDisplay extends YTSWindow {

    private final YTSGame game;
    private final Preferences prefs;
    private final Skin skin;

    private String lang;
    private int noBotherStart;
    private int noBotherEnd;

    public PrefsDisplay(Preferences prefs, Skin skin, YTSGame ytsGame) {
        super(ytsGame.getBundle().get("prefs"), skin, Gdx.graphics.getPpiY() > 200 ? "large" : "default");
        this.defaults().space(5).spaceBottom(35).grow().minWidth(Value.percentWidth(.45f, this));
        this.game = ytsGame;

        this.skin = skin;
        this.prefs = prefs;

        VolumeSlider.changeStyle(skin.get("default-horizontal", Slider.SliderStyle.class));

        lang = prefs.getString("lang", "fi");
        noBotherStart = prefs.getInteger("noBotherStart", 22);
        noBotherEnd = prefs.getInteger("noBotherEnd", 8);

        addMusicSlider();
        addSoundSlider();
        addStartSelect();
        addEndSelect();
        addLanguageSelect();
        addCharacterButton();
        addCreditsButton();

        getBackButton().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeLanguage(lang);
                remove();
            }
        });

        this.padLeft(10).padRight(10);
        this.pack();
    }

    private void addMusicSlider() {
        this.add(new Label(game.getBundle().get("musicSlider"), skin));
        this.add(new VolumeSlider(prefs, "music", game, skin)).row();
    }

    private void addSoundSlider() {
        this.add(new Label(game.getBundle().get("soundSlider"), skin));
        this.add(new VolumeSlider(prefs, "sound", game, skin)).row();
    }

    private void addStartSelect() {
        this.add(new Label(game.getBundle().get("noBother"), skin));
        this.row();
        this.add(new Label(game.getBundle().get("noBotherStart"), skin));
        final SelectBox<String> select = new SelectBox<String>(skin);
        Array<String> times = new Array<String>(24);
        for (int i = 0; i < 24; i++) {
            times.add(Integer.toString(i) + game.getBundle().get("timeEnding"));
        }
        select.setItems(times);
        select.setSelected(times.get(noBotherStart));
        this.add(select).row();
        select.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String selection = select.getSelected();
                selection = selection.replace(game.getBundle().get("timeEnding"), "");
                noBotherStart = Integer.parseInt(selection);
                prefs.putInteger("noBotherStart", noBotherStart);
                prefs.flush();
                game.setPrefs(prefs);
            }
        });
    }

    private void addEndSelect() {
        this.add(new Label(game.getBundle().get("noBotherEnd"), skin));
        final SelectBox<String> select = new SelectBox<String>(skin);
        Array<String> times = new Array<String>(24);
        for (int i = 0; i < 24; i++) {
            times.add(Integer.toString(i) + game.getBundle().get("timeEnding"));
        }
        select.setItems(times);
        select.setSelected(times.get(noBotherEnd));
        this.add(select);
        select.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String selection = select.getSelected();
                selection = selection.replace(game.getBundle().get("timeEnding"), "");
                noBotherEnd = Integer.parseInt(selection);
                prefs.putInteger("noBotherEnd", noBotherEnd);
                prefs.flush();
                game.setPrefs(prefs);
            }
        });
        this.row();
    }

    private void addLanguageSelect() {
        this.add(new Label(game.getBundle().get("language"), skin));
        final SelectBox<String> select = new SelectBox<String>(skin);
        select.setItems("FI", "EN");
        select.setSelected(lang.toUpperCase());
        this.add(select);
        this.row().padTop(20);
        select.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                lang = select.getSelected();
                prefs.putString("lang", lang);
                prefs.flush();
            }
        });
    }

    private void addCharacterButton() {
        TextButton button = new TextButton(game.getBundle().get("changeCharacter"), skin);
        button.pad(15);
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
        button.pad(15);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                boolean creditsExists = false;

                for (Actor stageActor : new Array.ArrayIterator<Actor>(self.getStage().getActors())) {
                    if (stageActor instanceof CreditsDisplay) {
                        creditsExists = true;
                        stageActor.remove();
                    }
                }
                if (!creditsExists) {
                    CreditsDisplay credits = new CreditsDisplay(game, skin);
                    credits.setPosition(self.getStage().getWidth() / 2, self.getStage().getHeight() / 2, Align.center);
                    self.getStage().addActor(credits);
                }
            }
        });
        this.add(button).grow();
        this.row();
    }

}
