import java.util.*;

class Edge implements Comparable<Edge> {
    int src, dest, weight;

    // Constructor for an edge
    public Edge(int src, int dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }

    // Comparator function used for sorting edges by weight
    public int compareTo(Edge compareEdge) {
        return this.weight - compareEdge.weight;
    }
}

class Subset {
    int parent, rank;
}

public class KruskalMST {

    // Function to find the set of an element using path compression
    int find(Subset subsets[], int i) {
        if (subsets[i].parent != i)
            subsets[i].parent = find(subsets, subsets[i].parent);
        return subsets[i].parent;
    }

    // Function to perform union of two sets using union by rank
    void union(Subset subsets[], int x, int y) {
        int rootX = find(subsets, x);
        int rootY = find(subsets, y);

        if (subsets[rootX].rank < subsets[rootY].rank)
            subsets[rootX].parent = rootY;
        else if (subsets[rootX].rank > subsets[rootY].rank)
            subsets[rootY].parent = rootX;
        else {
            subsets[rootY].parent = rootX;
            subsets[rootX].rank++;
        }
    }

    // Function to implement Kruskal's algorithm to find MST
    void KruskalMST(Edge edges[], int V) {
        // Initialize the result array to store the MST
        Edge result[] = new Edge[V - 1];
        int e = 0;  // Index for the result[]
        int i = 0;  // Index for sorted edges

        // Sort edges in non-decreasing order of weight
        Arrays.sort(edges);

        // Create V subsets for each vertex
        Subset subsets[] = new Subset[V];
        for (int v = 0; v < V; ++v) {
            subsets[v] = new Subset();
            subsets[v].parent = v;
            subsets[v].rank = 0;
        }

        // Number of edges to be taken is V-1
        while (e < V - 1) {
            // Pick the smallest edge and increment the index
            Edge nextEdge = edges[i++];

            int x = find(subsets, nextEdge.src);
            int y = find(subsets, nextEdge.dest);

            // If adding this edge doesn't cause a cycle
            if (x != y) {
                result[e++] = nextEdge;
                union(subsets, x, y);
            }
        }

        // Print the result
        System.out.println("Edges in the Minimum Spanning Tree:");
        for (i = 0; i < e; ++i)
            System.out.println(result[i].src + " -- " + result[i].dest + " == " + result[i].weight);
    }

    public static void main(String[] args) {
        int V = 4;  // Number of vertices
        int E = 5;  // Number of edges
        Edge edges[] = new Edge[E];

        // Add edges (src, dest, weight)
        edges[0] = new Edge(0, 1, 10);
        edges[1] = new Edge(0, 2, 6);
        edges[2] = new Edge(0, 3, 5);
        edges[3] = new Edge(1, 3, 15);
        edges[4] = new Edge(2, 3, 4);

        KruskalMST mst = new KruskalMST();
        mst.KruskalMST(edges, V);
    }
}
