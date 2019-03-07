package fi.tamk.yourtrueself.ui;

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

        this.defaults().grow();

        for (int i = 0; i < 5; i++) {
            this.add(new Label(STAT_NAMES[i] + ": ", skin)).right();
            ProgressBar bar = new ProgressBar(0, 100, 1f, false, skin);
            bar.setValue(i * 20);
            this.add(bar);
            this.row();
        }
        this.setDebug(true);
    }
}
