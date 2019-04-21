package fi.tamk.yourtrueself.ui;

import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.I18NBundle;

import static fi.tamk.yourtrueself.YTSGame.dp;

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

        background(skin.getDrawable("panel-primary"));

        defaults().fill().expandX().space(dp(5));

        Label name = new Label(bundle.get("char." + characterId), skin, "minititle-bg");
        name.setAlignment(Align.center);
        CharacterImage disp = new CharacterImage(characterId, skin);

        Label desc = new Label(bundle.get("story." + characterId), skin);
        desc.setWrap(true);
        chooseBtn = new TextButton(bundle.get("choose"), skin, "good");

        add(name).top().height(Value.percentHeight(.05f, this));
        row();
        add(disp).height(Value.percentHeight(.4f, this));
        row();
        add(desc).height(Value.percentHeight(.4f, this));
        row();
        add(chooseBtn).height(Value.percentHeight(.1f, this));
    }

    /**
     * Preferred width of the widget. Scales according to pixel density.
     *
     * @return preferred width of the widget
     */
    @Override
    public float getPrefWidth() {
        return dp(320);
    }

    /**
     * Preferred height of the widget. Scales according to pixel density.
     *
     * @return preferred height of the widget
     */
    @Override
    public float getPrefHeight() {
        return dp(480);
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
