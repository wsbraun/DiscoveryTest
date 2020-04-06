package za.co.discovery.assignment.billBraun.routecalculator.boundry;

import za.co.discovery.assignment.billBraun.routecalculator.entity.CalculatedRoute;
import za.co.discovery.assignment.billBraun.routecalculator.entity.Traffic;
import za.co.discovery.assignment.billBraun.storage.entity.Node;

public interface ShortestPathCalculator {
	CalculatedRoute calculateRoute(Node origin, Node destination);

	CalculatedRoute calculateRoute(Node origin, Node destination, Traffic traffic);
}
