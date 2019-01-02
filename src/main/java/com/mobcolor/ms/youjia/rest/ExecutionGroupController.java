package com.mobcolor.ms.youjia.rest;


import com.mobcolor.ms.youjia.model.ExecutionGroupModel;
import com.mobcolor.ms.youjia.model.dto.ExecutionGroupDTO;
import com.mobcolor.ms.youjia.service.ExecutionGroupService;
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
@RequestMapping(value = "/execution-group")
public class ExecutionGroupController extends BaseRestController {
    private static final ZKLogger logger = ZKLoggerFactory.getLogger(ExecutionGroupController.class);

    @Resource
    private ExecutionGroupService executionGroupService;


    /**
     * @api {post} /execution-group/add-execution-group [执行组管理]添加执行组
     * @apiSampleRequest /execution-group/add-execution-group
     * @apiName add-execution-group
     * @apiGroup /execution-group
     * @apiDescription [执行组管理]添加执行组
     * @apiParam {String} name 名称
     * @apiParam {String} [remarks] 备注
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
    @RequestMapping(value = "/add-execution-group", method = RequestMethod.POST)
    public ServerResponse addExecutionGroup(ExecutionGroupModel executionGroupModel) {
        ServerResponse response = new ServerResponse();
        try {
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
            executionGroupService.addExecutionGroup(executionGroupModel);
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }

    /**
     * @api {GET} /execution-group/query-execution-group-by-page [执行组管理]分页查询执行组
     * @apiSampleRequest /execution-group/query-execution-group-by-page
     * @apiName query-execution-group-by-page
     * @apiGroup /execution-group
     * @apiDescription [执行组管理]分页查询执行组
     * @apiParam {String} [name] name模糊查询
     * @apiParam {Number} to_page  当前页
     * @apiParam {Number} page_size  每页记录数
     * @apiParamExample {json} 参数示例:
     * {
     *  to_page:1,
     *  page_size:10
     * }
     * @apiSuccess {String} id 执行id
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
     *      'executionNumber' ：'124edw344322',
     *      'tsNumber':'123edsw2',
     *      'devcieModel':'MI 6Plus',
     *      'systemVersion':'android 7'
     *      'createTime':‘206-0-08  12:35:32’
     *      }
     *  }
     * }
     */
    @RequestMapping(value = "/query-execution-group-by-page", method = RequestMethod.GET)
    public ServerResponse queryExecutionByPage(ExecutionGroupDTO executionGroupDTO) {
        ServerResponse response = new ServerResponse();
        try {
            PageVO<ExecutionGroupModel> executionGroupModelPageVO = executionGroupService.queryExecutionGroupByPage(executionGroupDTO);
            response.setCode(ServerResponse.SUCCESS);
            response.setData(executionGroupModelPageVO);
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }




    /**
     * @api {post} /execution-group/update-execution-group [执行组管理]修改执行组
     * @apiSampleRequest /execution-group/update-execution-group
     * @apiName update-execution-group
     * @apiGroup /execution-group
     * @apiDescription [执行组管理]修改执行组
     * @apiParam {String} id id主键
     * @apiParam {String} [name] 名称
     * @apiParam {String} [remarks] 备注
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
    @RequestMapping(value = "/update-execution-group", method = RequestMethod.POST)
    public ServerResponse updateExecutionGroup(ExecutionGroupModel executionGroupModel) {
        ServerResponse response = new ServerResponse();
        try {
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
            executionGroupService.updateExecutionGroup(executionGroupModel);
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }

    /**
     * @api {POST} /execution-group/select-execution-groups [执行组管理]查询执行组
     * @apiSampleRequest /execution-group/select-execution-groups
     * @apiName select-execution-groups
     * @apiGroup /execution-group
     * @apiDescription [执行组管理]查询执行组
     * @apiParamExample {json} 参数示例:
     * {
     * }
     * @apiSuccess {String} id 执行id
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
     *      'executionNumber' ：'124edw344322',
     *      'tsNumber':'123edsw2',
     *      'devcieModel':'MI 6Plus',
     *      'systemVersion':'android 7'
     *      'createTime':‘206-0-08  12:35:32’
     *      }
     *  }
     * }
     */
    @RequestMapping(value = "/select-execution-groups", method = RequestMethod.POST)
    public ServerResponse selectExecutionGroups(ExecutionGroupDTO executionGroupDTO) {
        ServerResponse response = new ServerResponse();
        try {
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
            response.setData(executionGroupService.selectExecutionGroups(executionGroupDTO));
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }


}
