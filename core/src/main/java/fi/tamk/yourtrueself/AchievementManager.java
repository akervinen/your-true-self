package fi.tamk.yourtrueself;

import com.badlogic.gdx.Preferences;

public class AchievementManager {
    private Achievement[] achievementList;

    public AchievementManager(Achievement[] achievements) {
        achievementList = achievements;
    }

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

    public void saveProgress(Preferences preferences) {
        for (Achievement a : achievementList) {
            preferences.putInteger(a.getId(), a.getCurrent());
        }
        preferences.flush();
    }

    public void loadProgress(Preferences preferences) {
        for (Achievement a : achievementList) {
            a.setCurrent(preferences.getInteger(a.getId(), 0));
        }
    }

    public int getProgress(String id) {
        Achievement ach = getAchievement(id);

        return ach.getCurrent();
    }

    public void increaseProgress(String id, int amount) {
        Achievement ach = getAchievement(id);

        ach.setCurrent(ach.getCurrent() + amount);
    }
}
