package ${groupId}.handler;

import cn.org.citycloud.bean.base.ErrorResponse;
import cn.org.citycloud.exception.BusinessErrorException;
import cn.org.citycloud.exception.TokenValidationException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 全局异常处理
 * @author Lanbo
 *
 */
@ControllerAdvice
public class ApiExceptionHandler {

	/**
	 * Token验证异常处理
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(TokenValidationException.class)
	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	@ResponseBody
	public ErrorResponse handleTokenValidationException(TokenValidationException ex) {
		
		return new ErrorResponse("002", ex.getMessage());
	}
	
	/**
	 * 业务异常处理
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(BusinessErrorException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorResponse handleBusinessErrorException(BusinessErrorException ex) {
		
		return new ErrorResponse(ex.getCode(), ex.getMessage());
	}

	@ExceptionHandler(TypeMismatchException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public String handleTypeMismatchException(TypeMismatchException ex) {
		return "Request id must be an integer";
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorResponse handleMethodArgumentNotValidException(
			MethodArgumentNotValidException ex) {
		BindingResult bindingResult = ex.getBindingResult();
		String errorMesssage = "";

		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			errorMesssage += fieldError.getDefaultMessage()+"<br>";
		}
		return new ErrorResponse("001", errorMesssage);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorResponse handleHttpMessageNotReadableException(
			HttpMessageNotReadableException ex) {
		return new ErrorResponse("008", "请输入正确的格式");
	}

	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ErrorResponse handleUnexpectedServerError(RuntimeException ex) {
		ex.printStackTrace();
		return new ErrorResponse("012", ex.getMessage());
	}
}
