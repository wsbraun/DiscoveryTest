package za.co.discovery.assignment.billBraun.service.control;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import za.co.discovery.assignment.billBraun.service.boundry.RouteService;
import za.co.discovery.assignment.billBraun.service.entity.NotFoundException;
import za.co.discovery.assignment.billBraun.storage.boundry.RouteRepository;
import za.co.discovery.assignment.billBraun.storage.entity.Node;
import za.co.discovery.assignment.billBraun.storage.entity.Route;

@Service
@Transactional
public class RouteServiceImpl implements RouteService {
	
	@Inject
	RouteRepository routeRepo;

	@Override
	public List<Route> findAll() {
		return routeRepo.findAll();
	}

	@Override
	public Route getOne(Long routeId) throws NotFoundException {
		return routeRepo.findByRouteId(routeId).orElseThrow(() -> new NotFoundException(routeId.toString()));
	}
	
	

}
