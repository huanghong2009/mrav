package com.mobcolor.ms.youjia.rest;

import com.mobcolor.ms.youjia.model.ScriptModel;
import com.mobcolor.ms.youjia.model.dto.ScriptDTO;
import com.mobcolor.ms.youjia.service.ScriptService;
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
@RequestMapping(value = "/script")
public class ScriptController extends BaseRestController {
    private static final ZKLogger logger = ZKLoggerFactory.getLogger(ScriptController.class);

    @Resource
    private ScriptService scriptService;


    /**
     * @api {post} /script/add-script [脚本管理]添加一个脚本
     * @apiSampleRequest /script/add-script
     * @apiName add-script
     * @apiGroup /script
     * @apiDescription [脚本管理]添加一个脚本
     * @apiParam {String} name 脚本名称
     * @apiParam {String} platform 平台   ANDROID("安卓")，IOS("苹果")
     * @apiParam {String} type 脚本类型   MAIN("主脚本"),COMMON("公共脚本"),EXECUTION("执行脚本")
     * @apiParam {String} content 脚本内容
     * @apiParam {String} [remarks] 脚本备注
     * @apiParamExample {json} 参数示例:
     * {
     *  'name':'爱奇艺',
     *  'platform' ：'IOS',
     *  'type':'MAIN',
     *  'content':'xxxxxxxxxjhsdjdbjbddkjahd...'
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
    @RequestMapping(value = "/add-script", method = RequestMethod.POST)
    public ServerResponse addScript(ScriptModel scriptModel) {
        ServerResponse response = new ServerResponse();
        try {
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
            scriptService.addScript(scriptModel);
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }

    /**
     * @api {POST} /script/query-script-by-page [脚本管理]分页查询脚本
     * @apiSampleRequest /script/query-script-by-page
     * @apiName query-script-by-page
     * @apiGroup /script
     * @apiDescription [脚本管理]分页查询脚本
     * @apiParam {String} [name] 脚本名称(支持模糊查询)
     * @apiParam {String} [type] 脚本类型   MAIN("主脚本"),COMMON("公共脚本"),EXECUTION("执行脚本"),
     * @apiParam {Number} to_page  当前页
     * @apiParam {Number} page_size  每页记录数
     * @apiParamExample {json} 参数示例:
     * {
     *  name:'爱奇艺',
     *  type:'MAIN',
     *  to_page:1,
     *  page_size:10
     * }
     * @apiSuccess {String} id 脚本id
     * @apiSuccess {String} name  脚本名称
     * @apiSuccess {String} platform 平台   ANDROID("安卓")，IOS("苹果")
     * @apiSuccess {String} type 脚本类型   MAIN("主脚本"),COMMON("公共脚本"),EXECUTION("执行脚本")
     * @apiSuccess {String} content 脚本内容
     * @apiSuccess {String} remarks 脚本备注
     * @apiSuccess {String} createrTime  创建时间
     * @apiSuccessExample {json} 返回示例:
     * HTTP/1.1 200 OK
     * {
     *  "code": 0,
     *  "msg": null,
     *  "data": {[
     *      {
     *      'id':'82839383',
     *      'name':'爱奇艺',
     *      'platform':'IOS',
     *      'type':'MAIN',
     *      'content':'xxxxssssss',
     *      'remarks':'这是一个备注',
     *      'createTime':‘206-0-08  12:35:32’
     *      }
     *  }
     * }
     */
    @RequestMapping(value = "/query-script-by-page", method = RequestMethod.POST)
    public ServerResponse queryScriptByPage(ScriptDTO scriptDTO) {
        ServerResponse response = new ServerResponse();
        try {
            PageVO<ScriptModel> scriptModelPageVO = scriptService.queryScriptByPage(scriptDTO);
            response.setCode(ServerResponse.SUCCESS);
            response.setData(scriptModelPageVO);
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }

    /**
     * @api {POST} /script/select-scripts [脚本管理]查询指定脚本
     * @apiSampleRequest /script/select-scripts
     * @apiName select-scripts
     * @apiGroup /script
     * @apiDescription [脚本管理]查询指定脚本
     * @apiParam {String} name 指定脚本名称,多个脚本名称以','分隔
     * @apiParam {String} type 脚本类型   MAIN("主脚本"),COMMON("公共脚本"),EXECUTION("执行脚本"),
     * @apiParamExample {json} 参数示例:
     * {
     *  name:'爱奇艺1,爱奇艺2,爱奇艺3',
     *  type:'MAIN'
     * }
     * @apiSuccess {String} id 脚本id
     * @apiSuccess {String} name  脚本名称
     * @apiSuccess {String} platform 平台   ANDROID("安卓")，IOS("苹果")
     * @apiSuccess {String} type 脚本类型   MAIN("主脚本"),COMMON("公共脚本"),EXECUTION("执行脚本")
     * @apiSuccess {String} content 脚本内容
     * @apiSuccess {String} remarks 脚本备注（300字符）
     * @apiSuccess {String} createrTime  创建时间
     * @apiSuccessExample {json} 返回示例:
     * HTTP/1.1 200 OK
     * {
     *  "code": 0,
     *  "msg": null,
     *  "data": {[
     *      {
     *      'id':'82839383',
     *      'name':'爱奇艺',
     *      'platform':'IOS',
     *      'type':'MAIN',
     *      'content':'xxxxssssss',
     *      'remarks':'这是一个备注',
     *      'createTime':‘206-0-08  12:35:32’
     *      }
     *  }
     * }
     */
    @RequestMapping(value = "/select-scripts", method = RequestMethod.POST)
    public ServerResponse selectScripts(ScriptDTO scriptDTO) {
        ServerResponse response = new ServerResponse();
        try {
            response.setCode(ServerResponse.SUCCESS);
            response.setData(scriptService.selectScripts(scriptDTO));
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }


    /**
     * @api {post} /script/update-script [脚本管理]修改脚本内容
     * @apiSampleRequest /script/update-script
     * @apiName update-script
     * @apiGroup /script
     * @apiDescription [脚本管理]修改一个脚本内容
     * @apiParam {String} id 脚本id主键
     * @apiParam {String} content 脚本内容
     * @apiParamExample {json} 参数示例:
     * {
     *  'id':'12376899'，
     *  'content' ：'mmjshhsjjjsh'
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
    @RequestMapping(value = "/update-script", method = RequestMethod.POST)
    public ServerResponse updateScript(String id,String content) {
        ServerResponse response = new ServerResponse();
        try {
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
            response.setData(scriptService.updateScript(id,content));
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }


    /**
     * @api {POST} /script/select-execution-scripts [脚本管理]查询执行脚本
     * @apiSampleRequest /script/select-execution-scripts
     * @apiName select-execution-scripts
     * @apiGroup /script
     * @apiDescription [脚本管理]查询执行脚本
     * @apiParamExample {json} 参数示例:
     * {
     *
     * }
     * @apiSuccess {String} id 脚本id
     * @apiSuccess {String} name  脚本名称
     * @apiSuccess {String} platform 平台   ANDROID("安卓")，IOS("苹果")
     * @apiSuccess {String} type 脚本类型   MAIN("主脚本"),COMMON("公共脚本"),EXECUTION("执行脚本")
     * @apiSuccess {String} content 脚本内容
     * @apiSuccess {String} remarks 脚本备注
     * @apiSuccess {String} createrTime  创建时间
     * @apiSuccessExample {json} 返回示例:
     * HTTP/1.1 200 OK
     * {
     *  "code": 0,
     *  "msg": null,
     *  "data": {[
     *      {
     *      'id':'82839383',
     *      'name':'爱奇艺',
     *      'platform':'IOS',
     *      'type':'MAIN',
     *      'content':'xxxxssssss',
     *      'remarks':'这是一个备注',
     *      'createTime':‘206-0-08  12:35:32’
     *      }
     *  }
     * }
     */
    @RequestMapping(value = "/select-execution-scripts", method = RequestMethod.POST)
    public ServerResponse selectExecutionScripts() {
        ServerResponse response = new ServerResponse();
        try {
            response.setData(scriptService.selectExecutionScripts());
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }

    /**
     * @api {post} /script/delete-script [脚本管理]删除一个脚本
     * @apiSampleRequest /script/delete-script
     * @apiName delete-script
     * @apiGroup /script
     * @apiDescription [脚本管理]删除一个脚本
     * @apiParam {String} id 脚本id
     * @apiParamExample {json} 参数示例:
     * {
     *  'id':'13edjia1ed',
     * }
     * @apiSuccessExample {json} 返回示例:
     * <p/>
     * HTTP/1.1 200 OK
     * {
     * code:0,
     * msg:'操作成功',
     * data:{
     * 1
     * }
     * }
     */
    @RequestMapping(value = "/delete-script", method = RequestMethod.POST)
    public ServerResponse deleteScript(String id) {
        ServerResponse response = new ServerResponse();
        try {
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
            response.setData(scriptService.deleteScript(id));
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }

}
