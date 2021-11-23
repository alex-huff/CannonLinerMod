package dev.phonis.cannonliner.render;

import event.ClientChatEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.time.Duration;

public class ClockRender {

    private boolean isNeg = true;

    @SubscribeEvent
    public void onHudRender(RenderGameOverlayEvent event) {
        if (event.type != RenderGameOverlayEvent.ElementType.TEXT) return;

        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
        boolean wasNeg = this.isNeg;
        long timeTillCheckable = 180000L - (System.currentTimeMillis() - ClientChatEvent.lastChecked);
        this.isNeg = timeTillCheckable < 0;
        Duration duration = Duration.ofMillis(Math.abs(timeTillCheckable));
        long seconds = duration.getSeconds();
        long HH = seconds / 3600;
        long MM = (seconds % 3600) / 60;
        long SS = seconds % 60;
        String pretty = String.format(
            !this.isNeg ? "%02d:%02d:%02d" : "-%02d:%02d:%02d",
            HH,
            MM,
            SS
        );

        if (!wasNeg && this.isNeg) Toolkit.getDefaultToolkit().beep();

        fontRenderer.drawStringWithShadow(pretty, 0, 0, !this.isNeg ? 0xFFFFFF : 0xFF0000);
    }

}
