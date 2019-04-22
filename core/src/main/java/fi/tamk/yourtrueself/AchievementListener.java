package fi.tamk.yourtrueself;

/**
 * Callback interface for achievement completion tracking.
 */
public interface AchievementListener {
    /**
     * Called when an achievement is completed.
     *
     * @param achievement achievement that was completed
     */
    void achievementDone(Achievement achievement);
}
