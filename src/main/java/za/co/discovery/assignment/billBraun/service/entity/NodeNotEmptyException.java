package za.co.discovery.assignment.billBraun.service.entity;

public class NodeNotEmptyException extends Exception {

	private static final long serialVersionUID = 2549573913971054064L;

	public NodeNotEmptyException(String symbol) {
		super("Node still has routes " + symbol);
	}
}