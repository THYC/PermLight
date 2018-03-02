package net.teraoctet.permlight.api;

import java.util.HashMap;
import java.util.Map;
import static java.util.Objects.isNull;
import java.util.Optional;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;
import org.spongepowered.api.world.World;

/**
 *
 * @author thyc
 */
@ConfigSerializable
public class PermGroup {
    private String group;
    @Setting String parent;
    @Setting Map<String,Boolean> permission;
    @Setting String worldname;
    @Setting String prefix;
    @Setting String suffix;
    
    /**
     * 
     * @param group 
     */
    public PermGroup(String group){
        this.group = group;
        this.parent = "default";
        this.permission = new HashMap();
        this.worldname = null;
        this.prefix = null;
        this.suffix = null;
    }
    
    /**
     * 
     * @param worldname
     * @param group
     * @param permission 
     */
    public PermGroup(String group, String worldname, Map<String,Boolean> permission){
        this.group = group;
        this.worldname = worldname;
        this.permission = permission;
        this.parent = "default";
        this.prefix = null;
        this.suffix = null;
    }
    
    /**
     * 
     * @param group
     * @param permission 
     */
    public PermGroup(String group, Map<String,Boolean> permission){
        this.group = group;
        this.worldname = null;
        this.permission = permission;
        this.parent = "default";
        this.prefix = null;
        this.suffix = null;
    }
    
    /**
     * 
     * @param group
     * @param permission 
     * @param parent 
     * @param prefix 
     */
    public PermGroup(String group, Map<String,Boolean> permission, String parent, String prefix) {
        this.group = group;
        this.worldname = null;
        this.permission = permission;
        this.parent = parent;
        this.prefix = prefix;
        this.suffix = null;
    }
    
    /**
     * 
     * @param group
     * @param permission 
     * @param parent 
     * @param prefix 
     * @param suffix 
     */
    public PermGroup(String group, Map<String,Boolean> permission, String parent, String prefix, String suffix){
        this.group = group;
        this.worldname = null;
        this.permission = permission;
        this.parent = parent;
        this.prefix = prefix;
        this.suffix = suffix;
    }
    
    /**
     * 
     * @param worldname
     * @param group
     * @param permission 
     * @param parent 
     * @param prefix 
     * @param suffix 
     */
    public PermGroup(String group, String worldname, Map<String,Boolean> permission, String parent, String prefix, String suffix){
        this.group = group;
        this.worldname = worldname;
        this.permission = permission;
        this.parent = parent;
        this.prefix = prefix;
        this.suffix = suffix;
    }
    
    /**
     * 
     * @param groupParent 
     */
    public void setParent(String groupParent){
        this.parent = groupParent;
    }
    
    /**
     * 
     * @param worldname 
     */
    public void setWorldName(String worldname){
        this.worldname = worldname;
    }
    
    /**
     * 
     * @param world 
     */
    public void setWorldName(World world){
        this.worldname = world.getName();
    }
    
    /**
     * 
     * @param group 
     */
    public void setGroupName(String group){
        this.group = group;
    }
    
    /**
     * 
     * @param permission 
     */
    public void setPermission(Map<String,Boolean> permission){
        this.permission = permission;
    }
    
    /**
     * 
     * @param permission 
     * @param bool 
     */
    public void setPermission(String permission, Boolean bool){
        this.permission.put(permission, bool);
    }
    
    /**
     * 
     * @param permission 
     */
    public void setPermission(String permission){
        this.permission.put(permission, true);
    }
    
    /**
     * 
     * @param prefix 
     */
    public void setPrefix(String prefix){
        this.prefix = prefix;
    }
    
    /**
     * 
     * @param suffix 
     */
    public void setSuffix(String suffix){
        this.suffix = suffix;
    }
    
    /**
     * 
     * @return 
     */
    public Optional<String>getParent(){
        if(isNull(this.parent)){
            return Optional.empty();
        }else{
            return Optional.of(this.parent);
        }
    }
    
    /**
     * 
     * @return 
     */
    public String getGroup(){
        return this.group;
    }
    
    /**
     * 
     * @return 
     */
    public Optional<String>getPrefix(){
        if(isNull(this.prefix)){
            return Optional.empty();
        }else{
            return Optional.of(this.prefix);
        }
    }
    
    /**
     * 
     * @return 
     */
    public Optional<String>getSuffix(){
        if(isNull(this.suffix)){
            return Optional.empty();
        }else{
            return Optional.of(this.suffix);
        }
    }
    
    /**
     * 
     * @return 
     */
    public Map<String,Boolean>getPermission(){
        return this.permission;
    }
    
    /**
     * 
     * @return 
     */
    public Optional<String>getWorldName(){
        if(isNull(this.worldname)){
            return Optional.empty();
        }else{
            return Optional.of(this.worldname);
        }
    }
}
