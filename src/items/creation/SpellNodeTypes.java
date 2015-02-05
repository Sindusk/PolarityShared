package items.creation;

import spellforge.nodes.SpellNodeData;
import spellforge.nodes.cores.*;
import spellforge.nodes.effect.*;
import spellforge.nodes.generators.*;
import spellforge.nodes.modifiers.*;

/**
 *
 * @author SinisteRing
 */
public enum SpellNodeTypes {
    // Generators
    EnergyGenerator(EnergyGenData.class, 4),
    // Cores
    ProjectileCore(ProjectileCoreData.class, 4),
    // Effects
    DamageEffect(DamageEffectData.class, 3),
    // Modifier
    MultiplierModifier(MultiModData.class, 0),
    SpeedModifier(SpeedModData.class, 3);

    protected Class<? extends SpellNodeData> clazz;
    protected int dropWeight;

    public Class getTypeClass(){
        return clazz;
    }
    public int getDropWeight(){
        return dropWeight;
    }

    SpellNodeTypes(Class<? extends SpellNodeData> clazz, int dropWeight){
        this.clazz = clazz;
        this.dropWeight = dropWeight;
    }
}
