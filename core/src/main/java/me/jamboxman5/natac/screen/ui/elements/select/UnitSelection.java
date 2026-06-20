package me.jamboxman5.natac.screen.ui.elements.select;

import me.jamboxman5.natac.entity.units.army.Soldier;

public enum UnitSelection {

    SOLDIER("Soldier", Soldier.goldCost, Soldier.resourceCost);

    public final String name;
    public final int resourceCost;
    public final int goldCost;

    public String toString() {
        String s = "";
        s += name;
        if (goldCost > 0) s += " ($" + goldCost + ")";
        if (resourceCost > 0) s += " (" + resourceCost + "R)";
        return s;
    }

    UnitSelection(String name, int goldCost, int resourceCost) {
        this.name = name;
        this.goldCost = goldCost;
        this.resourceCost = resourceCost;
    }

}
