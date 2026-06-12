package net.vg.structurablevoid.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.ShapeRenderer;
import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.block.BlockModelResolver;
import net.minecraft.client.renderer.block.model.BlockDisplayContext;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.vg.structurablevoid.block.entity.StructureVoidBlockEntity;
import net.vg.structurablevoid.config.ModConfigs;
import org.jspecify.annotations.Nullable;

public class StructureVoidBlockEntityRenderer implements BlockEntityRenderer<StructureVoidBlockEntity, StructureVoidRenderState> {
    private final BlockModelRenderState renderState = new BlockModelRenderState();
    private final BlockModelResolver blockModelResolver;

    public StructureVoidBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.blockModelResolver = context.blockModelResolver();
    }

    @Override
    public StructureVoidRenderState createRenderState() {
        return new StructureVoidRenderState();
    }

    @Override
    public void extractRenderState(StructureVoidBlockEntity blockEntity, StructureVoidRenderState state, float f, Vec3 vec3, ModelFeatureRenderer.@Nullable CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, f, vec3, crumblingOverlay);
        state.isVisible = ModConfigs.OUTLINE_VISIBLE;
        state.displayBlock = ModConfigs.DISPLAY_BLOCK;
        state.fullBlock = ModConfigs.FULL_BLOCK_RENDER;
        state.blockType = ModConfigs.BLOCK_TYPE;
        state.outlineColor = ModConfigs.OUTLINE_COLOR;
    }

    @Override
    public void submit(StructureVoidRenderState state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState cameraState) {
        if (!state.isVisible) return;

        if (state.displayBlock) {
            renderSolidBlock(poseStack, collector, state.blockType);
        } else {
            renderInvisibleBlocks(poseStack, collector, state);
        }
    }

    private void renderSolidBlock(PoseStack poseStack, SubmitNodeCollector collector, String type) {
        BlockState blockState = switch (type) {
            case "deepslate" -> Blocks.DEEPSLATE.defaultBlockState();
            case "dirt" -> Blocks.DIRT.defaultBlockState();
            case "netherrack" -> Blocks.NETHERRACK.defaultBlockState();
            case "endstone" -> Blocks.END_STONE.defaultBlockState();
            default -> Blocks.STONE.defaultBlockState();
        };

        Minecraft mc = Minecraft.getInstance();
        BlockModel model = mc.getModelManager().getBlockModelSet().get(blockState);

        this.renderState.clear();
        this.blockModelResolver.update(this.renderState, blockState, BlockDisplayContext.create());
        model.update(this.renderState, blockState, BlockDisplayContext.create(), 42L);

        this.renderState.submit(poseStack, collector, 15728880, 655360, 0);
    }

    private void renderInvisibleBlocks(PoseStack poseStack, SubmitNodeCollector collector, StructureVoidRenderState state) {
        if (collector instanceof MultiBufferSource bufferSource) {
            VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderTypes.lines());

            double min = state.fullBlock ? 0.0 : 0.45;
            double max = state.fullBlock ? 1.0 : 0.55;

            int color = switch (state.outlineColor) {
                case "void" -> 0xFF24B2C7;
                case "barrier" -> 0xFFFF0000;
                default -> 0xFFFFC0C0;
            };

            VoxelShape shape = Shapes.box(min, min, min, max, max, max);
            ShapeRenderer.renderShape(poseStack, vertexConsumer, shape, 0.0, 0.0, 0.0, color, 1.0F);
        }
    }

    @Override
    public int getViewDistance() {
        return 128;
    }
}