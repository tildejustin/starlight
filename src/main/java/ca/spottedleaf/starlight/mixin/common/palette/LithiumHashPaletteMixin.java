package ca.spottedleaf.starlight.mixin.common.palette;

import ca.spottedleaf.starlight.common.palette.PaletteExtension;
import me.jellysquid.mods.lithium.common.world.chunk.palette.LithiumInt2ObjectBiMap;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import java.util.function.Predicate;

@Pseudo
@Mixin(targets = "me.jellysquid.mods.lithium.common.world.chunk.palette.LithiumHashPalette", remap = false)
public abstract class LithiumHashPaletteMixin implements PaletteExtension {
    @Shadow
    @Final
    private LithiumInt2ObjectBiMap<BlockState> map;

    @Shadow
    public abstract int getSize();

    @Override
    public boolean maybeHas(Predicate<BlockState> predicate) {
        for (int i = 0; i < this.getSize(); i++) {
            if (predicate.test(this.map.byId(i))) {
                return true;
            }
        }

        return false;
    }
}
