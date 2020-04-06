package za.co.discovery.assignment.billBraun.routecalculator.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import za.co.discovery.assignment.billBraun.routecalculator.boundry.ShortestPathCalculator;
import za.co.discovery.assignment.billBraun.routecalculator.entity.CalculatedRoute;
import za.co.discovery.assignment.billBraun.routecalculator.entity.CalculatedRoute.CalculatedRouteBuilder;
import za.co.discovery.assignment.billBraun.routecalculator.entity.RouteLeg;
import za.co.discovery.assignment.billBraun.routecalculator.entity.RouteLeg.RouteLegBuilder;
import za.co.discovery.assignment.billBraun.routecalculator.entity.Traffic;
import za.co.discovery.assignment.billBraun.storage.boundry.NodeRepository;
import za.co.discovery.assignment.billBraun.storage.boundry.RouteRepository;
import za.co.discovery.assignment.billBraun.storage.entity.Node;
import za.co.discovery.assignment.billBraun.storage.entity.Route;

@Service
@Slf4j
public class RouteCalculator implements ShortestPathCalculator {

	@Inject
	DijkstrasAlgorithm dijkstrasAlgorithm;

	@Inject
	NodeRepository nodeRepo;
	
	@Inject
	RouteRepository routeRepo;

	@Override
	public CalculatedRoute calculateRoute(Node origin, Node destination, Traffic traffic) {

		// Calculate adjacency matrix
		ReversableMap<Node, Integer> nodeIndexMap = new ReversableMap<>();
		List<Node> nodes = nodeRepo.findAll();
		int index = 0;
		nodeIndexMap.put(origin, index++);
		double adjacencyMatrix[][] = new double[nodes.size()][nodes.size()];
		for (Node node : nodes) {
			if (!nodeIndexMap.containsKey(node)) {
				nodeIndexMap.put(node, index);
				adjacencyMatrix[index][index] = 0.0d;
				index++;
			}
		}
		
		for (Route route : routeRepo.findAll()) {
			int originIndex = nodeIndexMap.get(route.getOrigin());
			int destinationIndex = nodeIndexMap.get(route.getDestination());
				if (!nodeIndexMap.containsKey(route.getDestination())) {
					nodeIndexMap.put(route.getDestination(), index++);
				}
				switch (traffic) {
				case EXCLUDED:
					adjacencyMatrix[originIndex][destinationIndex] = route.getDistance();
					adjacencyMatrix[destinationIndex][originIndex] = route.getDistance();
					break;
				case INCLUDED:
					adjacencyMatrix[originIndex][destinationIndex] = route.getDistance() + route.getTraffic();
					adjacencyMatrix[destinationIndex][originIndex] = route.getDistance() + route.getTraffic();
					break;
				}

		}
		
		int startVertex = 0;
		ShortestRoutes routes = dijkstrasAlgorithm.dijkstra(adjacencyMatrix, startVertex);
		log.debug("Routes calculated -> {}", routes);

		return translateRoute(origin, destination, traffic, routes, nodeIndexMap, adjacencyMatrix);
	}

	private CalculatedRoute translateRoute(Node origin, Node destination, Traffic traffic, ShortestRoutes routes,
			ReversableMap<Node, Integer> nodeIndexMap, double[][] adjacencyMatrix) {
		CalculatedRouteBuilder calculatedRouteBuilder = CalculatedRoute.builder();
		calculatedRouteBuilder.origin(origin);
		calculatedRouteBuilder.destination(destination);
		calculatedRouteBuilder.traffic(traffic);
		for (ShortestRoute shortRoute : routes.getRoutes()) {
			if (nodeIndexMap.getKey(shortRoute.getDestination()) == destination) {
				calculatedRouteBuilder.totalDistance(shortRoute.getTotalDistance());
				Node originNode = origin;
				for (Integer hop : shortRoute.getRoute()) {
					if (hop != 0) {
						Node destinationNode = nodeIndexMap.getKey(hop);
						RouteLegBuilder legBuilder = RouteLeg.builder().destination(destinationNode).origin(originNode)
								.distance(adjacencyMatrix[nodeIndexMap.get(originNode)][hop]);
						originNode = destinationNode;
						calculatedRouteBuilder.leg(legBuilder.build());
					}
				}
			}
		}
		return calculatedRouteBuilder.build();
	}

	@Override
	public CalculatedRoute calculateRoute(Node origin, Node destination) {
		return calculateRoute(origin, destination, Traffic.EXCLUDED);
	}

}

class ReversableMap<K, V> extends HashMap<K, V> {

	private static final long serialVersionUID = -1559305694192529545L;
	Map<V, K> reverseMap = new HashMap<>();

	@Override
	public V put(K key, V value) {
		reverseMap.put(value, key);
		return super.put(key, value);
	}

	public K getKey(V value) {
		return reverseMap.get(value);
	}

}
