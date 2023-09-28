package xyz.hydroxa.vulpine_machinery.item.custom.gunpart.cores;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.EntityHitResult;
import xyz.hydroxa.vulpine_machinery.item.ModItems;
import xyz.hydroxa.vulpine_machinery.item.custom.gunpart.CoreProperties;

public class BeanCoreItem extends StandardCoreItem {
    public BeanCoreItem(Item.Properties pProperties, CoreProperties properties) {
        super(pProperties, properties);
    }

    @Override
    public void onEntityHit(Projectile entity, Entity owner, EntityHitResult hitResult, float baseDamage) {
        super.onEntityHit(entity, owner, hitResult, baseDamage);
        if (hitResult.getEntity() instanceof Player player) {
            player.getInventory().add(new ItemStack(ModItems.BEANS.get()));
        }
    }
}
