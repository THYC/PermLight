package net.teraoctet.permlight.core.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import net.teraoctet.permlight.api.PermGroup;
import net.teraoctet.permlight.api.PermUser;
import static net.teraoctet.permlight.core.PermLight.format;
import static net.teraoctet.permlight.core.PermLight.PM;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.service.pagination.PaginationService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.World;

/**
 *
 * @author thyc
 */
public class CommandCallBack {
    
    private final Map<String,Boolean> perm = new HashMap();
        
    /**
     * Enregistre le groupe de permission transmis en paramètre pour le joueur transmis en paramètre
     * @param ctx
     * @return 
     */
    public Consumer<CommandSource> callCmdUserGroup(CommandContext ctx) {
	return (CommandSource src) -> {
            User user = ctx.<User> getOne("user").get();
            String group = ctx.<String> getOne("value").get();
            PermUser permUser = new PermUser(user.getIdentifier());
            if(PM.getUser(user.getIdentifier()).isPresent()){
                permUser = PM.getUser(user.getIdentifier()).get();
            }
            permUser.setGroup(group);
            if(PM.saveUser(permUser))src.sendMessage(format("&e" + user.getName() + ": &bchangement group = &e" + group));       
        };
    }
    
    /**
     * Ajoute la permission transmise en paramètre au joueur transmis en paramètre
     * @param ctx
     * @return 
     */
    public Consumer<CommandSource> callCmdUserPermission(CommandContext ctx) {
	return (CommandSource src) -> {
            User user = ctx.<User> getOne("user").get();
            String permission = ctx.<String> getOne("value").get();
            
            Boolean bool = true;
            if(ctx.<Boolean> getOne("bool").isPresent())bool = ctx.<Boolean> getOne("bool").get();
                      
            PermUser permUser = new PermUser(user.getIdentifier());
            if(PM.getUser(user.getIdentifier()).isPresent()){
                permUser = PM.getUser(user.getIdentifier()).get();
            }
            permUser.setPermission(permission, bool);
            if(PM.saveUser(permUser))src.sendMessage(format("&e" + user.getName() + ": &bajout permission = &e" + permission + " " + bool));
        };
    }
    
    /**
     * Affiche l'ensemble des paramètres enregistrés pour le joueur transmis en paramètre
     * @param ctx
     * @return 
     */
    public Consumer<CommandSource> callCmdUserInfo(CommandContext ctx) {
	return (CommandSource src) -> {
            User user = ctx.<User> getOne("user").get();
            PermUser permUser = new PermUser(user.getIdentifier());
            if(PM.getUser(user.getIdentifier()).isPresent()){
                permUser = PM.getUser(user.getIdentifier()).get();
            }   
            PermGroup permGroup = new PermGroup("default");
            if(PM.getGroup(permUser.getGroup()).isPresent()){
                permGroup = PM.getGroup(permUser.getGroup()).get();
            }            
            
            PaginationList.Builder pages = getGame().getServiceManager().provide(PaginationService.class).get().builder();
            pages.title(format("&bPermLight - Info joueur &e" + user.getName()));
            
            List<Text> list = new ArrayList<>();
            list.add(format("&eGroup :  &b" + permUser.getGroup()));      
            list.add(format("&ePermission : "));
            for (Map.Entry e : permUser.getMapPermission().entrySet()){
                list.add(format("&7- " + e.getKey() + " : " + e.getValue()));
            }
            for (Map.Entry e : permGroup.getPermission().entrySet()){
                list.add(format("&7- " + e.getKey() + " : " + e.getValue()));
            }
            if(PM.getPrefixGroup(permGroup.getGroup()).isPresent()){
                list.add(format("&ePrefix :  &b" + PM.getPrefixGroup(permGroup.getGroup()).get()));      
            }
            if(PM.getSuffixGroup(permGroup.getGroup()).isPresent()){
                list.add(format("&eSuffix :  &b" + PM.getSuffixGroup(permGroup.getGroup()).get()));      
            }
       
            pages.contents(list);                
            pages.sendTo(src);         
        };
    }
    
    /**
     * enregistre la permission transmise en paramètre pour le groupe transmis en paramètre
     * @param ctx
     * @return 
     */
    public Consumer<CommandSource> callCmdGroupPermission(CommandContext ctx) {
	return (CommandSource src) -> {
            String group = ctx.<String> getOne("group").get();
            String permission = ctx.<String> getOne("value").get();
            
            Boolean bool = true;
            if(ctx.<Boolean>getOne("bool").isPresent())bool = ctx.<Boolean> getOne("bool").get();
            
            perm.clear();
            perm.put(permission, bool);
                    
            PermGroup permGroup = new PermGroup(group);
            if(PM.getGroup(group).isPresent()){
                permGroup = PM.getGroup(group).get();
            }
            permGroup.setPermission(permission,bool);
            if(PM.saveGroup(permGroup))src.sendMessage(format("&e" + group + ": &bajout permission = &e" + permission + " " + bool));  
            PM.loadPermissions();
        };
    }
    
