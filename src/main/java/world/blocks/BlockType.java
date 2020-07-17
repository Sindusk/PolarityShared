package world.blocks;

/**
 *
 * @author SinisteRing
 */
public enum BlockType {
    CONCRETE("concrete", WallData.class),
    GRAVEL("gravel", TerrainData.class),
    DIRT_PATH("dirtBrick", TerrainData.class),
    WALL("powerCube", WallData.class);
    
    protected String icon;
    protected Class clazz;
    
    BlockType(String icon, Class clazz){
        this.icon = icon;
        this.clazz = clazz;
    }
    
    public Class getClazz(){
        return clazz;
    }
    public String getIcon(){
        return icon;
    }
}
