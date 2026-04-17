package tornato.lootr.mixin;

import eu.pb4.polymer.core.api.item.PolymerItemUtils;
import net.minecraft.world.item.Items;
import noobanidus.mods.lootr.fabric.init.ModItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModItems.class)
public class ModItemsMixin {
    @Inject(method = "registerItems", at = @At("TAIL"))
    private static void lootrPolymer$registerItemOverlays(CallbackInfo ci) {
        PolymerItemUtils.registerOverlay(ModItems.CHEST,
                (stack, context) -> Items.CHEST);
        PolymerItemUtils.registerOverlay(ModItems.TRAPPED_CHEST,
                (stack, context) -> Items.TRAPPED_CHEST);
        PolymerItemUtils.registerOverlay(ModItems.BARREL,
                (stack, context) -> Items.BARREL);
        PolymerItemUtils.registerOverlay(ModItems.SHULKER_BOX,
                (stack, context) -> Items.SHULKER_BOX);
        PolymerItemUtils.registerOverlay(ModItems.SUSPICIOUS_SAND,
                (stack, context) -> Items.SUSPICIOUS_SAND);
        PolymerItemUtils.registerOverlay(ModItems.SUSPICIOUS_GRAVEL,
                (stack, context) -> Items.SUSPICIOUS_GRAVEL);
        PolymerItemUtils.registerOverlay(ModItems.DECORATED_POT,
                (stack, context) -> Items.DECORATED_POT);
        PolymerItemUtils.registerOverlay(ModItems.TROPHY,
                (stack, context) -> Items.PLAYER_HEAD);
    }
}
