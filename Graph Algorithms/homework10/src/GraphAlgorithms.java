import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Stack;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.HashSet;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Sitong
 * @userid swu321 (i.e. gburdell3)
 * @GTID 9031636000 (i.e. 900000000)
 * @version 1.0
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * {@code start} which represents the starting vertex.
     *
     * When exploring a vertex, make sure to explore in the order that the
     * adjacency list returns the neighbors to you. Failure to do so may cause
     * you to lose points.
     *
     * You may import/use {@code java.util.Set}, {@code java.util.List},
     * {@code java.util.Queue}, and any classes that implement the
     * aforementioned interfaces, as long as it is efficient.
     *
     * The only instance of {@code java.util.Map} that you may use is the
     * adjacency list from {@code graph}. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @throws IllegalArgumentException if any input
     *  is null, or if {@code start} doesn't exist in the graph
     * @param <T> the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Any input is null.");
        }
        if (!graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("Start doesn't exist in "
                    + "the graph.");
        }

        // adjList gets all information about the graph
        // include all the vertex and the corresponding neighbor info
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();

        List<Vertex<T>> list = new ArrayList<>(); // list to be returned
        Queue<Vertex<T>> queue = new LinkedList<>(); // Queue
        list.add(start);
        queue.add(start); // add {start} node into Queue

        while (!queue.isEmpty()) {
            Vertex<T> visited = queue.remove();
            // get all (vertex, distance) pairs of the next level
            List<VertexDistance<T>> nextLevelInfo = adjList.get(visited);

            for (int i = 0; i < nextLevelInfo.size(); i++) {
                // get a single (vertex, distance) pair
                VertexDistance<T> vertexDistanceP = nextLevelInfo.get(i);
                // get the vertex from the (vertex, distance) pair
                Vertex<T> nextLevelV = vertexDistanceP.getVertex();

                if (!list.contains(nextLevelV)) {
                    list.add(nextLevelV);
                    queue.add(nextLevelV);
                }
            }
        } // end while
        return list;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * {@code start} which represents the starting vertex.
     *
     * When exploring a vertex, make sure to explore in the order that the
     * adjacency list returns the neighbors to you. Failure to do so may cause
     * you to lose points.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * most if not all points for this method.
     *
     * You may import/use {@code java.util.Set}, {@code java.util.List}, and
     * any classes that implement the aforementioned interfaces, as long as it
     * is efficient.
     *
     * The only instance of {@code java.util.Map} that you may use is the
     * adjacency list from {@code graph}. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @throws IllegalArgumentException if any input
     *  is null, or if {@code start} doesn't exist in the graph
     * @param <T> the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Any input is null.");
        }
        if (!graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("Start doesn't exist in "
                    + "the graph.");
        }

        // adjList gets all information about the graph
        // include all the vertex and the corresponding neighbor info
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();

        List<Vertex<T>> list = new ArrayList<>(); // list to be returned
        Stack<Vertex<T>> stack = new Stack<>();
        dfsHelper(adjList, start, stack, list);
        return list;
    }


    /**
     * dfsHelper() is the helper method of dfs()
     * @param <T> the generic typing of the data
     * @param adjList has all information about the graph
      *                which consists of (vertex, distance) pairs
     * @param upperLevelV the single vertex from the previous level
     * @param stack the vertex stack that might be pushed into list
     * @param list the current list been recorded
     */
    private static <T> void dfsHelper(Map<Vertex<T>,
            List<VertexDistance<T>>> adjList,
                                      Vertex<T> upperLevelV,
                                      Stack<Vertex<T>> stack,
                                      List<Vertex<T>> list) {
        // check whether in list
        if (!list.contains(upperLevelV)) {
            stack.push(upperLevelV);
            list.add(upperLevelV);
            // get all (vertex, distance) pairs of the next level
            List<VertexDistance<T>> nextLevelInfo = adjList.get(upperLevelV);

            // for each (vertex, distance) pair of the next level
            for (VertexDistance<T> vertexDistanceP : nextLevelInfo) {
                // get the vertex from the (vertex, distance) pair
                Vertex<T> nextLevelV = vertexDistanceP.getVertex();
                dfsHelper(adjList, nextLevelV, stack, list);
            }
        } // end if
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from {@code start}, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use {@code java.util.PriorityQueue},
     * {@code java.util.Map}, and {@code java.util.Set} and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check that not all vertices have been visited.
     * 2) Check that the PQ is not empty yet.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @throws IllegalArgumentException if any input is null, or if start
     *  doesn't exist in the graph.
     * @param <T> the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from {@code start} to every
     *          other node in the graph
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Any input is null.");
        }
        if (!graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("Start doesn't exist in "
                    + "the graph.");
        }

        // adjList gets all information about the graph
        // include all the vertex and the corresponding neighbor info
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();

        // dijkstra used to store the (vertex, vertex to start distance) pair
        Map<Vertex<T>, Integer> dijkstra = new HashMap<>();
        // pre-store the distance for each vertex into dijkstra
        for (Vertex<T> vertex : adjList.keySet()) {
            dijkstra.put(vertex, Integer.MAX_VALUE);
        }

        PriorityQueue<VertexDistance<T>> pq = new PriorityQueue<>();
        pq.add(new VertexDistance<T>(start, 0));
        dijkstra.put(start, 0);

        while (!pq.isEmpty()) {
            VertexDistance<T> curr = pq.remove();
            List<VertexDistance<T>> vdpairs =
                    graph.getAdjList().get(curr.getVertex());
            for (VertexDistance<T> vd : vdpairs) {
                int dist = curr.getDistance() + vd.getDistance();
                if (dist < dijkstra.get(vd.getVertex())) {
                    dijkstra.put(vd.getVertex(), dist);
                    pq.add(new VertexDistance<>(vd.getVertex(),
                            dist));
                }
            } // end for
        } // end while

        return dijkstra;
    }

    /**
     * Runs Prim's algorithm on the given graph and returns the Minimum
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * You should NOT allow self-loops or parallel edges in the MST.
     *
     * You may import/use {@code java.util.PriorityQueue},
     * {@code java.util.Set}, and any class that implements the aforementioned
     * interface.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * The only instance of {@code java.util.Map} that you may use is the
     * adjacency list from {@code graph}. DO NOT create new instances of Map
     * for this method (storing the adjacency list in a variable is fine).
     *
     * @throws IllegalArgumentException if any input
     *  is null, or if {@code start} doesn't exist in the graph
     * @param <T> the generic typing of the data
     * @param start the vertex to begin Prims on
     * @param graph the graph we are applying Prims to
     * @return the MST of the graph or null if there is no valid MST
     */
    public static <T> Set<Edge<T>> prims(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Any input is null.");
        }
        if (!graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("Start doesn't exist in "
                    + "the graph.");
        }

        // adjList gets all information about the graph
        // include all the vertex and the corresponding neighbor info
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();

        Set<Edge<T>> mst = new HashSet<>();
        Set<Vertex<T>> visited = new HashSet<>();
        Queue<Edge<T>> pq = new PriorityQueue<>();

        for (VertexDistance<T> vp
                : adjList.get(start)) {
            pq.add(new Edge<>(
                    start, vp.getVertex(), vp.getDistance()));
        }
        visited.add(start);

        while (!pq.isEmpty()) {
            Edge<T> current = pq.remove();
            if (!(visited.contains(current.getV()))) {
                mst.add(current);
                Edge<T> currentROrder = new Edge<T>(current.getV(),
                        current.getU(), current.getWeight());
                mst.add(currentROrder);
                visited.add(current.getV());
                for (VertexDistance<T> vp
                        : adjList.get(current.getV())) {
                    pq.add(new Edge<>(
                            current.getV(), vp.getVertex(),
                            vp.getDistance()));
                }
            } // end if
        } // end while
        if (visited.size() < adjList.size() - 1) {
            return null;
        }
        return mst;
    }
}