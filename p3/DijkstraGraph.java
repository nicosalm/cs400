// --== CS400 File Header Information ==--
// Name: Nico Salm
// Email: nbsalm
// Group and Team: AC blue
// Group TA: Rachit
// Lecturer: Dahl
// Notes to Grader: NONE

import java.util.PriorityQueue;
import java.util.Hashtable;
import java.util.List;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

// junit testing
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

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
        return computeShortestPath(start, end).cost; // return the cost of the shortest path
    }

    /**
     * JUnit test for DijkstraGraph class which tests computeShortestPath
     */
    @Test
    public void testComputeShortestPath() {

        var graph = new DijkstraGraph<String, Double>();
        var nodes = new String[] { "A", "B", "D", "E", "F", "G", "H", "I", "L", "M", "X" };
        // Add the nodes to the graph
        Arrays.stream(nodes).forEach(node -> graph.insertNode(node));
        // create an array of edges to add to the graph
        var edges = new String[][] {
                { "A", "H", "8.0" },
                { "A", "B", "1.0" },
                { "A", "M", "5.0" },
                { "B", "M", "3.0" },
                { "D", "A", "7.0" },
                { "D", "G", "2.0" },
                { "F", "G", "9.0" },
                { "G", "L", "7.0" },
                { "H", "B", "6.0" },
                { "H", "I", "2.0" },
                { "I", "H", "2.0" },
                { "I", "D", "1.0" },
                { "I", "L", "5.0" },
                { "M", "E", "3.0" },
                { "M", "F", "4.0" },
                // note that Node X is not connected to any other nodes
        };
        // Add the edges to the graph
        Arrays.stream(edges).forEach(edge -> graph.insertEdge(edge[0], edge[1], Double.parseDouble(edge[2])));

        // Test the shortest path from D to I
        var path = graph.computeShortestPath("D", "I");

        assertEquals("I", path.node.data); // check that the end node is indeed I
        assertEquals(17.0, path.cost); // check that the cost is correct (17.0)

        // Test the shortest path from A to L
        path = graph.computeShortestPath("A", "L");

        assertEquals("L", path.node.data); // check that the end node is indeed L
        assertEquals(15.0, path.cost); // check that the cost is correct (15.0)

        // Test the shortest path from A to G
        path = graph.computeShortestPath("A", "G");
        assertEquals(13.0, path.cost); // check that the cost is correct (13.0)

        // Tests exception when start node or end node is not in the graph
        assertThrows(NoSuchElementException.class, () -> graph.computeShortestPath("Z", "G"));
        assertThrows(NoSuchElementException.class, () -> graph.computeShortestPath("A", "Z"));

        // Tests exception when start node or end node is null
        assertThrows(NullPointerException.class, () -> graph.computeShortestPath(null, "G"));
        assertThrows(NullPointerException.class, () -> graph.computeShortestPath("A", null));

        // Tests exception when there is no path from start node to end node
        assertThrows(NoSuchElementException.class, () -> graph.computeShortestPath("G", "X"));
    }

    /**
     * JUnit test for the DijkstraGraph class which tests shortestPathData
     */
    @Test
    public void testShortestPathData() {

        var graph = new DijkstraGraph<String, Double>();
        var nodes = new String[] { "A", "B", "D", "E", "F", "G", "H", "I", "L", "M" };
        // Add the nodes to the graph
        Arrays.stream(nodes).forEach(node -> graph.insertNode(node));
        // create an array of edges to add to the graph
        var edges = new String[][] {
                { "A", "H", "8.0" },
                { "A", "B", "1.0" },
                { "A", "M", "5.0" },
                { "B", "M", "3.0" },
                { "D", "A", "7.0" },
                { "D", "G", "2.0" },
                { "F", "G", "9.0" },
                { "G", "L", "7.0" },
                { "H", "B", "6.0" },
                { "H", "I", "2.0" },
                { "I", "H", "2.0" },
                { "I", "D", "1.0" },
                { "I", "L", "5.0" },
                { "M", "E", "3.0" },
                { "M", "F", "4.0" }
        };
        // Add the edges to the graph
        Arrays.stream(edges).forEach(edge -> graph.insertEdge(edge[0], edge[1], Double.parseDouble(edge[2])));

        // Test the shortest path from D to I
        var path = graph.shortestPathData("D", "I");
        var expected = new String[] { "D", "A", "H", "I" };
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], path.get(i)); // expect D, A, H, I
        }

        // Test the shortest path from A to L
        path = graph.shortestPathData("A", "L");
        expected = new String[] { "A", "H", "I", "L" };
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], path.get(i)); // expect A, H, I, L
        }

        // Test the shortest path from A to G
        path = graph.shortestPathData("A", "G");
        expected = new String[] { "A", "H", "I", "D", "G" };
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], path.get(i)); // expect A, H, I, D, G
        }

        // Tests exception when start node or end node is not in the graph
        assertThrows(NoSuchElementException.class, () -> graph.shortestPathData("Z", "G"));
        assertThrows(NoSuchElementException.class, () -> graph.shortestPathData("A", "Z"));

        // Tests exception when start node or end node is null
        assertThrows(NullPointerException.class, () -> graph.shortestPathData(null, "G"));
        assertThrows(NullPointerException.class, () -> graph.shortestPathData("A", null));

        // Tests exception when there is no path from start node to end node
        assertThrows(NoSuchElementException.class, () -> graph.shortestPathData("G", "X"));

    }

    /**
     * JUnit test for the DijkstraGraph class which tests shortestPathCost
     */
    @Test
    public void testShortestPathCost() {
        var graph = new DijkstraGraph<String, Double>();
        var nodes = new String[] { "A", "B", "D", "E", "F", "G", "H", "I", "L", "M" };
        // Add the nodes to the graph
        Arrays.stream(nodes).forEach(node -> graph.insertNode(node));
        // create an array of edges to add to the graph
        var edges = new String[][] {
                { "A", "H", "8.0" },
                { "A", "B", "1.0" },
                { "A", "M", "5.0" },
                { "B", "M", "3.0" },
                { "D", "A", "7.0" },
                { "D", "G", "2.0" },
                { "F", "G", "9.0" },
                { "G", "L", "7.0" },
                { "H", "B", "6.0" },
                { "H", "I", "2.0" },
                { "I", "H", "2.0" },
                { "I", "D", "1.0" },
                { "I", "L", "5.0" },
                { "M", "E", "3.0" },
                { "M", "F", "4.0" }
        };
        // Add the edges to the graph
        Arrays.stream(edges).forEach(edge -> graph.insertEdge(edge[0], edge[1], Double.parseDouble(edge[2])));

        var path = graph.shortestPathCost("D", "I");
        assertEquals(17.0, path); // expect 10.0

        // Test the shortest path from A to L
        path = graph.shortestPathCost("A", "L");
        assertEquals(15.0, path); // expect 15.0

        // Test the shortest path from A to G
        path = graph.shortestPathCost("A", "G");
        assertEquals(13.0, path);

        // Test the shortest path from A to B
        path = graph.shortestPathCost("A", "B");
        assertEquals(1.0, path); // expect 1.0

        // Tests exception when start node or end node is not in the graph
        assertThrows(NoSuchElementException.class, () -> graph.shortestPathCost("Z", "G"));
        assertThrows(NoSuchElementException.class, () -> graph.shortestPathCost("A", "Z"));

        // Tests exception when start node or end node is null
        assertThrows(NullPointerException.class, () -> graph.shortestPathCost(null, "G"));
        assertThrows(NullPointerException.class, () -> graph.shortestPathCost("A", null));

        // Tests exception when there is no path from start node to end node
        assertThrows(NoSuchElementException.class, () -> graph.shortestPathCost("G", "X"));

    }

    public static void main(String[] args) {
    }
}