package dev.phonis.cannonliner.command;

import dev.phonis.cannonliner.networking.CTLineType;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import dev.phonis.cannonliner.state.CTLineManager;

public class ClearTraceCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return ".cleartrace";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return ".cleartrace";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] strings) {
        CTLineManager.instance.clearByType(CTLineType.ALL);
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

}
