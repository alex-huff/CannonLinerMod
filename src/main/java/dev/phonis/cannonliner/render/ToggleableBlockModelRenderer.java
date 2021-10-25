package dev.phonis.cannonliner.render;

import net.minecraft.block.BlockGlass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.pipeline.ForgeBlockModelRenderer;

public class ToggleableBlockModelRenderer extends ForgeBlockModelRenderer {

    public static boolean renderBlocks = true;
    public static boolean renderGlass = true;

    @Override
    public boolean renderModel(IBlockAccess blockAccessIn, IBakedModel modelIn, IBlockState blockStateIn, BlockPos blockPosIn, WorldRenderer worldRendererIn) {
        if (
            !ToggleableBlockModelRenderer.renderBlocks ||
            (!ToggleableBlockModelRenderer.renderGlass && blockStateIn.getBlock() instanceof BlockGlass)
        ) return false;

        return super.renderModel(blockAccessIn, modelIn, blockStateIn, blockPosIn, worldRendererIn);
    }

}
