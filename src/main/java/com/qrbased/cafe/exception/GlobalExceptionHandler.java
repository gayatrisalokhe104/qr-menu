package com.qrbased.cafe.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.qrbased.cafe.util.ErrorDetails;


@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(value = { ResourceNotFoundException.class })
	public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException) {
		ApiResponse apiResponse = new ApiResponse(HttpStatus.NOT_FOUND.value(), resourceNotFoundException.getMessage());
		return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = { DuplicateEntryException.class })
	public ResponseEntity<Object> handleDuplicateEntryException(DuplicateEntryException duplicateEntryException) {
		ApiResponse apiResponse = new ApiResponse(HttpStatus.CONFLICT.value(), duplicateEntryException.getMessage());
		return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(value = { PaymentException.class })
	public ResponseEntity<Object> handlePaymentException(PaymentException paymentException) {
		ApiResponse apiResponse = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), paymentException.getMessage());
		return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(ForbiddenException.class)
	public ResponseEntity<ErrorDetails> handleForbiddenExceptionException(ForbiddenException exception,
			WebRequest webRequest) {

		ErrorDetails errorDetails = new ErrorDetails();
		errorDetails.setMessage(exception.getMessage());
		errorDetails.setDetails(webRequest.getDescription(false));

		return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);

	}

	@ExceptionHandler(EmailAlreadyTakenException.class)
	public ResponseEntity<ErrorDetails> handleEmailAlreadyTakenException(EmailAlreadyTakenException exception,
			WebRequest webRequest) {

		ErrorDetails errorDetails = new ErrorDetails();
		errorDetails.setMessage(exception.getMessage());
		errorDetails.setDetails(webRequest.getDescription(true));

		return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);

	}
	
	@ExceptionHandler(AdminRegistrationException.class)
	public ResponseEntity<ErrorDetails> handleAdminRegistrationException(AdminRegistrationException exception,
			WebRequest webRequest) {

		ErrorDetails errorDetails = new ErrorDetails();
		errorDetails.setMessage(exception.getMessage());
		errorDetails.setDetails(webRequest.getDescription(false));

		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@ExceptionHandler(APIException.class)
	public ResponseEntity<ErrorDetails> handleAPIException(APIException exception,
			WebRequest webRequest) {

		ErrorDetails errorDetails = new ErrorDetails(exception.getMessage(),
				webRequest.getDescription(false));

		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);

	}
	
	// global exceptions 
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception,
			WebRequest webRequest) {

		ErrorDetails errorDetails = new ErrorDetails(exception.getMessage(),
				webRequest.getDescription(false));

		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);

	}
}
