package fi.tamk.yourtrueself;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;

import fi.tamk.yourtrueself.ui.CharacterDetails;

final class CharacterSelectScreen implements Screen {
    private static final String[] CHARACTERS = {
            "couchpotato",
            "couchpotato",
            "couchpotato"
    };

    private final YTSGame game;
    private AssetManager assetManager;

    private Skin uiSkin;
    private Stage stage;

    CharacterSelectScreen(YTSGame game) {
        this.game = game;
        this.assetManager = game.getAssetManager();

        uiSkin = assetManager.get(YTSGame.SKIN_PATH, Skin.class);
        uiSkin.add("char-couchpotato", assetManager.get("characters/couchpotato.png", Texture.class));

        stage = new Stage(game.getUiViewport());

        Table main = new Table();
        main.defaults().pad(10);

        Label title = new Label("Choose Your True Self", uiSkin, "title-white-bg");

        main.add(title).maxHeight(Value.percentHeight(.20f, main)).row();

        Table characters = new Table();
        characters.defaults().pad(10).uniform();

        for (String chr : CHARACTERS) {
            CharacterDetails det = new CharacterDetails(chr, uiSkin);
            characters.add(det);
        }

        //characters.setDebug(true);

        ScrollPane scroller = new ScrollPane(characters);
        scroller.setOverscroll(false, false);
        scroller.setScrollingDisabled(false, true);

        main.setFillParent(true);
        main.add(scroller);
        //main.setDebug(true);
        stage.addActor(main);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
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
