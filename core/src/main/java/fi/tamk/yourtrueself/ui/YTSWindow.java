package fi.tamk.yourtrueself.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.I18NBundle;

import fi.tamk.yourtrueself.YTSGame;

/**
 * Custom Window class that has a Back button in the title bar.
 */
public class YTSWindow extends Window {
    private TextButton backBtn;

    /**
     * Create a window instance.
     *
     * @param title     title of the window
     * @param skin      skin to use
     * @param styleName style name to use
     */
    public YTSWindow(String title, Skin skin, String styleName) {
        super(title, skin, styleName);

        this.setMovable(false);
        this.setModal(true);

        I18NBundle bundle = skin.get("i18n-bundle", I18NBundle.class);

        Label titleLbl = getTitleLabel();
        backBtn = new TextButton(bundle.get("back"), skin, "maroon");
        backBtn.pad(dp(8)).addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                remove();
            }
        });
        getTitleTable().clearChildren();
        getTitleTable().add(backBtn)
                .left()
                .padLeft(dp(1))
                .padRight(dp(10))
                .height(Value.percentHeight(.9f, getTitleTable()));
        getTitleTable().add(titleLbl).expandX().fillX().minWidth(0);

        padLeft(dp(5));
        padRight(dp(5));
    }

    /**
     * Get Back button object.
     *
     * @return Back button widget
     */
    public Button getBackButton() {
        return backBtn;
    }

    /**
     * Convert given pixel value to dp (Density Independent Pixel) value.
     *
     * @param px pixel value to convert
     * @return given pixel value in dp
     */
    static float dp(float px) {
        return YTSGame.dp(px);
    }
}
