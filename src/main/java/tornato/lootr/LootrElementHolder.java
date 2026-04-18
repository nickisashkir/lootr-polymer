package tornato.lootr;

import eu.pb4.polymer.virtualentity.api.ElementHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import noobanidus.mods.lootr.common.api.data.blockentity.ILootrBlockEntity;

public class LootrElementHolder extends ElementHolder {
    private int tickCounter = 0;
    private static final int PARTICLE_INTERVAL = 40;

    @Override
    protected void onTick() {
        tickCounter++;
        if (tickCounter < PARTICLE_INTERVAL) return;
        tickCounter = 0;

        var attachment = getAttachment();
        if (attachment == null) return;

        ServerLevel world = attachment.getWorld();
        var pos = attachment.getPos();
        BlockPos blockPos = BlockPos.containing(pos);

        BlockEntity be = world.getBlockEntity(blockPos);
        if (!(be instanceof ILootrBlockEntity lootrBE)) return;

        var center = lootrBE.getParticleCenter();
        double y = center.y + lootrBE.getParticleYOffset() + 0.5;

        for (var handler : getWatchingPlayers()) {
            ServerPlayer player = handler.getPlayer();
            if (!lootrBE.hasServerOpened(player)) {
                world.sendParticles(player, ParticleTypes.ENCHANT, false, false,
                        center.x, y, center.z, 3, 0.15, 0.1, 0.15, 0.02);
            }
        }
    }
}
