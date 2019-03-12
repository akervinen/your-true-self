package fi.tamk.yourtrueself.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.I18NBundle;

public final class CharacterMainPanel extends Table {
    private TextButton chooseBtn;

    public CharacterMainPanel(String character, Skin skin) {
        super();

        I18NBundle bundle = skin.get("i18n-bundle", I18NBundle.class);

        this.background(skin.getDrawable("panel-orange"));

        this.defaults().grow();

        Label name = new Label(bundle.get(character), skin, "white-bg");
        name.setAlignment(Align.center);
        CharacterImage disp = new CharacterImage(character, skin);

        this.add(name).top().height(Value.percentHeight(.1f, this));
        this.row();
        this.add(disp).height(Value.percentHeight(.8f, this));
    }

    @Override
    public float getPrefWidth() {
        return Gdx.graphics.getPpiX() * 1;
    }

    @Override
    public float getPrefHeight() {
        return Gdx.graphics.getPpiY() * 1.5f;
    }
}
