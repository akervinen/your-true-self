package fi.tamk.yourtrueself.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Scaling;

import static fi.tamk.yourtrueself.YTSGame.dp;

/**
 * Character portrait.
 * <p>
 * Uses the given internal ID to find the character image and sets the scaling.
 */
public final class CharacterImage extends Image {
    /**
     * Create a new character image with given internal ID. ID must exist in the character atlas.
     *
     * @param characterId internal ID of the character
     * @param skin        skin to use
     */
    public CharacterImage(String characterId, Skin skin) {
        super(skin.getDrawable("char-" + characterId));
        this.setScaling(Scaling.fit);
    }

    /**
     * Preferred width of the widget. Scales according to pixel density.
     *
     * @return preferred width of the widget
     */
    @Override
    public float getPrefWidth() {
        return dp(160);
    }

    /**
     * Minimum height of the widget. Scales according to pixel density.
     *
     * @return minimum height of the widget
     */
    @Override
    public float getMinHeight() {
        return dp(40);
    }

    /**
     * Preferred height of the widget. Scales according to pixel density.
     *
     * @return preferred height of the widget
     */
    @Override
    public float getPrefHeight() {
        return dp(200);
    }
}
