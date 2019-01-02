package com.mobcolor.ms.user.rest;

import com.mobcolor.ms.user.model.CrmUserModel;
import com.mobcolor.ms.user.model.dto.CrmUserDTO;
import com.mobcolor.ms.user.service.CrmUserService;
import com.mobcolor.framework.common.BaseRestController;
import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.common.ServerResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 黄鸿 E-mail：hong.huang@jsxfedu.com
 * @version 1.0 创建时间： 2016/9/29.
 */
@RestController
@RequestMapping(value="/crm/user")
public class CrmUserRestController extends BaseRestController {
    @Resource
    private CrmUserService crmUserService;

    /**
     * @api {POST} /crm/user/login-validation [用户管理]用户登陆验证
     * @apiSampleRequest /crm/user/login-validation
     * @apiName login-validation
     * @apiGroup /crm/user
     * @apiDescription [用户管理]用户登陆验证
     * @apiParam  {String} [email]  验证邮箱
     * @apiParam  {String} [password]  验证密码.
     * @apiParamExample {json} 参数示例:
     *  {
     *      email:'hong.huang@jsxfedu.com',
     *      password:dfghjklhjk
     *      time：18000
     *  }
     *
     *  @apiSuccess {int} resultsCode 调用返回结果 0 1 2  成功/失败/未激活
     *  @apiSuccess {String} sid cookie
     *  @apiSuccessExample {json} 返回示例:
     *  HTTP/1.1 200 OK
     *  {
     *      code:0,
     *      msg:'',
     *      data:{
     *          {
     *              "state": 0,
     *              data：{...}
     *          }
     *      }
     *  }
     *
     */
    @RequestMapping(value="/login-validation", method= RequestMethod.POST)
    public ServerResponse loginValidation(String email, String password) {
        ServerResponse response=new ServerResponse();
        try{
           //验证用户合法
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
            response.setData(crmUserService.loginValidation(email,password));
        }catch (BusinessException e){
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }

    /**
     * @api {get} /crm/user/query-crm-user-by-page [用户管理]查询所有用户
     * @apiSampleRequest /crm/user/query-crm-user-by-page
     * @apiName query-crm-user-by-page
     * @apiGroup /crm/user
     * @apiDescription [用户管理]查询所有用户
     * @apiParam  {String} [email]  用户邮箱/
     * @apiParam  {String} [status]  .状态 ENABLE("启用"),DISABLE("停用");
     * @apiParam   {Number} [to_page]  当前页
     * @apiParam   {Number} [page_size]  每页记录数
     * @apiParamExample {json} 参数示例:
     *  {
     *      email:'hong.huang@jsxfedu.com',
     *      status:'ENABLE;
     *      to_page:1,
     *      page_size:10
     *  }
     *
     *  @apiSuccess {String} [email]  用户邮箱/
     *  @apiSuccess {String} [status]  状态（暂时不开放） 0正常 1待激活
     *  @apiSuccess {String} createTime 创建时间
     *  @apiSuccessExample {json} 返回示例:
     *  HTTP/1.1 200 OK
     *  {
     *   "code": 0,
     *   "msg": null,
     *   "data": {
     *   "start": 0,
     *   "pageSize": 2,
     *   "totalCount": 5,
     *   "totalPageCount": 3,
     *   "currentPageNo": 1,
     *   "hasNextPage": true,
     *   "hasPreviousPage": false,
     *      data:{
     *          {
     *               email:'hong.huang@jsxfedu.com',
     *               status:"0"
     *               createTime:"2016-07-02 12:35:32"
     *          }
     *      }
     *  }
     *
     */
    @RequestMapping(value="/query-crm-user-by-page", method= RequestMethod.GET)
    public ServerResponse queryCrmUserByPage(CrmUserDTO crmUserDTO) {
        ServerResponse response=new ServerResponse();
        try{
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
            response.setData(crmUserService.queryCrmUsetByPage(crmUserDTO));
        }catch (BusinessException e){
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }


    /**
     * @api {POST} /crm/user/open-account [用户管理]用户开通
     * @apiSampleRequest /crm/user/open-account
     * @apiName open-account
     * @apiGroup /crm/user
     * @apiDescription [用户管理]用户开通
     * @apiParam  {String} name  名字.
     * @apiParam  {String} email  验证邮箱.
     * @apiParam  {String} passWord 密码
     * @apiParamExample {json} 参数示例:
     *  {
     *      name:"黄鸿"
     *      email:'hong.huang@jsxfedu.com',
     *      passWord:'8888888',
     *  }
     *
     *  @apiSuccess {int} resultsCode 调用返回结果 0 1 2  成功/失败/邮件已经存在
     *  @apiSuccessExample {json} 返回示例:
     *  HTTP/1.1 200 OK
     *  {
     *      code:0,
     *      msg:'',
     *      data:{
     *          {
     *              "resultsCode": 0,
     *          }
     *      }
     *  }
     *
     */
    @RequestMapping(value="/open-account", method= RequestMethod.POST)
    public ServerResponse openAccount(String name,String email,String passWord) {
        ServerResponse response=new ServerResponse();
        try{
            response.setMsg("操作成功");
            crmUserService.openAccount(name,email,passWord);
            response.setCode(ServerResponse.SUCCESS);

        }catch (BusinessException e){
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }

    /**
     * @api {POST} /crm/user/reset-password [用户管理]更改密码
     * @apiSampleRequest /crm/user/reset-password
     * @apiName reset-password
     * @apiGroup /crm/user
     * @apiDescription [用户管理]更改密码
     * @apiParam  {String} [email]  邮箱
     * @apiParam  {String} [password_old]  原密码.
     * @apiParam  {String} [password_new]  新密码.
     * @apiParamExample {json} 参数示例:
     *  {
     *      email:'hong.huang@jsxfedu.com',
     *      password:"dfghjkl;"
     *      uuid:xxxxxxxxxxxxxxxxxxx
     *  }
     *
     *  @apiSuccessExample {json} 返回示例:
     *  HTTP/1.1 200 OK
     *  {
     *      code:0,
     *      msg:'',
     *      }
     *  }
     *
     */
    @RequestMapping(value="/reset-password", method= RequestMethod.POST)
    public ServerResponse resetPassword( String email,String password_old,String password_new) {
        ServerResponse response=new ServerResponse();
        try{
            response.setMsg("操作成功");
            crmUserService.resetPassword(email,password_old,password_new);
            response.setCode(ServerResponse.SUCCESS);
        }catch (BusinessException e){
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }

}
