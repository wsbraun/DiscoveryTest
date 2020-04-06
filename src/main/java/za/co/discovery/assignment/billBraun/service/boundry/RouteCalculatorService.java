package za.co.discovery.assignment.billBraun.service.boundry;

import za.co.discovery.assignment.billBraun.routecalculator.entity.CalculatedRoute;
import za.co.discovery.assignment.billBraun.routecalculator.entity.Traffic;
import za.co.discovery.assignment.billBraun.service.entity.NotFoundException;

public interface RouteCalculatorService {

	CalculatedRoute processRoute(String origin, String destination, Traffic traffic) throws NotFoundException;

}
