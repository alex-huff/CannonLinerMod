package event;

import com.github.lunatrius.schematica.api.ISchematic;
import dev.phonis.cannonliner.schemutils.SchemUtils;
import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.tuple.Pair;

public class RightClickEvent {

    public static boolean safePlace = false;

    @SubscribeEvent
    public void onRightClick(PlayerInteractEvent playerInteractEvent) {
        if (safePlace && playerInteractEvent.action.equals(PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK)) {
            Pair<ISchematic, BlockPos> schematica = SchemUtils.getLoadedSchem();

            if (schematica == null) return;

            ISchematic schem = schematica.getLeft();
            BlockPos position = schematica.getRight();
            Block heldBlock = Block.getBlockFromItem(playerInteractEvent.entityPlayer.getHeldItem().getItem());
            Block schemBlock = schem.getBlockState(playerInteractEvent.pos.subtract(position).add(playerInteractEvent.face.getDirectionVec())).getBlock();

            if (schemBlock != null && !schemBlock.equals(heldBlock)) {
                playerInteractEvent.setCanceled(true);
            }
        }
    }

}
