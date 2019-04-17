package fi.tamk.yourtrueself.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import fi.tamk.yourtrueself.YTSGame;

/**
 * Simple scrollable text window with game credits.
 */
public class CreditsDisplay extends YTSWindow {

    /**
     * Create the credits window with given skin.
     *
     * @param game game instance, used to get i18n bundle
     * @param skin skin to use
     */
    public CreditsDisplay(YTSGame game, Skin skin) {
        super(game.getBundle().get("credits"), true, skin, "default");

        this.defaults().grow();
        this.setModal(true);

        Label creditsText = new Label(game.getBundle().get("creditsText"), skin);
        creditsText.setWrap(true);

        ScrollPane scroller = new ScrollPane(creditsText, skin, "no-bg");
        scroller.setOverscroll(false, false);
        scroller.setScrollingDisabled(true, false);
        scroller.setFadeScrollBars(false);

        this.add(scroller).pad(5);

        this.pack();
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
}
