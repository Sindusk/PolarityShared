package equipment;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class Equipment {
    protected WeaponData[] weapon = new WeaponData[4];
    protected AccessoryData[] accessory = new AccessoryData[2];
    protected ArmorData helmet;
    protected ArmorData body;
    protected ArmorData boots;
    
    public Equipment(){
        int i = 0;
        while(i < 4){
            weapon[i] = new WeaponData("weapon");
            i++;
        }
        i = 0;
        while(i < 2){
            accessory[i] = new AccessoryData("accessory");
            i++;
        }
        this.helmet = new ArmorData("helmet");
        this.body = new ArmorData("body");
        this.boots = new ArmorData("boots");
    }
    
    public WeaponData getWeapon(int slot){
        return weapon[slot];
    }
    public AccessoryData getAccessory(int slot){
        return accessory[slot];
    }
    public ArmorData getHelmet(){
        return helmet;
    }
    public ArmorData getBody(){
        return body;
    }
    public ArmorData getBoots(){
        return boots;
    }
}
