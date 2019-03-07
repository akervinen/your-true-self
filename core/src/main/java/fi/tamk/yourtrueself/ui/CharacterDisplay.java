package fi.tamk.yourtrueself.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Scaling;

public final class CharacterDisplay extends Image {
    public CharacterDisplay(String character, Skin skin) {
        super(skin.getDrawable("char-" + character));
        this.setScaling(Scaling.fit);
    }
}
