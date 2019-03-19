package fi.tamk.yourtrueself.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import fi.tamk.yourtrueself.Challenge;
import fi.tamk.yourtrueself.YTSGame;

public final class ChallengePanel extends Window {
    private YTSGame game;
    private TextButton doneButton;
    private Challenge challenge;

    public ChallengePanel(Challenge chl, YTSGame ytsGame, Skin skin) {
        super("Challenge", skin, "peach");

        this.game = ytsGame;
        this.challenge = chl;

        this.setMovable(false);

        Label chlText = new Label(game.getBundle().format(chl.getId(), 5), skin);
        chlText.setWrap(true);
        this.add(chlText).padBottom(5).grow().row();

        doneButton = new TextButton(game.getBundle().get("done"), skin, "orange-small");
        this.add(doneButton);

        doneButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.completeChallenge();
            }
        });
    }
}
