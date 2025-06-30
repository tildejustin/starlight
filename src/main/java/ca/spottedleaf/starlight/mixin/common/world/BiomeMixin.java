package ca.spottedleaf.starlight.mixin.common.world;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Biome.class)
public abstract class BiomeMixin {
    @WrapOperation(
            method = {"shouldFreeze(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Z)Z", "shouldSnow"},
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/LevelReader;getBrightness(Lnet/minecraft/world/level/LightLayer;Lnet/minecraft/core/BlockPos;)I")
    )
    private int removeBrightnessCalls(LevelReader instance, LightLayer lightLayer, BlockPos blockPos, Operation<Integer> original) {
        ChunkAccess chunk = instance.getChunk(blockPos.getX() >> 4, blockPos.getZ() >> 4, ChunkStatus.LIGHT, false);
        if (chunk != null) {
            return original.call(instance, lightLayer, blockPos);
        }
        return 0;
    }
}
