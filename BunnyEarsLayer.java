package net.bunnyears;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class BunnyEarsLayer extends RenderLayer<PlayerRenderState, PlayerModel> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(BunnyEarsMod.MODID, "textures/entity/bunny_ears.png");
    private final ModelPart ears;

    public BunnyEarsLayer(RenderLayerParent<PlayerRenderState, PlayerModel> parent) {
        super(parent);
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("left_ear", ear(), PartPose.offsetAndRotation(-2.4f, -8f, 0f, 0f, 0f, -0.15f));
        root.addOrReplaceChild("right_ear", ear(), PartPose.offsetAndRotation(2.4f, -8f, 0f, 0f, 0f, 0.15f));
        this.ears = LayerDefinition.create(mesh, 64, 64).bakeRoot();
    }

    // Textur: (0,0)=Gruen  (32,0)=Schwarz  (0,32)=Gelbgruen
    private static CubeListBuilder ear() {
        return CubeListBuilder.create()
                .texOffs(0, 32).addBox(-1.5f, -3f, -1f, 3, 3, 2)
                .texOffs(0, 0).addBox(-2f, -9f, -1f, 4, 6, 2)
                .texOffs(0, 0).addBox(-1.5f, -11f, -1f, 3, 2, 2)
                .texOffs(0, 0).addBox(-1f, -13f, -1f, 2, 2, 2)
                .texOffs(0, 32).addBox(-3f, -7f, -0.5f, 1, 3, 1)
                .texOffs(0, 32).addBox(-2.5f, -12f, -0.5f, 1, 3, 1)
                .texOffs(0, 32).addBox(2f, -9f, -0.5f, 1, 3, 1)
                .texOffs(0, 32).addBox(-0.5f, -16f, -0.5f, 1, 3, 1)
                .texOffs(32, 0).addBox(-1f, -10f, -1.1f, 2, 8, 0.1f)
                .texOffs(32, 0).addBox(-1f, -10f, 1.0f, 2, 8, 0.1f);
    }

    @Override
    public void render(PoseStack ps, MultiBufferSource buf, int light, PlayerRenderState state,
                       float yRot, float xRot) {
        if (!BunnyEarsMod.enabled || state.isInvisible) return;
        ps.pushPose();
        this.getParentModel().head.translateAndRotate(ps); // folgt Kopf (Drehen, Ducken)
        ears.render(ps, buf.getBuffer(RenderType.entityCutoutNoCull(TEXTURE)), light, OverlayTexture.NO_OVERLAY);
        ps.popPose();
    }
}
