package files;

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
public class PropertiesFileManager extends FileManager{
    protected HashMap<String,String> vars = new HashMap<String,String>();
    
    public PropertiesFileManager(String filename){
        super(filename);
    }
    
    public void setVar(String var, String value){
        vars.put(var, value);
    }
    public String getVar(String var){
        return vars.get(var);
    }
    
    public void save(File file){
        try{
            Util.log("[PropertiesFileManager] Saving properties...");
            file.createNewFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
            for(String var : vars.keySet()){
                bw.write(var+"="+vars.get(var)+"\n");
            }
            bw.close();
        }catch(IOException io){
            Util.log(io);
        }
    }
    
    public void load(File file){
        try{
            if(!file.exists()){
                Util.log("[PropertiesFileManager] Properties not found, creating new file...");
                save();
            }else{
                Util.log("Properties found, reading file...");
                BufferedReader br = new BufferedReader(new FileReader(file.getAbsoluteFile()));
                String[] line;
                while(br.ready()){
                    line = br.readLine().split("=");
                    if(line.length > 1){
                        vars.put(line[0], line[1]);
                        Util.log(line[0]+" = "+line[1], 2);
                    }else{
                        vars.put(line[0], "");
                        Util.log(line[0]+" not found", 2);
                    }
                }
                br.close();
            }
        }catch(IOException io){
            Util.log(io);
        }
    }
}
