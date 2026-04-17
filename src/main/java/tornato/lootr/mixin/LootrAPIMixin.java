package tornato.lootr.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import noobanidus.mods.lootr.common.api.interfaces.lootr.ILootrAPI;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * Replaces the old {@code LootrAPIImplMixin}, which targeted
 * {@code DefaultLootrAPIImpl#handleProviderOpen} and intercepted
 * {@code ILootrInfoProvider#hasOpened}. Both of those were removed in Lootr 26.1.
 *
 * The equivalent logic now lives as a default method on the {@code ILootrAPI}
 * interface, named {@code handleInstanceOpen(...)}, and the per-player open
 * check became {@code ILootrContainerInstance#hasServerOpened(ServerPlayer)}.
 *
 * Forcing the result to {@code true} causes the stat-award branch to be
 * skipped, matching the original mod's behavior of suppressing per-container
 * stat updates on the client.
 */
@Mixin(ILootrAPI.class)
public interface LootrAPIMixin {

    @ModifyExpressionValue(
            method = "handleInstanceOpen(Lnoobanidus/mods/lootr/common/api/data/ILootrContainerInstance;Lnet/minecraft/server/level/ServerPlayer;Lnoobanidus/mods/lootr/common/api/interfaces/container/IMenuBuilder;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnoobanidus/mods/lootr/common/api/data/ILootrContainerInstance;hasServerOpened(Lnet/minecraft/world/entity/player/Player;)Z"
            ),
            remap = false
    )
    private static boolean lootrPolymer$disableStatUpdates(boolean original) {
        return true;
    }
}
