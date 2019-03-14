package fi.tamk.yourtrueself.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

import fi.tamk.yourtrueself.Challenge;

public final class ChallengePanel extends Window {
    public ChallengePanel(Challenge chl, Skin skin) {
        super("Challenge", skin, "peach");

        this.setMovable(false);

        this.add(new Label(chl.getText(), skin)).padBottom(5).row();

        this.add(new TextButton("Done!", skin, "orange-small"));
    }
}
