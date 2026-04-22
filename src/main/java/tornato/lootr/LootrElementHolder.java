package tornato.lootr;

import eu.pb4.polymer.virtualentity.api.ElementHolder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
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

        ServerWorld world = attachment.getWorld();
        var pos = attachment.getPos();
        BlockPos blockPos = BlockPos.ofFloored(pos);

        BlockEntity be = world.getBlockEntity(blockPos);
        if (!(be instanceof ILootrBlockEntity lootrBE)) return;

        var center = lootrBE.getParticleCenter();
        double y = center.y + lootrBE.getParticleYOffset() + 0.5;

        for (var handler : getWatchingPlayers()) {
            ServerPlayerEntity player = handler.player;
            if (!lootrBE.hasServerOpened(player)) {
                world.spawnParticles(player, ParticleTypes.ENCHANT, false, false,
                        center.x, y, center.z, 3, 0.15, 0.1, 0.15, 0.02);
            }
        }
    }
}
