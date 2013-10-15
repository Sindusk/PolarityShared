package grid;

import java.util.HashMap;
import java.util.Map;

public class Grid {
    protected Map<Integer, GridNode> nodes;

    public Grid()
    {
        nodes = new HashMap<Integer, GridNode>();
    }

    public boolean addNode(GridNode node)
    {
        if (!nodes.containsKey(node.getId()))
        {
            nodes.put(node.getId(), node);
            return true;
        }
        return false;
    }
    public Map<Integer, GridNode> getNodes()
    {
        return nodes;
    }

    public void connect(GridNode nodeA, GridNode nodeB)
    {
        nodeA.addConnection(nodeB);
        nodeB.addConnection(nodeA);
    }

    public GridNode getNodeById(int id){
        return nodes.get(id);
    }
}
