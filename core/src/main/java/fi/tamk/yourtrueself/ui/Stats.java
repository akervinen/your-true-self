package fi.tamk.yourtrueself.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public final class Stats extends Table {
    private static final String[] statNames = {
            "Strength",
            "Endurance",
            "Agility",
            "Flexibility",
            "Balance"
    };

    public Stats(Skin skin) {
        super(skin);
        for (int i = 0; i < 5; i++) {
            this.add(new Label(statNames[i] + ": ", skin)).right();
            ProgressBar bar = new ProgressBar(0, 100, 1f, false, skin);
            bar.setValue(i * 20);
            this.add(bar);
            this.row();
        }
        this.setDebug(true);
    }
}
