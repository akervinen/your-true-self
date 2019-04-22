package fi.tamk.yourtrueself;

/**
 * A multi-tier achievement.
 */
public class TieredAchievement extends Achievement {
    private int[] tiers;

    /**
     * Create a multi-tier achievement with given ID, max progress and tiers. Max progress
     * value should match the highest tier value.
     *
     * @param id    internal ID of the achievement
     * @param max   max progress of the achievement
     * @param tiers achievement's tiers
     * @see Achievement#Achievement(String, int)
     */
    public TieredAchievement(String id, int max, int[] tiers) {
        super(id, max);

        this.tiers = tiers;
    }

    /**
     * Get title property name for the next tier of the achievement.
     *
     * @return title property name for the next tier
     */
    @Override
    public String getTitleProp() {
        return getId() + "." + getClosestTier() + ".Title";
    }

    /**
     * Get max progress of the next closest tier.
     *
     * @return max progress of the next closest tier
     */
    @Override
    public int getMax() {
        return getClosestTier();
    }

    /**
     * Get the next closest tier of the achievement. That is: first tier that is not yet completed.
     *
     * @return first non-completed tier
     */
    public int getClosestTier() {
        int tier = tiers[0];

        for (int t : tiers) {
            tier = t;
            if (t > getCurrent()) {
                break;
            }
        }

        return tier;
    }
}
