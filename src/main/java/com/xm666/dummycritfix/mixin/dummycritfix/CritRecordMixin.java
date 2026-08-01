package com.xm666.dummycritfix.mixin.dummycritfix;

import com.xm666.dummycritfix.handler.CritInfoHandler;
import net.mehvahdjukaar.dummmmmmy.common.ModEvents;
import net.mehvahdjukaar.dummmmmmy.common.TargetDummyEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

public class CritRecordMixin {
    @Mixin(value = ModEvents.class, remap = false)
    private static class ModEventsMixin {
        @ModifyArgs(method = "onEntityDamage", at = @At(value = "INVOKE", target = "Lnet/mehvahdjukaar/dummmmmmy/network/ClientBoundDamageNumberMessage;<init>(Lnet/minecraft/world/entity/LivingEntity;FLnet/minecraft/world/damagesource/DamageSource;Lnet/mehvahdjukaar/dummmmmmy/common/CritRecord;)V"))
        private static void modifyCritRecord(Args args) {
            args.set(3, CritInfoHandler.toRecord(args.get(2), args.get(3)));
        }
    }

    @Mixin(value = TargetDummyEntity.class, remap = false)
    private static class TargetDummyEntityMixin {
        @ModifyArgs(method = "showDamageAndAnimationsToClients", at = @At(value = "INVOKE", target = "Lnet/mehvahdjukaar/dummmmmmy/network/ClientBoundDamageNumberMessage;<init>(Lnet/minecraft/world/entity/LivingEntity;FLnet/minecraft/world/damagesource/DamageSource;Lnet/mehvahdjukaar/dummmmmmy/common/CritRecord;)V"))
        private void modifyCritRecord(Args args) {
            args.set(3, CritInfoHandler.toRecord(args.get(2), args.get(3)));
        }
    }
}
