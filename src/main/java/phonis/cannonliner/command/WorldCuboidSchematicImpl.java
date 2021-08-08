package phonis.cannonliner.command;

import com.github.lunatrius.schematica.api.ISchematic;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class WorldCuboidSchematicImpl implements ISchematic {

    private final BlockPos startPos;
    private final int width;
    private final int length;
    private final int height;
    private final World world;

    public WorldCuboidSchematicImpl(BlockPos startPos, int width, int length, int height, World world) {
        this.startPos = startPos;
        this.width = width;
        this.length = length;
        this.height = height;
        this.world = world;
    }

    @Override
    public IBlockState getBlockState(BlockPos pos) {
        return world.getBlockState(pos.add(startPos));
    }

    @Override
    public boolean setBlockState(BlockPos pos, IBlockState blockState) {
        return false;
    }

    @Override
    public TileEntity getTileEntity(BlockPos pos) {
        return world.getTileEntity(pos.add(startPos));
    }

    @Override
    public List<TileEntity> getTileEntities() {
        List<TileEntity> tileEntities = new ArrayList<TileEntity>();

        for (int x = this.startPos.getX(); x < this.startPos.getX() + width; x++) {
            for (int y = this.startPos.getY(); y < this.startPos.getY() + height; y++) {
                for (int z = this.startPos.getZ(); z < this.startPos.getZ() + length; z++) {
                    TileEntity tileEntity = this.world.getTileEntity(new BlockPos(x, y, z));

                    if (tileEntity != null) {
                        tileEntities.add(tileEntity);
                    }
                }
            }
        }

        return tileEntities;
    }

    @Override
    public void setTileEntity(BlockPos pos, TileEntity tileEntity) {

    }

    @Override
    public void removeTileEntity(BlockPos pos) {

    }

    @Override
    public List<Entity> getEntities() {
        return new ArrayList<Entity>();
    }

    @Override
    public void addEntity(Entity entity) {

    }

    @Override
    public void removeEntity(Entity entity) {

    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(Blocks.grass);
    }

    @Override
    public void setIcon(ItemStack icon) {

    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getLength() {
        return this.length;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Nonnull
    @Override
    public String getAuthor() {
        return "Jesus";
    }

    @Override
    public void setAuthor(@Nonnull String author) {

    }

}
