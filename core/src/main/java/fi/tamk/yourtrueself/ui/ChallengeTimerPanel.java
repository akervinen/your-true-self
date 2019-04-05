package fi.tamk.yourtrueself.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.I18NBundle;

import fi.tamk.yourtrueself.YTSGame;

public class ChallengeTimerPanel extends YTSWindow {
    private final YTSGame game;
    private boolean isDaily = false;
    private boolean isCongratulating = false;

    private Label timerText;

    /**
     * Create new challenge panel with given challenge.
     *
     * @param ytsGame game instance
     * @param skin    skin to use
     */
    public ChallengeTimerPanel(boolean daily, YTSGame ytsGame, Skin skin) {
        // Show thinner title on less dense displays (e.g. desktop)
        super(ytsGame.getBundle().get(daily ? "dailyTimerTitle" : "challengeTimerTitle"),
                false,
                skin,
                getWindowStyle(daily ? "maroon" : "default"));

        this.game = ytsGame;
        isDaily = daily;

        this.padLeft(dp(20)).padRight(dp(20));

        timerText = new Label("", skin);
        timerText.setWrap(true);
        updateLabel();

        this.add(timerText)
                .padTop(dp(15))
                .padBottom(dp(15))
                .grow().row();
    }

    private void updateLabel() {
        if (isCongratulating) {
            return;
        }

        long target;
        if (isDaily) {
            target = game.getNextDailyTime();
        } else {
            target = game.getNextChallengeTime();
        }

        long remaining = target - System.currentTimeMillis();
        if (remaining < 0) {
            return;
        }

        int hours = (int) ((remaining / (1000 * 60 * 60)) % 24);
        int minutes = (int) ((remaining / (1000 * 60)) % 60);

        if (isDaily) {
            timerText.setText(game.getBundle().format("dailyRemainingTime", hours, minutes));
        } else {
            timerText.setText(game.getBundle().format("challengeRemainingTime", hours, minutes));
        }
    }

    public void congratulate() {
        final I18NBundle bundle = getSkin().get("i18n-bundle", I18NBundle.class);
        isCongratulating = true;
        getColor().a = 0;

        RunnableAction changeStyle = new RunnableAction();
        changeStyle.setRunnable(new Runnable() {
            @Override
            public void run() {
                timerText.setStyle(getSkin().get("title-black", Label.LabelStyle.class));
                timerText.setText(bundle.get("challengeCongratulation"));
            }
        });

        RunnableAction resetAction = new RunnableAction();
        resetAction.setRunnable(new Runnable() {
            @Override
            public void run() {
                isCongratulating = false;
                timerText.setStyle(getSkin().get("default", Label.LabelStyle.class));
                updateLabel();
            }
        });

        AlphaAction fadeOut = Actions.fadeOut(.25f);
        fadeOut.setActor(timerText);

        AlphaAction fadeIn = Actions.fadeIn(.25f);
        fadeIn.setActor(timerText);

        SequenceAction seq = new SequenceAction();
        seq.addAction(Actions.alpha(0));
        seq.addAction(changeStyle);
        seq.addAction(Actions.fadeIn(.25f));
        seq.addAction(new DelayAction(1.5f));
        seq.addAction(fadeOut);
        seq.addAction(resetAction);
        seq.addAction(fadeIn);

        addAction(seq);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        updateLabel();

        super.draw(batch, parentAlpha);
    }
}
