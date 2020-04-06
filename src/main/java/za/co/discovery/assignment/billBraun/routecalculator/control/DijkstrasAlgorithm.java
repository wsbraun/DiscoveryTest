package za.co.discovery.assignment.billBraun.routecalculator.control;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// A Java program for Dijkstra's 

// single source shortest path 
// algorithm. The program is for 
// adjacency matrix representation 
// of the graph. 
//This code is contributed by Harikrishnan Rajan 
// From https://www.geeksforgeeks.org/printing-paths-dijkstras-shortest-path-algorithm/
@Component
public class DijkstrasAlgorithm {

	private static final int NO_PARENT = -1;

	// Function that implements Dijkstra's
	// single source shortest path
	// algorithm for a graph represented
	// using adjacency matrix
	// representation
	public ShortestRoutes dijkstra(double[][] adjacencyMatrix, int startVertex) {
		int nVertices = adjacencyMatrix[0].length;

		// shortestDistances[i] will hold the
		// shortest distance from src to i
		double[] shortestDistances = new double[nVertices];

		// added[i] will true if vertex i is
		// included / in shortest path tree
		// or shortest distance from src to
		// i is finalized
		boolean[] added = new boolean[nVertices];

		// Initialize all distances as
		// INFINITE and added[] as false
		for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
			shortestDistances[vertexIndex] = Double.MAX_VALUE;
			added[vertexIndex] = false;
		}

		// Distance of source vertex from
		// itself is always 0
		shortestDistances[startVertex] = 0.0d;

		// Parent array to store shortest
		// path tree
		int[] parents = new int[nVertices];

		// The starting vertex does not
		// have a parent
		parents[startVertex] = NO_PARENT;

		// Find shortest path for all
		// vertices
		for (int i = 1; i < nVertices; i++) {

			// Pick the minimum distance vertex
			// from the set of vertices not yet
			// processed. nearestVertex is
			// always equal to startNode in
			// first iteration.
			int nearestVertex = -1;
			double shortestDistance = Double.MAX_VALUE;
			for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
				if (!added[vertexIndex] && shortestDistances[vertexIndex] < shortestDistance) {
					nearestVertex = vertexIndex;
					shortestDistance = shortestDistances[vertexIndex];
				}
			}

			// Mark the picked vertex as
			// processed
			added[nearestVertex] = true;

			// Update dist value of the
			// adjacent vertices of the
			// picked vertex.
			for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
				double edgeDistance = adjacencyMatrix[nearestVertex][vertexIndex];

				if (edgeDistance > 0 && ((shortestDistance + edgeDistance) < shortestDistances[vertexIndex])) {
					parents[vertexIndex] = nearestVertex;
					shortestDistances[vertexIndex] = shortestDistance + edgeDistance;
				}
			}
		}

		ShortestRoutes routes = printSolution(startVertex, shortestDistances, parents);
		return routes;
	}

	// A utility function to print
	// the constructed distances
	// array and shortest paths
	private ShortestRoutes printSolution(int startVertex, double[] distances, int[] parents) {
		ShortestRoutes routes = ShortestRoutes.builder().source(startVertex).build();
		int nVertices = distances.length;

		for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
			if (vertexIndex != startVertex) {
				List<Integer> path = printPath(vertexIndex, parents);
				ShortestRoute route = ShortestRoute.builder().route(path).destination(vertexIndex).totalDistance(distances[vertexIndex]).build();
				routes.getRoutes().add(route);
			}
		}
		return routes;
	}

	// Function to print shortest path
	// from source to currentVertex
	// using parents array
	private List<Integer> printPath(int currentVertex, int[] parents) {

		// Base case : Source node has
		// been processed
		if (currentVertex == NO_PARENT) {
			return new ArrayList<>();
		}
		List<Integer> rsp =  printPath(parents[currentVertex], parents);
		rsp.add(currentVertex);
		return rsp;
	}

	// Driver Code
	public static void main(String[] args) {
		double[][] adjacencyMatrix = { { 0, 4, 0, 0, 0, 0, 0, 8, 0 }, { 4, 0, 8, 0, 0, 0, 0, 11, 0 },
				{ 0, 8, 0, 7, 0, 4, 0, 0, 2 }, { 0, 0, 7, 0, 9, 14, 0, 0, 0 }, { 0, 0, 0, 9, 0, 10, 0, 0, 0 },
				{ 0, 0, 4, 0, 10, 0, 2, 0, 0 }, { 0, 0, 0, 14, 0, 2, 0, 1, 6 }, { 8, 11, 0, 0, 0, 0, 1, 0, 7 },
				{ 0, 0, 2, 0, 0, 0, 6, 7, 0 } };
//		double[][] adjacencyMatrix = {
//				{0,9,6,5,3},
//				{0,0,0,0,0},
//				{0,2,0,4,0},
//				{0,0,0,0,0},
//				{0,0,0,0,0}
//				};
		
		
		DijkstrasAlgorithm algo = new DijkstrasAlgorithm();
		System.out.println(algo.dijkstra(adjacencyMatrix, 0));
	}
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class ShortestRoutes {
	private int source;
	@Builder.Default
	private List<ShortestRoute> routes = new ArrayList<>();
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class ShortestRoute {
	private int destination;
	private double totalDistance;
	@Builder.Default
	private List<Integer> route = new ArrayList<>();
}


