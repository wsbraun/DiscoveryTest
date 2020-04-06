package za.co.discovery.assignment.billBraun.loader.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import za.co.discovery.assignment.billBraun.loader.boundry.LoaderService;
import za.co.discovery.assignment.billBraun.storage.boundry.NodeRepository;
import za.co.discovery.assignment.billBraun.storage.boundry.RouteRepository;
import za.co.discovery.assignment.billBraun.storage.entity.Node;
import za.co.discovery.assignment.billBraun.storage.entity.Route;

@Service
@Transactional
@Slf4j
public class ExcelLoaderService implements LoaderService {
	// This class loads from the sheet as per assignment.
	// It is not currently configurable for size

	private DataFormatter dataFormatter = new DataFormatter();

	@Inject
	NodeRepository nodeService;

	@Inject
	RouteRepository routeRepo;

	@Override
	public void loadFromFile(File file) throws EncryptedDocumentException, IOException {
		loadFromStream(new FileInputStream(file));
	}

	@Override
	public void loadFromStream(InputStream stream) throws EncryptedDocumentException, IOException {
		log.debug("Importing from stream");
		Workbook workbook = WorkbookFactory.create(stream);
		Sheet planetNames = workbook.getSheet("Planet Names");
		Sheet routes = workbook.getSheet("Routes");
		Sheet traffic = workbook.getSheet("Traffic");

		log.debug("Got sheets");
		parsePlanets(planetNames);
		parseRoutes(routes);
		parseTraffic(traffic);

	}

	private void parseTraffic(Sheet traffic) {
		// Route Id Planet Origin Planet Destination Traffic Delay (Light Years)
		log.debug("Parsing traffic");
		// Route Id Planet Origin Planet Destination Distance(Light Years)
		for (Row row : traffic) {
			if (row.getRowNum() != 0) {
				long routeId = Long.parseLong(dataFormatter.formatCellValue(row.getCell(0)));
				// Recreate the node if not in the DB
//				String originSymbol = dataFormatter.formatCellValue(row.getCell(1));
//				String destinationSymbol = dataFormatter.formatCellValue(row.getCell(2));
//				Node origin = nodeService.findBySymbol(originSymbol).orElseGet(() -> {
//					Node node = Node.builder().name(originSymbol).symbol(originSymbol).build();
//					nodeService.save(node);
//					return node;
//				});
//				Node destination = nodeService.findBySymbol(destinationSymbol).orElseGet(() -> {
//					Node node = Node.builder().name(destinationSymbol).symbol(destinationSymbol).build();
//					nodeService.save(node);
//					return node;
//				});
				double delay = Double.parseDouble(dataFormatter.formatCellValue(row.getCell(3)).replace(",", "."));
				Route route = routeRepo.findByRouteId(routeId).get();
				route.setTraffic(delay);
				routeRepo.save(route);
//				List<Route> routes = routeRepo.findByRouteId(routeId);
//				for (Route route : routes) {
//					route.setTraffic(delay);
//				}
//				routeRepo.saveAll(routes);
			}
		}
	}

	private void parseRoutes(Sheet routes) {
		log.debug("Parsing routes");
		// Route Id Planet Origin Planet Destination Distance(Light Years)
		for (Row row : routes) {
			if (row.getRowNum() != 0) {
				long routeId = Long.parseLong(dataFormatter.formatCellValue(row.getCell(0)));
				String originSymbol = dataFormatter.formatCellValue(row.getCell(1));
				String destinationSymbol = dataFormatter.formatCellValue(row.getCell(2));
				Node origin = nodeService.findBySymbol(originSymbol).orElseGet(() -> {
					Node node = Node.builder().name(originSymbol).symbol(originSymbol).build();
					nodeService.save(node);
					return node;
				});
				Node destination = nodeService.findBySymbol(destinationSymbol).orElseGet(() -> {
					Node node = Node.builder().name(destinationSymbol).symbol(destinationSymbol).build();
					nodeService.save(node);
					return node;
				});
				double distance = Double.parseDouble(dataFormatter.formatCellValue(row.getCell(3)).replace(",", "."));
				Route route = Route.builder().routeId(routeId).origin(origin).destination(destination).distance(distance)
						.build();
				routeRepo.save(route);
				origin.getRoutes().add(route);
				destination.getRoutes().add(route);
				nodeService.save(origin);
				nodeService.save(destination);
//				Route inverseRoute = Route.builder().routeId(routeId).origin(destination).destination(origin).distance(distance)
//						.build();
//				origin.getRoutes().add(inverseRoute);
//				routeRepo.save(inverseRoute);
			}
		}
	}

	private void parsePlanets(Sheet planetNames) {
		log.debug("Parsing planets");
		// Planet Node Planet Name
		for (Row row : planetNames) {
			if (row.getRowNum() != 0) {
				String symbol = dataFormatter.formatCellValue(row.getCell(0));
				String name = dataFormatter.formatCellValue(row.getCell(1));
				Node node = Node.builder().name(name).symbol(symbol).build();
				log.debug("Storing Node {}", node);
				nodeService.save(node);
			}
		}
	}

}
