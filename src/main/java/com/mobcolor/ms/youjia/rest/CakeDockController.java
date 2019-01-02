package com.mobcolor.ms.youjia.rest;


import com.mobcolor.ms.youjia.model.CakeDockModel;
import com.mobcolor.ms.youjia.model.dto.CakeDockDTO;
import com.mobcolor.ms.youjia.service.CakeDockService;
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
@RequestMapping(value = "/cake")
public class CakeDockController extends BaseRestController {
    private static final ZKLogger logger = ZKLoggerFactory.getLogger(CakeDockController.class);

    @Resource
    private CakeDockService cakeService;


    /**
     * @api {post} /cake/add-cake [cake对接管理]添加一个cake对接
     * @apiSampleRequest /cake/add-cake
     * @apiName add-cake
     * @apiGroup /cake
     * @apiDescription [cake对接管理]添加一个cake对接
     * @apiParam {String} id idfa
     * @apiParam {String} taskDetailId 任务id
     * @apiParamExample {json} 参数示例:
     * {
     *  'id':'1234',
     *  'taskDetailId' ：'11233'
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
    @RequestMapping(value = "/add-cake", method = RequestMethod.POST)
    public ServerResponse addCakeDock(CakeDockModel cakeModel) {
        ServerResponse response = new ServerResponse();
        try {
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
            cakeService.addCakeDock(cakeModel);
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }

    /**
     * @api {POST} /cake/query-cake-by-page [cake对接管理]分页查询cake对接
     * @apiSampleRequest /cake/query-cake-by-page
     * @apiName query-cake-by-page
     * @apiGroup /cake
     * @apiDescription [cake对接管理]分页查询cake对接
     * @apiParam {String} [id] idfa
     * @apiParam {String} [taskDetailId] 任务id
     * @apiParam {Number} to_page  当前页
     * @apiParam {Number} page_size  每页记录数
     * @apiParamExample {json} 参数示例:
     * {
     *  to_page:1,
     *  page_size:10
     * }
     * @apiSuccess {String} id cake对接id
     * @apiSuccess {String} taskDetailId  任务id
     * @apiSuccess {String} createrTime  创建时间
     * @apiSuccessExample {json} 返回示例:
     * HTTP/1.1 200 OK
     * {
     *  "code": 0,
     *  "msg": null,
     *  "data": {[
     *      {
     *      'id':'82839383',
     *      'taskDetailId':'13234',
     *      'createTime':‘206-0-08  12:35:32’
     *      }
     *  }
     * }
     */
    @RequestMapping(value = "/query-cake-by-page", method = RequestMethod.POST)
    public ServerResponse queryCakeDockByPage(CakeDockDTO cakeDTO) {
        ServerResponse response = new ServerResponse();
        try {
            PageVO<CakeDockModel> cakeModelPageVO = cakeService.queryCakeDockByPage(cakeDTO);
            response.setCode(ServerResponse.SUCCESS);
            response.setData(cakeModelPageVO);
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }


}
