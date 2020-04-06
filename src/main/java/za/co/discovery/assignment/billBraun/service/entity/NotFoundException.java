package za.co.discovery.assignment.billBraun.service.entity;

public class NotFoundException extends Exception {

	private static final long serialVersionUID = 2549573913971054064L;

	public NotFoundException(String symbol) {
		super("Could not find node " + symbol);
	}
}