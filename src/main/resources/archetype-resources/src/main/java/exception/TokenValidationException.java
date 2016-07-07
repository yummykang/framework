package ${groupId}.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 自定义Token验证异常
 * @author lanbo
 *
 */
@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Token Validation Error")
public class TokenValidationException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3632733822096920706L;

	public TokenValidationException(String message) {
		super(message);
	}
}
