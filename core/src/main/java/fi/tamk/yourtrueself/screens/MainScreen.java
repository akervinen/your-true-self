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

import fi.tamk.yourtrueself.Character;
import fi.tamk.yourtrueself.YTSGame;
import fi.tamk.yourtrueself.ui.CharacterMainPanel;
import fi.tamk.yourtrueself.ui.PrefsDisplay;
import fi.tamk.yourtrueself.ui.StatsDisplay;

/**
 * First screen of the application. Displayed after the application is created.
 */
public class MainScreen implements Screen {
    private YTSGame game;

    private Skin uiSkin;

    private Stage stage;
    private Table table;
    private PrefsDisplay prefsDisplay;
    private StatsDisplay statsDisplay;

    public MainScreen(YTSGame ytsGame) {
        this.game = ytsGame;

        stage = new Stage(game.getUiViewport());
    }

    @Override
    public void show() {
        uiSkin = game.getSkin();

        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setFillParent(true);
        table.pad(5);
//        table.debug();

        table.defaults().maxWidth(Value.percentWidth(.45f, table));

        Character plyCharacter = game.getPlayer().getCurrentCharacter();

        TextButton chooseBtn = new TextButton(game.getBundle().get("changeCharacter"), uiSkin);
        chooseBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goToCharacterSelect();
            }
        });

        TextButton trainBtn = new TextButton(game.getBundle().get("train"), uiSkin);
        trainBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.getPlayer().train();
                statsDisplay.updateStats();
            }
        });

        TextButton prefsBtn = new TextButton(game.getBundle().get("prefs"), uiSkin);
        prefsBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (table.getCell(prefsDisplay) == null) {
                    table.add(prefsDisplay).right();
                } else {
                    table.removeActor(prefsDisplay);
                }
            }
        });

        table.add(chooseBtn)
                .height(Value.percentHeight(.1f, table))
                .top().left()
                .grow();

        table.add(prefsBtn)
                .height(Value.percentHeight(.1f, table))
                .top().right()
                .grow().row();

        table.add(trainBtn)
                .height(Value.percentHeight(.1f, table))
                .top().left()
                .grow().row();

        if (plyCharacter != null) {
            table.add(new CharacterMainPanel(plyCharacter.getId(), uiSkin))
                    .height(Value.percentHeight(.3f, table))
                    .left().grow().row();
        }

        statsDisplay = new StatsDisplay(game.getPlayer(), true, uiSkin);
        prefsDisplay = new PrefsDisplay(game.getPrefs(), uiSkin);
        table.add(statsDisplay)
                .height(Value.percentHeight(.3f, table))
                .left().grow();
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);

        stage.clear();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}