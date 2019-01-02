package com.mobcolor.ms.youjia.rest;


import com.mobcolor.ms.youjia.model.DeviceGroupModel;
import com.mobcolor.ms.youjia.model.dto.DeviceGroupDTO;
import com.mobcolor.ms.youjia.service.DeviceGroupService;
import com.mobcolor.framework.common.BaseRestController;
import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.common.PageVO;
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
@RequestMapping(value = "/device-group")
public class DeviceGroupController extends BaseRestController {
    private static final ZKLogger logger = ZKLoggerFactory.getLogger(DeviceGroupController.class);

    @Resource
    private DeviceGroupService deviceGroupService;


    /**
     * @api {post} /device-group/add-device-group [设备组管理]添加设备组
     * @apiSampleRequest /device-group/add-device-group
     * @apiName add-device-group
     * @apiGroup /device-group
     * @apiDescription [设备组管理]添加设备组
     * @apiParam {String} name 名称
     * @apiParam {String} [remarks] 备注
     * @apiParam {String} [execGroup] 执行组，多个','号分隔
     * @apiParamExample {json} 参数示例:
     * {
     *  'name':'6A-011',
     *  'remarks' ：'这是一个备注',
     *  'execGroup':'执行组A'
     * }
     * @apiSuccessExample {json} 返回示例:
     * <p/>
     * HTTP/1.1 200 OK
     * {
     * code:0,
     * msg:'操作成功',
     * data:{
     *
     * }
     * }
     */
    @RequestMapping(value = "/add-device-group", method = RequestMethod.POST)
    public ServerResponse addDeviceGroup(DeviceGroupModel deviceGroupModel) {
        ServerResponse response = new ServerResponse();
        try {
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
            deviceGroupService.addDeviceGroup(deviceGroupModel);
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }

    /**
     * @api {GET} /device-group/query-device-group-by-page [设备组管理]分页查询设备组
     * @apiSampleRequest /device-group/query-device-group-by-page
     * @apiName query-device-group-by-page
     * @apiGroup /device-group
     * @apiDescription [设备组管理]分页查询设备组
     * @apiParam {String} [name] name模糊查询
     * @apiParam {Number} to_page  当前页
     * @apiParam {Number} page_size  每页记录数
     * @apiParamExample {json} 参数示例:
     * {
     *  to_page:1,
     *  page_size:10
     * }
     * @apiSuccess {String} id 设备id
     * @apiSuccess {String} name 名称
     * @apiSuccess {String} remake 备注
     * @apiSuccess {String} createrTime  创建时间
     * @apiSuccessExample {json} 返回示例:
     * HTTP/1.1 200 OK
     * {
     *  "code": 0,
     *  "msg": null,
     *  "data": {[
     *      {
     *      'id':'82839383',
     *      'name':'6A-011',
     *      'remake' ：'124edw344322',
     *      'tsNumber':'123edsw2',
     *      'createTime':‘206-0-08  12:35:32’
     *      }
     *  }
     * }
     */
    @RequestMapping(value = "/query-device-group-by-page", method = RequestMethod.GET)
    public ServerResponse queryDeviceByPage(DeviceGroupDTO deviceGroupDTO) {
        ServerResponse response = new ServerResponse();
        try {
            PageVO<DeviceGroupModel> deviceGroupModelPageVO = deviceGroupService.queryDeviceGroupByPage(deviceGroupDTO);
            response.setCode(ServerResponse.SUCCESS);
            response.setData(deviceGroupModelPageVO);
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }



    /**
     * @api {post} /device-group/update-device-group [设备组管理]修改设备组
     * @apiSampleRequest /device-group/update-device-group
     * @apiName update-device-group
     * @apiGroup /device-group
     * @apiDescription [设备组管理]修改设备组
     * @apiParam {String} id 主键id
     * @apiParam {String} [name] 名称
     * @apiParam {String} [remarks] 备注
     * @apiParam {String} [execGroup] 执行组
     * @apiParamExample {json} 参数示例:
     * {
     *  'name':'6A-011',
     *  'remarks' ：'这是一个备注',
     *  'execGroup':'执行组A'
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
    @RequestMapping(value = "/update-device-group", method = RequestMethod.POST)
    public ServerResponse updateDeviceGroup(DeviceGroupModel deviceGroupModel) {
        ServerResponse response = new ServerResponse();
        try {
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
            deviceGroupService.updateDeviceGroup(deviceGroupModel);
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }

    /**
     * @api {GET} /device-group/load-device-group [设备组管理]加载一条设备组
     * @apiSampleRequest /device-group/load-device-group
     * @apiName load-device-group
     * @apiGroup /device-group
     * @apiDescription [设备组管理]加载一条设备组
     * @apiParam {String} id  id
     * @apiParamExample {json} 参数示例:
     * {
     *  'id':'xxx2dsd'
     * }
     * @apiSuccess {String} id 设备id
     * @apiSuccess {String} name 名称
     * @apiSuccess {String} remake 备注
     * @apiSuccess {String} createrTime  创建时间
     * @apiSuccess {String} execGroup  执行组
     * @apiSuccessExample {json} 返回示例:
     * HTTP/1.1 200 OK
     * {
     *  "code": 0,
     *  "msg": null,
     *  "data": {
     *
     *      'id':'82839383',
     *      'name':'6A-011',
     *      'remake' ：'124edw344322',
     *      'tsNumber':'123edsw2',
     *      'createTime':‘206-0-08  12:35:32’
     *
     *  }
     * }
     */
    @RequestMapping(value = "/load-device-group", method = RequestMethod.GET)
    public ServerResponse loadDeviceGroup(String id) {
        ServerResponse response = new ServerResponse();
        try {
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
            response.setData(deviceGroupService.loadDeviceGroup(id));
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }


    /**
     * @api {POST} /device-group/select-device-groups [设备组管理]查询设备组
     * @apiSampleRequest /device-group/select-device-groups
     * @apiName select-device-groups
     * @apiGroup /device-group
     * @apiDescription [设备组管理]查询设备组
     * @apiParamExample {json} 参数示例:
     * {
     * }
     * @apiSuccess {String} id 设备id
     * @apiSuccess {String} name 名称
     * @apiSuccess {String} remake 备注
     * @apiSuccess {String} createrTime  创建时间
     * @apiSuccessExample {json} 返回示例:
     * HTTP/1.1 200 OK
     * {
     *  "code": 0,
     *  "msg": null,
     *  "data": {[
     *      {
     *      'id':'82839383',
     *      'name':'6A-011',
     *      'remake' ：'124edw344322',
     *      'tsNumber':'123edsw2',
     *      'createTime':‘206-0-08  12:35:32’
     *      }
     *  }
     * }
     */
    @RequestMapping(value = "/select-device-groups", method = RequestMethod.POST)
    public ServerResponse selectDeviceGroups(DeviceGroupDTO deviceGroupDTO) {
        ServerResponse response = new ServerResponse();
        try {
            response.setCode(ServerResponse.SUCCESS);
            response.setData(deviceGroupService.selectDeviceGroups(deviceGroupDTO));
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }
}
