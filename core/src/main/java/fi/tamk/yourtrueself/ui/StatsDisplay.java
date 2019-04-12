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

import static fi.tamk.yourtrueself.YTSGame.dp;

/**
 * Progress bar for a stat. Modifies normal ProgressBar by setting different width.
 */
final class StatBar extends ProgressBar {
    private final Player player;
    private final Player.Stat stat;

    /**
     * Create new stat bar.
     *
     * @param player player whose stat points to use
     * @param stat   which stat to show
     * @param skin   skin to use
     */
    StatBar(Player player, Player.Stat stat, Skin skin) {
        super(0, 100, .5f, false, skin);

        this.player = player;
        this.stat = stat;

        ProgressBarStyle style = this.getStyle();
        // Change bar height
        if (style.knobBefore != null) {
            style.knobBefore.setMinHeight(dp(16));
        }
        if (style.background != null) {
            style.background.setMinHeight(dp(16));
        }

        update();
    }

    /**
     * Update stat bar from player's stat values.
     */
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
        return dp(60);
    }

    /**
     * Preferred width of the widget.
     *
     * @return preferred width of the widget
     */
    @Override
    public float getPrefWidth() {
        return dp(140);
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

    /**
     * Create a stats panel with a bar for each stat.
     * d
     *
     * @param player     player object
     * @param background whether to have a background
     * @param skin       skin object to use
     */
    public StatsDisplay(Player player, boolean background, Skin skin) {
        super(skin);

        padLeft(dp(10));
        padRight(getPadLeft());

        defaults().space(dp(10));

        I18NBundle bundle = skin.get("i18n-bundle", I18NBundle.class);

        if (background) {
            this.background(skin.getDrawable("panel-primary"));
        }

        for (int i = 0; i < Player.STAT_ENUMS.length; i++) {
            this.add(new Label(bundle.get(Player.STAT_NAMES[i]), skin)).left();

            bars[i] = new StatBar(player, Player.STAT_ENUMS[i], skin);
            this.add(bars[i]).grow();
            this.row();
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

    /**
     * Creates a label on given stat bar with given number. Fades in and out, shown for a total of
     * 3 seconds.
     *
     * @param statIdx index of stat according to STAT_ENUMS order
     * @param amount  how much stat changed
     */
    private void createFloatingLabel(int statIdx, float amount) {
        Label lbl = new Label("+" + new DecimalFormat("#.#").format(amount), getSkin());
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

    /**
     * Show a floating number to visualize stat changes. If given stat is NONE, number is shown
     * on all stat bars.
     *
     * @param stat   stat to show number on
     * @param amount how much stat changed
     */
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
