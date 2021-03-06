package fi.tamk.yourtrueself.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.I18NBundle;

import fi.tamk.yourtrueself.Achievement;

/**
 * Achievement popup window. Congratulates player and shows the name of the completed
 * achievement.
 */
public class AchievementPopup extends YTSWindow {
    /**
     * Create a new achievement popup for the given achievement.
     *
     * @param achievement achievement that was completed
     * @param skin        skin to use
     */
    public AchievementPopup(Achievement achievement, Skin skin) {
        super("", false, skin, "default");

        padLeft(dp(20));
        padRight(getPadLeft());
        padTop(getPadTop() + dp(10));
        padBottom(dp(20));

        I18NBundle bundle = skin.get("i18n-bundle", I18NBundle.class);
        getTitleLabel().setText(bundle.get("achievementDone"));

        Label title = new Label(bundle.get(achievement.getTitleProp()), skin, "title");
        title.setAlignment(Align.center);
        title.setWrap(true);

        add(title).grow();

        pack();
    }

    /**
     * Preferred width of the widget. Scales according to pixel density.
     *
     * @return preferred width of the widget
     */
    @Override
    public float getPrefWidth() {
        return dp(280);
    }
}
