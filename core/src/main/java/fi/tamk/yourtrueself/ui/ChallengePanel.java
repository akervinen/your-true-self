package fi.tamk.yourtrueself.ui;

import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

import fi.tamk.yourtrueself.Challenge;

public final class ChallengePanel extends Window {
    private TextButton doneButton;

    public ChallengePanel(Challenge chl, Skin skin) {
        super("Challenge", skin, "peach");

        this.setMovable(false);

        this.add(new Label(chl.getText(), skin)).padBottom(5).row();

        doneButton = new TextButton("Done!", skin, "orange-small");
        this.add(doneButton);
    }

    public boolean addButtonListener(EventListener eventListener) {
        return this.doneButton.addListener(eventListener);
    }
}
