package com.mobcolor.ms.youjia.rest;

import com.mobcolor.framework.common.BaseRestController;
import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.common.ServerResponse;
import com.mobcolor.framework.utils.BaseUtils;
import com.mobcolor.framework.utils.UUIDUtils;
import com.mobcolor.framework.utils.ZKLogger;
import com.mobcolor.framework.utils.ZKLoggerFactory;
import com.mobcolor.ms.youjia.model.VpnByDeviceModel;
import com.mobcolor.ms.youjia.model.VpnModel;
import com.mobcolor.ms.youjia.model.dto.VpnDTO;
import com.mobcolor.ms.youjia.service.VpnService;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2017/12/19
 */
@RestController
@RequestMapping(value = "/vpn")
public class VpnController extends BaseRestController {
    private static final ZKLogger logger = ZKLoggerFactory.getLogger(VpnController.class);

    @Resource
    private VpnService vpnService;
    
    
    /**
     * @api {post} /vpn/upload-vpn [vpn管理]导入vpn
     * @apiSampleRequest /vpn/upload-vpn
     * @apiName upload-vpn
     * @apiGroup /vpn
     * @apiDescription [vpn管理]导入vpn
     * @apiParam {String} file 文件
     * @apiParam {String} PatchClicksType 类型
     * @apiParamExample {json} 参数示例:
     * {
     * file:'文件',
     * 'PatchClicksType':'qq'
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
    @RequestMapping(value = "/upload-vpn", method = RequestMethod.POST)
    public ServerResponse uploadVpn(@RequestParam("file") MultipartFile file, String type) {
        ServerResponse response = new ServerResponse();
        try {
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");

            String filePath = "classpath:files/upload/";
            File dir = new File(filePath);
            if (!dir.exists()) {
                dir.mkdir();
            }
            String fileName = file.getOriginalFilename();
            // 获取文件后缀
            String prefix = fileName.substring(fileName.lastIndexOf("."));

            String path = filePath + UUIDUtils.getUUID() + prefix;

            File tempFile = new File(path);
            FileUtils.copyInputStreamToFile(file.getInputStream(), tempFile);

            response.setData(vpnService.assembleData(tempFile, type));

            //程序退出时删除临时文件
            deleteFile(tempFile);
        } catch (Exception e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }

    private void deleteFile(File... files) {
        for (File file : files) {
            if (file.exists()) {
                file.delete();
            }
        }
    }

    /**
     * @api {GET} /vpn/query-vpn-by-page [vpn管理]分页查询vpn
     * @apiSampleRequest /vpn/query-vpn-by-page
     * @apiName query-vpn-by-page
     * @apiGroup /vpn
     * @apiDescription [vpn管理]分页查询vpn
     * @apiParam {String} [PatchClicksType] 类型
     * @apiParam {String} [region] 地区
     * @apiParam {String} [state] 状态 INVALID("失效"),VALID("有效")
     * @apiParam {Number} to_page  当前页
     * @apiParam {Number} page_size  每页记录数
     * @apiParamExample {json} 参数示例:
     * {
     * state:'VALID',
     * to_page:1,
     * page_size:10
     * }
     * @apiSuccess {String} id id
     * @apiSuccess {String} PatchClicksType  类型
     * @apiSuccess {String} account  vpn
     * @apiSuccess {String} passWord  密码
     * @apiSuccess {String} state  INVALID("失效"),VALID("有效")
     * @apiSuccess {String} createrTime  创建时间
     * @apiSuccessExample {json} 返回示例:
     * HTTP/1.1 200 OK
     * {
     * "code": 0,
     * "msg": null,
     * "data": {
     * 'id':'82839383',
     * 'PatchClicksType':'QQ',
     * 'account':'767989222@qq.com',
     * 'passWord':'6bndsns',
     * 'state':'VALID',
     * 'createTime':‘206-0-08  12:35:32’
     * }
     * }
     */
    @RequestMapping(value = "/query-vpn-by-page", method = RequestMethod.GET)
    public ServerResponse queryVpnByPage(VpnDTO accountDTO) {
        ServerResponse response = new ServerResponse();
        try {
            response.setMsg("操作成功");
            response.setCode(ServerResponse.SUCCESS);
            response.setData(vpnService.queryVpnByPage(accountDTO));
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }

    /**
     * @api {post} /vpn/update-vpn-state [vpn管理]修改一个vpn
     * @apiSampleRequest /vpn/update-vpn-state
     * @apiName update-vpn-state
     * @apiGroup /vpn
     * @apiDescription [vpn管理]修改一个应用
     * @apiParam {String} id 应用id主键
     * @apiParam {String} [state] 状态 INVALID("失效"),VALID("有效")
     * @apiParamExample {json} 参数示例:
     * {
     * id:'12376899'，
     * 'state' ：'INVALID'
     * }
     * @apiSuccessExample {json} 返回示例:
     * <p/>
     * HTTP/1.1 200 OK
     * {
     * code:0,
     * msg:'操作成功',
     * data:{
     * {
     * 1
     * }
     * }
     * }
     */
    @RequestMapping(value = "/update-vpn-state", method = RequestMethod.POST)
    public ServerResponse updateVpn(VpnByDeviceModel vpnModel) {
        ServerResponse response = new ServerResponse();
        try {
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");

            if (BaseUtils.isBlank(vpnModel.getId()) || null == vpnModel.getState()) {
                throw new BusinessException("缺少必要参数...");
            }

            VpnByDeviceModel vpnModelNew = new VpnByDeviceModel();
            vpnModelNew.setId(vpnModel.getId());
            vpnModelNew.setState(vpnModel.getState());
            
            response.setData(vpnService.updateVpn(vpnModelNew));
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }


    /**
     * @api {GET} /vpn/load-vpn [vpn管理]加载一条数据
     * @apiSampleRequest /vpn/load-vpn
     * @apiName load-vpn
     * @apiGroup /vpn
     * @apiDescription [vpn管理]加载一条数据
     * @apiParam {String} id id主键
     * @apiParamExample {json} 参数示例:
     * {
     * 'id':'1233xxs1'
     * }
     * @apiSuccess {String} id id
     * @apiSuccess {String} PatchClicksType  vpn类型
     * @apiSuccess {String} account  vpn
     * @apiSuccess {String} passWord  密码
     * @apiSuccess {String} state  INVALID("失效"),VALID("有效")
     * @apiSuccess {String} createrTime  创建时间
     * @apiSuccessExample {json} 返回示例:
     * HTTP/1.1 200 OK
     * {
     * "code": 0,
     * "msg": null,
     * "data": {
     * 'id':'82839383',
     * 'name':'爱奇艺',
     * 'createTime':‘206-0-08  12:35:32’
     * }
     * }
     */
    @RequestMapping(value = "/load-vpn", method = RequestMethod.GET)
    public ServerResponse loadDeviceInfo(String id) {
        ServerResponse response = new ServerResponse();
        try {
            response.setMsg("操作成功");
            response.setCode(ServerResponse.SUCCESS);
            response.setData(vpnService.loadVpn(id));
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }


    /**
     * @api {GET} /vpn/get-vpn [vpn管理]获得一个随机vpn
     * @apiSampleRequest /vpn/get-vpn
     * @apiName get-vpn
     * @apiGroup /vpn
     * @apiDescription [vpn管理]获得一条数据
     * @apiParam {String} PatchClicksType 类型
     * @apiParam {String} [region] 地区
     * @apiParam {String} deviceId 设备id
     * @apiParamExample {json} 参数示例:
     * {
     * 'id':'1233xxs1'
     * }
     * @apiSuccess {String} id id
     * @apiSuccess {String} PatchClicksType  vpn类型
     * @apiSuccess {String} account  vpn
     * @apiSuccess {String} passWord  密码
     * @apiSuccess {String} state  INVALID("失效"),VALID("有效")
     * @apiSuccess {String} createrTime  创建时间
     * @apiSuccessExample {json} 返回示例:
     * HTTP/1.1 200 OK
     * {
     * "code": 0,
     * "msg": null,
     * "data": {
     * 'id':'82839383',
     * 'name':'爱奇艺',
     * 'createTime':‘206-0-08  12:35:32’
     * }
     * }
     */
    @RequestMapping(value = "/get-vpn", method = RequestMethod.GET)
    public ServerResponse getVpn(String type, String region, String deviceId) {
        ServerResponse response = new ServerResponse();
        try {
            response.setMsg("操作成功");
            response.setCode(ServerResponse.SUCCESS);
            response.setData(vpnService.getVpn(type, region, deviceId));
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }


    /**
     * @api {post} /vpn/unlock-vpn [vpn管理]解锁vpn锁定
     * @apiSampleRequest /vpn/unlock-vpn
     * @apiName unlock-vpn
     * @apiGroup /vpn
     * @apiDescription [vpn管理]解锁vpn锁定
     * @apiParam {String} deviceId 设备id
     * @apiParam {String} isInVaild 是否失效 Y/N
     * @apiParamExample {json} 参数示例:
     * {
     * deviceId:'12376899'，
     * 'isInVaild' ：'Y'
     * }
     * @apiSuccessExample {json} 返回示例:
     * <p/>
     * HTTP/1.1 200 OK
     * {
     * code:0,
     * msg:'操作成功',
     * data:{
     * {
     * 1
     * }
     * }
     * }
     */
    @RequestMapping(value = "/unlock-vpn", method = RequestMethod.POST)
    public ServerResponse unlockVpn(String deviceId, String isInVaild) {
        ServerResponse response = new ServerResponse();
        try {
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
            vpnService.unlockVpn(deviceId, isInVaild);
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }





    
}
