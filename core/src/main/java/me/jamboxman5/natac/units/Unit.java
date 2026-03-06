package me.jamboxman5.natac.units;

public abstract class Unit {
    int speed;
    int range;

    protected Unit(int speed, int range) {
        this.speed = speed;
        this.range = range;
    }

    public abstract void update();
    public abstract void draw();
}
