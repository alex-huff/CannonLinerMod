package dev.phonis.cannonliner;

import dev.phonis.cannonliner.command.ClearTraceCommand;
import dev.phonis.cannonliner.command.FireNearCommand;
import dev.phonis.cannonliner.command.SchemFireCommand;
import dev.phonis.cannonliner.keybinds.KeyEvent;
import dev.phonis.cannonliner.keybinds.Keybinds;
import dev.phonis.cannonliner.render.ToggleableBlockFluidRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import dev.phonis.cannonliner.render.CTWorldRenderer;

import java.lang.reflect.Field;

@Mod(modid = CannonLinerMod.MODID, version = CannonLinerMod.VERSION)
public class CannonLinerMod {

    public static final String MODID = "cannonlinermod";
    public static final String VERSION = "1.0";
    
    @EventHandler
    public void init(FMLInitializationEvent event) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Keybinds.register();
        MinecraftForge.EVENT_BUS.register(new CTWorldRenderer());
        MinecraftForge.EVENT_BUS.register(new KeyEvent());
        ClientCommandHandler.instance.registerCommand(new SchemFireCommand());
        ClientCommandHandler.instance.registerCommand(new FireNearCommand());
        ClientCommandHandler.instance.registerCommand(new ClearTraceCommand());

        Class<?> blockRendererClass = Class.forName("net.minecraft.client.renderer.BlockRendererDispatcher");
        BlockRendererDispatcher blockRenderer = Minecraft.getMinecraft().getBlockRendererDispatcher();
        Field fluidRendererField = blockRendererClass.getDeclaredField("field_175025_e");

        fluidRendererField.setAccessible(true);
        fluidRendererField.set(blockRenderer, new ToggleableBlockFluidRenderer());
    }

}
