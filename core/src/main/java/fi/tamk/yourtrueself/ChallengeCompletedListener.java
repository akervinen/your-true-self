package fi.tamk.yourtrueself;

/**
 * Callback for completed challenges.
 */
public interface ChallengeCompletedListener {
    /**
     * Called when a challenge is completed. Current challenge might be null when this is called.
     *
     * @param challenge completed challenge
     */
    void challengeCompleted(Challenge challenge);
}
