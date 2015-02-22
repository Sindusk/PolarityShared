package spellforge.nodes;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class CoreVals {
    public int count = 1;
    public int m_count;
    
    public float effectMult = 1;
    public float m_effectMult;
    
    public float interval = 0.1f;
    public float m_interval;
    public float speed = 10;
    public float m_speed;
    
    public CoreVals(){
        reset();
    }
    
    public final void reset(){
        m_count = count;
        m_effectMult = effectMult;
        m_interval = interval;
        m_speed = speed;
    }
}
