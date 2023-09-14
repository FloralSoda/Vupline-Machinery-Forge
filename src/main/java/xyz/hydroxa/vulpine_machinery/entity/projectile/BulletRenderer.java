package xyz.hydroxa.vulpine_machinery.entity.projectile;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import xyz.hydroxa.vulpine_machinery.VulpineMachineryMod;
import xyz.hydroxa.vulpine_machinery.item.custom.gunpart.BulletType;

public class BulletRenderer extends EntityRenderer<BulletProjectile> {
    public BulletType BulletType;
    public BulletRenderer(EntityRendererProvider.Context pContext, BulletType bulletType) {
        super(pContext);
        this.BulletType = bulletType;
    }

    public static BulletRenderer new_heavy(EntityRendererProvider.Context pContext) {
        return new BulletRenderer(pContext, xyz.hydroxa.vulpine_machinery.item.custom.gunpart.BulletType.Heavy);
    }
    public static BulletRenderer new_hand_cannon(EntityRendererProvider.Context pContext) {
        return new BulletRenderer(pContext, xyz.hydroxa.vulpine_machinery.item.custom.gunpart.BulletType.HandCannon);
    }
    public static BulletRenderer new_pistol(EntityRendererProvider.Context pContext) {
        return new BulletRenderer(pContext, xyz.hydroxa.vulpine_machinery.item.custom.gunpart.BulletType.Pistol);
    }
    public static BulletRenderer new_muffled(EntityRendererProvider.Context pContext) {
        return new BulletRenderer(pContext, xyz.hydroxa.vulpine_machinery.item.custom.gunpart.BulletType.Muffled);
    }
    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull BulletProjectile pEntity) {
        return switch (BulletType) {
            case Muffled, Pistol -> new ResourceLocation(VulpineMachineryMod.MOD_ID, "textures/entity/bullet.png");
            case Heavy, HandCannon ->
                    new ResourceLocation(VulpineMachineryMod.MOD_ID, "textures/entity/bullet_heavy.png");
        };
    }
}
