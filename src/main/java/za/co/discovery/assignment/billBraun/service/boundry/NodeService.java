package za.co.discovery.assignment.billBraun.service.boundry;

import java.util.List;

import za.co.discovery.assignment.billBraun.service.entity.DuplicateNodeException;
import za.co.discovery.assignment.billBraun.service.entity.NodeNotEmptyException;
import za.co.discovery.assignment.billBraun.service.entity.NotFoundException;
import za.co.discovery.assignment.billBraun.storage.entity.Node;

public interface NodeService {

	List<Node> findAll();

	void createNode(String symbol, String name) throws DuplicateNodeException;

	Node getOne(String symbol) throws NotFoundException;

	void removeNode(String symbol) throws NotFoundException, NodeNotEmptyException;

}
