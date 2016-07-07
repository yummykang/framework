package ${groupId}.bean.search;

import ${groupId}.bean.base.Page;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/5/26 14:51
 */
public class AdminUserSearch extends Page {

    private String userTruename;

    private int roleCode = -1;

    public String getUserTruename() {
        return userTruename;
    }

    public void setUserTruename(String userTruename) {
        this.userTruename = userTruename;
    }

    public int getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(int roleCode) {
        this.roleCode = roleCode;
    }
}
