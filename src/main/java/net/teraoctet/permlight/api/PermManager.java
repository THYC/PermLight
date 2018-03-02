package net.teraoctet.permlight.api;

import com.google.common.reflect.TypeToken;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import static net.teraoctet.permlight.core.PermLight.PERMGROUPS;
import static net.teraoctet.permlight.core.PermLight.PERMUSERS;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.permission.SubjectData;
import org.spongepowered.api.util.Tristate;

/**
 *
 * @author thyc
 */
public class PermManager {
    private static final TypeToken<Map<String,Boolean>> TOKEN_PERMGROUPS = new TypeToken<Map<String,Boolean>>() {};
    private static final TypeToken<PermUser> TOKEN_PERMUSERS = new TypeToken<PermUser>(){};
    private static final File FILE_GROUP = new File("config/permlight/permission.conf");
    private static final File FILE_USER = new File("config/permlight/user.conf");
    private static final ConfigurationLoader<?> MANAGER_GROUP = HoconConfigurationLoader.builder().setFile(FILE_GROUP).build();
    private static final ConfigurationLoader<?> MANAGER_USER = HoconConfigurationLoader.builder().setFile(FILE_USER).build();
    private static ConfigurationNode groupNode = MANAGER_GROUP.createEmptyNode(ConfigurationOptions.defaults());
    private static ConfigurationNode userNode = MANAGER_USER.createEmptyNode(ConfigurationOptions.defaults());
    
    /**
     * 
     */
    public void setup(){
        try {
            if (!FILE_GROUP.exists()) FILE_GROUP.createNewFile();
            if (!FILE_USER.exists()) FILE_USER.createNewFile();
        } catch (IOException e) {}
    }
    
