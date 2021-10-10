package dev.phonis.cannonliner.keybinds;

import dev.phonis.cannonliner.render.ToggleableBlockFluidRenderer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

public class KeyEvent {

    @SubscribeEvent
    public void onKey(KeyInputEvent event) {
        if (Keybinds.toggleFluidRenderingKeybinding.isPressed()) {
            ToggleableBlockFluidRenderer.renderLiquid = !ToggleableBlockFluidRenderer.renderLiquid;
        }
    }

}
