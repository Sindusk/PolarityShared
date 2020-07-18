package polarity.shared.tools;

import com.jme3.network.HostedConnection;
import polarity.shared.netdata.updates.MatrixUpdate;
import polarity.shared.spellforge.SpellMatrix;
import polarity.shared.spellforge.nodes.CoreData;
import polarity.shared.spellforge.nodes.EffectData;
import polarity.shared.spellforge.nodes.GeneratorData;
import polarity.shared.spellforge.nodes.ModifierData;
import polarity.shared.spellforge.nodes.SpellNodeData;
import polarity.shared.spellforge.nodes.conduits.EffectConduitData;
import polarity.shared.spellforge.nodes.conduits.PowerConduitData;
import polarity.shared.spellforge.nodes.cores.ProjectileCoreData;
import polarity.shared.spellforge.nodes.effect.DamageEffectData;
import polarity.shared.spellforge.nodes.generators.EnergyGenData;

/**
 *
 * @author SinisteRing
 */
public class DevCheats {
    private static void changeMatrixNode(HostedConnection conn, int id, SpellMatrix matrix, int x, int y, Class<? extends SpellNodeData> newClass) throws Exception{
        SpellNodeData data = matrix.getSpellNode(x, y).getData();
        SpellNodeData newData = newClass.newInstance();
        newData.setIndex(new Vector2i(x, y));
        newData.setLocation(data.getLocation());
        if(newData instanceof GeneratorData || newData instanceof CoreData || newData instanceof EffectData || newData instanceof ModifierData){
            newData.genProperties(1);
        }
        MatrixUpdate update = new MatrixUpdate(id, 0, newData);
        conn.send(update);
        matrix.changeData(update);
    }
    // Creates a basic damage projectile for the player, instead of starting the matrix empty.
    public static void initPlayerMatrix(HostedConnection conn, int id, SpellMatrix matrix){
        try{
            changeMatrixNode(conn, id, matrix, 3, 3, EnergyGenData.class);
            changeMatrixNode(conn, id, matrix, 3, 4, PowerConduitData.class);
            changeMatrixNode(conn, id, matrix, 3, 5, ProjectileCoreData.class);
            changeMatrixNode(conn, id, matrix, 4, 4, DamageEffectData.class);
            changeMatrixNode(conn, id, matrix, 4, 5, EffectConduitData.class);
        }catch(Exception ex){
            Util.log(ex);
        }
    }
}
