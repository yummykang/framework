package ${groupId}.exception;

/**
 * 业务异常错误
 * 
 * @author lanbo
 *
 */
public class BusinessErrorException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8014165495101227361L;

	private String code;

	private String message;
	
	public BusinessErrorException(String code) {
		this.code = code;
	}

	public BusinessErrorException(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
