package ca.spottedleaf.starlight.mixin.common.chunk;

import ca.spottedleaf.starlight.common.chunk.ExtendedChunk;
import ca.spottedleaf.starlight.common.light.StarLightLightingProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.lighting.LevelLightEngine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ChunkAccess.class)
public interface ChunkAccessMixin extends ExtendedChunk {
    @Shadow
    LevelLightEngine getLightEngine();

    /**
     * @reason Need to use our own hooks for retrieving light data
     * @author Spottedleaf
     */
    @Overwrite
    default int getRawBrightness(final BlockPos pos, final int ambientDarkness, boolean hasSkyLight) {
        StarLightLightingProvider levelLightEngine = (StarLightLightingProvider) this.getLightEngine();
        if (levelLightEngine == null) return 0;
        return levelLightEngine.getLightEngine().getRawBrightness(pos, ambientDarkness, hasSkyLight);
    }
}
