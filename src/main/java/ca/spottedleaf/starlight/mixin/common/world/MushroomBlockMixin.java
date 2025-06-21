package ca.spottedleaf.starlight.mixin.common.world;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Random;

@Mixin(MushroomBlock.class)
public abstract class MushroomBlockMixin {
    @Unique
    private static final Random random = new Random();

    @WrapOperation(method = "canSurvive", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/LevelReader;getRawBrightness(Lnet/minecraft/core/BlockPos;I)I"))
    @SuppressWarnings("deprecation")
    private int removeBrightnessCalls(LevelReader instance, BlockPos blockPos, int ambiance, Operation<Integer> original) {
        ChunkAccess chunk = instance.getChunk(blockPos.getX() >> 4, blockPos.getZ() >> 4, ChunkStatus.LIQUID_CARVERS, false);
        assert chunk != null;
        if (chunk.getStatus().isOrAfter(ChunkStatus.LIGHT)) {
            return original.call(instance, blockPos, ambiance);
        }
        if (!instance.getDimension().isHasSkyLight()) {
            return 0;
        }
        if (chunk.getStatus().isOrAfter(ChunkStatus.FEATURES)) {
            if (((WorldGenRegion) instance).getLevel().getServer().getTickCount() == 0) {
                return 15;
            }
            return random.nextInt(5) < 2 ? 15 : 0;
        }
        // liquid_carvers, do not spawn mushrooms (matches vanilla)
        return 15;
    }
}
