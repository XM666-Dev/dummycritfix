package com.xm666.dummycritfix.mixin.dummycritfix;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.xm666.dummycritfix.CritInfo;
import net.mehvahdjukaar.dummmmmmy.common.CritRecord;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

public class CritInfoMixin {
    @Mixin(Player.class)
    private static class PlayerMixin {
        @ModifyExpressionValue(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/damagesource/DamageSources;playerAttack(Lnet/minecraft/world/entity/player/Player;)Lnet/minecraft/world/damagesource/DamageSource;"))
        private DamageSource modifyCriticalHit(DamageSource original, @Local(name = "hitResult") CriticalHitEvent event) {
            if (event.isVanillaCritical()) {
                var info = (CritInfo) original;
                info.dummycritfix$apply(event.getDamageModifier());
            }
            return original;
        }
    }

    @Mixin(DamageSource.class)
    private static class DamageSourceMixin implements CritInfo {
        @Unique
        private float dummycritfix$damageMultiplier = 1.0F;
        @Unique
        private boolean dummycritfix$isCriticalHit;

        @Unique
        public void dummycritfix$apply(float damageMultiplier) {
            dummycritfix$damageMultiplier *= damageMultiplier;
            dummycritfix$isCriticalHit = true;
        }

        @Unique
        public CritRecord dummycritfix$toRecord(CritRecord fallback) {
            if (!dummycritfix$isCriticalHit) return fallback;

            var source = (DamageSource) (Object) this;
            var entity = source.getEntity();
            return new CritRecord(entity, dummycritfix$damageMultiplier);
        }
    }
}
