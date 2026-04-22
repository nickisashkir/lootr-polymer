package tornato.lootr;

import eu.pb4.polymer.rsm.api.RegistrySyncUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import noobanidus.mods.lootr.fabric.init.ModBlocks;
import noobanidus.mods.lootr.fabric.init.ModParticles;
import noobanidus.mods.lootr.fabric.init.ModStats;

import java.util.Map;

public class LootrPolymer implements ModInitializer {
    public static final String ID = "lootr_polymer";

    public static Identifier id(String path) {
        return Identifier.of(ID, path);
    }

    public static final Map<Block, Block> BLOCKS = Map.of(
            ModBlocks.CHEST, Blocks.CHEST,
            ModBlocks.BARREL, Blocks.BARREL,
            ModBlocks.TRAPPED_CHEST, Blocks.TRAPPED_CHEST,
            ModBlocks.SHULKER, Blocks.SHULKER_BOX,
            ModBlocks.SUSPICIOUS_SAND, Blocks.SUSPICIOUS_SAND,
            ModBlocks.SUSPICIOUS_GRAVEL, Blocks.SUSPICIOUS_GRAVEL,
            ModBlocks.DECORATED_POT, Blocks.DECORATED_POT,
            ModBlocks.TROPHY, Blocks.BLAST_FURNACE,
            ModBlocks.INVENTORY, Blocks.CHEST
    );

    @Override
    public void onInitialize() {
        FabricLoader.getInstance().getModContainer(ID).ifPresent(modContainer ->
                ResourceManagerHelper.registerBuiltinResourcePack(
                        id("lootr_polymer"),
                        modContainer,
                        Text.literal("Lootr Polymer"),
                        ResourcePackActivationType.ALWAYS_ENABLED));

        excludeFromSync(Registries.CUSTOM_STAT, ModStats.LOOTED_LOCATION);
        RegistrySyncUtils.setServerEntry(Registries.PARTICLE_TYPE, ModParticles.UNOPENED_PARTCLE);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void excludeFromSync(Registry registry, Object entry) {
        RegistrySyncUtils.setServerEntry(registry, entry);
    }
}
