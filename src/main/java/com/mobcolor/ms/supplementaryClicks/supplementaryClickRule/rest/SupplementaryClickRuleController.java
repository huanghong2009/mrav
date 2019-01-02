package com.mobcolor.ms.supplementaryClicks.supplementaryClickRule.rest;


import com.mobcolor.ms.supplementaryClicks.supplementaryClickRule.model.SupplementaryClickRuleModel;
import com.mobcolor.ms.supplementaryClicks.supplementaryClickRule.model.dto.SupplementaryClickRuleDTO;
import com.mobcolor.ms.supplementaryClicks.supplementaryClickRule.service.SupplementaryClickRuleService;
import com.mobcolor.ms.thirdParty.model.dto.YouWeiHuDongDTO;
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
@RequestMapping(value = "/supplementary-click-rule")
public class SupplementaryClickRuleController extends BaseRestController {
    private static final ZKLogger logger = ZKLoggerFactory.getLogger(SupplementaryClickRuleController.class);


    @Resource
    private SupplementaryClickRuleService supplementaryClickRuleService;


    /**
     * @api {post} /supplementary-click-rule/add-supplementary-click-rule [补量点击规则]添加一个规则
     * @apiSampleRequest /supplementary-click-rule/add-supplementary-click-rule
     * @apiName add-supplementary-click-rule
     * @apiGroup /supplementary-click-rule
     * @apiDescription [补量点击规则]添加一个规则
     * @apiParam {String} name 别名
     * @apiParam {String} initMethod 初始化方法
     * @apiParam {String} callBackMethod 回调激活方法
     * @apiParam {String} changeStateMethod 状态变更方法
     * @apiParamExample {json} 参数示例:
     * {
     *  'name':'cake的补量',
     *  'initMethod':'com.mobcolor.ms.supplementaryClicks.supplementaryClickRule.service.SupplementaryClickRuleService.addSupplementaryClickRule',
     *  'callBackMethod':'com.mobcolor.ms.supplementaryClicks.supplementaryClickRule.service.SupplementaryClickRuleService.addSupplementaryClickRule',
     *  'changeStateMethod':'com.mobcolor.ms.supplementaryClicks.supplementaryClickRule.service.SupplementaryClickRuleService.addSupplementaryClickRule'
     * }
     *
     * @apiSuccessExample {json} 返回示例:
     * <p/>
     * HTTP/1.1 200 OK
     * {
     * code:0,
     * msg:'操作成功',
     * data:{
     * {
     *  1
     * }
     * }
     * }
     */
    @RequestMapping(value = "/add-supplementary-click-rule", method = RequestMethod.POST)
    public ServerResponse addSupplementaryClickRule(SupplementaryClickRuleModel supplementaryClickRuleModel) {
        ServerResponse response = new ServerResponse();
        try {
            supplementaryClickRuleService.addSupplementaryClickRule(supplementaryClickRuleModel);
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }

    /**
     * @api {POST} /supplementary-click-rule/select-supplementary-click-rules [补量点击]查询规则
     * @apiSampleRequest /supplementary-click-rule/select-supplementary-click-rules
     * @apiName select-supplementary-click-rules
     * @apiGroup /supplementary-click-rule
     * @apiDescription [补量点击]查询规则
     * @apiParamExample {json} 参数示例:
     * {
     * }
     * @apiSuccess {String} id 主键
     * @apiSuccess {String} initMethod 初始化方法
     * @apiSuccess {String} callBackMethod 回调激活方法
     * @apiSuccess {String} changeStateMethod 状态变更方法
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
    @RequestMapping(value = "/select-supplementary-click-rules", method = RequestMethod.POST)
    public ServerResponse selectSupplementaryClickRules(SupplementaryClickRuleDTO supplementaryClickRuleDTO) {
        ServerResponse response = new ServerResponse();
        try {
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
            response.setData(supplementaryClickRuleService.selectSupplementaryClickRules(supplementaryClickRuleDTO));
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }
    

}
