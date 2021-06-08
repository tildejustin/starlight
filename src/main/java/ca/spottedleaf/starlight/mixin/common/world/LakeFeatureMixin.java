package ca.spottedleaf.starlight.mixin.common.world;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LakeFeature.class)
public abstract class LakeFeatureMixin {
    @WrapOperation(
            method = "place",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/WorldGenLevel;getBrightness(Lnet/minecraft/world/level/LightLayer;Lnet/minecraft/core/BlockPos;)I")
    )
    private int removeBrightnessCalls(WorldGenLevel instance, LightLayer lightLayer, BlockPos blockPos, Operation<Integer> original) {
        ChunkAccess chunk = instance.getChunk(blockPos.getX() >> 4, blockPos.getZ() >> 4, ChunkStatus.LIQUID_CARVERS, false);
        assert chunk != null;
        if (chunk.getStatus().isOrAfter(ChunkStatus.LIGHT)) {
            return original.call(instance, lightLayer, blockPos);
        }
        // bugged dirt lakes
        if (chunk.getStatus().isOrAfter(ChunkStatus.FEATURES)) {
            return 0;
        }
        // liquid_carvers, always grass? (matches vanilla)
        return 15;
    }
}
