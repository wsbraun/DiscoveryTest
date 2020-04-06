package za.co.discovery.assignment.billBraun.rest.entity;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateNodeRequest implements Serializable {

	private static final long serialVersionUID = 7736441552071017268L;

	private String symbol;
	private String name;

	@Builder
	CreateNodeRequest(String symbol, String name) {
		super();
		this.symbol = symbol;
		this.name = name;
	}

}
