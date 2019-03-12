package fi.tamk.yourtrueself.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.I18NBundle;

public final class CharacterSelectPanel extends Table {
    private TextButton chooseBtn;

    public CharacterSelectPanel(String character, Skin skin) {
        super();

        I18NBundle bundle = skin.get("i18n-bundle", I18NBundle.class);

        this.background(skin.getDrawable("panel-orange"));

        this.defaults().grow();

        Label name = new Label(bundle.get(character), skin, "white-bg");
        name.setAlignment(Align.center);
        CharacterImage disp = new CharacterImage(character, skin);

        Label desc = new Label(bundle.get(character + "Story"), skin);
        desc.setWrap(true);
        chooseBtn = new TextButton(bundle.get("choose"), skin);

        this.add(name).top().height(Value.percentHeight(.05f, this));
        this.row();
        this.add(disp).height(Value.percentHeight(.5f, this));
        this.row();
        this.add(desc).height(Value.percentHeight(.3f, this));
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
