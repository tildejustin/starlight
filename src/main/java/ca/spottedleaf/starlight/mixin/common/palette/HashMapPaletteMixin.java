package ca.spottedleaf.starlight.mixin.common.palette;

import ca.spottedleaf.starlight.common.palette.PaletteExtension;
import net.minecraft.util.CrudeIncrementalIntIdentityHashBiMap;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.HashMapPalette;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import java.util.function.Predicate;

@Mixin(HashMapPalette.class)
public abstract class HashMapPaletteMixin implements PaletteExtension {
    @Shadow
    @Final
    private CrudeIncrementalIntIdentityHashBiMap<BlockState> values;

    @Shadow
    public abstract int getSize();

    @Override
    public boolean maybeHas(Predicate<BlockState> predicate) {
        for (int i = 0; i < this.getSize(); i++) {
            if (predicate.test(this.values.byId(i))) {
                return true;
            }
        }

        return false;
    }
}
