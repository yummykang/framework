package ${groupId}.bean.business;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/5/26 15:03
 */
public class AdminUserBean {

    @ApiModelProperty(value = "登录名", required = true)
    @NotBlank(message = "登录名不能为空")
    private String userName;

    @ApiModelProperty(value = "登录密码", required = true)
    @NotBlank(message = "登录密码不能为空")
    private String userPwd;

    @ApiModelProperty(value = "使用者姓名", required = true)
    @NotBlank(message = "使用者姓名不能为空")
    private String userTruename;

    @ApiModelProperty(value = "联系电话", required = true)
    @NotBlank(message = "联系电话不能为空")
    @Pattern(regexp = "0?(13|14|15|18)[0-9]{9}", message = "联系电话格式不正确")
    private String userPhone;

    @ApiModelProperty(value = "用户角色，1为管理员，2为编辑，3为超级管理员", required = true)
    @Min(value = 1, message = "请输入正确的用户角色")
    @Max(value = 3, message = "请输入正确的用户角色")
    private int roleCode;

    @ApiModelProperty(value = "1 启用 0禁用", required = true)
    private int userStatus;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserTruename() {
        return userTruename;
    }

    public void setUserTruename(String userTruename) {
        this.userTruename = userTruename;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public int getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(int roleCode) {
        this.roleCode = roleCode;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }
}
