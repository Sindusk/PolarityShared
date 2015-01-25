package files;

import java.io.File;

/**
 *
 * @author SinisteRing
 */
public abstract class FileManager {
    protected String saveFilename;
    
    public FileManager(){}
    
    public abstract void save(File file);
    public void save(){
        if(saveFilename.contains("/")){
            String[] split = saveFilename.split("/");
            String dir = "";
            int i = 0;
            while(i < split.length-1){
                dir = dir.concat(split[i]);
                i++;
                if(i < split.length-1){
                    dir = dir.concat("/");
                }
            }
            File dirs = new File(dir);
            dirs.mkdirs();
        }
        File file = new File(saveFilename);
        save(file);
    }
    public abstract void load(File file);
    public void load(){
        File file = new File(saveFilename);
        load(file);
    }
}
