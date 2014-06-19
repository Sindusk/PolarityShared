package network;

import com.jme3.network.serializing.Serializable;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import tools.Util;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class ServerSettings {
    private final String saveFilename = "server.properties";
    protected HashMap<String,String> vars = new HashMap<String,String>();
    
    public ServerSettings(){
        for(ServerVar v : ServerVar.values()){
            vars.put(v.getVar(), v.getValue());
        }
    }
    
    public String getVar(String var){
        return vars.get(var);
    }
    
    public void save(File file){
        try{
            if(!file.exists()){
                Util.log("Saving server properties...");
                file.createNewFile();
                BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
                for(String var : vars.keySet()){
                    bw.write(var+"="+vars.get(var)+"\n");
                }
                bw.close();
            }
        }catch(IOException io){
            Util.log(io);
        }
    }
    public void save(){
        File file = new File(saveFilename);
        save(file);
    }
    
    public void load(){
        File file = new File(saveFilename);
        try{
            if(!file.exists()){
                Util.log("Server properties not found, creating new file...");
                save();
            }else{
                Util.log("Server properties found, reading file...");
                BufferedReader br = new BufferedReader(new FileReader(file.getAbsoluteFile()));
                String[] line;
                while(br.ready()){
                    line = br.readLine().split("=");
                    if(line.length > 1){
                        vars.put(line[0], line[1]);
                        Util.log(line[0]+" = "+line[1]);
                    }else{
                        vars.put(line[0], "");
                        Util.log(line[0]+" not found");
                    }
                }
                br.close();
            }
        }catch(IOException io){
            Util.log(io);
        }
    }
}
