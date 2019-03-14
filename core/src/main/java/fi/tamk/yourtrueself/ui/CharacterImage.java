package fi.tamk.yourtrueself.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Scaling;

public final class CharacterImage extends Image {
    public CharacterImage(String characterId, Skin skin) {
        super(skin.getDrawable("char-" + characterId));
        this.setScaling(Scaling.fit);
    }

    @Override
    public float getPrefWidth() {
        return Gdx.graphics.getPpiX() * 1;
    }

    @Override
    public float getMinHeight() {
        return Gdx.graphics.getPpiY() * .5f;
    }

    @Override
    public float getPrefHeight() {
        return Gdx.graphics.getPpiY() * 2;
    }
}
