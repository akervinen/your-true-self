package fi.tamk.yourtrueself.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.I18NBundle;

public final class StatsDisplay extends Table {
    private static final String[] STAT_NAMES = {
            "statStrength",
            "statEndurance",
            "statAgility",
            "statFlexibility",
            "statBalance"
    };

    public StatsDisplay(Skin skin) {
        super(skin);

        this.defaults();

        I18NBundle bundle = skin.get("i18n-bundle", I18NBundle.class);

        for (int i = 0; i < 5; i++) {
            this.add(new Label(bundle.get(STAT_NAMES[i]), skin)).left().padRight(2);
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
