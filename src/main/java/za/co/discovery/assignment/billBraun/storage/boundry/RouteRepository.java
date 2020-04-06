package za.co.discovery.assignment.billBraun.storage.boundry;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import za.co.discovery.assignment.billBraun.storage.entity.Route;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {

	Optional<Route> findByRouteId(long routeId);

}
