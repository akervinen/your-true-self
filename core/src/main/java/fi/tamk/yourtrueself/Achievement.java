package fi.tamk.yourtrueself;

public class Achievement {
    private final String id;

    private final int max;
    private int current;

    public Achievement(String id, int max) {
        this.id = id;
        this.max = max;
    }

    public String getId() {
        return id;
    }

    public int getMax() {
        return max;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }
}
