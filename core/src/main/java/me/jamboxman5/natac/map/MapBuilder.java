package me.jamboxman5.natac.map;

import me.jamboxman5.natac.map.tile.Tile;

public class MapBuilder {
    public static Map generateMap(int radius) {

        Map map = new Map();

        float hexRadius = 50;
        float columns = radius + 2;
        float rows = radius + 2;


        float startX = 200;
        float startY = 200;

        float gap = 10.0f;
        float stretchFactor = 1.5f;

        float verticalStep = (float) ((Math.sqrt(3) * hexRadius) + gap);

        float horizontalStep = 1.5f * (hexRadius * stretchFactor) + (gap * 0.866f * stretchFactor);

        for (int col = 0; col < columns; col++) {
            for (int row = 0; row < rows; row++) {
                float x = startX + (col * horizontalStep);
                float y = startY + (row * verticalStep);

                if (col % 2 != 0) y += verticalStep / 2f;

                map.addTile(new Tile(x, y));
            }
        }
        return map;
    }
}
