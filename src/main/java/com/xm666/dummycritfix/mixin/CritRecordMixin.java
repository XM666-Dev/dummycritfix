package com.xm666.dummycritfix.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.xm666.dummycritfix.CritInfo;
import net.mehvahdjukaar.dummmmmmy.common.CritRecord;
import net.mehvahdjukaar.dummmmmmy.common.ModEvents;
import net.mehvahdjukaar.dummmmmmy.common.TargetDummyEntity;
import net.minecraft.world.damagesource.DamageSource;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

public class CritRecordMixin {
    @Mixin(ModEvents.class)
    private static class ModEventsMixin {
        @ModifyArg(method = "onEntityDamage", at = @At(value = "INVOKE", target = "Lnet/mehvahdjukaar/dummmmmmy/network/ClientBoundDamageNumberMessage;<init>(IFLnet/minecraft/world/damagesource/DamageSource;Lnet/mehvahdjukaar/dummmmmmy/common/CritRecord;Lnet/minecraft/world/level/Level;)V"), index = 3)
        private static CritRecord modifyCritRecord(@Nullable CritRecord critical, @Local(argsOnly = true) DamageSource source) {
            var critInfo = (CritInfo) source;
            if (!critInfo.dummycritfix$isCriticalHit()) return critical;

            return new CritRecord(source.getEntity(), critInfo.dummycritfix$getDamageMultiplier());
        }
    }

    @Mixin(TargetDummyEntity.class)
    private static class TargetDummyEntityMixin {
        @ModifyArg(method = "showDamageAndAnimationsToClients", at = @At(value = "INVOKE", target = "Lnet/mehvahdjukaar/dummmmmmy/network/ClientBoundDamageNumberMessage;<init>(IFLnet/minecraft/world/damagesource/DamageSource;Lnet/mehvahdjukaar/dummmmmmy/common/CritRecord;Lnet/minecraft/world/level/Level;)V"), index = 3)
        private static CritRecord modifyCritRecord(@Nullable CritRecord critical, @Local(argsOnly = true) DamageSource source) {
            var critInfo = (CritInfo) source;
            if (!critInfo.dummycritfix$isCriticalHit()) return critical;

            return new CritRecord(source.getEntity(), critInfo.dummycritfix$getDamageMultiplier());
        }
    }
}
