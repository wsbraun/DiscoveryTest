package za.co.discovery.assignment.billBraun.service.entity;

public class DuplicateNodeException extends Exception {

	private static final long serialVersionUID = 2549573913971054064L;

	public DuplicateNodeException(String symbol) {
		super("Node with symbol " + symbol + " already exists");
	}
}