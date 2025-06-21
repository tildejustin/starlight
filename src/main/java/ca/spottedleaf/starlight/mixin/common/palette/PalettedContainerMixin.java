package ca.spottedleaf.starlight.mixin.common.palette;

import ca.spottedleaf.starlight.common.palette.PaletteExtension;
import ca.spottedleaf.starlight.common.palette.PalettedContainerExtension;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.Palette;
import net.minecraft.world.level.chunk.PalettedContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.function.Predicate;

@Mixin(PalettedContainer.class)
public abstract class PalettedContainerMixin<T> implements PalettedContainerExtension {
    @Shadow
    private Palette<T> palette;

    @Override
    public boolean maybeHas(Predicate<BlockState> predicate) {
        return ((PaletteExtension) this.palette).maybeHas(predicate);
    }
}
