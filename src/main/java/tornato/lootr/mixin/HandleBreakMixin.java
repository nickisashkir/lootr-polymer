package tornato.lootr.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.RandomizableContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import noobanidus.mods.lootr.fabric.event.HandleBreak;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HandleBreak.class)
public class HandleBreakMixin {
    @Inject(method = "beforeBlockBreak", at = @At("HEAD"), cancellable = true, remap = false)
    private static void lootrPolymer$allowDatapackContainerBreak(Level level, Player player, BlockPos pos, BlockState state, BlockEntity blockEntity, CallbackInfoReturnable<Boolean> cir) {
        if (blockEntity instanceof RandomizableContainer container) {
            if (container.getLootTable() == null) {
                cir.setReturnValue(true);
            }
        }
    }
}
