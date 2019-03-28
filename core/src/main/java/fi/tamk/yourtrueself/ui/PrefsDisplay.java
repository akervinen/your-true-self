package fi.tamk.yourtrueself.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
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

import java.util.Calendar;

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

    private CreditsDisplay credits;

    private String lang;
    private int noBotherStart;
    private int noBotherEnd;

    public PrefsDisplay(Preferences prefs, Skin skin, YTSGame ytsGame) {
        super(ytsGame.getBundle().get("prefs"), skin, Gdx.graphics.getPpiY() > 200 ? "large" : "default");

        this.game = ytsGame;
        this.skin = skin;
        this.prefs = prefs;

//        this.debug();

        credits = new CreditsDisplay(game, skin);

        defaults().grow().minWidth(Value.percentWidth(.45f, this));

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

        padLeft(dp(5)).padRight(dp(5));
        pack();
    }

    private <T extends Actor> Cell<T> addSetting(String lblProp, T actor) {
        row().center().padTop(dp(10)).padBottom(dp(10));
        add(new Label(game.getBundle().get(lblProp), skin)).center().padRight(dp(10));
        return add(actor).center();
    }

    private void addMusicSlider() {
        addSetting("musicSlider", new VolumeSlider(prefs, "music", game, skin));
    }

    private void addSoundSlider() {
        addSetting("soundSlider", new VolumeSlider(prefs, "sound", game, skin));
    }

    private Array<String> getHours() {
        Array<String> times = new Array<String>(24);
        Calendar c = Calendar.getInstance();
        for (int i = 0; i < 24; i++) {
            c.clear();
            c.set(Calendar.HOUR_OF_DAY, i);
            times.add(game.getBundle().format("dndTime", c.getTime()));
        }
        return times;
    }

    private void addStartSelect() {
        final SelectBox<String> select = new SelectBox<String>(skin);
        select.setItems(getHours());
        select.setSelectedIndex(noBotherStart);
        select.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                noBotherStart = select.getSelectedIndex();
                prefs.putInteger("noBotherStart", noBotherStart);
                prefs.flush();
                game.setPrefs(prefs);
            }
        });

        row().padTop(dp(10));
        add(new Label(game.getBundle().get("noBother"), skin));
        row().padBottom(dp(5));
        add(new Label(game.getBundle().get("noBotherStart"), skin)).center();
        add(select).center();
    }

    private void addEndSelect() {
        final SelectBox<String> select = new SelectBox<String>(skin);
        select.setItems(getHours());
        select.setSelectedIndex(noBotherEnd);
        select.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                noBotherEnd = select.getSelectedIndex();
                prefs.putInteger("noBotherEnd", noBotherEnd);
                prefs.flush();
                game.setPrefs(prefs);
            }
        });

        addSetting("noBotherEnd", select);
    }

    private void addLanguageSelect() {
        final SelectBox<String> select = new SelectBox<String>(skin);
        select.setItems("FI", "EN");
        select.setSelected(lang.toUpperCase());
        select.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                lang = select.getSelected();
                prefs.putString("lang", lang);
                prefs.flush();
            }
        });

        addSetting("language", select);
    }

    private void addCharacterButton() {
        TextButton button = new TextButton(game.getBundle().get("changeCharacter"), skin);
        button.pad(dp(10));
        button.getLabel().setWrap(true);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.goToCharacterSelect();
            }
        });

        row().padTop(dp(20));
        add(button).padRight(dp(10));
    }

    private void addCreditsButton() {
        TextButton button = new TextButton(game.getBundle().get("credits"), skin);
        button.pad(dp(10));
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showCredits();
            }
        });

        add(button);
    }

    private void showCredits() {
        credits.setPosition(getStage().getWidth() / 2, getStage().getHeight() / 2, Align.center);
        getStage().addActor(credits);
    }

}
