package com.xm666.dummycritfix.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.xm666.dummycritfix.CritInfo;
import dev.shadowsoffire.apothic_attributes.impl.AttributeEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class CritInfoMixin {
    @Mixin(Player.class)
    private static class PlayerMixin {
        @ModifyExpressionValue(method = "attack", at = @At(value = "INVOKE", target = "Lnet/neoforged/neoforge/common/CommonHooks;fireCriticalHit(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/entity/Entity;ZF)Lnet/neoforged/neoforge/event/entity/player/CriticalHitEvent;"))
        private CriticalHitEvent modifyCriticalHit(CriticalHitEvent original, @Local(name = "damagesource") DamageSource source) {
            ((CritInfo) source).dummycritfix$setCrit(original.getDamageMultiplier(), original.isCriticalHit());
            return original;
        }
    }

    @Mixin(AttributeEvents.class)
    private static class AttributeEventsMixin {
        @Inject(method = "apothCriticalStrike", at = @At(value = "INVOKE", target = "Lnet/neoforged/neoforge/network/PacketDistributor;sendToPlayersTrackingChunk(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/level/ChunkPos;Lnet/minecraft/network/protocol/common/custom/CustomPacketPayload;[Lnet/minecraft/network/protocol/common/custom/CustomPacketPayload;)V"))
        private void onCrit(LivingIncomingDamageEvent e, CallbackInfo ci, @Local(name = "damage") float damage) {
            var multiplier = damage / e.getAmount();
            var critInfo = (CritInfo) e.getSource();
            if (critInfo.dummycritfix$isCriticalHit()) {
                multiplier *= critInfo.dummycritfix$getDamageMultiplier();
            }
            critInfo.dummycritfix$setCrit(multiplier, true);
        }
    }

    @Mixin(DamageSource.class)
    private static class DamageSourceMixin implements CritInfo {
        @Unique
        private float dummycritfix$dmgMultiplier;
        @Unique
        private boolean dummycritfix$isCriticalHit;

        @Unique
        public void dummycritfix$setCrit(float dmgMultiplier, boolean isCriticalHit) {
            dummycritfix$dmgMultiplier = dmgMultiplier;
            dummycritfix$isCriticalHit = isCriticalHit;
        }

        @Unique
        public float dummycritfix$getDamageMultiplier() {
            return dummycritfix$dmgMultiplier;
        }

        @Unique
        public boolean dummycritfix$isCriticalHit() {
            return dummycritfix$isCriticalHit;
        }
    }
}
