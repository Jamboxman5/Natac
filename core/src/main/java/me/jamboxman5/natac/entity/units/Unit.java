package me.jamboxman5.natac.entity.units;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import me.jamboxman5.natac.entity.Entity;
import me.jamboxman5.natac.net.packet.PacketUtil;

import java.util.UUID;

public abstract class Unit extends Mob {

    protected transient Entity target;

    protected Unit() {
        color = Color.WHITE;
    }

    protected Unit(int speed, int range, int maxHealth, Vector2 tilePos, Vector2 position, Color color, UUID owner) {
        super(speed, range, maxHealth, tilePos, position, color, owner);
    }

    @Override
    public void update() {
        super.update();

        if (target != null) {
            //move toward current target
            seek(target.getPosition());
            if (position.dst(target.getPosition()) < 10) {
                if (target instanceof Unit) {
                    ((Unit) target).damage(10, this);
                    PacketUtil.damageEntity(target, 10);
                    PacketUtil.repositionMob((Unit) target, target.getPosition().add(position.cpy().sub(target.getPosition()).clamp(20, 20)));
                } else {
                    PacketUtil.damageEntity(target, 10);
                }
            }
        } else {
            //move back to standard position
            if (!position.epsilonEquals(homePos)) {
                seek(homePos);
            }
        }

    }










    public Entity getTarget() { return target; }
    public void setTarget(Entity newTarget) { target = newTarget; }

    public void damage(int damagePts, Unit damager) {
        damage(damagePts);
        Vector2 displacement = position.cpy().sub(damager.getPosition()).clamp(20, 20);
        position.add(displacement);
    }

}
