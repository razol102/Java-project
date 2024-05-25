package execution.instance.entity.ins.impl;

public class Coordinate {
    private final int x, y;
    private boolean set;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
        set = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setSet(boolean set) {
        this.set = set;
    }
    public boolean isSet() {
        return set;
    }
}
