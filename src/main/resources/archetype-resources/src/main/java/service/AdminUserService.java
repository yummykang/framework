package ${groupId}.service;

import cn.org.citycloud.bean.base.UserToken;
import cn.org.citycloud.bean.business.AdminUserBean;
import cn.org.citycloud.bean.business.UserLoginBean;
import cn.org.citycloud.bean.business.UserPwdBean;
import cn.org.citycloud.bean.search.AdminUserSearch;
import cn.org.citycloud.constants.Constants;
import cn.org.citycloud.constants.ErrorCodes;
import cn.org.citycloud.entity.AdminUser;
import cn.org.citycloud.exception.BusinessErrorException;
import cn.org.citycloud.repository.AdminUserDao;
import cn.org.citycloud.utils.PageUtils;
import cn.org.citycloud.utils.jpa.JpaHelper;
import com.auth0.jwt.JWTSigner;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * 后台管理用户service实现.
 *
 * @author demon
 * @Date 2016/4/22 16:26
 */
@Service
public class AdminUserService {

    @Autowired
    private AdminUserDao adminUserDao;

    @Autowired
    private MemcachedClient cacheClient;

    /**
     * 用户登录
     *
     * @param userLogin
     * @return
     * @throws BusinessErrorException
     * @throws InterruptedException
     * @throws MemcachedException
     * @throws TimeoutException
     */
    public Object login(UserLoginBean userLogin) throws BusinessErrorException, InterruptedException, MemcachedException, TimeoutException {
        AdminUser user = adminUserDao.findByUserNameAndUserPwd(userLogin.getUserName(), userLogin.getUserPwd());
        if (user == null) {
            throw new BusinessErrorException(ErrorCodes.NON_EXIST_MEMBER, "账号或者密码不正确");
        }
        if (Constants.MEMBER_STATE_CLOSED == user.getUserStatus()) {
            throw new BusinessErrorException(ErrorCodes.WRONG_MEMBER, "此会员已经被禁用！");
        }
        String token = generateToken(user.getUserId(), Constants.TOKEN_SECRET);
        UserToken userToken = new UserToken();
        userToken.setToken(token);
        userToken.setUserId(user.getUserId());
        userToken.setUserName(user.getUserName());
        userToken.setExpiresIn(Constants.TOKEN_EXPIRES_IN);
        userToken.setCreateTs(System.currentTimeMillis());
        userToken.setRoleCode(user.getRoleCode());
        cacheClient.set(token, (int) Constants.TOKEN_EXPIRES_IN, userToken);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("token", token);
        resultMap.put("userId", user.getUserId());
        resultMap.put("userName", user.getUserName());
        resultMap.put("roleCode", user.getRoleCode());
        return resultMap;
    }

    /**
     * 注销
     *
     * @param token
     * @throws InterruptedException
     * @throws MemcachedException
     * @throws TimeoutException
     */
    public void logout(String token) throws InterruptedException, MemcachedException, TimeoutException {
        cacheClient.delete(token);
    }

    /**
     * 修改密码
     *
     * @param userPwdBean
     * @param userId
     * @throws BusinessErrorException
     */
    public void changePwd(UserPwdBean userPwdBean, int userId) throws BusinessErrorException {
        AdminUser userInfo = adminUserDao.findOne(userId);
        if (userInfo == null) {
            throw new BusinessErrorException(ErrorCodes.NON_EXIST_MEMBER, "用户账号不存在");
        }
        if (!userPwdBean.getUserPwd().equals(userPwdBean.getConfirmPwd())) {
            throw new BusinessErrorException(ErrorCodes.PARAM_ERROR, "两次输入的密码不一致，请重新输入");
        }

        if (!userPwdBean.getOldUserPwd().equals(userInfo.getUserPwd())) {
            throw new BusinessErrorException(ErrorCodes.PARAM_ERROR, "原始密码输入错误，请重新输入");
        }
        userInfo.setUserPwd(userPwdBean.getUserPwd());
        userInfo.setUpdDate(new Date());
        adminUserDao.save(userInfo);
    }

    /**
     * 查询用户列表
     *
     * @param adminUserSearch
     * @return
     */
    public Object getUserList(AdminUserSearch adminUserSearch) {
        Sort sort = new Sort(Sort.Direction.DESC, "insDate");
        JpaHelper<AdminUser> helper = new JpaHelper<>();
        Specification<AdminUser> spec = Specifications.where(helper.eq("roleCode", adminUserSearch.getRoleCode())).
                and(helper.like("userTruename", adminUserSearch.getUserTruename()));
        return adminUserDao.findAll(spec, PageUtils.getPage(adminUserSearch, sort));
    }

    /**
     * 查询用户详情
     *
     * @param userId
     * @return
     */
    public Object getUserDetail(int userId) {
        return adminUserDao.findOne(userId);
    }

    /**
     * 新建用户
     *
     * @param adminUserBean
     * @return
     */
    public Object addUser(AdminUserBean adminUserBean) {
        AdminUser adminUser = new AdminUser();
        BeanUtils.copyProperties(adminUserBean, adminUser);
        adminUser.setInsDate(new Date());
        adminUser.setUpdDate(new Date());
        adminUserDao.save(adminUser);
        return adminUser;
    }

    /**
     * 修改用户
     *
     * @param userId
     * @param adminUserBean
     * @return
     */
    public Object updateUser(int userId, AdminUserBean adminUserBean) {
        AdminUser adminUser = adminUserDao.findOne(userId);
        BeanUtils.copyProperties(adminUserBean, adminUser);
        adminUser.setUpdDate(new Date());
        adminUserDao.save(adminUser);
        return adminUser;
    }

    /**
     * 生成token值
     *
     * @param userId
     * @param secret
     * @return
     */
    private String generateToken(int userId, String secret) {
        JWTSigner jwtSigner = new JWTSigner(secret);
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("crtime", System.currentTimeMillis());
        String token = jwtSigner.sign(claims);
        return token;
    }
}
