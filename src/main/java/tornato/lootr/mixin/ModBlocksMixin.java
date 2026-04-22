package tornato.lootr.mixin;

import eu.pb4.polymer.core.api.block.PolymerBlockUtils;
import eu.pb4.polymer.virtualentity.api.BlockWithElementHolder;
import net.minecraft.block.Block;
import noobanidus.mods.lootr.fabric.init.ModBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tornato.lootr.LootrInventoryRenderer;
import tornato.lootr.LootrPolymer;

import java.util.Map;

@Mixin(ModBlocks.class)
public class ModBlocksMixin {
    @Inject(method = "registerBlocks", at = @At("TAIL"))
    private static void lootrPolymer$registerOverlays(CallbackInfo ci) {
        for (Map.Entry<Block, Block> e : LootrPolymer.BLOCKS.entrySet()) {
            Block lootrBlock = e.getKey();
            Block vanillaBlock = e.getValue();
            PolymerBlockUtils.registerOverlay(
                    lootrBlock,
                    (state, context) -> vanillaBlock.getStateWithProperties(state)
            );
            BlockWithElementHolder.registerOverlay(lootrBlock, new LootrInventoryRenderer());
        }
    }
}
