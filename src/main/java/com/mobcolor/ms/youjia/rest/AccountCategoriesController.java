package com.mobcolor.ms.youjia.rest;


import com.mobcolor.ms.youjia.model.AccountCategoriesModel;
import com.mobcolor.ms.youjia.model.dto.AccountCategoriesDTO;
import com.mobcolor.ms.youjia.service.AccountCategoriesService;
import com.mobcolor.framework.common.BaseRestController;
import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.common.ServerResponse;
import com.mobcolor.framework.utils.ZKLogger;
import com.mobcolor.framework.utils.ZKLoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2017/12/19
 */
@RestController
@RequestMapping(value = "/account-categories")
public class AccountCategoriesController extends BaseRestController {
    private static final ZKLogger logger = ZKLoggerFactory.getLogger(AccountCategoriesController.class);

    @Resource
    private AccountCategoriesService accountCategoriesService;

    /**
     * @api {post} /account-categories/add-account-categories [账号类别]添加一个账号类别
     * @apiSampleRequest /account-categories/add-account-categories
     * @apiName add-account-categories
     * @apiGroup /account-categories
     * @apiDescription [账号类别]添加一个账号类别
     * @apiParam {String} name 类别名称
     * @apiParamExample {json} 参数示例:
     * {
     *  name:'网易应用'，
     * }
     * @apiSuccessExample {json} 返回示例:
     * <p/>
     * HTTP/1.1 200 OK
     * {
     * code:0,
     * msg:'操作成功',
     * data:{
     * {}
     * }
     * }
     */
    @RequestMapping(value = "/add-account-categories", method = RequestMethod.POST)
    public ServerResponse addAccountCategories(AccountCategoriesModel accountCategoriesModel) {
        ServerResponse response = new ServerResponse();
        try {
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
            accountCategoriesService.addAccountCategories(accountCategoriesModel);
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }

    /**
     * @api {GET} /account-categories/query-account-categories-by-page [账号类别]分页查询账号类别
     * @apiSampleRequest /account-categories/query-account-categories-by-page
     * @apiName query-account-categories-by-page
     * @apiGroup /account-categories
     * @apiDescription [账号类别]分页查询账号类别
     * @apiParam {String} [name] 名称
     * @apiParam {Number} to_page  当前页
     * @apiParam {Number} page_size  每页记录数
     * @apiParamExample {json} 参数示例:
     * {
     *  name:'爱奇艺',
     *  to_page:1,
     *  page_size:10
     * }
     * @apiSuccess {String} id 渠道id
     * @apiSuccess {String} name 类别名称
     * @apiSuccess {String} createrTime 创建时间
     * @apiSuccessExample {json} 返回示例:
     * HTTP/1.1 200 OK
     * {
     *  "code": 0,
     *  "msg": null,
     *  "data": {
     *      'id':'82839383',
     *      'name':'爱奇艺',
     *      'createTime':‘206-0-08  12:35:32’
     *  }
     * }
     */
    @RequestMapping(value = "/query-account-categories-by-page", method = RequestMethod.GET)
    public ServerResponse queryAccountCategoriesByPage(AccountCategoriesDTO accountCategoriesDTO) {
        ServerResponse response = new ServerResponse();
        try {
            response.setMsg("操作成功");
            response.setCode(ServerResponse.SUCCESS);
            response.setData(accountCategoriesService.queryAccountCategoriesByPage(accountCategoriesDTO));
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }

    /**
     * @api {post} /account-categories/update-account-categories [账号类别]修改一个应用
     * @apiSampleRequest /account-categories/update-account-categories
     * @apiName update-account-categories
     * @apiGroup /account-categories
     * @apiDescription [账号类别]修改一个应用
     * @apiParam {String} id id主键
     * @apiParam {String} [name] 应用名称
     * @apiParamExample {json} 参数示例:
     * {
     *  id:'12376899'，
     *  'name' ：'网易应用_修改后名字'
     * }
     * @apiSuccessExample {json} 返回示例:
     * <p/>
     * HTTP/1.1 200 OK
     * {
     * code:0,
     * msg:'操作成功',
     * data:{
     * {
     *     1
     * }
     * }
     * }
     */
    @RequestMapping(value = "/update-account-categories", method = RequestMethod.POST)
    public ServerResponse updateAccountCategories(String id,String name) {
        ServerResponse response = new ServerResponse();
        try {
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
            response.setData(accountCategoriesService.updateAccountCategories(id,name));
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }


    /**
     * @api {GET} /account-categories/select-account-categories [账号类别]查询账号类别
     * @apiSampleRequest /account-categories/select-account-categories
     * @apiName select-account-categories
     * @apiGroup /account-categories
     * @apiDescription [账号类别]查询账号类别
     * @apiParamExample {json} 参数示例:
     * {
     * }
     * @apiSuccess {String} id 渠道id
     * @apiSuccess {String} name  资源名称
     * @apiSuccess {String} createrTime  创建时间
     * @apiSuccessExample {json} 返回示例:
     * HTTP/1.1 200 OK
     * {
     *  "code": 0,
     *  "msg": null,
     *  "data": {
     *      'id':'82839383',
     *      'name':'爱奇艺',
     *      'createTime':‘206-0-08  12:35:32’
     *  }
     * }
     */
    @RequestMapping(value = "/select-account-categories", method = RequestMethod.GET)
    public ServerResponse selectAccountCategoriess() {
        ServerResponse response = new ServerResponse();
        try {
            response.setMsg("操作成功");
            response.setCode(ServerResponse.SUCCESS);
            response.setData(accountCategoriesService.selectAccountCategoriess( new AccountCategoriesDTO()));
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }


    /**
     * @api {GET} /account-categories/load-account-categories [账号类别]加载一条数据
     * @apiSampleRequest /account-categories/load-account-categories
     * @apiName load-account-categories
     * @apiGroup /account-categories
     * @apiDescription [账号类别]加载一条数据
     * @apiParam {String} id id主键
     * @apiParamExample {json} 参数示例:
     * {
     *  'id':'1233xxs1'
     * }
     * @apiSuccess {String} id 渠道id
     * @apiSuccess {String} name  资源名称
     * @apiSuccess {String} createrTime  创建时间
     * @apiSuccessExample {json} 返回示例:
     * HTTP/1.1 200 OK
     * {
     *  "code": 0,
     *  "msg": null,
     *  "data": {
     *      'id':'82839383',
     *      'name':'爱奇艺',
     *      'createTime':‘206-0-08  12:35:32’
     *  }
     * }
     */
    @RequestMapping(value = "/load-account-categories", method = RequestMethod.GET)
    public ServerResponse loadAccountCategories(String id) {
        ServerResponse response = new ServerResponse();
        try {
            response.setMsg("操作成功");
            response.setCode(ServerResponse.SUCCESS);
            response.setData(accountCategoriesService.loadAccountCategories(id));
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }
}
