package fi.tamk.yourtrueself.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

import fi.tamk.yourtrueself.Character;
import fi.tamk.yourtrueself.YTSGame;
import fi.tamk.yourtrueself.ui.CharacterSelectPanel;

import static fi.tamk.yourtrueself.YTSGame.dp;

/**
 * Character selection screen.
 */
public final class CharacterSelectScreen implements Screen {
    private final YTSGame game;

    private final Stage stage;

    /**
     * Create and initialize the character selection screen.
     *
     * @param yts instance of the game class
     */
    public CharacterSelectScreen(YTSGame yts) {
        this.game = yts;

        // Get the game's global skin
        Skin uiSkin = yts.getSkin();

        // Create stage using the game's global UI viewport
        stage = new Stage(yts.getUiViewport());

        Table main = new Table();
        main.defaults().pad(dp(5)).grow();

        Label title = new Label(game.getBundle().get("chooseYourTrueSelf"),
                uiSkin, "title-with-bg");
        title.setAlignment(Align.center);

        main.add(title).height(Value.percentHeight(.1f, main)).row();

        Label help = new Label(game.getBundle().get("selectHelp"), uiSkin);
        help.setWrap(true);

        main.add(help).height(Value.percentHeight(.1f, main)).row();

        Table characters = new Table();
        characters.defaults().pad(dp(10));

        for (final Character chr : YTSGame.CHARACTERS) {
            CharacterSelectPanel det = new CharacterSelectPanel(chr.getId(), uiSkin);
            characters.add(det);
            det.addButtonListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    game.setPlayerCharacter(chr);
                    game.goToMainScreen();
                }
            });
        }

        ScrollPane scroller = new ScrollPane(characters, uiSkin, "no-bg");
        scroller.setOverscroll(false, false);
        scroller.setScrollingDisabled(false, true);
        scroller.setFadeScrollBars(false);

        main.setFillParent(true);
        main.add(scroller).height(Value.percentHeight(.75f, main));
        stage.addActor(main);
    }

    /**
     * Called when game switches to this screen.
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Called when the game switches away from this screen.
     */
    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    /**
     * Render screen.
     *
     * @param delta time passed since last render call in seconds
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(201 / 255f, 221 / 255f, 255 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    /**
     * Called when game window is resized.
     *
     * @param width  new width
     * @param height new height
     */
    @Override
    public void resize(int width, int height) {
    }

    /**
     * Called when game is paused (out of focus, or put to background on Android)
     */
    @Override
    public void pause() {
    }

    /**
     * Called when game is resumed from pause.
     */
    @Override
    public void resume() {
    }

    /**
     * Dispose the screen's resources.
     */
    @Override
    public void dispose() {
        stage.dispose();
    }
}
