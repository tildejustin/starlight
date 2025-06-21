package ca.spottedleaf.starlight.common.palette;

import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Predicate;

public interface LevelChunkSectionExtension {
    boolean maybeHas(Predicate<BlockState> predicate);
}
