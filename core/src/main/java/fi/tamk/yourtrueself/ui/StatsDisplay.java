package fi.tamk.yourtrueself.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.I18NBundle;

import fi.tamk.yourtrueself.Player;

/**
 * Progress bar for a stat. Modifies normal ProgressBar by setting different width.
 */
final class StatBar extends ProgressBar {
    /**
     * Create new stat bar.
     *
     * @param skin skin to use
     */
    StatBar(Skin skin) {
        super(0, 100, .5f, false, skin);
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
    private final Player player;

    /**
     * List of stat progress bars.
     */
    private final StatBar[] bars = new StatBar[Player.STAT_ENUMS.length];

    public StatsDisplay(Player player, boolean background, Skin skin) {
        super(skin);

        this.player = player;

        I18NBundle bundle = skin.get("i18n-bundle", I18NBundle.class);

        if (background) {
            this.background(skin.getDrawable("panel-orange"));
        }

        for (int i = 0; i < Player.STAT_ENUMS.length; i++) {
            this.add(new Label(bundle.get(Player.STAT_NAMES[i]), skin)).left().padRight(2);
            StatBar bar = new StatBar(skin);
            bar.setValue(player.getByEnum(Player.STAT_ENUMS[i]));

            // Change bar height
            bar.getStyle().knobBefore.setMinHeight(Gdx.graphics.getPpiY() * 0.1f);
            bar.getStyle().background.setMinHeight(Gdx.graphics.getPpiY() * 0.1f);

            bars[i] = bar;
            this.add(bar).grow();
            this.row().space(0);
        }
    }

    /**
     * Update stat bar values.
     */
    public void updateStats() {
        for (int i = 0; i < bars.length; i++) {
            bars[i].setValue(player.getByEnum(Player.STAT_ENUMS[i]));
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
