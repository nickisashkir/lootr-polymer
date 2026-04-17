package tornato.lootr;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.fabricmc.fabric.api.resource.v1.pack.PackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import noobanidus.mods.lootr.fabric.init.ModBlocks;

import java.util.Map;

public class LootrPolymer implements ModInitializer {
    public static final String ID = "lootr_polymer";

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(ID, path);
    }

    /**
     * Maps each Lootr block to the vanilla block it should appear as on the client.
     * Anything not in this map falls back to STRUCTURE_BLOCK so it's obvious if a
     * new Lootr block was added upstream and isn't yet handled here.
     */
    public static final Map<Block, Block> BLOCKS = Map.of(
            ModBlocks.CHEST, Blocks.CHEST,
            ModBlocks.BARREL, Blocks.BARREL,
            ModBlocks.TRAPPED_CHEST, Blocks.TRAPPED_CHEST,
            ModBlocks.SHULKER_BOX, Blocks.SHULKER_BOX,
            ModBlocks.SUSPICIOUS_SAND, Blocks.SUSPICIOUS_SAND,
            ModBlocks.SUSPICIOUS_GRAVEL, Blocks.SUSPICIOUS_GRAVEL,
            ModBlocks.DECORATED_POT, Blocks.DECORATED_POT,
            // Trophy is a custom HorizontalDirectional block; player head is the
            // closest vanilla approximation. Not pixel-perfect, refine if needed.
            ModBlocks.TROPHY, Blocks.PLAYER_HEAD
    );

    @Override
    public void onInitialize() {
        FabricLoader.getInstance().getModContainer(ID).ifPresent(modContainer ->
                ResourceLoader.registerBuiltinPack(
                        id("lootr_polymer"),
                        modContainer,
                        Component.literal("Lootr Polymer"),
                        PackActivationType.ALWAYS_ENABLED));
    }
}
