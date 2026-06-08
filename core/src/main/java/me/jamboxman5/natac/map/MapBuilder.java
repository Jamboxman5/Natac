package me.jamboxman5.natac.map;

import com.badlogic.gdx.math.Vector2;
import me.jamboxman5.natac.map.tile.NeighborSet;
import me.jamboxman5.natac.map.tile.Tile;
import me.jamboxman5.natac.map.tile.TileNeighbor;
import me.jamboxman5.natac.map.tile.TileState;

import java.util.ArrayList;
import java.util.List;

public class MapBuilder {
    public static Map generateMap(int radius) {

        Map map = new Map();

        float hexRadius = 50;

        float gap = 20.0f;
        float stretchFactor = 1.5f;

        float verticalStep = (float) ((Math.sqrt(3) * hexRadius) + gap);

        float horizontalStep = 1.5f * (hexRadius * stretchFactor) + (gap * 0.866f * stretchFactor);

        {

            Tile base = new Tile(0, 0, TileState.BLOCKED);
            map.addTile(base);

            generateNeighbors(base, map, horizontalStep, verticalStep, radius);
            map.setupBaseTiles();

        }


//        for (int col = 0; col < columns; col++) {
//            for (int row = 0; row < rows; row++) {
//                float x = startX + (col * horizontalStep);
//                float y = startY + ((rows-1-row) * verticalStep);
//
//                if (col % 2 != 0) y += verticalStep / 2f;
//
//                map.addTile(new Tile(x, y, mapLayout[row][col]));
//            }
//        }
        return map;
    }

    private static void generateNeighbors(Tile base, Map map, float dX, float dY, int radius) {

        List<Tile> lastGenerated = new ArrayList<>();
        lastGenerated.add(base);

        for (int n = radius; n >= 0; n--) {

            List<Tile> newGenerated = new ArrayList<>();

            for (Tile t : lastGenerated) {
                NeighborSet existingNeighbors = map.getNeighborSet(t);
                if (existingNeighbors.isFull()) continue;
                Vector2 basePos = t.getTilePosition();

                //GENERATE NEIGHBORS FOR 1 BASE TILE
                for (TileNeighbor nPos : TileNeighbor.values()) {
                    if (!existingNeighbors.hasNeighbor(nPos)) {
                        Tile g = null;
                        switch (nPos) {
                            case UP:
                                g = new Tile(basePos.x, basePos.y + dY, TileState.BLOCKED);
                                break;
                            case DOWN:
                                g = new Tile(basePos.x, basePos.y -dY, TileState.BLOCKED);
                                break;

                            case UP_LEFT:
                                g = new Tile(basePos.x -dX, basePos.y + (dY/2), TileState.BLOCKED);
                                break;

                            case UP_RIGHT:
                                g = new Tile(basePos.x + dX, basePos.y + (dY/2), TileState.BLOCKED);
                                break;

                            case DOWN_LEFT:
                                g = new Tile(basePos.x -dX, basePos.y -(dY/2), TileState.BLOCKED);
                                break;

                            case DOWN_RIGHT:
                                g = new Tile(basePos.x + dX, basePos.y -(dY/2), TileState.BLOCKED);
                                break;
                        }
                        newGenerated.add(g);
                        map.addTile(g);
                    }
                }

            }

            lastGenerated = newGenerated;

        }



    }

    private static TileState[][] getHexLayout(int radius) {

        return new TileState[][]
            {
                {TileState.UNAVAILABLE, TileState.UNAVAILABLE, TileState.UNAVAILABLE, TileState.UNAVAILABLE, TileState.STARTING, TileState.UNAVAILABLE, TileState.UNAVAILABLE, TileState.UNAVAILABLE, TileState.UNAVAILABLE},
                {TileState.UNAVAILABLE, TileState.UNAVAILABLE, TileState.BLOCKED,  TileState.BLOCKED, TileState.BLOCKED, TileState.BLOCKED, TileState.BLOCKED,  TileState.UNAVAILABLE, TileState.UNAVAILABLE},
                {TileState.STARTING,  TileState.BLOCKED,  TileState.BLOCKED,  TileState.BLOCKED, TileState.BLOCKED, TileState.BLOCKED, TileState.BLOCKED,  TileState.BLOCKED,  TileState.STARTING},
                {TileState.BLOCKED,  TileState.BLOCKED,  TileState.BLOCKED,  TileState.BLOCKED, TileState.BLOCKED, TileState.BLOCKED, TileState.BLOCKED,  TileState.BLOCKED,  TileState.BLOCKED},
                {TileState.BLOCKED,  TileState.BLOCKED,  TileState.BLOCKED,  TileState.BLOCKED, TileState.BLOCKED, TileState.BLOCKED, TileState.BLOCKED,  TileState.BLOCKED,  TileState.BLOCKED},
                {TileState.BLOCKED,  TileState.BLOCKED,  TileState.BLOCKED,  TileState.BLOCKED, TileState.BLOCKED, TileState.BLOCKED, TileState.BLOCKED,  TileState.BLOCKED,  TileState.BLOCKED},
                {TileState.STARTING,  TileState.BLOCKED,  TileState.BLOCKED,  TileState.BLOCKED, TileState.BLOCKED, TileState.BLOCKED, TileState.BLOCKED,  TileState.BLOCKED,  TileState.STARTING},
                {TileState.UNAVAILABLE, TileState.BLOCKED,  TileState.BLOCKED,  TileState.BLOCKED, TileState.BLOCKED, TileState.BLOCKED, TileState.BLOCKED,  TileState.BLOCKED,  TileState.UNAVAILABLE},
                {TileState.UNAVAILABLE, TileState.UNAVAILABLE, TileState.UNAVAILABLE, TileState.BLOCKED, TileState.STARTING, TileState.BLOCKED, TileState.UNAVAILABLE, TileState.UNAVAILABLE, TileState.UNAVAILABLE},
            };
    }

}
