package fi.tamk.yourtrueself.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public final class CharacterDetails extends Table {
    public CharacterDetails(String character, Skin skin) {
        super();

        CharacterDisplay disp = new CharacterDisplay(character, skin);
        StatsDisplay stats = new StatsDisplay(skin);

        this.add(disp);
        this.row();
        this.add(stats);
    }
}
