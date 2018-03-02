package net.teraoctet.permlight.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

/**
 *
 * @author thyc
 */
@ConfigSerializable
public class PermUser {
    String uuid;
    @Setting String group;
    @Setting Map<String,Boolean> permission;
    
    public PermUser(){}
    
    /**
     * 
     * @param uuid identifient UUID du joueur
     */
    public PermUser(String uuid){
        this.uuid = uuid;
        this.group = "default";
        this.permission = new HashMap();
    }
    
    /**
     * 
     * @param uuid identifient UUID du joueur
     * @param group groupe de permission du joueur
     */
    public PermUser(String uuid, String group){
        this.uuid = uuid;
        this.group = group;
        this.permission = new HashMap();
    }
    
    /**
     * 
     * @param uuid identifient UUID du joueur
     * @param group groupe de permission du joueur
     * @param permission liste des permissions du joueur
     */
    public PermUser(String uuid, String group, Map<String,Boolean> permission){
        this.uuid = uuid;
        this.group = group;
        this.permission = permission;
    }

    /**
     * Definit le group de permission du joueeur
     * @param group 
     */
    public void setGroup(String group){
        this.group = group;
    }
    
    /**
     * Definit l'ensemble des permissions admises au joueur
     * @param permission Map<String,Boolean> liste des permissions
     */
    public void setPermission(Map<String,Boolean> permission){
        this.permission.putAll(permission);
    }
    
    /**
     * Ajoute la permission transmise en parametre au joueur
     * @param permission permission a jouter
     * @param bool
     */
    public void setPermission(String permission, Boolean bool){       
        this.permission.put(permission, bool);
    }
    
    /**
     * Ajoute la permission transmise en parametre au joueur
     * @param permission permission a jouter
     */
    public void setPermission(String permission){       
        this.permission.put(permission, true);
    }
    
    /**
     * retourne l'identifient du joueur
     * @return UUID string
     */
    public String getIdentifier(){
        return this.uuid;
    }
    
    /**
     * retourne l'ensemble des permissions enregistrés pour ce joueur
     * @return 
     */
    public Map<String,Boolean>getMapPermission(){
        return this.permission;
    }
    
    /**
     * retourne une liste des permissions du joueur
     * @return 
     */
    public String getPermission(){
        String perm = "";
        for (Entry e : this.permission.entrySet()){
            perm = perm + "| " + e.getKey() + " : " + e.getValue();
        }
        return perm;
    }
    
    /**
     * retourne le groupe de permission enregistré pour ce joueur
     * @return 
     */
    public String getGroup(){
        return this.group;
    }
}
