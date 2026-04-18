package tornato.lootr.mixin;

import net.minecraft.world.RandomizableContainer;
import net.minecraft.world.level.block.entity.BlockEntity;
import noobanidus.mods.lootr.common.block.entity.BlockEntityTicker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockEntityTicker.class)
public class BlockEntityTickerMixin {
    @Inject(method = "isValidEntity", at = @At("HEAD"), cancellable = true, remap = false)
    private static void lootrPolymer$skipNoLootTable(BlockEntity blockEntity, CallbackInfoReturnable<Boolean> cir) {
        if (isDatapackContainer(blockEntity)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "isValidEntityFull", at = @At("HEAD"), cancellable = true, remap = false)
    private static void lootrPolymer$skipNoLootTableFull(BlockEntity blockEntity, CallbackInfoReturnable<Boolean> cir) {
        if (isDatapackContainer(blockEntity)) {
            cir.setReturnValue(false);
        }
    }

    private static boolean isDatapackContainer(BlockEntity blockEntity) {
        if (blockEntity instanceof RandomizableContainer container) {
            return container.getLootTable() == null;
        }
        return false;
    }
}
