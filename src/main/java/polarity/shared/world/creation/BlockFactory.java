package polarity.shared.world.creation;

import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import java.util.ArrayList;
import java.util.HashMap;
import polarity.shared.world.blocks.BlockData;
import polarity.shared.world.blocks.BlockType;
import polarity.shared.world.blocks.TerrainData;
import polarity.shared.world.blocks.WallData;

/**
 *
 * @author SinisteRing
 */
public class BlockFactory {
    private static void addChance(HashMap<BlockType,Integer> chances, BlockType type, Integer val){
        if(chances.get(type) != -1){
            chances.put(type, chances.get(type)+val);
        }
    }
    private static void nullChance(HashMap<BlockType,Integer> chances, BlockType type){
        chances.put(type, -1);
    }
    public static BlockData generateBlock(Vector2f loc, ArrayList<BlockData> adjacent){
        HashMap<BlockType,Integer> chances = new HashMap();
        if(adjacent.isEmpty()){
            return new TerrainData(loc, BlockType.GRAVEL);
        }
        for(BlockType type : BlockType.values()){
            chances.put(type, 0);
        }
        for(BlockData data : adjacent){
            if(data.getType() == BlockType.CONCRETE){
                addChance(chances, BlockType.CONCRETE, 10);
                addChance(chances, BlockType.GRAVEL, 5);
                addChance(chances, BlockType.WALL, 2);
            }else if(data.getType() == BlockType.DIRT_PATH){
                addChance(chances, BlockType.DIRT_PATH, 4);
                addChance(chances, BlockType.GRAVEL, 3);
                nullChance(chances, BlockType.CONCRETE);
                nullChance(chances, BlockType.WALL);
            }else if(data.getType() == BlockType.GRAVEL){
                addChance(chances, BlockType.GRAVEL, 25);
                addChance(chances, BlockType.DIRT_PATH, 9);
                addChance(chances, BlockType.CONCRETE, 1);
                //nullChance(chances, BlockType.WALL);
            }else if(data.getType() == BlockType.WALL){
                //addChance(chances, BlockType.WALL, 1);
                addChance(chances, BlockType.CONCRETE, 10);
                //addChance(chances, BlockType.GRAVEL, 1);
                nullChance(chances, BlockType.GRAVEL);
                nullChance(chances, BlockType.DIRT_PATH);
            }
        }
        int maxRoll = 0;
        for(BlockType type : chances.keySet()){
            if(chances.get(type) == -1){
                chances.put(type, 0);
            }
            maxRoll += chances.get(type);
        }
        int roll = FastMath.nextRandomInt(1, maxRoll);
        int current = 0;
        BlockType blockType = BlockType.GRAVEL;
        for(BlockType type : chances.keySet()){
            current += chances.get(type);
            if(current >= roll){
                blockType = type;
                break;
            }
        }
        //Util.log(blockType);
        if(blockType.getClazz() == TerrainData.class){
            return new TerrainData(loc, blockType);
        }else if(blockType.getClazz() == WallData.class){
            return new WallData(loc, blockType);
        }
        return null;
    }
}
