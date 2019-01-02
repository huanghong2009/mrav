package com.mobcolor.ms.youjia.rest;


import com.mobcolor.framework.common.BaseRestController;
import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.common.ServerResponse;
import com.mobcolor.framework.utils.ZKLogger;
import com.mobcolor.framework.utils.ZKLoggerFactory;
import com.mobcolor.ms.youjia.model.VpnTypeModel;
import com.mobcolor.ms.youjia.model.dto.VpnTypeDTO;
import com.mobcolor.ms.youjia.service.VpnTypeService;
import com.mobcolor.ms.youjia.service.VpnTypeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2017/12/19
 */
@RestController
@RequestMapping(value = "/vpn-type")
public class VpnTypeController extends BaseRestController {
    private static final ZKLogger logger = ZKLoggerFactory.getLogger(VpnTypeController.class);

    @Resource
    private VpnTypeService vpnTypeService;

    /**
     * @api {post} /vpn-PatchClicksType/add-vpn-PatchClicksType [vpn类别]添加一个vpn类别
     * @apiSampleRequest /vpn-PatchClicksType/add-vpn-PatchClicksType
     * @apiName add-vpn-PatchClicksType
     * @apiGroup /vpn-PatchClicksType
     * @apiDescription [vpn类别]添加一个vpn类别
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
    @RequestMapping(value = "/add-vpn-type", method = RequestMethod.POST)
    public ServerResponse addVpnType(VpnTypeModel accountCategoriesModel) {
        ServerResponse response = new ServerResponse();
        try {
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
            vpnTypeService.addVpnType(accountCategoriesModel);
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }

    /**
     * @api {GET} /vpn-PatchClicksType/query-vpn-PatchClicksType-by-page [vpn类别]分页查询vpn类别
     * @apiSampleRequest /vpn-PatchClicksType/query-vpn-PatchClicksType-by-page
     * @apiName query-vpn-PatchClicksType-by-page
     * @apiGroup /vpn-PatchClicksType
     * @apiDescription [vpn类别]分页查询vpn类别
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
    @RequestMapping(value = "/query-vpn-type-by-page", method = RequestMethod.GET)
    public ServerResponse queryVpnTypeByPage(VpnTypeDTO accountCategoriesDTO) {
        ServerResponse response = new ServerResponse();
        try {
            response.setMsg("操作成功");
            response.setCode(ServerResponse.SUCCESS);
            response.setData(vpnTypeService.queryVpnTypeByPage(accountCategoriesDTO));
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }

    /**
     * @api {post} /vpn-PatchClicksType/update-vpn-PatchClicksType [vpn类别]修改一个应用
     * @apiSampleRequest /vpn-PatchClicksType/update-vpn-PatchClicksType
     * @apiName update-vpn-PatchClicksType
     * @apiGroup /vpn-PatchClicksType
     * @apiDescription [vpn类别]修改一个应用
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
    @RequestMapping(value = "/update-vpn-type", method = RequestMethod.POST)
    public ServerResponse updateVpnType(String id,String name) {
        ServerResponse response = new ServerResponse();
        try {
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
            response.setData(vpnTypeService.updateVpnType(id,name));
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }


    /**
     * @api {GET} /vpn-PatchClicksType/select-vpn-PatchClicksType [vpn类别]查询vpn类别
     * @apiSampleRequest /vpn-PatchClicksType/select-vpn-PatchClicksType
     * @apiName select-vpn-PatchClicksType
     * @apiGroup /vpn-PatchClicksType
     * @apiDescription [vpn类别]查询vpn类别
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
    @RequestMapping(value = "/select-vpn-type", method = RequestMethod.GET)
    public ServerResponse selectVpnTypes() {
        ServerResponse response = new ServerResponse();
        try {
            response.setMsg("操作成功");
            response.setCode(ServerResponse.SUCCESS);
            response.setData(vpnTypeService.selectVpnTypes( new VpnTypeDTO()));
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }


    /**
     * @api {GET} /vpn-PatchClicksType/load-vpn-PatchClicksType [vpn类别]加载一条数据
     * @apiSampleRequest /vpn-PatchClicksType/load-vpn-PatchClicksType
     * @apiName load-vpn-PatchClicksType
     * @apiGroup /vpn-PatchClicksType
     * @apiDescription [vpn类别]加载一条数据
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
    @RequestMapping(value = "/load-vpn-type", method = RequestMethod.GET)
    public ServerResponse loadVpnType(String id) {
        ServerResponse response = new ServerResponse();
        try {
            response.setMsg("操作成功");
            response.setCode(ServerResponse.SUCCESS);
            response.setData(vpnTypeService.loadVpnType(id));
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }
}
