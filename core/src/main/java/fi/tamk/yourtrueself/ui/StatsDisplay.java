package fi.tamk.yourtrueself.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.I18NBundle;

import java.text.DecimalFormat;

import fi.tamk.yourtrueself.Player;

/**
 * Progress bar for a stat. Modifies normal ProgressBar by setting different width.
 */
final class StatBar extends ProgressBar {
    private final Player player;
    private final Player.Stat stat;

    /**
     * Create new stat bar.
     *
     * @param skin skin to use
     */
    StatBar(Player player, Player.Stat stat, Skin skin) {
        super(0, 100, .5f, false, skin);

        this.player = player;
        this.stat = stat;

        // Change bar height
        this.getStyle().knobBefore.setMinHeight(Gdx.graphics.getPpiY() * 0.1f);
        this.getStyle().background.setMinHeight(Gdx.graphics.getPpiY() * 0.1f);

        update();
    }

    void update() {
        setValue(player.getByEnum(stat));
    }

    /**
     * Minimum width of the widget.
     *
     * @return minimum width of the widget
     */
    @Override
    public float getMinWidth() {
        return 60;
    }

    /**
     * Preferred width of the widget.
     *
     * @return preferred width of the widget
     */
    @Override
    public float getPrefWidth() {
        return 140;
    }
}

/**
 * Panel that shows player stats in rows. Left column contains stat name, right column
 * contains stat bar.
 */
public final class StatsDisplay extends Table {
    /**
     * List of stat progress bars.
     */
    private final StatBar[] bars = new StatBar[Player.STAT_ENUMS.length];

    public StatsDisplay(Player player, boolean background, Skin skin) {
        super(skin);

        I18NBundle bundle = skin.get("i18n-bundle", I18NBundle.class);

        if (background) {
            this.background(skin.getDrawable("panel-orange"));
        }

        for (int i = 0; i < Player.STAT_ENUMS.length; i++) {
            this.add(new Label(bundle.get(Player.STAT_NAMES[i]), skin)).left().padRight(2);

            bars[i] = new StatBar(player, Player.STAT_ENUMS[i], skin);
            this.add(bars[i]).grow();
            this.row().space(0);
        }
    }

    /**
     * Update stat bar values.
     */
    public void updateStats() {
        for (StatBar bar : bars) {
            bar.update();
        }
    }

    private void createFloatingLabel(int statIdx, float amount) {
        Label lbl = new Label("+" + new DecimalFormat("#.#").format(amount), getSkin(), "black");
        lbl.getColor().a = 0;

        float x = bars[statIdx].getX(Align.center);
        float y = bars[statIdx].getY(Align.center);

        lbl.setPosition(x, y, Align.center);

        SequenceAction seq = new SequenceAction();
        seq.addAction(Actions.fadeIn(.5f));
        seq.addAction(Actions.delay(2f));
        seq.addAction(Actions.fadeOut(.5f));
        seq.addAction(Actions.removeActor());

        lbl.addAction(seq);

        addActor(lbl);
    }

    public void showFloatingNumber(Player.Stat stat, float amount) {
        if (stat == Player.Stat.NONE) {
            for (Player.Stat s : Player.STAT_ENUMS) {
                createFloatingLabel(s.ordinal() - 1, amount);
            }
        } else {
            createFloatingLabel(stat.ordinal() - 1, amount);
        }
    }

    /**
     * Preferred width of the widget. Scales according to pixel density.
     *
     * @return preferred width of the widget
     */
    @Override
    public float getPrefWidth() {
        return Gdx.graphics.getPpiX() * 1;
    }

    /**
     * Minimum height of the widget. Scales according to pixel density.
     *
     * @return minimum height of the widget
     */
    @Override
    public float getMinHeight() {
        return Gdx.graphics.getPpiY() * 0;
    }

    /**
     * Preferred height of the widget. Scales according to pixel density.
     *
     * @return preferred height of the widget
     */
    @Override
    public float getPrefHeight() {
        return Gdx.graphics.getPpiY() * 1;
    }

    /**
     * Maximum height of the widget. Scales according to pixel density.
     *
     * @return maximum height of the widget
     */
    @Override
    public float getMaxHeight() {
        return Gdx.graphics.getPpiY() * 2;
    }
}
