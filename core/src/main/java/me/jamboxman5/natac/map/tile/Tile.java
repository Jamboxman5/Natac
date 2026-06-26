package me.jamboxman5.natac.map.tile;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.*;
import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.entity.Entity;
import me.jamboxman5.natac.entity.structures.constructed.ScoutTower;
import me.jamboxman5.natac.map.Map;
import me.jamboxman5.natac.net.packet.PacketClaimTile;
import me.jamboxman5.natac.net.packet.PacketUtil;
import me.jamboxman5.natac.player.Player;
import me.jamboxman5.natac.screen.GameScreen;
import me.jamboxman5.natac.sfx.Sounds;
import me.jamboxman5.natac.entity.structures.Structure;
import me.jamboxman5.natac.entity.structures.constructed.Barracks;
import me.jamboxman5.natac.entity.structures.constructed.TownHall;
import me.jamboxman5.natac.entity.structures.generated.Ruins;
import me.jamboxman5.natac.entity.structures.prop.Prop;
import me.jamboxman5.natac.entity.structures.prop.Tree;
import me.jamboxman5.natac.entity.units.Unit;
import me.jamboxman5.natac.entity.units.army.Soldier;
import space.earlygrey.shapedrawer.JoinType;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.*;

public class Tile {

    private UUID owner;
    private TileType type;

    private boolean scoutDefogged = false;

    private final List<Entity> entities;
    private final List<Entity> pendingEntities;
    private final List<Entity> removingEntities;

    private float currentScale = 1f;

    private transient Sprite sprite;
    private transient boolean isFogged;
    private transient boolean soundPlayed = false;

    private boolean inBattle = false;

//    public final static Texture mountainsLayer = new Texture(Gdx.files.internal("tile/MountainTileSprite_3.png"));

    private Vector2 pos;

    private final boolean flip;

    public Tile() {
        entities = new ArrayList<>();
        pendingEntities = new ArrayList<>();
        removingEntities = new ArrayList<>();
        isFogged = true;
        flip = Math.random() > .5;
    }

    public Tile(float x, float y, TileState state) {
        this();
        this.state = state;
        this.type = getRandomType();

        pos = new Vector2(x, y);
        bounds = new Hexagon(pos);

        if (Math.random() > 0.8 && state != TileState.STARTING) entities.add(new Ruins(pos));
        if (type == TileType.PLAINS) {
            for (int i = 0; i < 10; i++) {
                if (Math.random() > .5)
                    entities.add(new Tree(pos, getRandomPosition()));
            }
        }
    }


    private transient Hexagon bounds;

    private TileState state;

    private float highlightWidth = 1f;

    private transient float pulse = 0;
    private transient float fogOpacity = 1f;

    public void draw(Camera camera, SpriteBatch batch, ShapeDrawer shapes, GameScreen.SelectionMode tileSelectState) {

        if (bounds == null) {
            bounds = new Hexagon(pos);
        }

        if (state == TileState.UNAVAILABLE) return;

        if (sprite == null) sprite = new Sprite(type.texture);

        if (!isFogged) {

            if (flip) sprite.setFlip(true, false);
            sprite.setCenter(bounds.shape.getX(), bounds.shape.getY());
            sprite.setOriginCenter();
            sprite.setAlpha(1f-fogOpacity);
            sprite.draw(batch);

        }

        Color highlight = new Color(state.highlightColor);

        if (tileSelectState == GameScreen.SelectionMode.BASE && state == TileState.STARTING) {
            if (pulse > 1) pulse = 0f;
            highlight.r = pulse;
            highlight.g = pulse;
            pulse += 0.005f;
        } else if (tileSelectState == GameScreen.SelectionMode.TRAVEL && state != TileState.ENEMY_CLAIMED && !Natac.instance.getGame().getSelectedUnit().getTilePosition().epsilonEquals(pos)) {
            if (pulse > 1) pulse = 0.3f;
            highlight.r = pulse;
            highlight.g = pulse;
            highlight.b = pulse;
            pulse += 0.005f;
        } else if (pulse != 1) pulse = 1;

        if (state == TileState.BLOCKED && !isFogged) {
            if (fogOpacity > 0) fogOpacity -= 0.0025f;
//            Color fog = Color.BLACK;
//            fog.a = fogOpacity;
//            shapes.setColor(fog);
//            shapes.filledPolygon(bounds.shape);
        }

        shapes.setDefaultLineWidth(highlightWidth);
        shapes.setColor(highlight);
        shapes.polygon(bounds.shape, JoinType.POINTY);

        if (isFogged) return;

//        if (type == TileType.MOUNTAINS) {
//            Sprite layer = new Sprite(mountainsLayer);
//            if (flip) layer.setFlip(true, false);
//            layer.setScale(currentScale);
//            layer.setCenter(bounds.shape.getX(), bounds.shape.getY());
//            layer.setOriginCenter();
//            layer.setAlpha(1f-fogOpacity);
//            layer.draw(batch);
//        }

        for (Entity e : entities) e.draw(batch, shapes, pos, getCurrentScale());

    }

