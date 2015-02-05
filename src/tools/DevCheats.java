package tools;

import com.jme3.network.HostedConnection;
import netdata.updates.MatrixUpdate;
import spellforge.SpellMatrix;
import spellforge.nodes.GeneratorData;
import spellforge.nodes.PowerableData;
import spellforge.nodes.SpellNodeData;
import spellforge.nodes.conduits.EffectConduitData;
import spellforge.nodes.conduits.PowerConduitData;
import spellforge.nodes.cores.ProjectileCoreData;
import spellforge.nodes.effect.DamageEffectData;
import spellforge.nodes.generators.EnergyGenData;
import tools.Util.Vector2i;

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
        if(newData instanceof GeneratorData){
            newData.genProperties(1);
        }else if(newData instanceof PowerableData){
            newData.genProperties(1);
        }
        MatrixUpdate update = new MatrixUpdate(id, 0, newData);
        conn.send(update);
        matrix.changeData(update);
    }
    // Creates a basic damage projectile for the player, instead of starting the matrix empty.
    public static void initPlayerMatrix(HostedConnection conn, int id, SpellMatrix matrix){
        try{
            changeMatrixNode(conn, id, matrix, 2, 2, EnergyGenData.class);
            changeMatrixNode(conn, id, matrix, 2, 3, PowerConduitData.class);
            changeMatrixNode(conn, id, matrix, 2, 4, ProjectileCoreData.class);
            changeMatrixNode(conn, id, matrix, 3, 3, DamageEffectData.class);
            changeMatrixNode(conn, id, matrix, 3, 4, EffectConduitData.class);
        }catch(Exception ex){
            Util.log(ex);
        }
    }
}
