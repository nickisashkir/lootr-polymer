package tornato.lootr.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.LootableInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import noobanidus.mods.lootr.fabric.event.HandleBreak;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HandleBreak.class)
public class HandleBreakMixin {
    @Inject(method = "beforeBlockBreak", at = @At("HEAD"), cancellable = true, remap = false)
    private static void lootrPolymer$allowDatapackContainerBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity, CallbackInfoReturnable<Boolean> cir) {
        if (blockEntity instanceof LootableInventory container) {
            if (container.getLootTable() == null) {
                cir.setReturnValue(true);
            }
        }
    }
}
