package polarity.shared.files;

import com.jme3.export.binary.BinaryExporter;
import com.jme3.export.binary.BinaryImporter;
import java.io.File;
import java.io.IOException;
import polarity.shared.tools.Util;
import polarity.shared.world.Chunk;

/**
 *
 * @author SinisteRing
 */
public class ChunkFileManager extends FileManager {
    protected Chunk chunk;
    
    public ChunkFileManager(String filename, Chunk chunk){
        super(filename);
        this.chunk = chunk;
    }
    
    @Override
    public void save(File file) {
        try{
            BinaryExporter exporter = new BinaryExporter();
            exporter.save(chunk, file);
        }catch(IOException ex){
            Util.log(ex);
        }
    }

    @Override
    public void load(File file) {
        try{
            BinaryImporter importer = new BinaryImporter();
            chunk.loadChunk(importer.load(file));
        }catch(IOException ex){
            Util.log(ex);
        }
    }
    
}
