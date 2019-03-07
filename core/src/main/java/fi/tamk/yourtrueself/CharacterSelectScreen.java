package fi.tamk.yourtrueself;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import fi.tamk.yourtrueself.ui.CharacterDetails;

final class CharacterSelectScreen implements Screen {
    private static final String[] CHARACTERS = {
            "couchpotato",
            "couchpotato",
            "couchpotato"
    };

    private YTSGame game;
    private AssetManager assetManager;

    private Skin uiSkin;
    private Stage stage;

    CharacterSelectScreen(YTSGame game) {
        this.game = game;
        this.assetManager = game.getAssetManager();

        uiSkin = assetManager.get("ui/skin.json", Skin.class);
        uiSkin.add("char-couchpotato", assetManager.get("characters/couchpotato.png", Texture.class));

        stage = new Stage(new ScreenViewport());

        Table table = new Table();
        for (String chr : CHARACTERS) {
            table.add(new CharacterDetails(chr, uiSkin));
        }

        table.setDebug(true);

        table.setFillParent(true);

        ScrollPane scroller = new ScrollPane(table);
        scroller.setFillParent(true);
        scroller.setOverscroll(false, false);
        stage.addActor(scroller);
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

    }

    @Override
    public void resume() {

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
