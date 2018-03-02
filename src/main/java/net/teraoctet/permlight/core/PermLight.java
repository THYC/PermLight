package net.teraoctet.permlight.core;

import com.google.inject.Inject;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.teraoctet.permlight.api.PermGroup;
import net.teraoctet.permlight.api.PermManager;
import net.teraoctet.permlight.api.PermUser;
import net.teraoctet.permlight.core.command.CommandManager;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.event.entity.living.humanoid.player.RespawnPlayerEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.serializer.TextSerializers;

@Plugin(
    id = "permlight", 
    name = "PermLight", 
    version = "0.1.0",
    authors = {
        "thyc82"
    },
    description = "permission plugin")

public class PermLight {
    
    @Inject private Logger logger;
    public Logger getLogger(){return logger;}  
    
    public static Game game;
    @Inject public static PluginContainer container;
    public static PermLight plugin;
    public static final Map<String, PermGroup> PERMGROUPS = new HashMap<>(); 
    public static final Map<String, PermUser> PERMUSERS = new HashMap<>();    
    public static final PermManager PM = new PermManager();  
    
    /**
     *
     * @param event
     */
    @Listener
    public void onServerInit(GameInitializationEvent event){
        MessageChannel.TO_CONSOLE.send(format("&e[PERMLIGHT] &b... Init..."));
        PermLight.plugin = this;
        getGame().getCommandManager().register(this, new CommandManager().CommandPermLight, "permlight", "perml", "lp", "pl", "pex");                

        File folder = new File("config/permlight");
        if(!folder.exists()) folder.mkdir();
        PM.setup();
        PermManager.init_group();
        PermManager.init_user();
    }
	
    /**
     *
     * @param event
     */
    @Listener
    public void onReload(GameReloadEvent event){
        PM.reload();
    }
    
    /**
     * 
     * @param event
     * @param player 
     */
    @Listener(order = Order.FIRST, beforeModifications = true)
    public void onRespawnPlayer(RespawnPlayerEvent event, @First Player player) {      
        PM.loadPermissions(player);
    }
    
    /**
     *
     * @param event
     * @param player
     */
    @Listener(order = Order.FIRST)
    public void onClientConnectionJoin(ClientConnectionEvent.Join event, @First Player player) {
        PM.loadPermissions(player);
    }
    
    @Listener
    public void onTeleport(MoveEntityEvent.Teleport event, @First Player player) {
        if (!event.getFromTransform().getExtent().getName().equals(event.getToTransform().getExtent().getName())){
            PM.loadPermissions(player);
        }
    }
    
    /**
     * 
     * @param list
     * @return 
     */
    public static Text format(List<String> list){
        String msg = "";
        for(String s : list) {
            if(s.equals(list.get(list.size()-1))){
                msg = msg + s; 
            } else {
                msg = msg + s + "\n"; 
            }       
        }
        return Text.builder().append(TextSerializers.formattingCode('&').deserialize(msg)).toText();
    }
    
    /**
     * 
     * @param msg
     * @return 
     */
    public static Text format(String msg){
        return Text.builder().append(TextSerializers.formattingCode('&').deserialize(msg)).toText();
    }
}
