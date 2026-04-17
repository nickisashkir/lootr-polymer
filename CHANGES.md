# LootrPolymer — 26.1 update notes

## Build
- MC `26.1`, fabric-loader `0.18.5`, fabric-api `0.144.3+26.1`
- polymer-core / polymer-virtual-entity `0.16.0-pre.3+26.1`
- Java 25, Loom `1.15-SNAPSHOT`
- **No Yarn.** Modern Loom defaults to Mojang mappings; the source was rewritten accordingly to match Lootr 26.1's own mojmap source.
- Lootr runtime dep: `maven.modrinth:lootr:s0EnSZFB` (lootr-fabric-26.1-1.21.35.103)

## Source changes vs the 1.21.x version

### `LootrPolymer.java`
- Mojmap imports (`net.minecraft.world.level.block.*`, `net.minecraft.resources.Identifier`, `Component`)
- `ModBlocks.SHULKER` → `ModBlocks.SHULKER_BOX` (renamed upstream)
- Added the new Lootr blocks to the overlay map: `SUSPICIOUS_SAND`, `SUSPICIOUS_GRAVEL`, `DECORATED_POT`, `TROPHY`
- TROPHY → `Blocks.PLAYER_HEAD` is a placeholder (closest vanilla shape; properties don't match so facing won't sync). Refine later if you care.

### `LootrInventoryRenderer.java`
- `ServerWorld` → `ServerLevel`, `Vec3d` → `Vec3`, `BlockPos` package change
- `getDefaultState()` → `defaultBlockState()`, `state.isIn(tag)` → `state.is(tag)`

### `ModBlocksMixin.java`
- Switched from `@ModifyArg` (which intercepted every `Registry.register` call) to `@Inject(at = TAIL)` iterating `LootrPolymer.BLOCKS`. This is robust against new blocks being added upstream and against Lootr changing how it calls Registry.register.

### `ModBlockEntitiesMixin.java`
- Same `@Inject TAIL` pattern.
- **Per-BE mappings** instead of "everything is BARREL." New entries for `BRUSHABLE_BLOCK` → `BlockEntityType.BRUSHABLE_BLOCK` and `DECORATED_POT` → `BlockEntityType.DECORATED_POT`. Without these, brushable blocks and decorated pots would have the wrong client-side BE.

### `ModEntitiesMixin.java`
- **This was a hard crash in the old code.** Lootr 26.1 registers two entity types in `registerEntities()`: the chest minecart AND a new `LootrItemFrame`. The old `@ModifyArg` blindly cast every registered entity to `EntityType<LootrChestMinecartEntity>` — `ClassCastException` at startup when it hit the item frame.
- New version registers both explicitly: minecart → `CHEST_MINECART`, item frame → `ITEM_FRAME`.

### `ModItemsMixin.java`
- Per-item mapping to vanilla counterparts instead of "everything is a barrier" (the old behavior would show `/give @s lootr:chest` as a barrier in the player's inventory).

### `ModStatsMixin.java`
- Unchanged logic, just mojmap-clean.

### `LootrAPIMixin.java` (was `LootrAPIImplMixin.java`)
- **Big rewrite.** The old mixin targeted `DefaultLootrAPIImpl#handleProviderOpen` and intercepted `ILootrInfoProvider#hasOpened(PlayerEntity)`. **Both are gone in 26.1.**
- The logic moved up to a default method on the `ILootrAPI` interface, called `handleInstanceOpen(ILootrContainerInstance, ServerPlayer, IMenuBuilder)`, and the per-player open check became `ILootrContainerInstance#hasServerOpened(ServerPlayer)`.
- New mixin retargets accordingly. It is an `interface` mixin (required when targeting an interface), with the handler as a `private static` method (Java 9+ interface feature, fine on Java 25).

## Known risks — read before shipping

1. **Polymer 0.16 API surface.** I kept the original's `PolymerBlockUtils.registerOverlay` / `PolymerEntityUtils.registerOverlay` / `PolymerItemUtils.registerOverlay` / `BlockWithElementHolder.registerOverlay` calls because I couldn't verify the 0.16 jar contents from this sandbox. Your toms_mobs uses `PolymerEntityUtils.registerType(type)` (single-arg), suggesting at least the entity API may have shifted. **If a `registerOverlay` method was renamed/removed, the build will fail with a clear "cannot resolve method" error** — grep the polymer source jar for the new name and update the four call sites.

2. **Trophy mapping.** `Blocks.PLAYER_HEAD` is a guess — Trophy is a custom HorizontalDirectional block and PLAYER_HEAD has rotation 0–15, not horizontal facing, so `withPropertiesOf` will produce a default-facing state. Cosmetic issue, easy to swap later.

3. **`PolymerBlockUtils.registerBlockEntity` overload.** I'm using the two-arg form `(type, mapper)`. The Polymer docs currently show only the single-arg `registerBlockEntity(BlockEntityType...)` form, which strips the BE from clients entirely (would break chest lid animations). If the two-arg overload was removed, you'll need a different approach — likely making the polymer overlay return a vanilla CHEST so the BE comes along for free.

4. **I have not run `./gradlew build`.** This sandbox has no internet to Maven and no Java 25 toolchain. The code is correct against the Lootr 26.1 source as I read it, but the first build will likely surface 1–3 fixable issues from the polymer API uncertainty above.

## Suggested first-build workflow

```bash
./gradlew build --stacktrace
```

If polymer methods don't resolve, open the polymer-core / polymer-virtual-entity source jars in your IDE and search for `registerOverlay` / `registerBlockEntity` to find the current names. Each broken call site is independent and 1–3 lines.
