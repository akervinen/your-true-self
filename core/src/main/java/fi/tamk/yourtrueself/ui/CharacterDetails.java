package fi.tamk.yourtrueself.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.utils.Align;

public final class CharacterDetails extends Table {
    private TextButton chooseBtn;

    public CharacterDetails(String character, Skin skin) {
        super();

        this.background(skin.getDrawable("panel-orange"));

        this.defaults().grow();

        Label name = new Label(character, skin, "white-bg");
        name.setAlignment(Align.center);
        CharacterDisplay disp = new CharacterDisplay(character, skin);
        StatsDisplay stats = new StatsDisplay(skin);
        chooseBtn = new TextButton("Choose", skin);

        //this.add(name).top().height(Gdx.graphics.getPpiY() * 0.25f);
        this.add(name).top().height(Value.percentHeight(.05f, this));
        this.row();
        this.add(disp).height(Value.percentHeight(.5f, this));
        this.row();
        this.add(stats).height(Value.percentHeight(.3f, this));
        this.row();
        this.add(chooseBtn).height(Value.percentHeight(.1f, this));
    }

    @Override
    public float getPrefWidth() {
        return Gdx.graphics.getPpiX() * 2;
    }

    @Override
    public float getPrefHeight() {
        return Gdx.graphics.getPpiY() * 4;
    }

    public boolean addButtonListener(EventListener eventListener) {
        return this.chooseBtn.addListener(eventListener);
    }
}
