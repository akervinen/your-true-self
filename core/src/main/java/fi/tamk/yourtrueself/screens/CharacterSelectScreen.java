package fi.tamk.yourtrueself.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
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

public final class CharacterSelectScreen implements Screen {
    private final YTSGame game;

    private Skin uiSkin;
    private Stage stage;

    public CharacterSelectScreen(YTSGame yts) {
        this.game = yts;

        uiSkin = yts.getSkin();

        stage = new Stage(yts.getUiViewport());
//        stage.setDebugAll(true);

        Table main = new Table();
        main.defaults().pad(10).grow();

        Label title = new Label(game.getBundle().get("chooseYourTrueSelf"), uiSkin, "title-white-bg");
        title.setAlignment(Align.center);

        float ppiY = Gdx.graphics.getPpiY();

        main.add(title).height(Value.percentHeight(.1f, main)).row();

        Table characters = new Table();
        characters.defaults().pad(10);

        for (final Character chr : YTSGame.CHARACTERS) {
            CharacterSelectPanel det = new CharacterSelectPanel(chr.getId(), uiSkin);
            characters.add(det);
            det.addButtonListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    game.setPlayerCharacter(chr);
                    game.goToMainScreen();
                }
            });
        }

        ScrollPane scroller = new ScrollPane(characters, uiSkin, "no-bg");
        scroller.setOverscroll(false, false);
        scroller.setScrollingDisabled(false, true);

        main.setFillParent(true);
        main.add(scroller).height(Value.percentHeight(.85f, main));
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
