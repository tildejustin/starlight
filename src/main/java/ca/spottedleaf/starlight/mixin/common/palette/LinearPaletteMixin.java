package ca.spottedleaf.starlight.mixin.common.palette;

import ca.spottedleaf.starlight.common.palette.PaletteExtension;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LinearPalette;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import java.util.function.Predicate;

@Mixin(LinearPalette.class)
public abstract class LinearPaletteMixin implements PaletteExtension {
    @Shadow
    private int size;

    @Shadow
    @Final
    private BlockState[] values;

    @Override
    public boolean maybeHas(Predicate<BlockState> predicate) {
        for (int i = 0; i < this.size; i++) {
            if (predicate.test(this.values[i])) {
                return true;
            }
        }

        return false;
    }
}
