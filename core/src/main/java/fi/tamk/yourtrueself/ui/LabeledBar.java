package fi.tamk.yourtrueself.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.utils.Align;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * ProgressBar widget with a visible progress number.
 */
public class LabeledBar extends Stack {
    private ProgressBar progressBar;
    private Label progressLabel;

    /**
     * Create a new progress bar with visible progress numbers.
     *
     * @param min      minimum value
     * @param max      maximum value
     * @param stepSize step size between values
     * @param vertical whether the bar should be vertical
     * @param skin     skin to use
     */
    public LabeledBar(float min, float max, float stepSize, boolean vertical, Skin skin) {
        super();

        progressBar = new ProgressBar(min, max, stepSize, vertical, skin);

        progressLabel = new Label("", skin);
        updateLabel();

        progressLabel.setAlignment(Align.center);

        addActor(progressBar);
        addActor(progressLabel);
    }

    /**
     * Set current progress to given value and update the progress label.
     *
     * @param value new value
     * @return false if the value was not changed because the progress bar already had the value or it was canceled by a listener
     */
    public boolean setValue(float value) {
        boolean res = progressBar.setValue(value);
        updateLabel();
        return res;
    }

    /**
     * Sets the range of this progress bar. The progress bar's current value is clamped to the range.
     *
     * @param min minimum value
     * @param max maximum value
     */
    public void setRange(float min, float max) {
        progressBar.setRange(min, max);
        updateLabel();
    }

    /**
     * Update progress label to match current value and maximum value.
     */
    private void updateLabel() {
        DecimalFormat df = new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.FLOOR);

        progressLabel.setText(df.format(progressBar.getValue()) + "/" + df.format(progressBar.getMaxValue()));
    }
}