    /**
     * Enregistre le groupe parent transmis en paramètre pour le groupe transmis en paramètre
     * @param ctx
     * @return 
     */
    public Consumer<CommandSource> callCmdGroupParent(CommandContext ctx) {
	return (CommandSource src) -> {
            String group = ctx.<String> getOne("group").get();
            String parent = ctx.<String> getOne("value").get();
                        
            PermGroup permGroup = new PermGroup(group);
            if(PM.getGroup(group).isPresent()){
                permGroup = PM.getGroup(group).get();
            }
            if(!PM.getGroup(parent).isPresent()){
                src.sendMessage(format("&4Erreur : le groupe " + parent + " n'existe pas !"));
                return;
            }
            permGroup.setParent(parent);
            if(PM.saveGroup(permGroup))src.sendMessage(format("&e" + group + ": &bparent = &e" + parent));
            PM.loadPermissions();
        };
    }
    
    /**
     * enregistre la prefix transmis en paramètre pour le groupe transmis en paramètre
     * @param ctx
     * @return 
     */
    public Consumer<CommandSource> callCmdGroupPrefix(CommandContext ctx) {
	return (CommandSource src) -> {
            String group = ctx.<String> getOne("group").get();
            String prefix = ctx.<String> getOne("value").get();
                        
            PermGroup permGroup = new PermGroup(group);
            if(PM.getGroup(group).isPresent()){
                permGroup = PM.getGroup(group).get();
            }
            permGroup.setPrefix(prefix);
            if(PM.saveGroup(permGroup))src.sendMessage(format("&e" + group + ": &bprefix = &e" + prefix));
            PM.loadPermissions();
        };
    }
    
    /**
     * enregistre le suffix transmis en paramètre pour le groupe transmis en paramètre
     * @param ctx
     * @return 
     */
    public Consumer<CommandSource> callCmdGroupSuffix(CommandContext ctx) {
	return (CommandSource src) -> {
            String group = ctx.<String> getOne("group").get();
            String suffix = ctx.<String> getOne("value").get();
                        
            PermGroup permGroup = new PermGroup(group);
            if(PM.getGroup(group).isPresent()){
                permGroup = PM.getGroup(group).get();
            }
            permGroup.setSuffix(suffix);
            if(PM.saveGroup(permGroup))src.sendMessage(format("&e" + group + ": &bsuffix = &e" + suffix));
            PM.loadPermissions();
        };
    }
    
    /**
     * 
     * @param ctx
     * @return 
     */
    public Consumer<CommandSource> callCmdWorldGroupPermission(CommandContext ctx) {
	return (CommandSource src) -> {
            World world = ctx.<World> getOne("world").get();
            String group = ctx.<String> getOne("group").get();
            String permission = ctx.<String> getOne("value").get();
            
            Boolean bool = true;
            if(ctx.<Boolean>getOne("bool").isPresent())bool = ctx.<Boolean> getOne("bool").get();
            
            perm.clear();
            perm.put(permission, bool);
                    
            PermGroup permGroup = new PermGroup(group);
            if(PM.getGroup(group).isPresent()){
                permGroup = PM.getGroup(group).get();
            }
            permGroup.setPermission(permission,bool);
            if(PM.saveGroup(permGroup))src.sendMessage(format("&e" + group + ": &bajout permission = &e" + permission + " " + bool));    
            PM.loadPermissions();
        };
    }
    
    /**
     * 
     * @param ctx
     * @return 
     */
    public Consumer<CommandSource> callCmdWorldGroupPrefix(CommandContext ctx) {
	return (CommandSource src) -> {
            src.sendMessage(Text.of("group-world-permission"));
            
        };
    }
    
    /**
     * 
     * @param ctx
     * @return 
     */
    public Consumer<CommandSource> callCmdWorldGroupSuffix(CommandContext ctx) {
	return (CommandSource src) -> {
            src.sendMessage(Text.of("group-world-permission"));
            
        };
    }
    
    /**
     * 
     * @param ctx
     * @return 
     */
    public Consumer<CommandSource> callCmdWorldInfo(CommandContext ctx) {
	return (CommandSource src) -> {
            src.sendMessage(Text.of("group-world-permission"));
            
        };
    }
    
    /**
     * 
     * @param ctx
     * @return 
     */
    public Consumer<CommandSource> callCmdWorldGroupInfo(CommandContext ctx) {
	return (CommandSource src) -> {
            src.sendMessage(Text.of("group-world-permission"));
            
        };
    }
    
    /**
     * affiche les paramètres enregistrés pour le groupe transmis en paramètre
     * @param ctx
     * @return 
     */
    public Consumer<CommandSource> callCmdGroupInfo(CommandContext ctx) {
	return (CommandSource src) -> {
            String group = ctx.<String> getOne("group").get();
            PermGroup permGroup = new PermGroup(group);
            if(PM.getGroup(group).isPresent()){
                permGroup = PM.getGroup(group).get();
            }            
            
            PaginationList.Builder pages = getGame().getServiceManager().provide(PaginationService.class).get().builder();
            pages.title(format("&bPermLight - Info &e" + group));
            
            List<Text> list = new ArrayList<>();
            if(permGroup.getParent().isPresent()){
                list.add(format("&eParent :  &b" + permGroup.getParent().get()));      
            }
            list.add(format("&ePermission : "));
            for (Map.Entry e : permGroup.getPermission().entrySet()){
                list.add(format("&7- " + e.getKey() + " : " + e.getValue()));
            }
            if(PM.getPrefixGroup(group).isPresent()){
                list.add(format("&ePrefix :  &b" + PM.getPrefixGroup(group).get()));      
            }
            if(PM.getSuffixGroup(group).isPresent()){
                list.add(format("&eSuffix :  &b" + PM.getSuffixGroup(group).get()));      
            }
            
            pages.contents(list);                
            pages.sendTo(src);
            
        };
    }
}
