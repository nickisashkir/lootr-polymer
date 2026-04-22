package tornato.lootr;

import eu.pb4.polymer.virtualentity.api.BlockWithElementHolder;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.elements.BlockDisplayElement;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.decoration.DisplayEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import noobanidus.mods.lootr.fabric.init.ModBlocks;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

public class LootrInventoryRenderer implements BlockWithElementHolder {
    @Override
    public boolean tickElementHolder(ServerWorld world, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public @Nullable ElementHolder createElementHolder(ServerWorld world, BlockPos pos, BlockState initialBlockState) {
        var holder = new LootrElementHolder();

        if (initialBlockState.getBlock() == ModBlocks.TROPHY) {
            var trophy = new ItemDisplayElement(new ItemStack(Items.GOLD_INGOT));
            trophy.setItemDisplayContext(ItemDisplayContext.GROUND);
            trophy.setScale(new Vector3f(1.2f, 1.2f, 1.2f));
            trophy.setOffset(new Vec3d(0, 0.15, 0));
            trophy.setBillboardMode(DisplayEntity.BillboardMode.VERTICAL);
            holder.addElement(trophy);
        } else {
            var state = Blocks.LODESTONE.getDefaultState();
            float depth = (initialBlockState.isIn(ConventionalBlockTags.CHESTS) ? 14 / 16f : 1f) + 1 / 24f;

            float scale = 0.2f;
            double shortOffset = -scale / 2;
            double longOffset = -depth / 2;

            var display1 = new BlockDisplayElement();
            display1.setBlockState(state);
            display1.setScale(new Vector3f(scale, scale, depth));
            display1.setOffset(new Vec3d(shortOffset, -0.4, longOffset));

            var display2 = new BlockDisplayElement();
            display2.setBlockState(state);
            display2.setScale(new Vector3f(depth, scale, scale));
            display2.setOffset(new Vec3d(longOffset, -0.4, shortOffset));

            holder.addElement(display1);
            holder.addElement(display2);
        }

        return holder;
    }
}
