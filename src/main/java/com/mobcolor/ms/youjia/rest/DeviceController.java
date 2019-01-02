package com.mobcolor.ms.youjia.rest;

import com.mobcolor.ms.youjia.model.DeviceModel;
import com.mobcolor.ms.youjia.model.dto.DeviceDTO;
import com.mobcolor.ms.youjia.service.DeviceService;
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
@RequestMapping(value = "/device")
public class DeviceController extends BaseRestController {
    private static final ZKLogger logger = ZKLoggerFactory.getLogger(DeviceController.class);

    @Resource
    private DeviceService deviceService;


    /**
     * @api {post} /device/report-device [设备管理]上报设备
     * @apiSampleRequest /device/report-device
     * @apiName report-device
     * @apiGroup /device
     * @apiDescription [设备管理]上报设备
     * @apiParam {String} name 编号
     * @apiParam {String} deviceNumber 设备编号（唯一）
     * @apiParam {String} tsNumber 触动编号（唯一）
     * @apiParam {String} devcieModel 设备型号
     * @apiParam {String} systemVersion 系统版本号
     * @apiParam {String} [deviceGroupId]设备组id
     * @apiParam {String} [otherInfo] 其他信息
     * @apiParamExample {json} 参数示例:
     * {
     *  'name':'6A-011',
     *  'deviceNumber' ：'124edw344322',
     *  'tsNumber':'123edsw2',
     *  'devcieModel':'MI 6Plus',
     *  'systemVersion':'android 7'
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
    @RequestMapping(value = "/report-device", method = RequestMethod.POST)
    public ServerResponse reportDevice(DeviceModel deviceModel) {
        logger.info("report-device:{}",deviceModel);
        ServerResponse response = new ServerResponse();
        try {
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
            deviceService.reportDevice(deviceModel);
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }

    /**
     * @api {GET} /device/query-device-by-page [设备管理]分页查询设备
     * @apiSampleRequest /device/query-device-by-page
     * @apiName query-device-by-page
     * @apiGroup /device
     * @apiDescription [设备管理]分页查询设备
     * @apiParam {String} [name] name模糊查询
     * @apiParam {Number} to_page  当前页
     * @apiParam {Number} page_size  每页记录数
     * @apiParamExample {json} 参数示例:
     * {
     *  to_page:1,
     *  page_size:10
     * }
     * @apiSuccess {String} id 设备id
     * @apiSuccess {String} name 编号
     * @apiSuccess {String} deviceNumber 设备编号（唯一）
     * @apiSuccess {String} tsNumber 触动编号（唯一）
     * @apiSuccess {String} devcieModel 设备型号
     * @apiSuccess {String} systemVersion 系统版本号
     * @apiSuccess {String} otherInfo 其他信息
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
     *      'deviceNumber' ：'124edw344322',
     *      'tsNumber':'123edsw2',
     *      'devcieModel':'MI 6Plus',
     *      'systemVersion':'android 7'
     *      'createTime':‘206-0-08  12:35:32’
     *      }
     *  }
     * }
     */
    @RequestMapping(value = "/query-device-by-page", method = RequestMethod.GET)
    public ServerResponse queryDeviceByPage(DeviceDTO deviceDTO) {
        ServerResponse response = new ServerResponse();
        try {
            PageVO<DeviceModel> deviceModelPageVO = deviceService.queryDeviceByPage(deviceDTO);
            response.setCode(ServerResponse.SUCCESS);
            response.setData(deviceModelPageVO);
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }



    /**
     * @api {GET} /device/load-device [设备管理]加载一条设备
     * @apiSampleRequest /device/load-device
     * @apiName load-device
     * @apiGroup /device
     * @apiDescription [设备管理]加载一条设备
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
    @RequestMapping(value = "/load-device", method = RequestMethod.GET)
    public ServerResponse loadDeviceModel(String id) {
        ServerResponse response = new ServerResponse();
        try {
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
            response.setData(deviceService.loadDeviceModel(id));
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }


    /**
     * @api {GET} /device/load-device-number [设备管理]加载一条设备-设备编码
     * @apiSampleRequest /device/load-device-number
     * @apiName load-device-number
     * @apiGroup /device
     * @apiDescription [设备管理]加载一条设备-设备编码
     * @apiParam {String} deviceNumber  设备编码
     * @apiParamExample {json} 参数示例:
     * {
     *  'deviceNumber':'xxx2dsd'
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
    @RequestMapping(value = "/load-device-number", method = RequestMethod.GET)
    public ServerResponse loadDeviceModelByDeviceNumber(String deviceNumber) {
        ServerResponse response = new ServerResponse();
        try {
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
            response.setData(deviceService.loadDeviceModelByDeviceNumber(deviceNumber));
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }
    /**
     * @api {POST} /device/update-device [设备管理]修改一条设备
     * @apiSampleRequest /device/update-device
     * @apiName update-device
     * @apiGroup /device
     * @apiDescription [设备管理]修改一条设备
     * @apiParam {String} id  id
     * @apiParam {String} [deviceGroupId]  设备组id
     * @apiParam {String} [otherInfo]  其他信息
     * @apiParamExample {json} 参数示例:
     * {
     *  'id':'xxx2dsd',
     *  'deviceGroupId':'123exa',
     *  'otherInfo':'{ssssssssss}'
     *
     * }
     * @apiSuccessExample {json} 返回示例:
     * HTTP/1.1 200 OK
     * {
     *  "code": 0,
     *  "msg": null,
     *  "data": {
     *      1
     *  }
     * }
     */
    @RequestMapping(value = "/update-device", method = RequestMethod.POST)
    public ServerResponse updateDevice(DeviceModel deviceModel) {
        ServerResponse response = new ServerResponse();
        try {
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
            response.setData(deviceService.updateDevice(deviceModel));
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }

}
