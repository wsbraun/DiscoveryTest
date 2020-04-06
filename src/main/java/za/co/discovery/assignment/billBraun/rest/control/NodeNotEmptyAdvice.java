package za.co.discovery.assignment.billBraun.rest.control;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import za.co.discovery.assignment.billBraun.service.entity.NodeNotEmptyException;
import za.co.discovery.assignment.billBraun.service.entity.NotFoundException;

@ControllerAdvice
class NodeNotEmptyAdvice {

	@ResponseBody
	@ExceptionHandler(NodeNotEmptyException.class)
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	String nodeNotFoundHandler(NotFoundException ex) {
		return ex.getMessage();
	}
}