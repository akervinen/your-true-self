package fi.tamk.yourtrueself;

/**
 * Character information.
 */
public class Character {

    /**
     * Main stat of the character.
     */
    private final Player.Stat mainStat;

    /**
     * Internal ID of the character. Must be found from the i18n bundle and character atlas.
     */
    private final String id;

    private boolean visible;

    /**
     * Create a new character with given ID and main stat. ID must exist in i18n bundle and
     * character atlas.
     *
     * @param id       internal ID of the character
     * @param mainStat main stat of the character
     */
    Character(String id, Player.Stat mainStat, boolean visible) {
        this.id = id;
        this.mainStat = mainStat;
        setVisibility(visible);
    }

    /**
     * Get the character's main stat.
     *
     * @return main stat of the character
     */
    public Player.Stat getMainStat() {
        return mainStat;
    }

    /**
     * Get the character's internal ID.
     *
     * @return internal ID of the character
     */
    public String getId() {
        return id;
    }

    public boolean getVisibility() {
        return visible;
    }

    public void setVisibility(boolean visible) {
        this.visible = visible;
    }

}
