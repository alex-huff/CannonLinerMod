package event;

import com.github.lunatrius.schematica.api.ISchematic;
import dev.phonis.cannonliner.schemutils.SchemUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.tuple.Pair;

public class RightClickEvent {

    public static boolean safePlace = false;
    private static boolean canceled = false;

    @SubscribeEvent
    public void onRightClick(PlayerInteractEvent playerInteractEvent) {
        if (!RightClickEvent.safePlace) return;

        if (playerInteractEvent.action.equals(PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK)) {
            Pair<ISchematic, BlockPos> schematica = SchemUtils.getLoadedSchem();

            if (schematica == null) return;

            ISchematic schem = schematica.getLeft();
            BlockPos position = schematica.getRight();
            ItemStack heldItemStack = playerInteractEvent.entityPlayer.getHeldItem();

            if (heldItemStack == null) return;

            Item heldItem = heldItemStack.getItem();
            Block schemBlock = schem.getBlockState(playerInteractEvent.pos.subtract(position).add(playerInteractEvent.face.getDirectionVec())).getBlock();

            if (heldItem instanceof ItemBucket) {
                RightClickEvent.canceled = !(schemBlock instanceof BlockLiquid);

                playerInteractEvent.setCanceled(RightClickEvent.canceled);
            } else if (heldItem instanceof ItemBlock) {
                Block heldBlock = Block.getBlockFromItem(heldItemStack.getItem());

                if (schemBlock != null && !schemBlock.equals(heldBlock)) {
                    playerInteractEvent.setCanceled(true);
                }
            }
        } else if (RightClickEvent.canceled && playerInteractEvent.action.equals(PlayerInteractEvent.Action.RIGHT_CLICK_AIR)) {
            ItemStack heldItemStack = playerInteractEvent.entityPlayer.getHeldItem();

            if (heldItemStack == null) return;

            Item heldItem = heldItemStack.getItem();

            if (heldItem instanceof ItemBucket) {
                playerInteractEvent.setCanceled(true);
            }
        }
    }

}
