package ${groupId}.constants;

import ${groupId}.bean.base.UserToken;

/**
 * 常量类.
 *
 * @author demon
 * @Date 2016/5/26 14:16
 */
public class Constants {
    /** token密钥 */
    public static final String TOKEN_SECRET = "IFFa52XkBEQ9AoO8";

    /** token有效时间 */
    public static final long TOKEN_EXPIRES_IN = 24*3600;

    /** 当前用户的token */
    public static UserToken USER_TOKEN = null;

    public static String DEFAULT_PWD = "888888";

    /************************* 用户状态 *************************/
    /** 启用 */
    public static final int MEMBER_STATE_OPEN = 1;

    /** 禁用 */
    public static final int MEMBER_STATE_CLOSED = 0;
}
