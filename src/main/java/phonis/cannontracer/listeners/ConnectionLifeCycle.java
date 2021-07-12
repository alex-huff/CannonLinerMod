package phonis.cannontracer.listeners;

import com.google.common.base.Predicate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import phonis.cannontracer.CannonTracerMod;
import phonis.cannontracer.networking.CTChannel;
import phonis.cannontracer.networking.CTLineType;
import phonis.cannontracer.networking.CTRegister;
import phonis.cannontracer.state.CTLineManager;
import phonis.cannontracer.state.CTState;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionLifeCycle {

    private volatile boolean started = false;
    private final AtomicInteger ticks = new AtomicInteger(0);

    @SubscribeEvent
    public void playerJoinEvent(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        SocketAddress address = event.manager.getRemoteAddress();

        if (address instanceof InetSocketAddress) {
            InetSocketAddress iNetSocketAddress = (InetSocketAddress) address;

            if (iNetSocketAddress.getHostName().equals("pvp.cosmicproxy.net.")) return;
            if (iNetSocketAddress.getHostName().equals("pvp.cosmicproxy.net")) return;
        }

        this.started = true;

        this.ticks.set(0);
    }

    @SubscribeEvent
    public void playerQuitEvent(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        this.started = false;

        this.ticks.set(0);
        CTLineManager.instance.clearByType(CTLineType.ALL);
        CTState.currentWorld = null;
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (this.started) {
            if (this.ticks.get() == 20) { // delay it a little before handshake, quite horrible a way to do this
                // CTChannel.instance.send(new CTRegister(CannonTracerMod.protocolVersion));
                this.ticks.set(0);

                started = false;
            } else {
                this.ticks.getAndIncrement();
            }
        } else {
            CTLineManager.instance.onTick();
        }
    }

}
