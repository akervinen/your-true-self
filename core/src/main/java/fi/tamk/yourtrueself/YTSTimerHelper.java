package fi.tamk.yourtrueself;

/**
 * Helper interface for platform-specific background timer methods.
 */
public interface YTSTimerHelper {
    /**
     * Start timer that triggers at specified real-time in milliseconds from System.currentTimeMillis.
     *
     * @param time time to trigger
     */
    void startTimer(long time);
}
