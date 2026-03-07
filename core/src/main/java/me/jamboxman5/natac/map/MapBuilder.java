package me.jamboxman5.natac.map;

import me.jamboxman5.natac.map.tile.Tile;
import me.jamboxman5.natac.map.tile.TileState;

public class MapBuilder {
    public static Map generateMap(int radius) {

        Map map = new Map();

        float hexRadius = 50;


        float startX = 200;
        float startY = 200;

        float gap = 10.0f;
        float stretchFactor = 1.5f;

        float verticalStep = (float) ((Math.sqrt(3) * hexRadius) + gap);

        float horizontalStep = 1.5f * (hexRadius * stretchFactor) + (gap * 0.866f * stretchFactor);

        TileState[][] mapLayout = getHexLayout(3);
        int columns = mapLayout[0].length;
        int rows = mapLayout.length;

        for (int col = 0; col < columns; col++) {
            for (int row = 0; row < rows; row++) {
                float x = startX + (col * horizontalStep);
                float y = startY + ((rows-1-row) * verticalStep);

                if (col % 2 != 0) y += verticalStep / 2f;

                map.addTile(new Tile(x, y, mapLayout[row][col]));
            }
        }
        return map;
    }

    private static TileState[][] getHexLayout(int radius) {
        return new TileState[][]
            {
                {TileState.HIDDEN, TileState.HIDDEN, TileState.HIDDEN, TileState.HIDDEN, TileState.SELECTABLE, TileState.HIDDEN, TileState.HIDDEN, TileState.HIDDEN, TileState.HIDDEN},
                {TileState.HIDDEN, TileState.HIDDEN, TileState.BLOCKED,  TileState.BLOCKED, TileState.BLOCKED, TileState.BLOCKED, TileState.BLOCKED,  TileState.HIDDEN, TileState.HIDDEN},
                {TileState.SELECTABLE,  TileState.BLOCKED,  TileState.BLOCKED,  TileState.BLOCKED, TileState.BLOCKED, TileState.BLOCKED, TileState.BLOCKED,  TileState.BLOCKED,  TileState.SELECTABLE},
                {TileState.BLOCKED,  TileState.BLOCKED,  TileState.BLOCKED,  TileState.BLOCKED, TileState.BLOCKED, TileState.BLOCKED, TileState.BLOCKED,  TileState.BLOCKED,  TileState.BLOCKED},
                {TileState.BLOCKED,  TileState.BLOCKED,  TileState.BLOCKED,  TileState.BLOCKED, TileState.BLOCKED, TileState.BLOCKED, TileState.BLOCKED,  TileState.BLOCKED,  TileState.BLOCKED},
                {TileState.BLOCKED,  TileState.BLOCKED,  TileState.BLOCKED,  TileState.BLOCKED, TileState.BLOCKED, TileState.BLOCKED, TileState.BLOCKED,  TileState.BLOCKED,  TileState.BLOCKED},
                {TileState.SELECTABLE,  TileState.BLOCKED,  TileState.BLOCKED,  TileState.BLOCKED, TileState.BLOCKED, TileState.BLOCKED, TileState.BLOCKED,  TileState.BLOCKED,  TileState.SELECTABLE},
                {TileState.HIDDEN, TileState.BLOCKED,  TileState.BLOCKED,  TileState.BLOCKED, TileState.BLOCKED, TileState.BLOCKED, TileState.BLOCKED,  TileState.BLOCKED,  TileState.HIDDEN},
                {TileState.HIDDEN, TileState.HIDDEN, TileState.HIDDEN, TileState.BLOCKED, TileState.SELECTABLE, TileState.BLOCKED, TileState.HIDDEN, TileState.HIDDEN, TileState.HIDDEN},
            };
    }

}
