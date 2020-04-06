package za.co.discovery.assignment.billBraun.ws.control;

import javax.inject.Inject;

import org.example.routecalculator.LegType;
import org.example.routecalculator.NodeType;
import org.example.routecalculator.RouteRequest;
import org.example.routecalculator.RouteResponse;
import org.example.routecalculator.TrafficIndicator;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import lombok.extern.slf4j.Slf4j;
import za.co.discovery.assignment.billBraun.routecalculator.entity.CalculatedRoute;
import za.co.discovery.assignment.billBraun.routecalculator.entity.RouteLeg;
import za.co.discovery.assignment.billBraun.routecalculator.entity.Traffic;
import za.co.discovery.assignment.billBraun.service.boundry.RouteCalculatorService;
import za.co.discovery.assignment.billBraun.service.entity.NotFoundException;
import za.co.discovery.assignment.billBraun.storage.entity.Node;

@Endpoint
@Slf4j
public class RouteCalculatorEndpoint {

	@Inject
	RouteCalculatorService service;

	@PayloadRoot(namespace = "http://www.example.org/routecalculator/", localPart = "RouteRequest")
	@ResponsePayload
	public RouteResponse calculateRoute(@RequestPayload RouteRequest request) throws NotFoundException {
		log.info("Endpoint received request[origin={},destination={}, traffic={}]", request.getOrigin(),
				request.getDestination(), request.getTraffic());
		CalculatedRoute rsp = service.processRoute(request.getOrigin(), request.getDestination(),
				changeTraffic(request.getTraffic()));

		return changeResponse(rsp);
	}

	private RouteResponse changeResponse(CalculatedRoute rsp) {
		RouteResponse response = new RouteResponse();
		response.setOrigin(convertNode(rsp.getOrigin()));
		response.setDestination(convertNode(rsp.getDestination()));
		response.setTotalDistance(rsp.getTotalDistance());
		response.setTraffic(changeTraffic(rsp.getTraffic()));
		for (RouteLeg leg : rsp.getLegs()) {
			response.getLeg().add(convertLeg(leg));
		}
		return response;
	}

	private LegType convertLeg(RouteLeg leg) {
		LegType legType = new LegType();
		legType.setOrigin(convertNode(leg.getOrigin()));
		legType.setDestination(convertNode(leg.getDestination()));
		legType.setDistance(leg.getDistance());
		return legType;
	}

	private NodeType convertNode(Node node) {
		NodeType nodeType = new NodeType();
		nodeType.setName(node.getName());
		nodeType.setSymbol(node.getSymbol());
		return nodeType;
	}

	private Traffic changeTraffic(TrafficIndicator traffic) {
		if (traffic == null) {
			return Traffic.EXCLUDED;
		}
		switch (traffic) {
		case INCLUDED:
			return Traffic.INCLUDED;
		case EXCLUDED:
		default:
			return Traffic.EXCLUDED;
		}
	}
	
	private TrafficIndicator changeTraffic(Traffic traffic) {
		if (traffic == null) {
			return TrafficIndicator.EXCLUDED;
		}
		switch (traffic) {
		case INCLUDED:
			return TrafficIndicator.INCLUDED;
		case EXCLUDED:
		default:
			return TrafficIndicator.EXCLUDED;
		}
	}
}