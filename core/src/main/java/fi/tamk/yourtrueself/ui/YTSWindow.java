package fi.tamk.yourtrueself.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.I18NBundle;

import fi.tamk.yourtrueself.YTSGame;

/**
 * Custom Window class that has a Back button in the title bar.
 */
public class YTSWindow extends Window {
    private TextButton backBtn;

    private boolean isClickingOutside = false;

    /**
     * Create a window instance.
     *
     * @param title      title of the window
     * @param addBackBtn whether to add back button to window title bar
     * @param skin       skin to use
     * @param styleName  style name to use
     */
    public YTSWindow(String title, boolean addBackBtn, Skin skin, String styleName) {
        super(title, skin, styleName);

        setMovable(false);

        I18NBundle bundle = skin.get("i18n-bundle", I18NBundle.class);

        if (addBackBtn) {
            Label titleLbl = getTitleLabel();
            backBtn = new TextButton(bundle.get("back"), skin, "bad");
            backBtn.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    remove();
                }
            });
            getTitleTable().clearChildren();
            getTitleTable().add(backBtn)
                    .left()
                    .padRight(dp(10));
            getTitleTable().add(titleLbl).expandX().fillX().minWidth(0);
        }

        if (Gdx.graphics.getDensity() > 2.5) {
            padTop(56 * 3);
        } else if (Gdx.graphics.getDensity() > 1.5) {
            padTop(56 * 2);
        } else {
            padTop(56);
        }

        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                isClickingOutside = false;

                if (!isModal()) {
                    return false;
                }
                if (x < 0 || y < 0 || x > getWidth() || y > getHeight()) {
                    isClickingOutside = true;
                    return true;
                }
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (!isClickingOutside) {
                    return;
                }

                if (x < 0 || y < 0 || x > getWidth() || y > getHeight()) {
                    isClickingOutside = false;
                    remove();
                }
            }
        });
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
