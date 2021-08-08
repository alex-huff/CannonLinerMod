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
import phonis.cannonliner.schemutils.SchemUtils;
import phonis.cannonliner.state.CTLineManager;

import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.zip.GZIPOutputStream;

public class SchemFireCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return ".schemfire";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return ".schemfire";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        try {
            CTLineManager.instance.clearByType(CTLineType.ALL);

            if (CannonLinerClient.currentCannonLinerClient != null) {
                CannonLinerClient.currentCannonLinerClient.close();
            }

            Class<?> renderSchematicClass = Class.forName("com.github.lunatrius.schematica.client.renderer.RenderSchematic");
            Field worldField = renderSchematicClass.getDeclaredField("world");

            worldField.setAccessible(true);

            Field instanceField = renderSchematicClass.getDeclaredField("INSTANCE");
            Object renderSchematic = worldField.get(instanceField.get(null));
            Field schematicField = renderSchematic.getClass().getDeclaredField("schematic");

            schematicField.setAccessible(true);

            Field positionField = renderSchematic.getClass().getDeclaredField("position");

            positionField.setAccessible(true);

            ISchematic schematic = (ISchematic) schematicField.get(renderSchematic);
            final BlockPos position = (BlockPos) positionField.get(renderSchematic);
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

            SchemUtils.schemOrigin = new BlockPos(position);
            SchemUtils.currentPosition = position;
            SchemUtils.isTiedToSchem = true;
            CannonLinerClient.currentCannonLinerClient = new CannonLinerClient(position, baos.toByteArray());
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
            sender.getCommandSenderEntity().addChatMessage(new ChatComponentText("Failed to retrieve loaded schematica"));
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

}