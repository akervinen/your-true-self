package fi.tamk.yourtrueself.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import fi.tamk.yourtrueself.YTSGame;

public class ChallengeTimerPanel extends YTSWindow {
    private final YTSGame game;
    private boolean isDaily = false;
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

    @Override
    public void draw(Batch batch, float parentAlpha) {
        updateLabel();

        super.draw(batch, parentAlpha);
    }
}
