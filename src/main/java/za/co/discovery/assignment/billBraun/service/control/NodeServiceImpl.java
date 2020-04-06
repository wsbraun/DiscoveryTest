package za.co.discovery.assignment.billBraun.service.control;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import za.co.discovery.assignment.billBraun.service.boundry.NodeService;
import za.co.discovery.assignment.billBraun.service.entity.DuplicateNodeException;
import za.co.discovery.assignment.billBraun.service.entity.NodeNotEmptyException;
import za.co.discovery.assignment.billBraun.service.entity.NotFoundException;
import za.co.discovery.assignment.billBraun.storage.boundry.NodeRepository;
import za.co.discovery.assignment.billBraun.storage.entity.Node;

@Service
@Transactional
public class NodeServiceImpl implements NodeService {

	@Inject
	NodeRepository nodeRepo;

	@Override
	public List<Node> findAll() {
		return nodeRepo.findAll();
	}

	@Override
	public void createNode(String symbol, String name) throws DuplicateNodeException {
		// save to database
		if (nodeRepo.findBySymbol(symbol).isEmpty()) {
			nodeRepo.save(Node.builder().symbol(symbol).name(name).build());
		} else {
			throw new DuplicateNodeException(symbol);
		}
	}

	@Override
	public Node getOne(String symbol) throws NotFoundException {
		return nodeRepo.findBySymbol(symbol).orElseThrow(() -> new NotFoundException(symbol));
	}

	@Override
	public void removeNode(String symbol) throws NotFoundException, NodeNotEmptyException {
		Node nodeToRemove = nodeRepo.findBySymbol(symbol).orElseThrow(() -> new NotFoundException(symbol));
		if (!nodeToRemove.getRoutes().isEmpty()) {
			throw new NodeNotEmptyException(symbol);
		} else {
			nodeRepo.delete(nodeToRemove);
		}
		
	}

}
