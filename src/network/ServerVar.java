/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

/**
 *
 * @author SinisteRing
 */
public enum ServerVar {
    ServerPlayerData("serverPlayerData", "true"),
    MaxPlayers("maxPlayers", "10"),
    Password("password", "");
    
    protected String var;
    protected String value;
    ServerVar(String var, String value){
        this.var = var;
        this.value = value;
    }
    public String getVar(){
        return var;
    }
    public String getValue(){
        return value;
    }
}
