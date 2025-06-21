package ca.spottedleaf.starlight.mixin.common.palette;

import ca.spottedleaf.starlight.common.palette.LevelChunkSectionExtension;
import ca.spottedleaf.starlight.common.palette.PalettedContainerExtension;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.PalettedContainer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import java.util.function.Predicate;

@Mixin(LevelChunkSection.class)
public abstract class LevelChunkSectionMixin implements LevelChunkSectionExtension {
    @Shadow
    @Final
    public PalettedContainer<BlockState> states;

    @Override
    public boolean maybeHas(Predicate<BlockState> predicate) {
        return ((PalettedContainerExtension) this.states).maybeHas(predicate);
    }
}
