package fi.tamk.yourtrueself.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.utils.I18NBundle;

import fi.tamk.yourtrueself.Achievement;
import fi.tamk.yourtrueself.YTSGame;

public class AchievementWindow extends YTSWindow {
    private VerticalGroup achievementList;

    public AchievementWindow(YTSGame ytsGame, Skin skin) {
        super(ytsGame.getBundle().get("achievements"), true, skin, "default");

        setModal(true);

        achievementList = new VerticalGroup();

        for (Achievement ach : YTSGame.ACHIEVEMENTS) {
            achievementList.addActor(new AchievementPanel(ach, skin));
        }

        achievementList.grow().space(dp(10));

        ScrollPane scroller = new ScrollPane(achievementList, skin);
        scroller.setScrollingDisabled(true, false);
        scroller.setFadeScrollBars(false);

        add(scroller).grow().padTop(dp(5));

        pack();
    }

    public void update() {
        for (Actor act : achievementList.getChildren()) {
            if (act instanceof AchievementPanel) {
                AchievementPanel ach = (AchievementPanel) act;
                ach.update();
            }
        }
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

class AchievementPanel extends Table {
    private Achievement achievement;

    private Label titleLabel;
    private Label descLabel;
    private LabeledBar bar;

    public AchievementPanel(Achievement achievement, Skin skin) {
        super(skin);

        this.achievement = achievement;

        this.background(skin.getDrawable("panel-primary"));

        left();

        defaults().grow().uniform().left();

        titleLabel = new Label("", skin, "minititle");
        descLabel = new Label("", skin);
        bar = new LabeledBar(0, achievement.getMax(), 1, false, skin);

        add(titleLabel).left().row();
        add(descLabel).left().row();
        add(bar).left();

        update();
    }

    public void update() {
        I18NBundle bundle = getSkin().get("i18n-bundle", I18NBundle.class);

        titleLabel.setText(bundle.get(achievement.getTitleProp()));
        descLabel.setText(bundle.format(achievement.getDescProp(), achievement.getMax()));
        bar.setRange(0, achievement.getMax());
        bar.setValue(achievement.getCurrent());
    }
}
