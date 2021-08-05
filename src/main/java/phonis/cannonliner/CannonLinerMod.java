package phonis.cannonliner;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import phonis.cannonliner.listeners.ConnectionLifeCycle;
import phonis.cannonliner.networking.CTChannel;
import phonis.cannonliner.render.CTWorldRenderer;

@Mod(modid = CannonLinerMod.MODID, version = CannonLinerMod.VERSION)
public class CannonLinerMod {

    public static final String MODID = "cannonlinermod";
    public static final String VERSION = "1.0";
    public static final int protocolVersion = 1;
    public static final String channelName = "cannonliner:main";
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
        CTChannel.initialize();
        MinecraftForge.EVENT_BUS.register(new CTWorldRenderer());
        MinecraftForge.EVENT_BUS.register(new ConnectionLifeCycle());
    }

}
