package fi.tamk.yourtrueself.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public final class CharacterDetails extends Table {
    public CharacterDetails(String character, Skin skin) {
        super();

        this.defaults().fill();

        CharacterDisplay disp = new CharacterDisplay(character, skin);
        StatsDisplay stats = new StatsDisplay(skin);

        this.add(disp);
        this.row().grow();
        this.add(stats);

        this.debug();

        this.background(skin.getDrawable("window-orange"));
    }
}
