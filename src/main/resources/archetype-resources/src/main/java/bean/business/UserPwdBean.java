package ${groupId}.bean.business;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/4/22 16:18
 */
public class UserPwdBean {
    @NotBlank(message = "原密码不能为空")
    @ApiModelProperty(value="原密码", required=true)
    private String oldUserPwd;

    @NotBlank(message = "新的登录密码不能为空")
    @ApiModelProperty(value="新的登录密码", required=true)
    @Pattern(regexp = "[A-Za-z0-9]{6,12}", message = "请输入6~12位数字或英文字母")
    private String userPwd;

    @NotBlank(message = "再次输入的密码不能为空")
    @ApiModelProperty(value="确认密码", required=true)
    @Pattern(regexp = "[A-Za-z0-9]{6,12}", message = "请输入6~12位数字或英文字母")
    private String confirmPwd;

    public String getOldUserPwd() {
        return oldUserPwd;
    }

    public void setOldUserPwd(String oldUserPwd) {
        this.oldUserPwd = oldUserPwd;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getConfirmPwd() {
        return confirmPwd;
    }

    public void setConfirmPwd(String confirmPwd) {
        this.confirmPwd = confirmPwd;
    }
}
