package grid;

import com.jme3.math.Vector3f;

import java.util.HashSet;
import java.util.Set;

public class GridNode {
    private int id;
    private Vector3f pos;
    private Set<GridNode> connections;

    public GridNode(int id, Vector3f pos)
    {
        this.id = id;
        this.pos = pos;
    }
    public int getId() {
        return id;
    }

    public Vector3f getPos()
    {
        return pos;
    }

    public void addConnection(GridNode node){
        if (connections == null)
            connections = new HashSet<GridNode>();
        connections.add(node);
    }

    public Set getConnections()
    {
        return connections;
    }
}
