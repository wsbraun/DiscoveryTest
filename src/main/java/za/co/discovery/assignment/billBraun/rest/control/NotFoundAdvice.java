package za.co.discovery.assignment.billBraun.rest.control;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import za.co.discovery.assignment.billBraun.service.entity.NotFoundException;

@ControllerAdvice
class NotFoundAdvice {

	@ResponseBody
	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String nodeNotFoundHandler(NotFoundException ex) {
		return ex.getMessage();
	}
}