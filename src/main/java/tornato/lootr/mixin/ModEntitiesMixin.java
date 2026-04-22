package tornato.lootr.mixin;

import eu.pb4.polymer.core.api.entity.PolymerEntityUtils;
import net.minecraft.entity.EntityType;
import noobanidus.mods.lootr.common.entity.LootrChestMinecartEntity;
import noobanidus.mods.lootr.common.entity.LootrItemFrame;
import noobanidus.mods.lootr.fabric.init.ModEntities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModEntities.class)
public class ModEntitiesMixin {
    @Inject(method = "registerEntities", at = @At("TAIL"))
    private static void lootrPolymer$registerEntityOverlays(CallbackInfo ci) {
        PolymerEntityUtils.registerOverlay(
                (EntityType<LootrChestMinecartEntity>) (EntityType<?>) ModEntities.LOOTR_MINECART_ENTITY,
                entity -> context -> EntityType.CHEST_MINECART
        );
        PolymerEntityUtils.registerOverlay(
                (EntityType<LootrItemFrame>) (EntityType<?>) ModEntities.ITEM_FRAME,
                entity -> context -> EntityType.ITEM_FRAME
        );
    }
}
