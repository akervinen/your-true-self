package fi.tamk.yourtrueself.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.I18NBundle;

import fi.tamk.yourtrueself.Player;

final class StatBar extends ProgressBar {
    public StatBar(Skin skin) {
        super(0, 100, .5f, false, skin);
    }

    @Override
    public float getMinWidth() {
        return 60;
    }

    @Override
    public float getPrefWidth() {
        return 140;
    }
}

public final class StatsDisplay extends Table {
    private Player player;

    private ProgressBar[] bars = new ProgressBar[Player.STAT_ENUMS.length];

    public StatsDisplay(Player player, boolean background, Skin skin) {
        super(skin);

        this.defaults();

        this.player = player;

        I18NBundle bundle = skin.get("i18n-bundle", I18NBundle.class);

        if (background) {
            this.background(skin.getDrawable("panel-orange"));
        }

        for (int i = 0; i < Player.STAT_ENUMS.length; i++) {
            this.add(new Label(bundle.get(Player.STAT_NAMES[i]), skin)).left().padRight(2);
            StatBar bar = new StatBar(skin);
            bar.setValue(player.getByEnum(Player.STAT_ENUMS[i]));
            bar.getStyle().knobBefore.setMinHeight(Gdx.graphics.getPpiY() * 0.1f);
            bar.getStyle().background.setMinHeight(Gdx.graphics.getPpiY() * 0.1f);
            bars[i] = bar;
            this.add(bar).grow();
            this.row().space(0);
        }
    }

    public void updateStats() {
        for (int i = 0; i < bars.length; i++) {
            bars[i].setValue(player.getByEnum(Player.STAT_ENUMS[i]));
        }
    }

    @Override
    public float getPrefWidth() {
        return Gdx.graphics.getPpiX() * 1;
    }

    @Override
    public float getMinHeight() {
        return Gdx.graphics.getPpiY() * 0;
    }

    @Override
    public float getPrefHeight() {
        return Gdx.graphics.getPpiY() * 1;
    }

    @Override
    public float getMaxHeight() {
        return Gdx.graphics.getPpiY() * 2;
    }
}
