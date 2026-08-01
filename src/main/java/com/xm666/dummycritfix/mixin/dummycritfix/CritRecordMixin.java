package com.xm666.dummycritfix.mixin.dummycritfix;

import com.llamalad7.mixinextras.sugar.Local;
import com.xm666.dummycritfix.handler.CritInfoHandler;
import net.mehvahdjukaar.dummmmmmy.common.CritRecord;
import net.mehvahdjukaar.dummmmmmy.common.ModEvents;
import net.mehvahdjukaar.dummmmmmy.common.TargetDummyEntity;
import net.minecraft.world.damagesource.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

public class CritRecordMixin {
    @Mixin(value = ModEvents.class, remap = false)
    private static class ModEventsMixin {
        @ModifyArg(method = "onEntityDamage", at = @At(value = "INVOKE", target = "Lnet/mehvahdjukaar/dummmmmmy/network/ClientBoundDamageNumberMessage;<init>(Lnet/minecraft/world/entity/LivingEntity;FLnet/minecraft/world/damagesource/DamageSource;Lnet/mehvahdjukaar/dummmmmmy/common/CritRecord;)V"))
        private static CritRecord modifyCritRecord(CritRecord record, @Local(argsOnly = true) DamageSource source) {
            return CritInfoHandler.toRecord(source, record);
        }
    }

    @Mixin(value = TargetDummyEntity.class, remap = false)
    private static class TargetDummyEntityMixin {
        @ModifyArg(method = "showDamageAndAnimationsToClients", at = @At(value = "INVOKE", target = "Lnet/mehvahdjukaar/dummmmmmy/network/ClientBoundDamageNumberMessage;<init>(Lnet/minecraft/world/entity/LivingEntity;FLnet/minecraft/world/damagesource/DamageSource;Lnet/mehvahdjukaar/dummmmmmy/common/CritRecord;)V"))
        private CritRecord modifyCritRecord(CritRecord record, @Local(argsOnly = true) DamageSource source) {
            return CritInfoHandler.toRecord(source, record);
        }
    }
}
