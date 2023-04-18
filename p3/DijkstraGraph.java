// --== CS400 File Header Information ==--
// Name: <your full name>
// Email: <your @wisc.edu email address>
// Group and Team: <your group name: two letters, and team color>
// Group TA: <name of your group's ta>
// Lecturer: <name of your lecturer>
// Notes to Grader: <optional extra notes>

import java.util.PriorityQueue;
import java.util.Hashtable;
import java.util.List;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * This class extends the BaseGraph data structure with additional methods for
 * computing the total cost and list of node data along the shortest path
 * connecting a provided starting to ending nodes. This class makes use of
 * Dijkstra's shortest path algorithm.
 */
public class DijkstraGraph<NodeType, EdgeType extends Number>
        extends BaseGraph<NodeType, EdgeType>
        implements GraphADT<NodeType, EdgeType> {

    /**
     * While searching for the shortest path between two nodes, a SearchNode
     * contains data about one specific path between the start node and another
     * node in the graph. The final node in this path is stored in it's node
     * field. The total cost of this path is stored in its cost field. And the
     * predecessor SearchNode within this path is referened by the predecessor
     * field (this field is null within the SearchNode containing the starting
     * node in it's node field).
     *
     * SearchNodes are Comparable and are sorted by cost so that the lowest cost
     * SearchNode has the highest priority within a java.util.PriorityQueue.
     */
    protected class SearchNode implements Comparable<SearchNode> {
        public Node node;
        public double cost;
        public SearchNode predecessor;

        public SearchNode(Node node, double cost, SearchNode predecessor) {
            this.node = node;
            this.cost = cost;
            this.predecessor = predecessor;
        }

        public int compareTo(SearchNode other) {
            if (cost > other.cost)
                return +1;
            if (cost < other.cost)
                return -1;
            return 0;
        }
    }

    /**
     * This helper method creates a network of SearchNodes while computing the
     * shortest path between the provided start and end locations. The
     * SearchNode that is returned by this method is represents the end of the
     * shortest path that is found: it's cost is the cost of that shortest path,
     * and the nodes linked together through predecessor references represent
     * all of the nodes along that shortest path (ordered from end to start).
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return SearchNode for the final end node within the shortest path
     * @throws NoSuchElementException when no path from start to end is found
     *                                or when either start or end data do not
     *                                correspond to a graph node
     */
    protected SearchNode computeShortestPath(NodeType start, NodeType end) {
        if (!nodes.containsKey(start) || !nodes.containsKey(end)) { // check if the start and end nodes are in the graph
            throw new NoSuchElementException("Start or end node not found");
        }
        PriorityQueue<SearchNode> pq = new PriorityQueue<SearchNode>();
        Hashtable<Node, SearchNode> searchNodes = new Hashtable<Node, SearchNode>();
        SearchNode startNode = new SearchNode(nodes.get(start), 0, null);
        pq.add(startNode); // add the start node to the priority queue
        searchNodes.put(startNode.node, startNode); // add the start node to the hashtable
        while (!pq.isEmpty()) {
            SearchNode curr = pq.remove(); // remove the node with the lowest cost from the priority queue
            if (curr.node.data.equals(end)) {
                return curr; // return the current node if it is the end node
            }
            for (Edge edge : curr.node.edgesLeaving) { // iterate through the edges of the current node
                SearchNode next = searchNodes.get(edge.successor); // get the next node from the hashtable
                if (next == null) {
                    next = new SearchNode(edge.successor, Double.POSITIVE_INFINITY, null);
                    searchNodes.put(next.node, next); // add the next node to the hashtable
                }
                double cost = curr.cost + edge.data.doubleValue(); // compute the cost of the next node
                if (cost < next.cost) {
                    next.cost = cost; // update the cost of the next node
                    next.predecessor = curr; // update the predecessor of the next node
                    pq.add(next); // add the next node to the priority queue
                }
            }
        }
        throw new NoSuchElementException("No path from start to end found");
    }

    /**
     * Returns the list of data values from nodes along the shortest path
     * from the node with the provided start value through the node with the
     * provided end value. This list of data values starts with the start
     * value, ends with the end value, and contains intermediary values in the
     * order they are encountered while traversing this shorteset path. This
     * method uses Dijkstra's shortest path algorithm to find this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return list of data item from node along this shortest path
     */
    public List<NodeType> shortestPathData(NodeType start, NodeType end) {
        if (!nodes.containsKey(start) || !nodes.containsKey(end)) { // check if the start and end nodes are in the graph
            throw new NoSuchElementException("Start or end node not found");
        }
        SearchNode endNode = computeShortestPath(start, end); // find the end node of the shortest path
        LinkedList<NodeType> path = new LinkedList<NodeType>();
        SearchNode curr = endNode;
        while (curr != null) {
            path.addFirst(curr.node.data); // add the data from the current node to the front of the list
            curr = curr.predecessor; // move to the predecessor node
        }
        return path;
    }

    /**
     * Returns the cost of the path (sum over edge weights) of the shortest
     * path freom the node containing the start data to the node containing the
     * end data. This method uses Dijkstra's shortest path algorithm to find
     * this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return the cost of the shortest path between these nodes
     */
    public double shortestPathCost(NodeType start, NodeType end) {
        if (!nodes.containsKey(start) || !nodes.containsKey(end)) { // check if the start and end nodes are in the graph
            throw new NoSuchElementException("Start or end node not found");
        }
        SearchNode endNode = computeShortestPath(start, end); // find the end node of the shortest path
        return endNode.cost; // return the cost of the shortest path
    }

    // TODO: implement 3+ tests in step 8.

    public static void main(String[] args) {
        DijkstraGraph<String, Double> graph = new DijkstraGraph<String, Double>();
        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("C");
        graph.insertNode("D");
        graph.insertNode("E");
        graph.insertNode("F");

        graph.insertEdge("B", "E", 1.0);
        graph.insertEdge("B", "C", 2.0);
        graph.insertEdge("C", "B", 3.0);
        graph.insertEdge("C", "F", 1.0);
        graph.insertEdge("F", "D", 1.0);
        graph.insertEdge("D", "E", 3.0);
        graph.insertEdge("E", "A", 4.0);
        graph.insertEdge("A", "D", 5.0);
        graph.insertEdge("A", "B", 6.0);
        graph.insertEdge("F", "A", 1.0);
        graph.insertEdge("A", "C", 2.0);

        // shortest path from A to G should be A -> C -> F -> G

        System.out.println(graph.shortestPathData("B", "F")); // should print [A, B, C]
    }
}
