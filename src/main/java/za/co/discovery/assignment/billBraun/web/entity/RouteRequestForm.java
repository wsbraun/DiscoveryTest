package za.co.discovery.assignment.billBraun.web.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.discovery.assignment.billBraun.routecalculator.entity.Traffic;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteRequestForm {
	
	@NotNull
	@Size(min=1, max=4)
	private String origin;
	@NotNull
	@Size(min=1, max=4)
	private String destination;
	
	private Traffic traffic;

}
