package me.jamboxman5.natac.screen.ui.elements.select;

import me.jamboxman5.natac.entity.structures.constructed.*;

public enum StructureSelection {

    BARRACKS("Barracks", Barracks.goldCost, Barracks.resourceCost),
    ARMY_OUTPOST("Army Outpost", ArmyOutpost.goldCost, ArmyOutpost.resourceCost),
    DEPOT("Depot", Depot.goldCost, Depot.resourceCost),
    LIBRARY("Library", Library.goldCost, Library.resourceCost),
    LOGGER("Logger", Logger.goldCost, Logger.resourceCost),
    QUARRY("Quarry", Quarry.goldCost, Quarry.resourceCost),
    SCOUT_TOWER("Scout Tower", ScoutTower.goldCost, ScoutTower.resourceCost);

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

    StructureSelection(String name, int goldCost, int resourceCost) {
        this.name = name;
        this.goldCost = goldCost;
        this.resourceCost = resourceCost;
    }

}
