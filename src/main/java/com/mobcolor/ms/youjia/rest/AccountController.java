package com.mobcolor.ms.youjia.rest;

import com.mobcolor.ms.youjia.model.AccountModel;
import com.mobcolor.ms.youjia.model.dto.AccountDTO;
import com.mobcolor.ms.youjia.service.AccountExecService;
import com.mobcolor.ms.youjia.service.AccountService;
import com.mobcolor.framework.common.BaseRestController;
import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.common.ServerResponse;
import com.mobcolor.framework.utils.UUIDUtils;
import com.mobcolor.framework.utils.ZKLogger;
import com.mobcolor.framework.utils.ZKLoggerFactory;
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
@RequestMapping(value = "/account")
public class AccountController extends BaseRestController {
    private static final ZKLogger logger = ZKLoggerFactory.getLogger(AccountController.class);

    @Resource
    private AccountService accountService;

    @Resource
    private AccountExecService accountExecService;


    /**
     * @api {post} /account/upload-account [账号管理]导入账号
     * @apiSampleRequest /account/upload-account
     * @apiName upload-account
     * @apiGroup /account
     * @apiDescription [账号管理]导入账号
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
    @RequestMapping(value = "/upload-account", method = RequestMethod.POST)
    public ServerResponse uploadAccount(@RequestParam("file") MultipartFile file, String type) {
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

            response.setData(accountExecService.assembleData(tempFile, type));

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
     * @api {GET} /account/query-account-by-page [账号管理]分页查询账号
     * @apiSampleRequest /account/query-account-by-page
     * @apiName query-account-by-page
     * @apiGroup /account
     * @apiDescription [账号管理]分页查询账号
     * @apiParam {String} [PatchClicksType] 账号类型
     * @apiParam {String} [state] 状态 INVALID("失效"),VALID("有效")
     * @apiParam {Number} to_page  当前页
     * @apiParam {Number} page_size  每页记录数
     * @apiParamExample {json} 参数示例:
     * {
     * accountCategoriesId:'12222x',
     * to_page:1,
     * page_size:10
     * }
     * @apiSuccess {String} id id
     * @apiSuccess {String} PatchClicksType  账号类型
     * @apiSuccess {String} account  账号
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
    @RequestMapping(value = "/query-account-by-page", method = RequestMethod.GET)
    public ServerResponse queryAccountByPage(AccountDTO accountDTO) {
        ServerResponse response = new ServerResponse();
        try {
            response.setMsg("操作成功");
            response.setCode(ServerResponse.SUCCESS);
            response.setData(accountService.queryAccountByPage(accountDTO));
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }

    /**
     * @api {post} /account/update-account [账号管理]修改一个账号
     * @apiSampleRequest /account/update-account
     * @apiName update-account
     * @apiGroup /account
     * @apiDescription [账号管理]修改一个应用
     * @apiParam {String} id 应用id主键
     * @apiParam {String} [account] 账号
     * @apiParam {String} [passWord] 密码
     * @apiParamExample {json} 参数示例:
     * {
     * id:'12376899'，
     * 'account' ：'6566722@qq.ocm'
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
    @RequestMapping(value = "/update-account", method = RequestMethod.POST)
    public ServerResponse updateAccount(AccountModel accountModel) {
        ServerResponse response = new ServerResponse();
        try {
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
            response.setData(accountService.updateAccount(accountModel));
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }


    /**
     * @api {GET} /account/load-account [账号管理]加载一条数据
     * @apiSampleRequest /account/load-account
     * @apiName load-account
     * @apiGroup /account
     * @apiDescription [账号管理]加载一条数据
     * @apiParam {String} id id主键
     * @apiParamExample {json} 参数示例:
     * {
     * 'id':'1233xxs1'
     * }
     * @apiSuccess {String} id id
     * @apiSuccess {String} PatchClicksType  账号类型
     * @apiSuccess {String} account  账号
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
    @RequestMapping(value = "/load-account", method = RequestMethod.GET)
    public ServerResponse loadDeviceInfo(String id) {
        ServerResponse response = new ServerResponse();
        try {
            response.setMsg("操作成功");
            response.setCode(ServerResponse.SUCCESS);
            response.setData(accountService.loadAccount(id));
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }


    /**
     * @api {GET} /account/get-account [账号管理]获得一条数据
     * @apiSampleRequest /account/get-account
     * @apiName get-account
     * @apiGroup /account
     * @apiDescription [账号管理]获得一条数据
     * @apiParam {String} PatchClicksType 类型
     * @apiParam {String} appId 应用id
     * @apiParam {String} deviceId 设备id
     * @apiParamExample {json} 参数示例:
     * {
     * 'id':'1233xxs1'
     * }
     * @apiSuccess {String} id id
     * @apiSuccess {String} PatchClicksType  账号类型
     * @apiSuccess {String} account  账号
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
    @RequestMapping(value = "/get-account", method = RequestMethod.GET)
    public ServerResponse getAccount(String type, String appId, String deviceId) {
        ServerResponse response = new ServerResponse();
        try {
            response.setMsg("操作成功");
            response.setCode(ServerResponse.SUCCESS);
            response.setData(accountExecService.getAccount(type, appId, deviceId));
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }


    /**
     * @api {post} /account/unlock-account [账号管理]解锁账号锁定
     * @apiSampleRequest /account/unlock-account
     * @apiName unlock-account
     * @apiGroup /account
     * @apiDescription [账号管理]解锁账号锁定
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
    @RequestMapping(value = "/unlock-account", method = RequestMethod.POST)
    public ServerResponse unlockAccount(String deviceId, String isInVaild) {
        ServerResponse response = new ServerResponse();
        try {
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
            accountExecService.unlockAccount(deviceId, isInVaild);
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }


    /**
     * @api {post} /account/account-invaild [账号管理]账号停用
     * @apiSampleRequest /account/account-invaild
     * @apiName account-invaild
     * @apiGroup /account
     * @apiDescription [账号管理]账号停用
     * @apiParam {String} id 账号id
     * @apiParamExample {json} 参数示例:
     * {
     * id:'12376899'，
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
    @RequestMapping(value = "/account-invaild", method = RequestMethod.POST)
    public ServerResponse accountInVaild(String id) {
        ServerResponse response = new ServerResponse();
        try {
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
            accountExecService.acocuntInVaild(id);
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }


    /**
     * @api {GET} /account/init-data [账号管理]初始化缓存
     * @apiSampleRequest /account/init-data
     * @apiName init-data
     * @apiGroup /account
     * @apiDescription [账号管理]初始化缓存
     * @apiParam {String} taskId 任务id
     * @apiParamExample {json} 参数示例:
     * {
     * 'taskId':'1233xxs1'
     * }
     * @apiSuccessExample {json} 返回示例:
     * HTTP/1.1 200 OK
     * {
     * "code": 0,
     * "msg": null,
     * "data": {
     * }
     * }
     */
    @RequestMapping(value = "/init-data", method = RequestMethod.GET)
    public ServerResponse initData(String taskId) {
        ServerResponse response = new ServerResponse();
        try {
            response.setMsg("操作成功");
            response.setCode(ServerResponse.SUCCESS);
            accountExecService.initData(taskId);
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }
}
