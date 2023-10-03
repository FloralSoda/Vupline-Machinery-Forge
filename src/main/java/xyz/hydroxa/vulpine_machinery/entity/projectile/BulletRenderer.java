package xyz.hydroxa.vulpine_machinery.entity.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;
import xyz.hydroxa.vulpine_machinery.VulpineMachineryMod;
import xyz.hydroxa.vulpine_machinery.item.ModItems;
import xyz.hydroxa.vulpine_machinery.item.custom.gunpart.BulletType;

public class BulletRenderer extends EntityRenderer<BulletProjectile> {
    public BulletType BulletType;
    private final ItemRenderer itemRenderer;
    public BulletRenderer(EntityRendererProvider.Context pContext, BulletType bulletType) {
        super(pContext);
        this.BulletType = bulletType;
        itemRenderer = pContext.getItemRenderer();
    }

    public void render(BulletProjectile pEntity, float pEntityYaw, float pPartialTicks, @NotNull PoseStack pMatrixStack, @NotNull MultiBufferSource pBuffer, int pPackedLight) {
        if (pEntity.tickCount >= 2 || !(entityRenderDispatcher.camera.getEntity().distanceToSqr(pEntity) < 12.25D)) {
            pMatrixStack.pushPose();
            pMatrixStack.mulPose(entityRenderDispatcher.cameraOrientation());
            pMatrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
            Item toRender = switch (BulletType) {
                case Unset, Pistol, Muffled -> ModItems.BULLET_PROJECTILE_SMALL.get();
                case Heavy, HandCannon -> ModItems.BULLET_PROJECTILE_LARGE.get();
            };

            itemRenderer.renderStatic(toRender.getDefaultInstance(), ItemTransforms.TransformType.GROUND, pPackedLight, OverlayTexture.NO_OVERLAY, pMatrixStack, pBuffer, pEntity.getId());
            pMatrixStack.popPose();
            super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
        }
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
    public @NotNull ResourceLocation getTextureLocation(@NotNull BulletProjectile _1) {
        return switch (BulletType) {
            case Unset, Muffled, Pistol -> new ResourceLocation(VulpineMachineryMod.MOD_ID, "textures/entity/bullet.png");
            case Heavy, HandCannon ->
                    new ResourceLocation(VulpineMachineryMod.MOD_ID, "textures/entity/bullet_heavy.png");
        };
    }
}
