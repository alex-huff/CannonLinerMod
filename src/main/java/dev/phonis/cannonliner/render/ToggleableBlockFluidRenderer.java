package dev.phonis.cannonliner.render;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockFluidRenderer;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ToggleableBlockFluidRenderer extends BlockFluidRenderer {

    public static boolean renderLiquid = false;

    @Override
    public boolean renderFluid(IBlockAccess blockAccess, IBlockState blockStateIn, BlockPos blockPosIn, WorldRenderer worldRendererIn) {
        if (!ToggleableBlockFluidRenderer.renderLiquid) return false;

        return super.renderFluid(blockAccess, blockStateIn, blockPosIn, worldRendererIn);
    }

}