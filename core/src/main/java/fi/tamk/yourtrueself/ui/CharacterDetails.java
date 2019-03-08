package fi.tamk.yourtrueself.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

public final class CharacterDetails extends Table {
    public CharacterDetails(String character, Skin skin) {
        super();

        this.background(skin.getDrawable("panel-orange"));

        this.defaults().grow();

        Label name = new Label(character, skin, "white-bg");
        name.setAlignment(Align.center);
        CharacterDisplay disp = new CharacterDisplay(character, skin);
        StatsDisplay stats = new StatsDisplay(skin);

        this.add(name).top().height(Gdx.graphics.getPpiY() * 0.25f);
        this.row();
        this.add(disp);
        this.row();
        this.add(stats);
    }

    @Override
    public float getPrefWidth() {
        return Gdx.graphics.getPpiX() * 2;
    }

    @Override
    public float getPrefHeight() {
        return Gdx.graphics.getPpiY() * 4;
    }
}
