package za.co.discovery.assignment.billBraun.routecalculator.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.discovery.assignment.billBraun.rest.entity.NodeView;
import za.co.discovery.assignment.billBraun.storage.entity.Node;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonView({NodeView.Summary.class})
public class RouteLeg implements Serializable {
	
	private static final long serialVersionUID = 6923427100634075762L;
	private Node origin;
	private Node destination;
	private double distance; 

}
