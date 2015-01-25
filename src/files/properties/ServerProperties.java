package files.properties;

import files.PropertiesFileManager;
import files.properties.vars.ServerVar;

/**
 *
 * @author SinisteRing
 */
public class ServerProperties extends PropertiesFileManager {
    public ServerProperties(String saveFilename){
        super(saveFilename);
        for(ServerVar v : ServerVar.values()){
            vars.put(v.getVar(), v.getValue());
        }
    }
}
