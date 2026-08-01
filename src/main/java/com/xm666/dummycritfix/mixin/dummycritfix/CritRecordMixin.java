package com.xm666.dummycritfix.mixin.dummycritfix;

import com.xm666.dummycritfix.handler.CritInfoHandler;
import net.mehvahdjukaar.dummmmmmy.common.ModEvents;
import net.mehvahdjukaar.dummmmmmy.common.TargetDummyEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

public class CritRecordMixin {
    @Mixin(ModEvents.class)
    private static class ModEventsMixin {
        @ModifyArgs(method = "onEntityDamage", at = @At(value = "INVOKE", target = "Lnet/mehvahdjukaar/dummmmmmy/network/ClientBoundDamageNumberMessage;<init>(IFLnet/minecraft/world/damagesource/DamageSource;Lnet/mehvahdjukaar/dummmmmmy/common/CritRecord;Lnet/minecraft/world/level/Level;)V"))
        private static void modifyCritRecord(Args args) {
            args.set(3, CritInfoHandler.toRecord(args.get(2), args.get(3)));
        }
    }

    @Mixin(TargetDummyEntity.class)
    private static class TargetDummyEntityMixin {
        @ModifyArgs(method = "showDamageAndAnimationsToClients", at = @At(value = "INVOKE", target = "Lnet/mehvahdjukaar/dummmmmmy/network/ClientBoundDamageNumberMessage;<init>(IFLnet/minecraft/world/damagesource/DamageSource;Lnet/mehvahdjukaar/dummmmmmy/common/CritRecord;Lnet/minecraft/world/level/Level;)V"))
        private static void modifyCritRecord(Args args) {
            args.set(3, CritInfoHandler.toRecord(args.get(2), args.get(3)));
        }
    }
}
