package fi.tamk.yourtrueself;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

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

        uiSkin = game.getSkin();

        stage = new Stage(game.getUiViewport());

        Table main = new Table();
        main.defaults().pad(10).grow();

        Label title = new Label("Choose Your True Self", uiSkin, "title-white-bg");
        title.setAlignment(Align.center);

        float ppiY = Gdx.graphics.getPpiY();

        main.add(title).height(ppiY * 1).row();

        Table characters = new Table();
        characters.defaults().pad(10);

        for (String chr : CHARACTERS) {
            CharacterDetails det = new CharacterDetails(chr, uiSkin);
            characters.add(det);
        }

        ScrollPane scroller = new ScrollPane(characters);
        scroller.setOverscroll(false, false);
        scroller.setScrollingDisabled(false, true);

        main.setFillParent(true);
        main.add(scroller);
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