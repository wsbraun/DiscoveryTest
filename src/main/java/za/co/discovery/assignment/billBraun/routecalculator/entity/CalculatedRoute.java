package za.co.discovery.assignment.billBraun.routecalculator.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;
import za.co.discovery.assignment.billBraun.rest.entity.NodeView;
import za.co.discovery.assignment.billBraun.storage.entity.Node;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonView({NodeView.Summary.class})
public class CalculatedRoute implements Serializable{

	private static final long serialVersionUID = 6939922825528775885L;
	
	private Node origin;
	private Node destination;
	private double totalDistance;
	private Traffic traffic;
	@Singular("leg")
	private List<RouteLeg> legs;
}
