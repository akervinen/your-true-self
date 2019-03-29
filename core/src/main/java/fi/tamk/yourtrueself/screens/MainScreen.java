package fi.tamk.yourtrueself.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import fi.tamk.yourtrueself.Challenge;
import fi.tamk.yourtrueself.ChallengeCompletedListener;
import fi.tamk.yourtrueself.Character;
import fi.tamk.yourtrueself.DailyChallenge;
import fi.tamk.yourtrueself.YTSGame;
import fi.tamk.yourtrueself.ui.ChallengePanel;
import fi.tamk.yourtrueself.ui.CharacterMainPanel;
import fi.tamk.yourtrueself.ui.PrefsDisplay;
import fi.tamk.yourtrueself.ui.StatsDisplay;

/**
 * Main screen of the game. Player spends almost all of their time here.
 */
public class MainScreen implements Screen {
    private final YTSGame game;

    private Skin uiSkin;

    private final Stage stage;
    private Table challengeTable;
    private PrefsDisplay prefsDisplay;
    private StatsDisplay statsDisplay;

    /**
     * Create a main screen instance.
     *
     * @param ytsGame instance of the game class
     */
    public MainScreen(YTSGame ytsGame) {
        this.game = ytsGame;
        stage = new Stage(game.getUiViewport());
    }

    /**
     * Called when the screen is switched to. The main layout is created here to show up-to-date
     * information.
     */
    @Override
    public void show() {
        uiSkin = game.getSkin();

        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        table.pad(5);

        table.defaults().maxWidth(Value.percentWidth(.45f, table));

        prefsDisplay = new PrefsDisplay(game.getPrefs(), uiSkin, game);

        TextButton prefsBtn = new TextButton(game.getBundle().get("prefs"), uiSkin);
        prefsBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // First check if preferences window is already open
                // Remove if yes, open if no
                boolean addWindow = true;
                for (Actor stageActor : new Array.ArrayIterator<Actor>(stage.getActors())) {
                    if (stageActor.equals(prefsDisplay)) {
                        prefsDisplay.remove();
                        addWindow = false;
                    }
                }

                if (addWindow) {
                    prefsDisplay.setPosition(stage.getWidth()/2, stage.getHeight()/2, Align.center);
                    stage.addActor(prefsDisplay);
                }
            }
        });

        // Main screen top buttons

        table.add();
        table.add(prefsBtn)
                .height(Value.percentHeight(.1f, table))
                .top().right()
                .grow().row();

        // Separate the rest of the main screen into two elements:

        // - Main screen player info (image and stats)

        Table playerInfo = new Table();
        playerInfo.defaults().grow();

        // - Challenge list

        challengeTable = new Table();

        // Add the current challenges
        refreshChallenges();

        // Add a callback to update the UI after a challenge is completed
        game.setChallengeCompletedListener(new ChallengeCompletedListener() {
            @Override
            public void challengeCompleted(Challenge challenge) {
                statsDisplay.updateStats();
                refreshChallenges();
            }
        });

        statsDisplay = new StatsDisplay(game.getPlayer(), true, uiSkin);

        Character plyCharacter = game.getPlayer().getCurrentCharacter();
        // Defensive check in case player gets to main screen without a character (???)
        if (plyCharacter != null) {
            playerInfo.add(new CharacterMainPanel(plyCharacter.getId(), uiSkin)).row();
        }
        playerInfo.add(statsDisplay);

        table.add(playerInfo).height(Value.percentHeight(.8f, table)).top().left().grow();
        table.add(challengeTable).height(Value.percentHeight(.8f, table)).top().right().grow();

        stage.addActor(table);
    }

    /**
     * Called when the game switches away from this screen.
     */
    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);

        stage.clear();
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

    /**
     * Clear challenge list and add active challenges if some exist.
     */
    private void refreshChallenges() {
        challengeTable.clearChildren();
        challengeTable.top();
        challengeTable.defaults().top().growX().padBottom(dp(40));

        DailyChallenge dchl = game.getCurrentDaily();
        if (dchl != null) {
            challengeTable.add(new ChallengePanel(dchl, game, uiSkin));
        }

        Challenge chl = game.getCurrentChallenge();
        if (chl != null) {
            challengeTable.row().padTop(dp(40));
            challengeTable.add(new ChallengePanel(chl, game, uiSkin));
        }
    }

    /**
     * Render screen.
     *
     * @param delta time passed since last render call in seconds
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (game.checkChallenges() || (!challengeTable.hasChildren() && game.getCurrentChallenge() != null)) {
            refreshChallenges();
        }

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
        // Invoked when your application is paused.
    }

    /**
     * Called when game is resumed from pause.
     */
    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    /**
     * Dispose the screen's resources.
     */
    @Override
    public void dispose() {
        stage.dispose();
    }
}