package fi.tamk.yourtrueself.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import fi.tamk.yourtrueself.YTSGame;

public class ChallengeTimerPanel extends YTSWindow {
    private final YTSGame game;

    private Label timerText;

    /**
     * Create new challenge panel with given challenge.
     *
     * @param ytsGame game instance
     * @param skin    skin to use
     */
    public ChallengeTimerPanel(YTSGame ytsGame, Skin skin) {
        // Show thinner title on less dense displays (e.g. desktop)
        super(ytsGame.getBundle().get("challengeTimerTitle"), false, skin, Gdx.graphics.getPpiY() > 200 ? "large" : "default");

        this.game = ytsGame;

        this.setMovable(false);
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
        long remaining = game.getNextChallengeTime() - System.currentTimeMillis();
        if (remaining < 0) {
            return;
        }

        int hours = (int) ((remaining / (1000 * 60 * 60)) % 24);
        int minutes = (int) ((remaining / (1000 * 60)) % 60);

        timerText.setText(game.getBundle().format("challengeRemainingTime", hours, minutes));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        updateLabel();

        super.draw(batch, parentAlpha);
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
