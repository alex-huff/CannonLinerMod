package phonis.cannonliner;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import phonis.cannonliner.command.FireNearCommand;
import phonis.cannonliner.command.SchemFireCommand;
import phonis.cannonliner.render.CTWorldRenderer;

@Mod(modid = CannonLinerMod.MODID, version = CannonLinerMod.VERSION)
public class CannonLinerMod {

    public static final String MODID = "cannonlinermod";
    public static final String VERSION = "1.0";
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new CTWorldRenderer());
        ClientCommandHandler.instance.registerCommand(new SchemFireCommand());
        ClientCommandHandler.instance.registerCommand(new FireNearCommand());
    }

}
