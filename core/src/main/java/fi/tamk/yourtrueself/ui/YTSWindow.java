package fi.tamk.yourtrueself.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.I18NBundle;

public class YTSWindow extends Window {
    private TextButton backBtn;

    public YTSWindow(String title, Skin skin, String styleName) {
        super(title, skin, styleName);

        this.setMovable(false);
        this.setModal(true);

        I18NBundle bundle = skin.get("i18n-bundle", I18NBundle.class);

        Label titleLbl = getTitleLabel();
        backBtn = new TextButton(bundle.get("back"), skin, "maroon");
        backBtn.pad(Gdx.graphics.getPpiY() * 0.1f).addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                remove();
            }
        });
        this.getTitleTable().clearChildren();
        this.getTitleTable().add(backBtn).left().padRight(35).height(Value.percentHeight(.9f, getTitleTable()));
        this.getTitleTable().add(titleLbl).expandX().fillX().minWidth(0);
    }

    public Button getBackButton() {
        return backBtn;
    }
}
