package dev.phonis.cannonliner.keybinds;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class Keybinds {

    public static final KeyBinding toggleFluidRenderingKeybinding = new KeyBinding("Toggle Fluid Rendering", Keyboard.KEY_LBRACKET, "Utils");
    public static final KeyBinding toggleBlockRenderingKeybinding = new KeyBinding("Toggle Block Rendering", Keyboard.KEY_RBRACKET, "Utils");
    public static final KeyBinding toggleGlassBlockRenderingKeybinding = new KeyBinding("Toggle Glass Block Rendering", Keyboard.KEY_BACKSLASH, "Utils");

    public static void register() {
        ClientRegistry.registerKeyBinding(Keybinds.toggleFluidRenderingKeybinding);
        ClientRegistry.registerKeyBinding(Keybinds.toggleBlockRenderingKeybinding);
        ClientRegistry.registerKeyBinding(Keybinds.toggleGlassBlockRenderingKeybinding);
    }

}
