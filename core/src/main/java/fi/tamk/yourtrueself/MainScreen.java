package fi.tamk.yourtrueself;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import fi.tamk.yourtrueself.ui.CharacterDetails;

/**
 * First screen of the application. Displayed after the application is created.
 */
public class MainScreen implements Screen {
    private YTSGame game;

    private Skin uiSkin;

    private Stage stage;

    public MainScreen(YTSGame ytsGame) {
        this.game = ytsGame;

        uiSkin = ytsGame.getSkin();

        stage = new Stage(ytsGame.getUiViewport());

        Table table = new Table();
        table.left();

        TextButton chooseBtn = new TextButton("Select Character", uiSkin);
        chooseBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goToCharacterSelect();
            }
        });

        table.add(chooseBtn).top().left().row();
        table.add(new CharacterDetails("couchpotato", uiSkin)).center().left();

        table.setFillParent(true);
        stage.addActor(table);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.getViewport().apply();

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
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}