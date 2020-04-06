package za.co.discovery.assignment.billBraun.service.control;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import za.co.discovery.assignment.billBraun.routecalculator.control.RouteCalculator;
import za.co.discovery.assignment.billBraun.routecalculator.entity.CalculatedRoute;
import za.co.discovery.assignment.billBraun.routecalculator.entity.Traffic;
import za.co.discovery.assignment.billBraun.service.boundry.RouteCalculatorService;
import za.co.discovery.assignment.billBraun.service.entity.NotFoundException;
import za.co.discovery.assignment.billBraun.storage.boundry.NodeRepository;
import za.co.discovery.assignment.billBraun.storage.entity.Node;

@Service
public class RouteCalculatorServiceImpl implements RouteCalculatorService {

	@Inject
	RouteCalculator routeCalculator;

	@Inject
	NodeRepository nodeRepo;

	@Override
	@Transactional
	public CalculatedRoute processRoute(String origin, String destination, Traffic traffic) throws NotFoundException {
		Node originNode = nodeRepo.findBySymbol(origin).orElseThrow(() -> new NotFoundException(origin));
		Node destinationNode = nodeRepo.findBySymbol(destination)
				.orElseThrow(() -> new NotFoundException(destination));
		return routeCalculator.calculateRoute(originNode, destinationNode, traffic);
	}

}
