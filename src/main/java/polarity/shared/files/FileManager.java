package polarity.shared.files;

import java.io.File;

/**
 *
 * @author SinisteRing
 */
public abstract class FileManager {
    protected String filename;
    
    public FileManager(String filename){
        this.filename = filename;
    }
    
    public abstract void save(File file);
    public void save(){
        if(filename.contains("/")){
            String[] split = filename.split("/");
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
        File file = new File(filename);
        save(file);
    }
    public abstract void load(File file);
    public void load(){
        File file = new File(filename);
        load(file);
    }
}
