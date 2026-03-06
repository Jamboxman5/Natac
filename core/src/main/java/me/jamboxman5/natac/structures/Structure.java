package me.jamboxman5.natac.structures;

public abstract class Structure {
    int buildCost;
    int revenuePerTurn;

    protected Structure(int buildCost, int revenuePerTurn) {
        this.buildCost = buildCost;
        this.revenuePerTurn = revenuePerTurn;
    }

    public abstract void update();
    public abstract void draw();
}
