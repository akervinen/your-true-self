package fi.tamk.yourtrueself.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
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

    private final HorizontalGroup characterList;

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
        help.setAlignment(Align.center, Align.left);

        main.add(help)
                .height(Value.percentHeight(.1f, main))
                .width(dp(320))
                .row();

        characterList = new HorizontalGroup();
        characterList.space(dp(10));
        characterList.grow();

        addCharacters();

        ScrollPane scroller = new ScrollPane(characterList, uiSkin, "no-bg");
        scroller.setOverscroll(false, false);
        scroller.setScrollingDisabled(false, true);
        scroller.setFadeScrollBars(false);

        main.setFillParent(true);
        main.add(scroller).height(Value.percentHeight(.75f, main));
        stage.addActor(main);
    }

    /**
     * Add all visible characters to {@link #characterList}.
     */
    private void addCharacters() {
        for (final Character chr : YTSGame.CHARACTERS) {
            if (chr.getVisibility()) {
                CharacterSelectPanel det = new CharacterSelectPanel(chr.getId(), game.getSkin());
                characterList.addActor(det);
                det.addButtonListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        game.setPlayerCharacter(chr);
                        game.goToMainScreen();
                    }
                });
            }
        }
    }

    /**
     * Called when game switches to this screen. Refreshes character list in case player has
     * unlocked a new character and sets input processor to its own stage.
     *
     * @see Screen#show()
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        characterList.clearChildren();
        addCharacters();
    }

    /**
     * Called when the game switches away from this screen. Resets input processor.
     *
     * @see Screen#hide()
     */
    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    /**
     * Clears screen with background color, acts and draws its stage.
     *
     * @param delta {@inheritDoc}
     * @see Screen#render(float)
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(201 / 255f, 221 / 255f, 255 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resize(int width, int height) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void pause() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resume() {
    }

    /**
     * Dispose the screen's resources.
     *
     * @see Screen#dispose()
     */
    @Override
    public void dispose() {
        stage.dispose();
    }
}
