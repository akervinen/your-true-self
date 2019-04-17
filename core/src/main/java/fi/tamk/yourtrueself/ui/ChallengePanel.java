package fi.tamk.yourtrueself.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import fi.tamk.yourtrueself.Challenge;
import fi.tamk.yourtrueself.DailyChallenge;
import fi.tamk.yourtrueself.YTSGame;

/**
 * Panel containing challenge information and a button to complete it.
 */
public final class ChallengePanel extends YTSWindow {
    private final YTSGame game;

    /**
     * Create new challenge panel with given challenge.
     *
     * @param chl     challenge to show
     * @param ytsGame game instance
     * @param skin    skin to use
     */
    public ChallengePanel(Challenge chl, YTSGame ytsGame, Skin skin) {
        // Show thinner title on less dense displays (e.g. desktop)
        super(ytsGame.getBundle().get("challengeTitle"), false, skin, "default");

        this.game = ytsGame;

        final Challenge challenge = chl;

        this.setMovable(false);
        this.padLeft(dp(10)).padRight(dp(10));

        // Challenge text
        Label chlText = new Label(game.getBundle().format(chl.getId(), chl.getModifier()), skin);
        chlText.setWrap(true);
        this.add(chlText)
                .padTop(dp(5))
                .padBottom(dp(15))
                .grow()
                .colspan(2)
                .row();

        // Button to complete challenge
        TextButton doneButton = new TextButton(game.getBundle().get("done"), skin, "good");
//        doneButton.pad(dp(15));
        this.add(doneButton).growY();

        doneButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                RunnableAction completeAction = new RunnableAction();
                completeAction.setRunnable(new Runnable() {
                    @Override
                    public void run() {
                        game.completeChallenge(challenge, false);
                    }
                });

                addAction(new SequenceAction(Actions.fadeOut(.25f), completeAction));
            }
        });

        // Button to skip challenge
        TextButton skipButton = new TextButton(game.getBundle().get("skip"), skin, "secondary");
//        skipButton.pad(dp(15));
        this.add(skipButton).growY();

        skipButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                RunnableAction completeAction = new RunnableAction();
                completeAction.setRunnable(new Runnable() {
                    @Override
                    public void run() {
                        game.completeChallenge(challenge, true);
                    }
                });

                addAction(new SequenceAction(Actions.fadeOut(.25f), completeAction));
            }
        });

    }

    /**
     * Create new challenge panel with given challenge.
     *
     * @param chl     challenge to show
     * @param ytsGame game instance
     * @param skin    skin to use
     */
    public ChallengePanel(DailyChallenge chl, YTSGame ytsGame, Skin skin) {
        // Show thinner title on less dense displays (e.g. desktop)
        super(ytsGame.getBundle().get("dailyChallengeTitle"), false, skin, "secondary");

        this.game = ytsGame;

        final Challenge challenge = chl;

        this.setMovable(false);
        this.padLeft(dp(10)).padRight(dp(10));

        // Challenge text
        Label chlText = new Label(game.getBundle().format(chl.getId(), chl.getModifier()), skin);
        chlText.setWrap(true);
        this.add(chlText)
                .padTop(dp(5))
                .padBottom(dp(15))
                .colspan(2)
                .grow()
                .row();


        // Button to complete challenge
        TextButton doneButton = new TextButton(game.getBundle().get("done"), skin, "good");
//        doneButton.pad(dp(15));
        this.add(doneButton).growY();

        doneButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                RunnableAction completeAction = new RunnableAction();
                completeAction.setRunnable(new Runnable() {
                    @Override
                    public void run() {
                        game.completeChallenge(challenge, false);
                    }
                });

                addAction(new SequenceAction(Actions.fadeOut(.25f), completeAction));
            }
        });

        // Button to skip challenge
        TextButton skipButton = new TextButton(game.getBundle().get("skip"), skin, "secondary");
//        skipButton.pad(dp(15));
        this.add(skipButton).growY();

        skipButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                RunnableAction completeAction = new RunnableAction();
                completeAction.setRunnable(new Runnable() {
                    @Override
                    public void run() {
                        game.completeChallenge(challenge, true);
                    }
                });

                addAction(new SequenceAction(Actions.fadeOut(.25f), completeAction));
            }
        });
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
