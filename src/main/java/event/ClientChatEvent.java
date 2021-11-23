package event;

import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientChatEvent {

    public static long lastChecked = -1L;

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        if (event.message.getUnformattedText().contains("checked WALLS")) ClientChatEvent.lastChecked = System.currentTimeMillis();
    }

}
