package fi.tamk.yourtrueself.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

import fi.tamk.yourtrueself.Achievement;
import fi.tamk.yourtrueself.AchievementListener;
import fi.tamk.yourtrueself.Challenge;
import fi.tamk.yourtrueself.ChallengeCompletedListener;
import fi.tamk.yourtrueself.Character;
import fi.tamk.yourtrueself.DailyChallenge;
import fi.tamk.yourtrueself.Player;
import fi.tamk.yourtrueself.YTSGame;
import fi.tamk.yourtrueself.ui.AchievementPopup;
import fi.tamk.yourtrueself.ui.AchievementWindow;
import fi.tamk.yourtrueself.ui.ChallengePanel;
import fi.tamk.yourtrueself.ui.ChallengeTimerPanel;
import fi.tamk.yourtrueself.ui.CharacterMainPanel;
import fi.tamk.yourtrueself.ui.PrefsDisplay;
import fi.tamk.yourtrueself.ui.StatsDisplay;

import static fi.tamk.yourtrueself.YTSGame.dp;

/**
 * Main screen of the game. Player spends almost all of their time here.
 */
public class MainScreen implements Screen, AchievementListener {
    private final YTSGame game;
    private final Stage stage;
    private Skin uiSkin;
    private Table challengeTable;
    private PrefsDisplay prefsDisplay;
    private StatsDisplay statsDisplay;
    private AchievementWindow achievementWindow;

    private Challenge currentChallenge;
    private DailyChallenge currentDaily;

    private ChallengeTimerPanel currentChallengeTimer;
    private ChallengeTimerPanel currentDailyTimer;

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

        table.defaults().width(Value.percentWidth(.46f, table));

        prefsDisplay = new PrefsDisplay(game.getPrefs(), uiSkin, game);
        currentChallengeTimer = new ChallengeTimerPanel(false, game, uiSkin);
        currentDailyTimer = new ChallengeTimerPanel(true, game, uiSkin);
        achievementWindow = new AchievementWindow(uiSkin);

        TextButton prefsBtn = new TextButton(game.getBundle().get("prefs"), uiSkin, "misc");
        prefsBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                preferences();
                game.playSound("buttonPress");
            }
        });

        TextButton achBtn = new TextButton(game.getBundle().get("achievements"), uiSkin, "good");
        achBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                achievements();
                game.playSound("buttonPress");
            }
        });

        // Main screen top buttons

        table.add(achBtn)
                .height(Value.percentHeight(.1f, table))
                .top().left()
                .grow();
        table.add(prefsBtn)
                .height(Value.percentHeight(.1f, table))
                .top().right()
                .grow().row();

        // Separate the rest of the main screen into two elements:

        // - Main screen player info (image and stats)

        Table playerInfo = new Table();
        playerInfo.defaults().grow().space(dp(20));

        // - Challenge list

        challengeTable = new Table();

        // Add the current challenges
        refreshChallengeList();

        // Add a callback to update the UI after a challenge is completed
        game.setChallengeCompletedListener(new ChallengeCompletedListener() {
            @Override
            public void challengeCompleted(Challenge challenge) {
                statsDisplay.updateStats();
                refreshChallengeList();

                Player.Stat mainStat = challenge.getMainStat();

                if (mainStat == Player.Stat.NONE) {
                    statsDisplay.showFloatingNumber(mainStat, 1f);
                } else {
                    statsDisplay.showFloatingNumber(mainStat, 2f);
                }

                if (challenge instanceof DailyChallenge && currentDaily == null) {
                    currentDailyTimer.congratulate();
                } else if (currentChallenge == null) {
                    currentChallengeTimer.congratulate();
                }
            }
        });

        game.getAchievementManager().setListener(this);

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
     * Open preferences window.
     */
    public void preferences() {
        prefsDisplay.setPosition(stage.getWidth() / 2, stage.getHeight() / 2, Align.center);
        stage.addActor(prefsDisplay);
    }

    /**
     * Open achievement window.
     */
    private void achievements() {
        achievementWindow.setPosition(stage.getWidth() / 2, stage.getHeight() / 2, Align.center);
        achievementWindow.update();
        stage.addActor(achievementWindow);
    }

    /**
     * Create an achievement popup when an achievement is completed.
     *
     * @param achievement achievement that was completed
     * @see AchievementListener#achievementDone(Achievement)
     */
    @Override
    public void achievementDone(Achievement achievement) {
        AchievementPopup popup = new AchievementPopup(achievement, uiSkin);

        popup.getColor().a = 0;
        popup.setPosition(stage.getWidth() / 2, popup.getHeight() / 2 + dp(80), Align.center);

        stage.addActor(popup);

        popup.addAction(new SequenceAction(
                Actions.fadeIn(.5f),
                Actions.delay(2.5f),
                Actions.fadeOut(.5f),
                Actions.removeActor()
        ));
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
     * Clear challenge list and add active challenges if some exist.
     */
    private void refreshChallengeList() {
        challengeTable.clearChildren();
        challengeTable.top();
        challengeTable.defaults().top().growX().space(dp(20));

        currentDaily = game.getCurrentDaily();
        if (currentDaily != null) {
            challengeTable.add(new ChallengePanel(currentDaily, game, uiSkin));
        } else {
            challengeTable.add(currentDailyTimer);
        }

        currentChallenge = game.getCurrentChallenge();
        if (currentChallenge != null) {
            challengeTable.row();
            challengeTable.add(new ChallengePanel(currentChallenge, game, uiSkin));
        } else {
            challengeTable.row();
            challengeTable.add(currentChallengeTimer);
        }
    }

    /**
     * Render screen, check for challenge updates and refresh challenge list if necessary.
     *
     * @param delta time passed since last render call in seconds
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(201 / 255f, 221 / 255f, 255 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.checkChallenges();
        if (currentChallenge != game.getCurrentChallenge() || currentDaily != game.getCurrentDaily()) {
            refreshChallengeList();
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