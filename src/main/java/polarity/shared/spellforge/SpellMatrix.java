package polarity.shared.spellforge;

import polarity.shared.character.Player;
import com.jme3.math.Vector2f;
import com.jme3.network.HostedConnection;
import com.jme3.scene.Node;
import polarity.shared.actions.Action;
import java.util.ArrayList;

import polarity.shared.events.Event;
import polarity.shared.netdata.ActionData;
import polarity.shared.netdata.updates.MatrixUpdate;
import polarity.shared.spellforge.nodes.ConduitData;
import polarity.shared.spellforge.nodes.CoreData;
import polarity.shared.spellforge.nodes.EffectData;
import polarity.shared.spellforge.nodes.GeneratorData;
import polarity.shared.spellforge.nodes.ModifierData;
import polarity.shared.spellforge.nodes.SpellNode;
import polarity.shared.spellforge.nodes.SpellNodeData;
import polarity.shared.spellforge.nodes.generators.EnergyGenData;
import polarity.shared.spellforge.nodes.generators.ManaGenData;
import polarity.shared.tools.Sys;
import polarity.shared.tools.Util;

/**
 *
 * @author SinisteRing
 */
public class SpellMatrix {
    protected static final float MATRIX_MAX_HEIGHT = Sys.height*0.9f;
    protected static final float MATRIX_MAX_WIDTH = Sys.width*0.4f;
    protected static final float SPREAD_MULT = 0.05f;
    
    protected float nodeSize;
    
    protected Player owner;
    protected Node parent;
    protected Node node = new Node("SpellMatrix");
    protected ArrayList<ArrayList<SpellNode>> spellNodes = new ArrayList();
    protected ArrayList<GeneratorData> manaGens = new ArrayList();
    protected ArrayList<GeneratorData> energyGens = new ArrayList();
    protected ArrayList<SpellNode> conduits = new ArrayList();
    protected ArrayList<SpellNode> generators = new ArrayList();
    protected ArrayList<SpellNode> effects = new ArrayList();
    protected ArrayList<SpellNode> modifiers = new ArrayList();
    protected ArrayList<SpellNode> cores = new ArrayList();
    
    public SpellMatrix(Node parent, Player owner, Vector2f loc, int width, int height){
        this.parent = parent;
        this.owner = owner;
        int x = 0;
        int y;
        
        float size = Math.min(MATRIX_MAX_WIDTH/width, MATRIX_MAX_HEIGHT/height);
        
        nodeSize = size;
        float nodeX = loc.x-(width*size*0.5f)+(size*0.5f);
        float nodeY = Math.max(loc.y-((Sys.height-MATRIX_MAX_HEIGHT)/2f)-(size*height*0.5f), size);
        node.setLocalTranslation(nodeX, nodeY, 0);
        while(x < width){
            spellNodes.add(new ArrayList());
            y = 0;
            while(y < height){
                spellNodes.get(x).add(new SpellNode(node, this, new SpellNodeData(x, y, new Vector2f(x*size, y*size)), size));
                y++;
            }
            x++;
        }
        parent.attachChild(node);
    }
    
    public void setVisible(boolean show){
        if(show){
            parent.attachChild(node);
        }else{
            node.removeFromParent();
        }
    }
    
    public float getCost(){
        float power = 0;
        for(SpellNode spellNode : cores){
            power += ((CoreData)spellNode.getData()).getTotalCost();
        }
        return Util.roundedFloat(power, 1);
    }
    public float getStoredPower(){
        float power = 0;
        for(SpellNode spellNode : generators){
            power += ((GeneratorData)spellNode.getData()).getStoredPower();
        }
        return Util.roundedFloat(power, 1);
    }
    
    public SpellNode getSpellNode(int x, int y){
        if(x >= 0 && x < spellNodes.size() && y >= 0 && y < spellNodes.get(x).size()){
            return spellNodes.get(x).get(y);
        }
        return null;
    }
    
    public void changeData(MatrixUpdate d){
        SpellNodeData data = d.getData();
        getSpellNode(data.getX(), data.getY()).changeData(data);
    }
    
    public ArrayList<Event> calculateEvents(HostedConnection conn, ActionData actionData){
        if(cores.size() > 0){
            ArrayList<Event> events = new ArrayList();
            float spread = 0;
            for(SpellNode core : cores){
                CoreData data = (CoreData) core.getData();
                if(data.getSources().size() > 0){
                    int i = 0;
                    ArrayList<Action> actions = data.calculateActions(conn, owner, actionData);
                    while(i < data.values.m_count){
                        Event event = data.getEvent(owner, actionData.getStart(), actionData.getTarget());
                        event.setExecuteTime(i*data.values.m_interval);
                        event.offset(spread);
                        if(actions != null){
                            event.addActions(actions);
                            events.add(event);
                        }
                        i++;
                    }
                    spread += actionData.getStart().distance(actionData.getTarget())*SPREAD_MULT;
                }else{
                    //Util.log(conn, "No generators attached to Core @ "+data.getX()+", "+data.getY());
                }
            }
            if(events.size() > 0){
                return events;
            }else{
                //Util.log(conn, "No events found!");
            }
        }else{
            //Util.log(conn, "No cores!");
        }
        return null;
    }
    
    public void recalculate(){
        conduits = new ArrayList();
        generators = new ArrayList();
        energyGens = new ArrayList();
        manaGens = new ArrayList();
        effects = new ArrayList();
        modifiers = new ArrayList();
        cores = new ArrayList();
        int x = 0;
        int y;
        SpellNode spellNode;
        SpellNodeData data;
        // Calls preRecalculate for every node on the matrix. This clears old data before the recalculation.
        for(ArrayList<SpellNode> sna : spellNodes){
            for(SpellNode sn : sna){
                sn.getData().preRecalculate();
            }
        }
        while(x < spellNodes.size()){
            y = 0;
            while(y < spellNodes.get(x).size()){
                spellNode = spellNodes.get(x).get(y);
                data = spellNode.getData();
                if(data instanceof ConduitData){
                    conduits.add(spellNode);
                }else if(data instanceof GeneratorData){
                    generators.add(spellNode);
                    if(data instanceof EnergyGenData){
                        energyGens.add((EnergyGenData)data);
                    }else if(data instanceof ManaGenData){
                        manaGens.add((ManaGenData)data);
                    }
                    spellNode.recalculate();
                }else if(data instanceof EffectData){
                    effects.add(spellNode);
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
        owner.getResources().powerGenerators(owner.getResources().getEnergy(), energyGens, tpf);
        owner.getResources().powerGenerators(owner.getResources().getMana(), manaGens, tpf);
        for(SpellNode spellNode : generators){
            spellNode.update(tpf);
        }
        for(SpellNode spellNode : effects){
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
