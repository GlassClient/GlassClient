/*
 * GlassClient PVP Client
 * A free open-source mixin-based PVP client based on liquidbounce with all cheats removed.
 * https://github.com/GlassClient/GlassClient
 */
package net.ccbluex.liquidbounce.features.module.modules.mods;

import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.particles.EvictingList;
import net.ccbluex.liquidbounce.utils.particles.Particle;
import net.ccbluex.liquidbounce.utils.particles.Vec3;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.timer.ParticleTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.minecraft.entity.EntityLivingBase;

import java.util.List;

@ModuleInfo(name = "illuminatedHitParticles", category = ModuleCategory.MODS)
public class Particles extends Module {

    // todo: merge into hiteffects

    private final IntegerValue amount = new IntegerValue("Amount", 10, 1, 20);

    private final BoolValue physics = new BoolValue("Physics", true);

    private final List<Particle> particles = new EvictingList<>(100);
    private final ParticleTimer timer = new ParticleTimer();
    private EntityLivingBase target;

    @EventTarget
    public void onAttack(final AttackEvent event) {
        if (event.getTargetEntity() instanceof EntityLivingBase)
            target = (EntityLivingBase) event.getTargetEntity();
    }

    @EventTarget
    public void UpdateEvent(final UpdateEvent event) {
        if (target != null && target.hurtTime >= 9 && mc.thePlayer.getDistance(target.posX, target.posY, target.posZ) < 10) {
            for (int i = 0; i < amount.get(); i++)
                particles.add(new Particle(new Vec3(target.posX + (Math.random() - 0.5) * 0.5, target.posY + Math.random() * 1 + 0.5, target.posZ + (Math.random() - 0.5) * 0.5)));

            target = null;
        }
    }

    @EventTarget
    public void onRender3D(final Render3DEvent event) {
        if (particles.isEmpty())
            return;

        for (int i = 0; i <= timer.getElapsedTime() / 1E+11; i++) {
            if (physics.get())
                particles.forEach(Particle::update);
            else
                particles.forEach(Particle::updateWithoutPhysics);
        }

        particles.removeIf(particle -> mc.thePlayer.getDistanceSq(particle.position.xCoord, particle.position.yCoord, particle.position.zCoord) > 50 * 10);

        timer.reset();

        RenderUtils.renderParticles(particles);
    }
}