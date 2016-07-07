package ${groupId}.common;

import ${groupId}.bean.base.UserToken;

/**
 * 控制器基类
 * 
 * @author lanbo
 *
 */
public class BaseController {
	
	private String token;
	
	private UserToken userToken;
	
	private int userId;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public UserToken getUserToken() {
		return userToken;
	}

	public void setUserToken(UserToken userToken) {
		this.userToken = userToken;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}
