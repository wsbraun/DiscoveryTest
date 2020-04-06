package za.co.discovery.assignment.billBraun.rest.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RouteDTO {
	private Long id;
	private String origin;
	private String destination;
	private double distance;
	private double traffic;

}
