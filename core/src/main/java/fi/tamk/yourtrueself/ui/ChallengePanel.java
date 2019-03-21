package fi.tamk.yourtrueself.ui;

import com.badlogic.gdx.Gdx;
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

    public ChallengePanel(Challenge chl, YTSGame ytsGame, Skin skin) {
        super("Challenge", skin, Gdx.graphics.getPpiY() > 200 ? "large" : "default");

        this.game = ytsGame;
        final Challenge challenge = chl;

        this.setMovable(false);
        this.padLeft(20).padRight(20);

        Label chlText = new Label(game.getBundle().format(chl.getId(), chl.getModifier()), skin);
        chlText.setWrap(true);
        this.add(chlText).padBottom(5).grow().row();

        TextButton doneButton = new TextButton(game.getBundle().get("done"), skin, "orange-small");
        doneButton.pad(15);
        this.add(doneButton).growY();

        doneButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.completeChallenge(challenge);
            }
        });
    }
}
