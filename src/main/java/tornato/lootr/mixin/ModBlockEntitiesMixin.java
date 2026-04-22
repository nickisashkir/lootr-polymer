package tornato.lootr.mixin;

import eu.pb4.polymer.core.api.block.PolymerBlockUtils;
import net.minecraft.block.entity.BlockEntityType;
import noobanidus.mods.lootr.fabric.init.ModBlockEntities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModBlockEntities.class)
public class ModBlockEntitiesMixin {
    @Inject(method = "registerBlockEntities", at = @At("TAIL"))
    private static void lootrPolymer$registerBeOverlays(CallbackInfo ci) {
        PolymerBlockUtils.registerBlockEntity(ModBlockEntities.LOOTR_CHEST,
                (be, ctx) -> BlockEntityType.CHEST);
        PolymerBlockUtils.registerBlockEntity(ModBlockEntities.LOOTR_TRAPPED_CHEST,
                (be, ctx) -> BlockEntityType.TRAPPED_CHEST);
        PolymerBlockUtils.registerBlockEntity(ModBlockEntities.LOOTR_SHULKER,
                (be, ctx) -> BlockEntityType.SHULKER_BOX);
        PolymerBlockUtils.registerBlockEntity(ModBlockEntities.LOOTR_BARREL,
                (be, ctx) -> BlockEntityType.BARREL);
        PolymerBlockUtils.registerBlockEntity(ModBlockEntities.LOOTR_BRUSHABLE_BLOCK,
                (be, ctx) -> BlockEntityType.BRUSHABLE_BLOCK);
        PolymerBlockUtils.registerBlockEntity(ModBlockEntities.LOOTR_DECORATED_POT,
                (be, ctx) -> BlockEntityType.DECORATED_POT);
        PolymerBlockUtils.registerBlockEntity(ModBlockEntities.LOOTR_INVENTORY,
                (be, ctx) -> BlockEntityType.CHEST);
    }
}
