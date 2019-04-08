package fi.tamk.yourtrueself.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.I18NBundle;

import fi.tamk.yourtrueself.Achievement;

public class AchievementPopup extends YTSWindow {
    public AchievementPopup(Achievement achievement, Skin skin) {
        super("", false, skin, "default");

        padLeft(dp(40));
        padRight(getPadLeft());
        padBottom(dp(20));

        I18NBundle bundle = skin.get("i18n-bundle", I18NBundle.class);
        getTitleLabel().setText(bundle.get("achievementDone"));

        Label title = new Label(bundle.get(achievement.getTitleProp()), skin, "title");

        add(title);

        pack();
    }
}
