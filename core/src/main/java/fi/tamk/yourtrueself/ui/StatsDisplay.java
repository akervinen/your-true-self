package fi.tamk.yourtrueself.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public final class StatsDisplay extends Table {
    private static final String[] STAT_NAMES = {
            "Strength",
            "Endurance",
            "Agility",
            "Flexibility",
            "Balance"
    };

    public StatsDisplay(Skin skin) {
        super(skin);

        this.defaults();

        for (int i = 0; i < 5; i++) {
            this.add(new Label(STAT_NAMES[i] + ": ", skin)).left();
            ProgressBar bar = new ProgressBar(0, 100, 1f, false, skin);
            bar.setValue(i * 20);
            bar.getStyle().knobBefore.setMinHeight(Gdx.graphics.getPpiY() * 0.1f);
            bar.getStyle().background.setMinHeight(Gdx.graphics.getPpiY() * 0.1f);
            this.add(bar).grow();
            this.row();
        }
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
