package za.co.discovery.assignment.billBraun.rest.control;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import za.co.discovery.assignment.billBraun.rest.entity.CreateNodeRequest;
import za.co.discovery.assignment.billBraun.rest.entity.NodeView;
import za.co.discovery.assignment.billBraun.service.boundry.NodeService;
import za.co.discovery.assignment.billBraun.service.entity.DuplicateNodeException;
import za.co.discovery.assignment.billBraun.service.entity.NodeNotEmptyException;
import za.co.discovery.assignment.billBraun.service.entity.NotFoundException;
import za.co.discovery.assignment.billBraun.storage.entity.Node;

@RestController
public class NodeController {

	@Inject
	NodeService nodeService;

	// Aggregate root

	@GetMapping("/nodes")
	@JsonView({NodeView.Summary.class})
	public List<Node> all() {
		return nodeService.findAll();
	}

	@PostMapping("/nodes")
	public ResponseEntity<Void> create(@RequestBody CreateNodeRequest node) throws URISyntaxException {
		try {
			nodeService.createNode(node.getSymbol(), node.getName());
			return ResponseEntity.created(new URI("/nodes/" + node.getSymbol())).build();
		} catch (DuplicateNodeException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		
	}

	// Single item

	@GetMapping("/nodes/{symbol}")
	@JsonView(NodeView.Extended.class)
	public Node one(@PathVariable String symbol) throws NotFoundException {
		
		return nodeService.getOne(symbol);
	}
	
	@DeleteMapping("/nodes/{symbol}")
	@JsonView(NodeView.Extended.class)
	public void removeNode(@PathVariable String symbol) throws NotFoundException, NodeNotEmptyException {
		nodeService.removeNode(symbol);
	}

}
