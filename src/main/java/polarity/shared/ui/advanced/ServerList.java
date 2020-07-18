/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package polarity.shared.ui.advanced;

import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import java.util.ArrayList;
import polarity.shared.network.ServerStatus;
import polarity.shared.ui.Panel;

/**
 *
 * @author SinisteRing
 */
public class ServerList extends Panel {
    protected ArrayList<ServerEntry> servers = new ArrayList();
    protected Node[] entries = new Node[4];
    
    public ServerList(Node parent, Vector2f loc, float x, float y, float z){
        super(parent, loc, x, y, z);
    }
    
    public void addEntry(ServerStatus status){
        ServerEntry entry = new ServerEntry(entries[0], new Vector2f(0, 0), 5, 5, 5);
    }
}
