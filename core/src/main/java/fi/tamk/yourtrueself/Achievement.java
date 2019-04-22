package fi.tamk.yourtrueself;

/**
 * Achievement for the player to complete.
 */
public class Achievement {
    private final String id;

    private final int max;
    private int current;

    /**
     * Create a new achievement with given ID and maximum progress.
     * <p>
     * I18NBundle should have two properties: {@code id + ".Title"} and {@code id + ".Description"}.
     *
     * @param id  internal ID
     * @param max maximum progress
     */
    public Achievement(String id, int max) {
        this.id = id;
        this.max = max;
    }

    /**
     * Get internal ID of the achievement.
     *
     * @return internal ID
     */
    public String getId() {
        return id;
    }

    /**
     * Get property name of the achievement's title ({@code id + ".Title"}).
     *
     * @return property name of achievement's title
     */
    public String getTitleProp() {
        return getId() + ".Title";
    }

    /**
     * Get property name of the achievement's description ({@code id + ".Description"}).
     *
     * @return property name of achievement's description
     */
    public String getDescProp() {
        return getId() + ".Description";
    }

    /**
     * Get maximum progress of the achievement. If current &gt;= max, achievement is considered done.
     *
     * @return maximum progress
     */
    public int getMax() {
        return max;
    }

    /**
     * Get current progress of the achievement. If current &gt;= max, achievement is considered done.
     *
     * @return maximum progress
     */
    public int getCurrent() {
        return current;
    }

    /**
     * Whether achievement is completed (current &gt;= max).
     *
     * @return true if achievement is completed
     */
    public boolean isCompleted() {
        return current >= max;
    }

    /**
     * Set current progress of the achievement. If current &gt;= max, achievement is considered done.
     *
     * @param current new progress value, will be capped to maximum progress
     */
    public void setCurrent(int current) {
        if (current > max) {
            current = max;
        }
        this.current = current;
    }
}
