package net.teraoctet.permlight.core;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.event.entity.living.humanoid.player.RespawnPlayerEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.network.ClientConnectionEvent;

/**
 *
 * @author thyc
 */
public class PermListener {
    
    @Listener(order = Order.FIRST)
    public void onJoin(ClientConnectionEvent.Join event, @First Player player) {
        
    }
    
    @Listener(order = Order.FIRST, beforeModifications = true)
    public void onRespawnPlayer(RespawnPlayerEvent event, @First Player player) {      
        
    }
	
    @Listener
    public void onDisconned(ClientConnectionEvent.Disconnect event, @First Player player) {
        
    }
    
    @Listener
    public void onTeleport(MoveEntityEvent.Teleport event, @First Player player) {
        if (!event.getFromTransform().getExtent().getName().equals(event.getToTransform().getExtent().getName())){
            
        }
    } 
}
