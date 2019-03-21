package fi.tamk.yourtrueself.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import fi.tamk.yourtrueself.YTSGame;

public class CreditsDisplay extends Window {
    private YTSGame game;

    public CreditsDisplay(Skin skin, YTSGame game) {
        super(game.getBundle().get("credits"), skin, Gdx.graphics.getPpiY() > 200 ? "large" : "default");

        this.defaults().grow();

        this.game = game;

        this.setMovable(false);

        Label creditsText = new Label(game.getBundle().get("creditsText"), skin);
        creditsText.setWrap(true);

        ScrollPane scroller = new ScrollPane(creditsText, skin, "no-bg");
        scroller.setScrollingDisabled(true, false);

        this.add(scroller).pad(5).row();

        TextButton closeBtn = new TextButton(game.getBundle().get("ok"), skin);
        closeBtn.pad(10);
        final CreditsDisplay self = this;
        closeBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                self.remove();
            }
        });

        this.add(closeBtn).padTop(10).grow();

        this.pack();

//        this.setSize(Gdx.graphics.getPpiX() * 3, Gdx.graphics.getPpiX() * 3);
    }

    @Override
    public float getPrefWidth() {
        return Gdx.graphics.getPpiX() * 2;
    }

    @Override
    public float getPrefHeight() {
        return Gdx.graphics.getPpiY() * 2;
    }
}