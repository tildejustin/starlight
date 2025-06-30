package ca.spottedleaf.starlight.mixin.common.world;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Random;

@Mixin(LakeFeature.class)
public abstract class LakeFeatureMixin {
    @Unique
    private static final Random random = new Random();

    @WrapOperation(
            method = "place(Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/chunk/ChunkGenerator;Ljava/util/Random;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/levelgen/feature/configurations/BlockStateConfiguration;)Z",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/WorldGenLevel;getBrightness(Lnet/minecraft/world/level/LightLayer;Lnet/minecraft/core/BlockPos;)I")
    )
    private int removeBrightnessCalls(WorldGenLevel instance, LightLayer lightLayer, BlockPos blockPos, Operation<Integer> original) {
        ChunkAccess chunk = instance.getChunk(blockPos.getX() >> 4, blockPos.getZ() >> 4, ChunkStatus.LIQUID_CARVERS, false);
        assert chunk != null;
        if (chunk.getStatus().isOrAfter(ChunkStatus.LIGHT)) {
            return original.call(instance, lightLayer, blockPos);
        }
        if (chunk.getStatus().isOrAfter(ChunkStatus.FEATURES)) {
//            if (instance.getLevel().getServer().getTickCount() == 0) {
//                return !isUnderground(chunk, blockPos) ? 15 : 0;
//            }
//            return random.nextInt(3) == 0 && !isUnderground(chunk, blockPos) ? 15 : 0;
            return !isUnderground(chunk, blockPos) ? 15 : 0;
        }
        // liquid_carvers, always grass? (matches vanilla)
        return 15;
    }

    @Unique
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean isUnderground(ChunkAccess chunk, BlockPos pos) {
        return pos.getY() + 4 < chunk.getHeight(Heightmap.Types.WORLD_SURFACE_WG, pos.getX(), pos.getZ());
    }
}
