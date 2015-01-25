package files.properties.vars;

/**
 *
 * @author SinisteRing
 */
public enum PlayerVar {
    PlayerName("name", ""),
    LocX("x", "0"),
    LocY("y", "0");
    
    protected String var;
    protected String value;
    PlayerVar(String var, String value){
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
