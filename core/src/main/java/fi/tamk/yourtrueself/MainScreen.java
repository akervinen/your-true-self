package fi.tamk.yourtrueself;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import fi.tamk.yourtrueself.ui.CharacterDetails;
import fi.tamk.yourtrueself.ui.CharacterDisplay;
import fi.tamk.yourtrueself.ui.Stats;

/**
 * First screen of the application. Displayed after the application is created.
 */
public class MainScreen implements Screen {
    private YTSGame game;
    private AssetManager assetManager;

    private Skin uiSkin;

    private Stage stage;

    public MainScreen(YTSGame game) {
        this.game = game;
        this.assetManager = game.getAssetManager();

        uiSkin = assetManager.get("ui/skin.json", Skin.class);
        uiSkin.add("char-couchpotato", assetManager.get("characters/couchpotato.png", Texture.class));

        stage = new Stage(new ScreenViewport());

        Table table = new Table();
        //table.add(new CharacterDisplay("couchpotato", uiSkin)).center().fill();
        //table.row();
        //table.add(new Stats(uiSkin)).fill();
        table.add(new CharacterDetails("couchpotato", uiSkin));
        table.left();

        table.setDebug(true);

        table.setFillParent(true);
        stage.addActor(table);
    }

    static void loadAssets(AssetManager assets) {
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