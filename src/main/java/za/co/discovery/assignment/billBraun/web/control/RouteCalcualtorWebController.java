package za.co.discovery.assignment.billBraun.web.control;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import za.co.discovery.assignment.billBraun.routecalculator.entity.CalculatedRoute;
import za.co.discovery.assignment.billBraun.routecalculator.entity.Traffic;
import za.co.discovery.assignment.billBraun.service.boundry.RouteCalculatorService;
import za.co.discovery.assignment.billBraun.service.entity.NotFoundException;
import za.co.discovery.assignment.billBraun.storage.boundry.NodeRepository;
import za.co.discovery.assignment.billBraun.web.entity.RouteRequestForm;

@Controller
public class RouteCalcualtorWebController {
	
	@Inject
	RouteCalculatorService service;
	@Inject 
	NodeRepository nodeRepo;

	@GetMapping("/route")
	public ModelAndView showForm(Model model) {
		model.addAttribute("routeForm", RouteRequestForm.builder().build());
		model.addAttribute("planets", nodeRepo.findAll());
		model.addAttribute("traffics", Traffic.values());
		return new ModelAndView("route-request-form");
	}

	@PostMapping("/route")
	public ModelAndView checkPersonInfo(Model model, @ModelAttribute("routeForm")  @Valid RouteRequestForm routeRequestForm, BindingResult bindingResult) throws NotFoundException {

		if (bindingResult.hasErrors()) {
			return new ModelAndView("route-request-form");
		}
		
		CalculatedRoute route = service.processRoute(routeRequestForm.getOrigin(), routeRequestForm.getDestination(), routeRequestForm.getTraffic());
		
		model.addAttribute("route", route);

		return new ModelAndView("route-result.html");
	}

}