package ${groupId}.controller;

import ${groupId}.bean.business.AdminUserBean;
import ${groupId}.bean.business.UserLoginBean;
import ${groupId}.bean.business.UserPwdBean;
import ${groupId}.bean.search.AdminUserSearch;
import ${groupId}.constants.Constants;
import ${groupId}.exception.BusinessErrorException;
import ${groupId}.service.AdminUserService;
import io.swagger.annotations.*;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.concurrent.TimeoutException;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/4/25 9:12
 */
@RestController
@Api(tags = "用户管理")
@RequestMapping("/user")
public class AdminUserController {

    @Autowired
    private AdminUserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @Transactional
    @ApiOperation(value = "用户登录", notes = "用户登录")
    public Object login(@ApiParam(value = "用户登录信息", required = true) @Valid @RequestBody UserLoginBean userLoginBean) throws InterruptedException, TimeoutException, BusinessErrorException, MemcachedException {
        return userService.login(userLoginBean);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ApiOperation(value = "退出登录", notes = "退出登录")
    @ApiImplicitParams(value={@ApiImplicitParam(name="token",value="token",required=false,dataType="string",paramType="header")})
    public void logout() throws InterruptedException, MemcachedException, TimeoutException {
        userService.logout(Constants.USER_TOKEN.getToken());
    }

    @RequestMapping(value = "/modifyUserPwd", method = RequestMethod.POST)
    @ApiOperation(value = "修改登录密码", notes = "修改登录密码")
    @ApiImplicitParams(value={@ApiImplicitParam(name="token",value="token",required=false,dataType="string",paramType="header")})
    public void changePwd(@ApiParam(value = "登录密码修改信息", required = true) @Valid @RequestBody UserPwdBean userPwdBean) throws BusinessErrorException {
        userService.changePwd(userPwdBean, Constants.USER_TOKEN.getUserId());
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiOperation(value="获取用户列表",notes="获取用户列表",consumes="application/json",produces="application/json")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name="pageNo",value="页数",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="pageSize",value="每页大小",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="roleCode",value="角色code",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="userTruename",value="帐号使用人姓名",required=false,dataType="string",paramType="query")
    })
    public Object getUserList(@ApiIgnore AdminUserSearch adminUserSearch) {
        return userService.getUserList(adminUserSearch);
    }

    @RequestMapping(value="/{userId}",method=RequestMethod.GET)
    @ApiOperation(value="获取用户详情",notes="获取用户详情",consumes="application/json",produces="application/json",httpMethod="GET")
    public Object getUserDetail(@ApiParam(name="userId",value="用户Id",required=true) @PathVariable int userId) {
        return userService.getUserDetail(userId);
    }

    @RequestMapping(value="/",method=RequestMethod.POST)
    @ApiOperation(value="添加用户",notes="添加用户",consumes="application/json",produces="application/json")
    public Object addUser(@RequestBody @Valid AdminUserBean adminUserBean) {
        return userService.addUser(adminUserBean);
    }

    @RequestMapping(value="/{userId}",method=RequestMethod.PUT)
    @ApiOperation(value="修改用户",notes="修改用户",consumes="application/json",produces="application/json",httpMethod="PUT")
    public Object updateUser(@ApiParam(name="userId",value="用户Id",required=true) @PathVariable int userId, @RequestBody @Valid AdminUserBean adminUserBean) {
        return userService.updateUser(userId, adminUserBean);
    }
}
