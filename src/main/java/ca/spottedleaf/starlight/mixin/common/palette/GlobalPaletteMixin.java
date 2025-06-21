package ca.spottedleaf.starlight.mixin.common.palette;

import ca.spottedleaf.starlight.common.palette.PaletteExtension;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.GlobalPalette;
import org.spongepowered.asm.mixin.Mixin;

import java.util.function.Predicate;

@Mixin(GlobalPalette.class)
public abstract class GlobalPaletteMixin implements PaletteExtension {
    @Override
    public boolean maybeHas(Predicate<BlockState> predicate) {
        return true;
    }
}