    private void defogNeighbors(int radius) {
        if (radius == 0) {
            defog();
            return;
        }

        radius--;
        defog();
        for (Tile t : getNeighbors()) t.defogNeighbors(radius);
    }

    public List<Tile> getNeighbors() {
        return Natac.instance.getGame().getMap().getNeighbors(this);
    }
    public List<Tile> getNeighbors(Map map) { return map.getNeighbors(this); }

    public void defog() {
        if (state != TileState.UNAVAILABLE) isFogged = false;
    }

    public void claim(UUID claimingPlayerID, boolean sendPacket) {
        owner = claimingPlayerID;

        if (Natac.instance.player.getID().equals(claimingPlayerID)) {
            setState(TileState.CLAIMED);
            defog();
            defogNeighbors(Natac.instance.getGame().getMap().getDefogTileRadius());
        }
        else setState(TileState.ENEMY_CLAIMED);

        if (sendPacket) {
            PacketClaimTile packet = new PacketClaimTile();
            packet.claimingID = claimingPlayerID;
            packet.tilePos = pos;
            Natac.instance.getClientManager().sendPacketTCP(packet);
            Sounds.TILE_CLAIM.play();
        } else {
            Sounds.TILE_CLAIM.play();
        }

        if (Natac.instance.getGame().getState() == GameScreen.State.CLAIM) {

            PacketUtil.buildStructure(new TownHall(Natac.instance.player.getPlayerClass(), pos), pos);
            PacketUtil.spawnUnit(new Soldier(pos, new Vector2(-20, -20), owner), pos);
            PacketUtil.spawnUnit(new Soldier(pos, new Vector2(20, -20), owner), pos);

            Natac.instance.getGame().setState(GameScreen.State.WAIT);
        }
    }

    public void update(Vector2 touchPos) {

        if (bounds == null) {
            bounds = new Hexagon(pos);
        }

        if (hasScoutTower() && owner != null && owner.equals(Natac.instance.player.getID()) && !scoutDefogged) {
            defogNeighbors(5);
            scoutDefogged = true;
        }

        if (state == TileState.STARTING && isFogged) {
            defog();
            fogOpacity = 0f;
        }

        if (inBattle) {
            //TODO: update logic when battling
            update();

            return;
        }

        if (sprite == null) sprite = new Sprite(type.texture);

        bounds.update(touchPos);

        float targetScale = bounds.contains(touchPos) ? 1.1f : 1f;

        if (bounds.contains(touchPos) && state != TileState.UNAVAILABLE) {
            if (!soundPlayed) {
                Sounds.TILE_HOVER.play();
                soundPlayed = true;
            }
        } else {
            soundPlayed = false;
        }

        currentScale = MathUtils.lerp(currentScale, targetScale, 0.1f);

        sprite.setScale(currentScale, currentScale);

        update();

        float targetHighlightWidth = bounds.contains(touchPos) ? 4f : 2.5f;
        highlightWidth = MathUtils.lerp(highlightWidth, targetHighlightWidth, 0.05f);

    }

    public void sortEntities() {
        entities.sort(new Comparator<Entity>() {
            @Override
            public int compare(Entity o1, Entity o2) {
                float y1 = o1.getPosition().y * 10;
                float y2 = o2.getPosition().y * 10;
                return (int) (y2-y1);
            }
        });
    }

    public void setBattleStatus(boolean battling) { this.inBattle = battling; }

    public void update() {

        for (Entity e : entities) {
            e.update();
            if (e.isDestroyed()) removingEntities.add(e);
        }

        boolean sort = false;
        if (!removingEntities.isEmpty()) {
            entities.removeAll(removingEntities);
            removingEntities.clear();
            sort = true;
        }
        if (!pendingEntities.isEmpty()) {
            entities.addAll(pendingEntities);
            pendingEntities.clear();
            sort = true;
        }

        if (sort) sortEntities();
    }

    public boolean hasEnemies() {
        for (Unit u : getUnits()) {
            if (!u.getOwner().equals(Natac.instance.player.getID())) return true;
        }
        return false;
    }

    public boolean battlePending() {
        if (inBattle) return false;
        if (!hasUnits(Natac.instance.player.getID())) return false;
        return getUnitOwners().size() > 1;
    }

    public boolean hasUnits(UUID owner) {
        for (Unit u : getUnits()) {
            if (u.getOwner().equals(owner)) return true;
        }
        return false;
    }

    public HashSet<UUID> getUnitOwners() {
        HashSet<UUID> owners = new HashSet<>();
        for (Unit u : getUnits()) {
            owners.add(u.getOwner());
        }
        return owners;
    }

    public void add(Entity entity) { pendingEntities.add(entity); }

    public boolean contains(Vector2 point) {
        return bounds.shape.contains(point);
    }

    public void setState(TileState state) {
        this.state = state;
    }

