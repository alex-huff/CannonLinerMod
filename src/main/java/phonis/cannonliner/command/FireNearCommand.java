package phonis.cannonliner.command;

import com.github.lunatrius.schematica.api.ISchematic;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import phonis.cannonliner.networking.CTLineType;
import phonis.cannonliner.networking.CannonLinerClient;
import phonis.cannonliner.state.CTLineManager;

import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.lang.reflect.Method;
import java.util.zip.GZIPOutputStream;

public class FireNearCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return ".firenear";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return ".firenear";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] strings) {
        try {
            CTLineManager.instance.clearByType(CTLineType.ALL);

            if (CannonLinerClient.currentCannonLinerClient != null) {
                CannonLinerClient.currentCannonLinerClient.close();
            }

            BlockPos middle = sender.getPosition();
            int radius = 50;
            int width = 101;
            int length = 101;
            int height = 101;
            BlockPos startPosition = new BlockPos(middle.getX() - 50, middle.getY() - 50, middle.getZ() - 50);
            ISchematic schematic = new WorldCuboidSchematicImpl(startPosition, width, length, height, sender.getEntityWorld());
            NBTTagCompound nbtTagCompound = new NBTTagCompound();
            Class<?> schematicaAlphaClass = Class.forName("com.github.lunatrius.schematica.world.schematic.SchematicAlpha");
            Object schematicaAlpha = schematicaAlphaClass.newInstance();
            Method writeToNBTMethod = schematicaAlphaClass.getMethod("writeToNBT", NBTTagCompound.class, ISchematic.class);

            writeToNBTMethod.invoke(schematicaAlpha, nbtTagCompound, schematic);

            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(new GZIPOutputStream(baos));

            try {
                Method writeEntryMethod = NBTTagCompound.class.getDeclaredMethod("func_150298_a", String.class, NBTBase.class, DataOutput.class);

                writeEntryMethod.setAccessible(true);
                writeEntryMethod.invoke(null, "Schematic", nbtTagCompound, dataOutputStream);
            } finally {
                dataOutputStream.close();
            }

            CannonLinerClient.currentCannonLinerClient = new CannonLinerClient(startPosition, baos.toByteArray());
            final CannonLinerClient cannonLinerClient = CannonLinerClient.currentCannonLinerClient;

            new Thread() {
                @Override
                public void run() {
                    cannonLinerClient.start();
                }
            }.start();
            sender.getCommandSenderEntity().addChatMessage(new ChatComponentText("Firing schematica"));
        } catch (Throwable e) {
            e.printStackTrace();
            sender.getCommandSenderEntity().addChatMessage(new ChatComponentText("Failed to send nearby world"));
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

}
