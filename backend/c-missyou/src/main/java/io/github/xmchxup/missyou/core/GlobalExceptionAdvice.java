package io.github.xmchxup.missyou.core;

import io.github.xmchxup.missyou.core.configuration.ExceptionCodeConfiguration;
import io.github.xmchxup.missyou.exception.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@ControllerAdvice
public class GlobalExceptionAdvice {

	@Autowired
	private ExceptionCodeConfiguration codeConfiguration;

	//	未知异常
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public UnifyResponse handleException(HttpServletRequest req, Exception e) {
		String url = req.getRequestURI();
		String method = req.getMethod();

		return new UnifyResponse(9999, "服务器异常", method + " " + url);
	}

	//	已知异常
	@ExceptionHandler(value = HttpException.class)
	public ResponseEntity<UnifyResponse> handleHttpException(HttpServletRequest req, HttpException e) {
		String url = req.getRequestURI();
		String method = req.getMethod();

		UnifyResponse message = new UnifyResponse(
				e.getCode(),
				codeConfiguration.getMessage(e.getCode()),
				method + " " + url);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpStatus httpStatus = HttpStatus.resolve(e.getHttpStatusCode());

		return new ResponseEntity<>(message, headers, httpStatus);
	}

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public UnifyResponse handleBeanValidation(HttpServletRequest req,
											  MethodArgumentNotValidException e) {
		String url = req.getRequestURI();
		String method = req.getMethod();

		List<ObjectError> errors = e.getBindingResult().getAllErrors();
		String message = formatAllErrorMessage(errors);
		return new UnifyResponse(10001, message, method + " " + url);

	}


	@ExceptionHandler(value = ConstraintViolationException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public UnifyResponse handleConstraintException(HttpServletRequest req,
												   ConstraintViolationException e) {
		String url = req.getRequestURI();

		String method = req.getMethod();

//		特殊处理
		StringBuilder errorMsg = new StringBuilder();
		for (ConstraintViolation<?> error : e.getConstraintViolations()) {
			String msg = error.getMessage();
			String m = error.getPropertyPath().toString();
			String name = m.split("[.]")[1];
			errorMsg.append(name).append(": ").append(msg).append(";");
		}

//		默认message
		String message = e.getMessage();
		return new UnifyResponse(10001, errorMsg.toString(), method + " " + url);
	}

	private String formatAllErrorMessage(List<ObjectError> errors) {
		StringBuffer errorMsg = new StringBuffer();
		errors.forEach(error ->
				errorMsg.append(error.getDefaultMessage()).append(";")
		);
		return errorMsg.toString();
	}
}
