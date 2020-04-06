package za.co.discovery.assignment.billBraun.rest.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import za.co.discovery.assignment.billBraun.rest.entity.RouteDTO;
import za.co.discovery.assignment.billBraun.storage.entity.Route;

public class CustomListSerializer extends StdSerializer<List<Route>> {

	private static final long serialVersionUID = 7987050874070302466L;

	public CustomListSerializer() {
		this(null);
	}

	public CustomListSerializer(Class<List<Route>> t) {
		super(t);
	}

	@Override
	public void serialize(List<Route> items, JsonGenerator generator, SerializerProvider provider)
			throws IOException, JsonProcessingException {

		List<RouteDTO> ids = new ArrayList<>();
		for (Route item : items) {
			
			RouteDTO routeDTO = RouteDTO.builder()
					.id(item.getRouteId())
					.origin(item.getOrigin().getSymbol())
					.destination(item.getDestination().getSymbol())
					.distance(item.getDistance())
					.traffic(item.getTraffic())
					.build();
			ids.add(routeDTO);
		}
		generator.writeObject(ids);
	}
}