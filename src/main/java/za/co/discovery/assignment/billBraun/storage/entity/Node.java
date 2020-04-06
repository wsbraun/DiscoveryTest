package za.co.discovery.assignment.billBraun.storage.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import za.co.discovery.assignment.billBraun.rest.control.CustomListSerializer;
import za.co.discovery.assignment.billBraun.rest.entity.NodeView;
import za.co.discovery.assignment.billBraun.rest.entity.RouteView;

@Data
@NoArgsConstructor
@Entity
@Table(name="node")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Node implements Serializable {
	
	private static final long serialVersionUID = 7736441552071017268L;
	
	@Id
	@GeneratedValue
	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	@EqualsAndHashCode.Include
	private Long id;
	
	@Basic
	@Column(name = "symbol", unique = true, nullable =  false, length = 4)
	@JsonView({NodeView.Summary.class, NodeView.Extended.class, RouteView.Summary.class, RouteView.Extended.class})
	private String symbol;
	
	@Basic
	@Column(name = "name", unique = false, nullable =  false, length = 30)
	@JsonView({NodeView.Summary.class, NodeView.Extended.class, RouteView.Summary.class, RouteView.Extended.class})
	private String name;
	
	@ManyToMany
	@Setter(AccessLevel.PRIVATE)
	@ToString.Exclude
	@JsonSerialize(using = CustomListSerializer.class)
	@JsonView({NodeView.Extended.class})
	private Collection<Route> routes;

	@Builder
	Node(String symbol, String name) {
		super();
		this.symbol = symbol;
		this.name = name;
	}
	
	public Collection<Route> getRoutes() {
		if (routes == null) {
			routes = new ArrayList<>();
		}
		return routes;
	}
	
	
	
	

}