    public Vector2 getTilePosition() { return new Vector2(bounds.shape.getX(), bounds.shape.getY()); }

    public TileState getState() { return state;
    }

    public UUID getOwner() { return owner; }

    public Sprite getSprite() { return sprite; }

    public List<Entity> getEntities() { return entities; }

    public List<Structure> getStructures() {
        ArrayList<Structure> structures = new ArrayList<>();
        for (Entity e : entities) if (e instanceof Structure && !(e instanceof Prop)) structures.add((Structure) e);
        return structures;
    }
    public List<Unit> getUnits() {
        ArrayList<Unit> units = new ArrayList<>();
        for (Entity e : entities) if (e instanceof Unit) units.add((Unit) e);
        return units;
    }
    public TileType getType() {
        return type;
    }

    public void remove(Entity entity) { removingEntities.add(entity); }

    public boolean hasUnits(Player player) {
        for (Entity e : entities) {
            if (!(e instanceof Unit)) continue;
            Unit u = (Unit) e;
            if (u.getOwner().equals(player.getID())) return true;
        }
        return false;
    }

    public void clearStructures() {
        entities.removeAll(getStructures());
    }

    public List<Prop> getProps() {
        ArrayList<Prop> props = new ArrayList<>();
        for (Entity e : entities) if (e instanceof Prop) props.add((Prop) e);
        return props;
    }

    public static class Hexagon {
        private float currentScale = 1f;

        private final Polygon shape;


        public Hexagon(Vector2 center) {
            float[] vertices = new float[12];
            for (int i = 0; i < 6; i++) {
                float angle = i * MathUtils.PI / 3;
                // Multiply the X-offset by the stretch factor
                float stretchFactor = 1.5f;
                int radius = 55;
                vertices[i * 2] = (radius * MathUtils.cos(angle) * stretchFactor);
                vertices[i * 2 + 1] = (radius * MathUtils.sin(angle));
            }
            shape = new Polygon(vertices);
            shape.setPosition(center.x, center.y);
            shape.setOrigin(0,0);
        }

        public void update(Vector2 touchPos) {
            float targetScale = shape.contains(touchPos.x, touchPos.y) ? 1.1f : 1.0f;

            currentScale = MathUtils.lerp(currentScale, targetScale, 0.1f);

            shape.setScale(currentScale, currentScale);
        }

        public float[] getVertices() { return shape.getTransformedVertices(); }
        public boolean contains(Vector2 point) { return shape.contains(point); }
    }

    private static TileType getRandomType() {
        TileType[] types = TileType.values();
        int idx = (int) (Math.random() * types.length);
        return types[idx];
    }

    public boolean isAt(Vector2 pos) {
        return this.pos.epsilonEquals(pos);
    }

    public float getCurrentScale() { return currentScale; }

    public boolean hasBarracks() {
        for (Entity s : entities) {
            if (s instanceof Barracks) return true;
        }
        return false;
    }

    public boolean hasScoutTower() {
        for (Entity s : entities) {
            if (s instanceof ScoutTower) return true;
        }
        return false;
    }

    protected static Vector2 getRandomPosition() {
        float xDiff = (float) (Math.random() * 50f);
        float yDiff = (float) (Math.random() * 50f);
        if (Math.random() > .5) xDiff = -xDiff;
        if (Math.random() > .5) yDiff = -yDiff;
        return new Vector2(xDiff, yDiff);
    }

    public Unit getClosestUnitTarget(Unit targeter) {
        Unit closest = null;
        Vector2 startPos = targeter.getPosition();
        float shortestDistance = Float.POSITIVE_INFINITY;
        for (Entity e : entities) {
            if (!(e instanceof Unit)) continue;
            Unit u = (Unit) e;
            if (u.getOwner().equals(targeter.getOwner())) continue;
            if (u.getPosition().dst(startPos) < shortestDistance) {
                closest = u;
                shortestDistance = u.getPosition().dst(startPos);
            }

        }
        return closest;
    }

    public Entity getClosestTarget(Unit targeter) {
        Entity closest = null;
        Vector2 startPos = targeter.getPosition();
        float shortestDistance = Float.POSITIVE_INFINITY;
        for (Entity e : entities) {
            if (e instanceof Prop) continue;
            if (e instanceof Unit) {
                if (((Unit) e).getOwner().equals(targeter.getOwner())) continue;
            }
            if (e.getPosition().dst(startPos) < shortestDistance) {
                closest = e;
                shortestDistance = e.getPosition().dst(startPos);
            }

        }
        return closest;
    }

    public boolean collides(UUID checkingID, Vector2 pos, Vector2 bounds) {

        Rectangle checkBounds = new Rectangle(pos.x - (bounds.x / 2f), pos.y - (bounds.y / 2f), bounds.x, bounds.y);

        for (Entity e : entities) {
            if (e.getID().equals(checkingID)) continue;
            if (e.getCollisionBox().contains(checkBounds)) return true;
        }
        return false;
    }

}


