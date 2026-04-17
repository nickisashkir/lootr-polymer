package tornato.lootr.mixin;

import noobanidus.mods.lootr.fabric.init.ModStats;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ModStats.class)
public class ModStatsMixin {
    /**
     * @author TorNato
     * @reason Suppress registration of Lootr's custom statistic — it would
     * appear on every vanilla client as an unknown stat ID.
     */
    @Overwrite(remap = false)
    public static void registerStats() {
    }
}
