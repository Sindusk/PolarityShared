package spellforge;

import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import java.util.ArrayList;
import spellforge.nodes.CoreData;
import spellforge.nodes.GeneratorData;
import spellforge.nodes.ModifierData;
import spellforge.nodes.SpellNode;
import spellforge.nodes.SpellNodeData;
import tools.Sys;

/**
 *
 * @author SinisteRing
 */
public class SpellMatrix {
    protected static final float MATRIX_MAX_HEIGHT = Sys.height*0.9f;
    protected static final float MATRIX_MAX_WIDTH = Sys.width*0.4f;
    
    protected Node node = new Node("SpellMatrix");
    protected ArrayList<ArrayList<SpellNode>> spellNodes = new ArrayList();
    protected ArrayList<SpellNode> generators = new ArrayList();
    protected ArrayList<SpellNode> modifiers = new ArrayList();
    protected ArrayList<SpellNode> cores = new ArrayList();
    
    public SpellMatrix(Node parent, int width, int height){
        int x = 0;
        int y;
        
        float size = Math.min(MATRIX_MAX_WIDTH/width, MATRIX_MAX_HEIGHT/height);
        
        SpellNode.setNodeSize(size);
        //node.setLocalTranslation(-(width*SIZE)*0.3f, -(height*SIZE)*0.3f, 0);
        float nodeX = (Sys.width*0.5f)-(width*size*0.5f)+(size*0.5f);
        float nodeY = Math.max((Sys.height/2f)-((Sys.height-MATRIX_MAX_HEIGHT)/2f)-(size*height*0.5f), size);
        node.setLocalTranslation(nodeX, nodeY, 0);
        while(x < width){
            spellNodes.add(new ArrayList());
            y = 0;
            while(y < height){
                spellNodes.get(x).add(new SpellNode(node, this, new SpellNodeData(x, y, new Vector2f(x*SpellNode.SIZE, y*SpellNode.SIZE))));
                y++;
            }
            x++;
        }
        parent.attachChild(node);
    }
    
    public SpellNode getSpellNode(int x, int y){
        if(x >= 0 && x < spellNodes.size() && y >= 0 && y < spellNodes.get(x).size()){
            return spellNodes.get(x).get(y);
        }
        return null;
    }
    
    public void recalculate(){
        generators = new ArrayList();
        modifiers = new ArrayList();
        cores = new ArrayList();
        int x = 0;
        int y;
        SpellNode spellNode;
        SpellNodeData data;
        while(x < spellNodes.size()){
            y = 0;
            while(y < spellNodes.get(x).size()){
                spellNode = spellNodes.get(x).get(y);
                data = spellNode.getData();
                if(data instanceof GeneratorData){
                    generators.add(spellNode);
                    spellNode.recalculate();
                }else if(data instanceof ModifierData){
                    modifiers.add(spellNode);
                    spellNode.recalculate();
                }else if(data instanceof CoreData){
                    cores.add(spellNode);
                    spellNode.recalculate();
                }
                y++;
            }
            x++;
        }
    }
    
    public void update(float tpf){
        for(SpellNode spellNode : generators){
            spellNode.update(tpf);
        }
        for(SpellNode spellNode : modifiers){
            spellNode.update(tpf);
        }
        for(SpellNode spellNode : cores){
            spellNode.update(tpf);
        }
    }
    
    public SpellNode findNode(Vector2f cursorLoc){
        //Vector3f worldLoc = Util.getWorldLoc(cursorLoc, Sys.getCamera());
        //Vector2f worldCursorLoc = new Vector2f(worldLoc.x, worldLoc.y);
        for(ArrayList<SpellNode> nodes : spellNodes){
            for(SpellNode spellNode : nodes){
                if(spellNode.withinBounds(cursorLoc)){
                    return spellNode;
                }
            }
        }
        return null;
    }
}
