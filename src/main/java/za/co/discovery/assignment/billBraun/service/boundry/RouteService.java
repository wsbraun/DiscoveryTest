package za.co.discovery.assignment.billBraun.service.boundry;

import java.util.List;

import za.co.discovery.assignment.billBraun.service.entity.NotFoundException;
import za.co.discovery.assignment.billBraun.storage.entity.Node;
import za.co.discovery.assignment.billBraun.storage.entity.Route;

public interface RouteService {

	List<Route> findAll();

	Route getOne(Long routeId) throws NotFoundException;

}
