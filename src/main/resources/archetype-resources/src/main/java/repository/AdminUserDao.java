package ${groupId}.repository;

import cn.org.citycloud.entity.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 后台管理用户dao接口.
 *
 * @author demon
 * @Date 2016/4/22 16:04
 */
public interface AdminUserDao extends JpaRepository<AdminUser, Integer>, JpaSpecificationExecutor<AdminUser> {
    public AdminUser findByUserNameAndUserPwd(String userName, String userPwd);
}
