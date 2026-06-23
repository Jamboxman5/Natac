package me.jamboxman5.natac.entity.units;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import me.jamboxman5.natac.entity.Entity;
import me.jamboxman5.natac.entity.structures.Structure;
import me.jamboxman5.natac.net.packet.PacketUtil;

import java.util.UUID;

public abstract class Unit extends Mob {

    protected transient Entity target;

    protected long attackCooldownMS;
    protected int baseDamage;
    protected int range;
    protected int attackForce;

    protected long lastHit = 0;

    protected Unit() {
        color = Color.WHITE;
    }

    protected Unit(float speed, int range, int maxHealth,
                   int baseDamage, long attackCooldownMS, int attackForce,
                   Vector2 tilePos, Vector2 position,
                   Color color, UUID owner) {
        super(speed, maxHealth, tilePos, position, color, owner);

        this.attackCooldownMS =attackCooldownMS;
        this.baseDamage = baseDamage;
        this.range = range;
        this.attackForce = attackForce;

    }

    @Override
    public void update() {
        super.update();

        if (target != null) {
            //move toward current target
            seek(target.getPosition());
            if (position.dst(target.getPosition()) < range && System.currentTimeMillis() - lastHit > attackCooldownMS) {
                PacketUtil.damageEntity(target, baseDamage, getAttackDisplacement(target));
                lastHit = System.currentTimeMillis();
            }
        } else {
            //move back to standard position
            if (!position.epsilonEquals(homePos)) {
                seek(homePos);
            }
        }

    }

    private Vector2 getAttackDisplacement(Entity target) {
        if (target instanceof Structure) return new Vector2(0, 0);
        return target.getPosition().cpy().sub(position).clamp(attackForce, attackForce);
    }

    public Entity getTarget() { return target; }
    public void setTarget(Entity newTarget) { target = newTarget; }

}
