package fi.tamk.yourtrueself;

import com.badlogic.gdx.Preferences;

/**
 * Helper class to manage achievements, their progress, completion, loading and saving.
 */
public class AchievementManager {
    private Achievement[] achievementList;

    private AchievementListener listener;

    /**
     * Create a new manager using the given list of achievements.
     *
     * @param achievements achievements to use
     */
    public AchievementManager(Achievement[] achievements) {
        achievementList = achievements;
    }

    /**
     * Get achievement object by its internal ID. Throws IllegalArgumentException if ID is invalid.
     *
     * @param id internal ID of the achievement
     * @return achievement with given ID
     */
    public Achievement getAchievement(String id) {
        Achievement ach = null;

        for (Achievement a : achievementList) {
            if (id.equals(a.getId())) {
                ach = a;
            }
        }

        if (ach == null) {
            throw new IllegalArgumentException("invalid achievement \"" + id + "\"");
        }

        return ach;
    }

    /**
     * Save the progress of all achievements.
     *
     * @param preferences preferences object to save progress to
     */
    public void saveProgress(Preferences preferences) {
        for (Achievement a : achievementList) {
            preferences.putInteger(a.getId(), a.getCurrent());
        }
        preferences.flush();
    }

    /**
     * Load the progress of all achievements.
     *
     * @param preferences preferences object to load progress from
     */
    public void loadProgress(Preferences preferences) {
        for (Achievement a : achievementList) {
            a.setCurrent(preferences.getInteger(a.getId(), 0));
        }
    }

    /**
     * Get the current progress of an achievement with the given ID.
     *
     * @param id internal ID of the achievement
     * @return current progress of the given achievement
     */
    public int getProgress(String id) {
        Achievement ach = getAchievement(id);

        return ach.getCurrent();
    }

    /**
     * Increase the progress of an achievement by the given amount.
     *
     * @param id     internal ID of the achievement to progress
     * @param amount how much to progress by
     */
    public void increaseProgress(String id, int amount) {
        Achievement ach = getAchievement(id);

        int max = ach.getMax();
        boolean wasCompleted = ach.getCurrent() >= max;
        int newAmt = ach.getCurrent() + amount;

        if (newAmt >= max && !wasCompleted) {
            if (listener != null) {
                listener.achievementDone(ach);
            }
        }

        ach.setCurrent(newAmt);
    }

    /**
     * Set an achievement completion listener object.
     *
     * @param listener listener object
     */
    public void setListener(AchievementListener listener) {
        this.listener = listener;
    }
}
