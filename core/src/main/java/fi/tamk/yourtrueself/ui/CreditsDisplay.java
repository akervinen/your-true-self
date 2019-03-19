package fi.tamk.yourtrueself.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import fi.tamk.yourtrueself.YTSGame;

public class CreditsDisplay extends Window {
    private YTSGame game;

    public CreditsDisplay(Skin skin, YTSGame game) {
        super(game.getBundle().get("credits"), skin);

        this.game = game;

        this.setMovable(false);

        Label creditsText = new Label(game.getBundle().get("creditsText"), skin);
        creditsText.setWrap(true);

        ScrollPane scroller = new ScrollPane(creditsText, skin, "no-bg");
        scroller.setScrollingDisabled(true, false);

        this.add(scroller).grow();

        Button closeBtn = new Button(skin, "close");
        final CreditsDisplay self = this;
        closeBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                self.remove();
            }
        });

        this.getTitleTable().add(closeBtn);

        this.setSize(Gdx.graphics.getPpiX() * 3, Gdx.graphics.getPpiX() * 3);
    }

    @Override
    public float getPrefWidth() {
        return Gdx.graphics.getPpiX() * 3;
    }

    @Override
    public float getPrefHeight() {
        return Gdx.graphics.getPpiY() * 4;
    }
}
