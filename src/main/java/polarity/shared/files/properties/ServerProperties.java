package polarity.shared.files.properties;

import polarity.shared.files.PropertiesFileManager;
import polarity.shared.files.properties.vars.ServerVar;

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
