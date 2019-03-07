package fi.tamk.yourtrueself.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;

public final class CharacterDetails extends VerticalGroup {
    public CharacterDetails(String character, Skin skin) {
        super();

        this.addActor(new CharacterDisplay(character, skin));
        this.addActor(new StatsDisplay(skin));
    }
}
