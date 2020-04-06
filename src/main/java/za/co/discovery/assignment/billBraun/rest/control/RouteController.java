package za.co.discovery.assignment.billBraun.rest.control;

import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import za.co.discovery.assignment.billBraun.rest.entity.RouteView;
import za.co.discovery.assignment.billBraun.service.boundry.RouteService;
import za.co.discovery.assignment.billBraun.service.entity.NotFoundException;
import za.co.discovery.assignment.billBraun.storage.entity.Route;

@RestController
public class RouteController {

	@Inject
	RouteService routeService;

	// Aggregate root

	@GetMapping("/routes")
	@JsonView({RouteView.Summary.class})
	@JsonSerialize(using = CustomListSerializer.class)
	public List<Route> all() {
		return routeService.findAll();
	}

//	@PostMapping("/nodes")
//	public ResponseEntity<Void> create(@RequestBody CreateNodeRequest node) throws URISyntaxException {
//		try {
//			nodeService.createNode(node.getSymbol(), node.getName());
//			return ResponseEntity.created(new URI("/nodes/" + node.getSymbol())).build();
//		} catch (DuplicateNodeException e) {
//			return ResponseEntity.status(HttpStatus.CONFLICT).build();
//		}
//		
//	}

	// Single item
	@GetMapping("/routes/{routeId}")
	@JsonView({RouteView.Extended.class})
	public Route one(@PathVariable Long routeId) throws NotFoundException {
		return routeService.getOne(routeId);
	}
	
//	@DeleteMapping("/nodes/{symbol}")
//	@JsonView(NodeView.Extended.class)
//	public void removeNode(@PathVariable String symbol) throws NodeNotFoundException, NodeNotEmptyException {
//		nodeService.removeNode(symbol);
//	}

}