    /**
     * Initialisation PERMGROUPS
     * @return 
     */
    public static boolean init_group(){
        try {
            groupNode = MANAGER_GROUP.load();           
            groupNode.getChildrenMap().entrySet().stream().forEach((group) -> {
                try {
                    PermGroup permGroup = new PermGroup(
                        group.getKey().toString(),
                        groupNode.getNode(group.getKey().toString(),"parent").getString("default"),
                        groupNode.getNode(group.getKey().toString(),"permission").getValue(TOKEN_PERMGROUPS),
                        groupNode.getNode(group.getKey().toString(),"worldname").getString(null),
                        groupNode.getNode(group.getKey().toString(),"prefix").getString(null),
                        groupNode.getNode(group.getKey().toString(),"suffix").getString(null));
                    PERMGROUPS.put(group.getKey().toString(),permGroup);
                } catch (ObjectMappingException ex) {
                    Logger.getLogger(PermManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            return true;
        } catch (IOException ex) {
            Logger.getLogger(PermManager.class.getName()).log(Level.SEVERE, null, ex);    
        }
        return false;
    }
    
    /**
     * Initialisation PERMUSERS
     * @return 
     */
    public static boolean init_user(){
        try {
            userNode = MANAGER_USER.load();
            userNode.getChildrenMap().entrySet().stream().forEach((user) -> {
                try {
                    PermUser permUser = userNode.getNode(user.getKey().toString()).getValue(TOKEN_PERMUSERS);
                    PERMUSERS.put(user.getKey().toString(),permUser);
                } catch (ObjectMappingException ex) {
                    Logger.getLogger(PermManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            return true;
        } catch (IOException ex) {
            Logger.getLogger(PermManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    /**
     * Relance la lecture du group transmis en paramètre
     * @param group
     * @return 
     */
    public Optional<PermGroup> reload(String group){
        try {
            groupNode = MANAGER_GROUP.load();
            if(!groupNode.getNode(group).isVirtual()){   
                PermGroup permGroup = new PermGroup(
                    group,
                    groupNode.getNode(group,"parent").getString("default"),
                    groupNode.getNode(group,"permission").getValue(TOKEN_PERMGROUPS),
                    groupNode.getNode(group,"worldname").getString(null),
                    groupNode.getNode(group,"prefix").getString(null),
                    groupNode.getNode(group,"suffix").getString(null));
                PERMGROUPS.put(group, permGroup);
                return Optional.of(permGroup);
            }
        } catch (ObjectMappingException | IOException ex) {
            Logger.getLogger(PermManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Optional.empty();
    }
    
    /**
     * Relance la lecture des données User à partir de l'UUID transmis en paramètre
     * @param uuid
     * @return 
     */
    public Optional<PermUser> reloadUser(String uuid){
        try {
            userNode = MANAGER_USER.load();
            if(!userNode.getNode(uuid).isVirtual()){
                PermUser permUser = userNode.getNode(uuid).getValue(TOKEN_PERMUSERS);
                PERMUSERS.put(uuid, permUser);
                return Optional.of(permUser);
            }
        } catch (ObjectMappingException | IOException ex) {
            Logger.getLogger(PermManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Optional.empty();
    }
    
    /**
     * Relance la procedure d'initialisation des données
     * @return 
     */
    public boolean reload(){
        return init_group() && init_user() && loadPermissions();
    }
    
    /**
     * Retourne l'objet PermGroup correspondant au paramètre transmis
     * @param group
     * @return 
     */
    public Optional<PermGroup> getGroup(String group){
        for (Entry<String,PermGroup> g : PERMGROUPS.entrySet()) {
            if(g.getKey().equalsIgnoreCase(group))return Optional.of(g.getValue());
        }
        return Optional.empty();
    }
    
    /**
     * Retourne l'objet PermGroup parent du group transmis en paramètre
     * @param permGroup
     * @return 
     */
    public Optional<PermGroup> getGroupParent(PermGroup permGroup){
        if(permGroup.getParent().isPresent()){
            return getGroup(permGroup.getParent().get());
        }
        return Optional.empty();
    }
    
    /**
     * Retourne l'objet PermUser correspondant au paramètre transmis
     * @param uuid
     * @return 
     */
    public Optional<PermUser> getUser(String uuid){
        for (Entry<String,PermUser> u : PERMUSERS.entrySet()) {
            if(u.getKey().equalsIgnoreCase(uuid))return Optional.of(u.getValue());
        }        
        return Optional.empty();
    }
    
    /**
     * Sauvegarde l'objet PermGroup transmis en paramètre 
     * @param permGroup 
     * @return  
     */
    public boolean saveGroup(PermGroup permGroup){
        try {
            groupNode = MANAGER_GROUP.load();
            if(permGroup.getParent().isPresent())groupNode.getNode(permGroup.getGroup(),"parent").setValue(permGroup.getParent().get());
            groupNode.getNode(permGroup.getGroup(),"permission").setValue(TOKEN_PERMGROUPS,permGroup.permission);
            if(permGroup.getWorldName().isPresent())groupNode.getNode(permGroup.getGroup(),"worldname").setValue(permGroup.getWorldName().get());
            if(permGroup.getPrefix().isPresent())groupNode.getNode(permGroup.getGroup(),"prefix").setValue(permGroup.getPrefix().get());
            if(permGroup.getSuffix().isPresent())groupNode.getNode(permGroup.getGroup(),"suffix").setValue(permGroup.getSuffix().get());
            MANAGER_GROUP.save(groupNode);
            PERMGROUPS.put(permGroup.getGroup(), permGroup);
            return true;            
        } catch (IOException | ObjectMappingException ex) {
            Logger.getLogger(PermManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    /**
     * Sauvergarde l'objet PermUser transmis en paramètre
     * @param permUser 
     * @return  
     */
    public boolean saveUser(PermUser permUser){
        try {
            userNode = MANAGER_USER.load();           
            userNode.getNode(permUser.getIdentifier()).setValue(TOKEN_PERMUSERS, permUser);
            MANAGER_USER.save(userNode);
            PERMUSERS.put(permUser.getIdentifier(), permUser);
            return true;
        } catch (IOException | ObjectMappingException ex) {
            Logger.getLogger(PermManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    /**
     * Retourne la liste des permissions enregistré pour le group transmis en paramètre
     * @param group
     * @return 
     */
    public Map<String,Boolean>getPermissionGroup(String group){
        Map<String,Boolean> permissions = new HashMap();
        if(getGroup(group).isPresent()){
            PermGroup permGroup = getGroup(group).get();
            if(getGroupParent(permGroup).isPresent()){
                permissions.putAll(getPermissionGroup(getGroupParent(permGroup).get().toString()));
            }
            if(!permGroup.getPermission().isEmpty()){
                permissions.putAll(permGroup.getPermission());
            }
        }
        return permissions;
    }
    
    /**
     * Retourne la liste des permissions spécifiques enregistré pour l'UUID transmis en paramètre
     * @param uuid
     * @return 
     */
    public Map<String,Boolean>getPermissionUser(String uuid){
        Map<String,Boolean> permissions = new HashMap();
        if(getUser(uuid).isPresent()){
            PermUser permUser = getUser(uuid).get();
            if(!permUser.getPermission().isEmpty()){
                permissions = permUser.getMapPermission();
            }
        }
        return permissions;
    }
    
    /**
     * Retourne la liste de toutes les permissions du joueur transmis en paramètre
     * @param uuid
     * @return 
     */
    public Map<String,Boolean>getAllPermission(String uuid){
        Map<String,Boolean> permissions = new HashMap();
        Optional<PermUser> permUser = getUser(uuid);
        if(permUser.isPresent()){
            permissions = getPermissionUser(uuid);
        }else{
            permUser = Optional.of(new PermUser(uuid,"default"));
        }
        if(getGroup(permUser.get().getGroup()).isPresent()){
            PermGroup permGroup = getGroup(permUser.get().getGroup()).get();
            permissions.putAll(getPermissionGroup(permGroup.toString()));
        }
        return permissions;
    }
    
    public Optional<String> getPrefixGroup(String group){
        if(getGroup(group).isPresent()){
            PermGroup permGroup = getGroup(group).get();
            Optional<String> prefix = permGroup.getPrefix();
            if(!prefix.isPresent()){
                if(getGroupParent(permGroup).isPresent()){
                    prefix = getPrefixGroup(getGroupParent(permGroup).get().getGroup());
                }   
            }
            return prefix;
        }
        return Optional.empty();
    }
    
    public Optional<String> getSuffixGroup(String group){
        if(getGroup(group).isPresent()){
            PermGroup permGroup = getGroup(group).get();
            Optional<String> suffix = permGroup.getSuffix();
            if(!suffix.isPresent()){
                if(getGroupParent(permGroup).isPresent()){
                    suffix = getSuffixGroup(getGroupParent(permGroup).get().getGroup());
                }   
            }
            return suffix;
        }
        return Optional.empty();
    }
    
    /**
     * 
     * @param player 
     */
    public void loadPermissions(Player player){
        String uuid = player.getIdentifier();
        PermUser permUser = new PermUser(uuid);
        if(getUser(uuid).isPresent())permUser = getUser(uuid).get();
        getAllPermission(uuid).entrySet().forEach((e) -> {
            player.getSubjectData().setPermission(SubjectData.GLOBAL_CONTEXT, e.getKey(), Tristate.fromBoolean(e.getValue()));
        });
        if(getSuffixGroup(permUser.getGroup()).isPresent())player.getSubjectData().setOption(SubjectData.GLOBAL_CONTEXT, "suffix", getSuffixGroup(permUser.getGroup()).get());          
        if(getPrefixGroup(permUser.getGroup()).isPresent())player.getSubjectData().setOption(SubjectData.GLOBAL_CONTEXT, "prefix", getPrefixGroup(permUser.getGroup()).get()); 
    }
    
    /**
     * 
     * @return 
     */
    public Boolean loadPermissions(){
        Sponge.getGame().getServer().getOnlinePlayers().forEach((player) -> {
            PermUser permUser = new PermUser(player.getIdentifier());
            String uuid = permUser.getIdentifier();
            if(getUser(uuid).isPresent())permUser = getUser(uuid).get();
            getAllPermission(uuid).entrySet().forEach((e) -> {
                player.getSubjectData().setPermission(SubjectData.GLOBAL_CONTEXT, e.getKey(), Tristate.fromBoolean(e.getValue()));
            });
            if(getSuffixGroup(permUser.getGroup()).isPresent())player.getSubjectData().setOption(SubjectData.GLOBAL_CONTEXT, "suffix", getSuffixGroup(permUser.getGroup()).get());
            if (getPrefixGroup(permUser.getGroup()).isPresent()) {
                player.getSubjectData().setOption(SubjectData.GLOBAL_CONTEXT, "prefix", getPrefixGroup(permUser.getGroup()).get());
            }
        });
        return true;
    }
}
