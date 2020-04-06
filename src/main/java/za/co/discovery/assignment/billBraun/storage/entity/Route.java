package za.co.discovery.assignment.billBraun.storage.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import za.co.discovery.assignment.billBraun.rest.entity.RouteView;

@Data
@Entity
@NoArgsConstructor
@Table(name = "route")
public class Route implements Serializable {

	private static final long serialVersionUID = 6801660732265572659L;
	
	@Id
	@GeneratedValue
	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private Long id;

	@Basic
	@Column(name = "routeId", unique = false, nullable = false)
	@JsonView({RouteView.Summary.class, RouteView.Extended.class})
	private Long routeId;
	
	@ManyToOne
	@JsonView({RouteView.Summary.class, RouteView.Extended.class})
	private Node origin;
	
	@ManyToOne
	@JsonView({RouteView.Summary.class, RouteView.Extended.class})
	private Node destination;

	@Basic
	@Column(name = "distance", unique = false, nullable = false)
	@JsonView({RouteView.Summary.class, RouteView.Extended.class})
	private double distance;

	@Basic
	@Column(name = "traffic", unique = false, nullable = false)
	@JsonView({RouteView.Summary.class, RouteView.Extended.class})
	private double traffic;

	@Builder
	Route(Long routeId, Node origin, Node destination, double distance) {
		super();
		this.routeId = routeId;
		this.origin = origin;
		this.destination = destination;
		this.distance = distance;
	}
}
