package fi.tamk.yourtrueself;

public class TieredAchievement extends Achievement {
    private int[] tiers;

    public TieredAchievement(String id, int max, int[] tiers) {
        super(id, max);

        this.tiers = tiers;
    }

    @Override
    public String getTitleProp() {
        return getId() + "." + getClosestTier() + ".Title";
    }

    @Override
    public int getMax() {
        return getClosestTier();
    }

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
