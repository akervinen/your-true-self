package fi.tamk.yourtrueself.ui;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import java.util.Calendar;

import fi.tamk.yourtrueself.DefaultPreferences;
import fi.tamk.yourtrueself.YTSGame;

/**
 * Custom Slider with better DPI scaling.
 */
class VolumeSlider extends Slider {
    private final YTSGame game;
    private final Preferences preferences;
    private final String prefName;

    /**
     * Create new volume slider using given preference name.
     *
     * @param preferences Preferences object
     * @param prefName    name of the preference to change
     * @param ytsGame     game object
     * @param skin        skin to use
     */
    VolumeSlider(Preferences preferences, final String prefName, YTSGame ytsGame, Skin skin) {
        super(0, 1, 0.1f, false, skin);

        this.game = ytsGame;
        this.preferences = preferences;
        this.prefName = prefName;

        if (prefName.equals(DefaultPreferences.PREF_MUSIC)) {
            setValue(preferences.getFloat(prefName, DefaultPreferences.PREF_MUSIC_DEFAULT));
        } else if (prefName.equals(DefaultPreferences.PREF_SOUND)) {
            setValue(preferences.getFloat(prefName, DefaultPreferences.PREF_SOUND_DEFAULT));
        }

        this.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                VolumeSlider slider = (VolumeSlider) actor;
                if (slider.isDragging()) {
                    return;
                }

                updatePref();
                if (prefName.equals(DefaultPreferences.PREF_MUSIC)) {
                    game.setMusicVolume(getValue());
                    game.playSound("success");
                } else {
                    game.setSoundVolume(getValue());
                    game.playSound("success");
                }
            }
        });
    }

    /**
     * Update preference and save it.
     */
    private void updatePref() {
        preferences.putFloat(prefName, getValue());
        preferences.flush();
    }
}

/**
 * Preferences window.
 */
public class PrefsDisplay extends YTSWindow {

    private final YTSGame game;
    private final Preferences prefs;
    private final Skin skin;

    private CreditsDisplay credits;

    private String lang;
    private int noBotherStart;
    private int noBotherEnd;

    /**
     * Create new preferences window.
     *
     * @param prefs   preferences object to use
     * @param skin    skin to use
     * @param ytsGame game object
     */
    public PrefsDisplay(final Preferences prefs, Skin skin, YTSGame ytsGame) {
        super(ytsGame.getBundle().get("prefs"), true, skin, "default");

        this.game = ytsGame;
        this.skin = skin;
        this.prefs = prefs;

        this.setModal(true);

        credits = new CreditsDisplay(skin);

        defaults().grow().minWidth(Value.percentWidth(.45f, this));

        lang = prefs.getString(DefaultPreferences.PREF_LANGUAGE, "en");
        noBotherStart = prefs.getInteger(DefaultPreferences.PREF_DND_START, DefaultPreferences.PREF_DND_START_DEFAULT);
        noBotherEnd = prefs.getInteger(DefaultPreferences.PREF_DND_END, DefaultPreferences.PREF_DND_END_DEFAULT);

        addSetting("musicSlider", new VolumeSlider(prefs, DefaultPreferences.PREF_MUSIC, game, skin));
        addSetting("soundSlider", new VolumeSlider(prefs, DefaultPreferences.PREF_SOUND, game, skin));

        CheckBox notifBox = new CheckBox("", skin);
        notifBox.setChecked(prefs.getBoolean(DefaultPreferences.PREF_NOTIFICATIONS,
                DefaultPreferences.PREF_NOTIFICATIONS_DEFAULT));
        notifBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                prefs.putBoolean(DefaultPreferences.PREF_NOTIFICATIONS,
                        !prefs.getBoolean(DefaultPreferences.PREF_NOTIFICATIONS,
                                DefaultPreferences.PREF_NOTIFICATIONS_DEFAULT));
                prefs.flush();
            }
        });

        addSetting("notifications", notifBox).expand(false, false).fill(false);
        addStartSelect();
        addEndSelect();
        addLanguageSelect();
        addCharacterButton();
        addCreditsButton();

        pack();
    }

    @Override
    public float getPrefWidth() {
        return dp(320);
    }

    /**
     * Add setting row with a label and given actor.
     *
     * @param lblProp label of the property
     * @param actor   actor to add
     * @param <T>     type of the actor to add
     * @return Cell of added actor
     */
    private <T extends Actor> Cell<T> addSetting(String lblProp, T actor) {
        row().center().padTop(dp(10)).padBottom(dp(10));
        add(new Label(game.getBundle().get(lblProp), skin)).center().padRight(dp(10));
        return add(actor).center();
    }

    /**
     * Get a properly formatted list of hours 0-23.
     *
     * @return list of hours
     */
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

    /**
     * Add row with DND start time selection.
     */
    private void addStartSelect() {
        final SelectBox<String> select = new SelectBox<String>(skin);
        select.setItems(getHours());
        select.setSelectedIndex(noBotherStart);
        select.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                noBotherStart = select.getSelectedIndex();
                prefs.putInteger(DefaultPreferences.PREF_DND_START, noBotherStart);
                prefs.flush();
                game.playSound("success");
            }
        });

        row().padTop(dp(10));
        add(new Label(game.getBundle().get("noBother"), skin)).colspan(2);
        row().padBottom(dp(5));
        add(new Label(game.getBundle().get("noBotherStart"), skin)).center();
        add(select).center();
    }

    /**
     * Add row with DND end time selection.
     */
    private void addEndSelect() {
        final SelectBox<String> select = new SelectBox<String>(skin);
        select.setItems(getHours());
        select.setSelectedIndex(noBotherEnd);
        select.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                noBotherEnd = select.getSelectedIndex();
                prefs.putInteger(DefaultPreferences.PREF_DND_END, noBotherEnd);
                prefs.flush();
                game.playSound("success");
            }
        });

        addSetting("noBotherEnd", select);
    }

    /**
     * Add row language selection.
     */
    private void addLanguageSelect() {
        final SelectBox<String> select = new SelectBox<String>(skin);
        select.setItems("FI", "EN");
        select.setSelected(lang.toUpperCase());
        select.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                lang = select.getSelected();
                prefs.putString(DefaultPreferences.PREF_LANGUAGE, lang);
                prefs.flush();
                game.changeLanguage(lang);
                game.playSound("success");
            }
        });

        addSetting("language", select);
    }

    /**
     * Add "Change Character" button to open character selection screen.
     */
    private void addCharacterButton() {
        TextButton button = new TextButton(game.getBundle().get("changeCharacter"), skin, "good");
        button.getLabel().setWrap(true);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.goToCharacterSelect();
                game.playSound("buttonPress");
            }
        });

        row().padTop(dp(20));
        add(button).padRight(dp(10));
    }

    /**
     * Add credits button to open credits window.
     */
    private void addCreditsButton() {
        TextButton button = new TextButton(game.getBundle().get("credits"), skin, "misc");
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showCredits();
                game.playSound("buttonPress");
            }
        });

        add(button);
    }

    /**
     * Show credits window.
     */
    private void showCredits() {
        credits.setPosition(getStage().getWidth() / 2, getStage().getHeight() / 2, Align.center);
        getStage().addActor(credits);
    }

}
