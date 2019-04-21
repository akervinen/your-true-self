package fi.tamk.yourtrueself.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.I18NBundle;

import static fi.tamk.yourtrueself.YTSGame.dp;

/**
 * Character information panel for the main screen. Shows character name and portrait.
 */
public final class CharacterMainPanel extends Table {
    /**
     * Create  character information panel.
     *
     * @param characterId ID of the character to show
     * @param skin        skin to use
     */
    public CharacterMainPanel(String characterId, Skin skin) {
        super();

        I18NBundle bundle = skin.get("i18n-bundle", I18NBundle.class);

        this.background(skin.getDrawable("panel-primary"));

        this.defaults().grow();

        Label name = new Label(bundle.get("char." + characterId), skin, "minititle-bg");
        name.setAlignment(Align.center);
        CharacterImage img = new CharacterImage(characterId, skin);

        this.add(name).top().height(Value.percentHeight(.1f, this));
        this.row();
        this.add(img).height(Value.percentHeight(.8f, this));
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
        return dp(80);
    }

    /**
     * Preferred height of the widget. Scales according to pixel density.
     *
     * @return preferred height of the widget
     */
    @Override
    public float getPrefHeight() {
        return dp(120);
    }
}
