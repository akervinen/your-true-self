package fi.tamk.yourtrueself.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.utils.Align;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class LabeledBar extends Stack {
    private ProgressBar progressBar;
    private Label progressLabel;

    public LabeledBar(float min, float max, float stepSize, boolean vertical, Skin skin) {
        super();

        progressBar = new ProgressBar(min, max, stepSize, vertical, skin);

        progressLabel = new Label("", skin);
        updateLabel();

        progressLabel.setAlignment(Align.center);

        addActor(progressBar);
        addActor(progressLabel);
    }

    public boolean setValue(float value) {
        boolean res = progressBar.setValue(value);
        updateLabel();
        return res;
    }

    public void setRange(float min, float max) {
        progressBar.setRange(min, max);
        updateLabel();
    }

    private void updateLabel() {
        DecimalFormat df = new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.FLOOR);

        Gdx.app.log("YTS", "" + progressBar.getValue());

        progressLabel.setText(df.format(progressBar.getValue()) + "/" + df.format(progressBar.getMaxValue()));
    }
}
