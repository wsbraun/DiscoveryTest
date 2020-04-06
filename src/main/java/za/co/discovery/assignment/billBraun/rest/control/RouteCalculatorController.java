package za.co.discovery.assignment.billBraun.rest.control;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import za.co.discovery.assignment.billBraun.rest.entity.NodeView;
import za.co.discovery.assignment.billBraun.routecalculator.entity.CalculatedRoute;
import za.co.discovery.assignment.billBraun.routecalculator.entity.Traffic;
import za.co.discovery.assignment.billBraun.service.boundry.RouteCalculatorService;
import za.co.discovery.assignment.billBraun.service.entity.NotFoundException;

@RestController
public class RouteCalculatorController {

	@Inject
	RouteCalculatorService service;

	@GetMapping(path = "/route/{origin}/{destination}")
	@JsonView({NodeView.Summary.class})
	public CalculatedRoute processRoutes(@PathVariable(name = "origin") String origin,
			@PathVariable(name = "destination") String destination,
			@RequestParam(name = "traffic", defaultValue = "EXCLUDED", required = false) Traffic traffic) throws NotFoundException {
		
		CalculatedRoute response = service.processRoute(origin, destination, traffic);
		return response;
		
	}

}
