package tools;

import com.jme3.network.HostedConnection;
import netdata.updates.MatrixUpdate;
import spellforge.SpellMatrix;
import spellforge.nodes.CoreData;
import spellforge.nodes.EffectData;
import spellforge.nodes.GeneratorData;
import spellforge.nodes.ModifierData;
import spellforge.nodes.SpellNodeData;
import spellforge.nodes.conduits.EffectConduitData;
import spellforge.nodes.conduits.PowerConduitData;
import spellforge.nodes.cores.ProjectileCoreData;
import spellforge.nodes.effect.DamageEffectData;
import spellforge.nodes.generators.EnergyGenData;

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
