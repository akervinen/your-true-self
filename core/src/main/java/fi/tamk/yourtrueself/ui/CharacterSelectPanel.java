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

/**
 * Character information panel for the selection screen. Contains character name,
 * portrait, description/backstory and a button to pick the character.
 */
public final class CharacterSelectPanel extends Table {
    private final TextButton chooseBtn;

    /**
     * Create a new character panel with given internal ID. ID must exist in bundle and character
     * atlas.
     *
     * @param characterId internal ID of the character
     * @param skin        skin to use
     */
    public CharacterSelectPanel(String characterId, Skin skin) {
        super();

        I18NBundle bundle = skin.get("i18n-bundle", I18NBundle.class);

        this.background(skin.getDrawable("panel-primary"));

        this.defaults().grow();

        Label name = new Label(bundle.get("char." + characterId), skin, "minititle-bg");
        name.setAlignment(Align.center);
        CharacterImage disp = new CharacterImage(characterId, skin);

        Label desc = new Label(bundle.get("story." + characterId), skin);
        desc.setWrap(true);
        chooseBtn = new TextButton(bundle.get("choose"), skin, "good");

        this.add(name).top().height(Value.percentHeight(.05f, this));
        this.row();
        this.add(disp).height(Value.percentHeight(.5f, this));
        this.row();
        this.add(desc).height(Value.percentHeight(.3f, this));
        this.row();
        this.add(chooseBtn).height(Value.percentHeight(.1f, this));
    }

    /**
     * Preferred width of the widget. Scales according to pixel density.
     *
     * @return preferred width of the widget
     */
    @Override
    public float getPrefWidth() {
        return Gdx.graphics.getPpiX() * 2;
    }

    /**
     * Preferred height of the widget. Scales according to pixel density.
     *
     * @return preferred height of the widget
     */
    @Override
    public float getPrefHeight() {
        return Gdx.graphics.getPpiY() * 4;
    }

    /**
     * Add a callback for clicking the 'choose' button.
     *
     * @param eventListener callback object to add
     */
    public void addButtonListener(EventListener eventListener) {
        this.chooseBtn.addListener(eventListener);
    }
}
