package polarity.shared.entity.floatingtext;

import com.jme3.math.ColorRGBA;
import polarity.shared.entity.LivingEntity;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author SinisteRing
 */
public class FloatingTextManager {
    private final static float FTEXT_INTERVAL = 0.2f;
    
    protected HashMap<String,Float> textQueue = new HashMap();
    protected HashMap<String,ColorRGBA> colors = new HashMap();
    protected ArrayList<FloatingText> texts = new ArrayList();
    protected float timer = 0;
    
    public FloatingTextManager(){}
    
    public void queue(String tag, ColorRGBA color, float value){
        if(textQueue.containsKey(tag)){
            textQueue.put(tag, textQueue.get(tag)+value);
        }else{
            textQueue.put(tag, value);
            colors.put(tag, color);
        }
    }
    
    private void display(LivingEntity entity){
        for(String tag : textQueue.keySet()){
            texts.add(new FloatingText(entity.getNode(), colors.get(tag), textQueue.get(tag), 0));
        }
    }
    
    public void update(LivingEntity entity, float tpf){
        timer += tpf;
        if(timer > FTEXT_INTERVAL){
            display(entity);
            textQueue.clear();
            timer -= FTEXT_INTERVAL;
        }
        int i = 0;
        while(i < texts.size()){
            if(texts.get(i).update(tpf)){
                texts.get(i).destroy();
                texts.remove(i);
            }else{
                i++;
            }
        }
    }
}
