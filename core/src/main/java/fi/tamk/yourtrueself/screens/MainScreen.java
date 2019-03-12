package fi.tamk.yourtrueself.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import fi.tamk.yourtrueself.Character;
import fi.tamk.yourtrueself.YTSGame;
import fi.tamk.yourtrueself.ui.CharacterDetails;
import fi.tamk.yourtrueself.ui.StatsDisplay;

/**
 * First screen of the application. Displayed after the application is created.
 */
public class MainScreen implements Screen {
    private YTSGame game;

    private Skin uiSkin;

    private Stage stage;

    public MainScreen(YTSGame ytsGame) {
        this.game = ytsGame;

        stage = new Stage(game.getUiViewport());
    }

    @Override
    public void show() {
        uiSkin = game.getSkin();

        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.defaults().pad(20);
        table.left();

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
            }
        });

        table.add(chooseBtn).top().left();
        table.add(trainBtn).top().right().row();

        Character plyCharacter = game.getPlayer().getCurrentCharacter();

        if (plyCharacter != null) {
            table.add(new CharacterDetails(plyCharacter.getId(), uiSkin)).center().left();
        }
        table.add(new StatsDisplay(game.getPlayer(), uiSkin));

        table.setFillParent(true);
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