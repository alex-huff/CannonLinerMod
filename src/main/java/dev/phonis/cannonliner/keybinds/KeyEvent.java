package dev.phonis.cannonliner.keybinds;

import dev.phonis.cannonliner.render.ToggleableBlockFluidRenderer;
import dev.phonis.cannonliner.render.ToggleableBlockModelRenderer;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

public class KeyEvent {

    @SubscribeEvent
    public void onKey(KeyInputEvent event) {
        if (Keybinds.toggleFluidRenderingKeybinding.isPressed()) {
            ToggleableBlockFluidRenderer.renderLiquid = !ToggleableBlockFluidRenderer.renderLiquid;

            Minecraft.getMinecraft().renderGlobal.loadRenderers();
        }

        if (Keybinds.toggleBlockRenderingKeybinding.isPressed()) {
            ToggleableBlockModelRenderer.renderBlocks = !ToggleableBlockModelRenderer.renderBlocks;

            Minecraft.getMinecraft().renderGlobal.loadRenderers();
        }

        if (Keybinds.toggleGlassBlockRenderingKeybinding.isPressed()) {
            ToggleableBlockModelRenderer.renderGlass = !ToggleableBlockModelRenderer.renderGlass;

            Minecraft.getMinecraft().renderGlobal.loadRenderers();
        }
    }

}
