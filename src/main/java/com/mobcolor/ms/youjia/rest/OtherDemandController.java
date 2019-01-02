package com.mobcolor.ms.youjia.rest;

import com.mobcolor.framework.common.BaseRestController;
import com.mobcolor.framework.common.ServerResponse;
import com.mobcolor.framework.utils.ZKLogger;
import com.mobcolor.framework.utils.ZKLoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2017/12/20
 */
@RestController
@RequestMapping(value = "/other/demand")
public class OtherDemandController extends BaseRestController {
    private static final ZKLogger logger = ZKLoggerFactory.getLogger(OtherDemandController.class);

    private LinkedList<String> accounts = new LinkedList<>();


    /**
     * @api {post} /other/demand/get-accounts [独立功能]读取一个账号
     * @apiSampleRequest /other/demand/get-accounts
     * @apiName get-accounts
     * @apiGroup /other/demand
     * @apiDescription 【独立功能】读取一个账号
     * @apiParam {Number} num 需要读取的条数
     * @apiParamExample {json} 参数示例:
     * {
     * num:1
     * }
     * @apiSuccessExample {json} 返回示例:
     * <p/>
     * HTTP/1.1 200 OK
     * {
     * code:0,
     * msg:'操作成功',
     * data:{
     * [
     * 13134562357
     * ]
     * }
     * }
     */
    @RequestMapping(value = "/get-accounts", method = RequestMethod.POST)
    public ServerResponse getAccounts(int num) {
        ServerResponse response = new ServerResponse();
        List<String> resultData = new ArrayList<>();
        try {
            if (num <= 0 || num > 1000) {
                response.setCode(ServerResponse.ERROR_BBUSINESS);
                response.setMsg("请输入一个正常的数字 0 < num < 1000");
            }
            if (accounts.size() == 0 || accounts.size() < num) {
                InputStream inputStream = this.getClass().getResourceAsStream("/files/accounts.txt");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String str = null;
                while ((str = bufferedReader.readLine()) != null) {
                    accounts.add(str);
                }
                //close  
                inputStream.close();
                bufferedReader.close();
            }
            while (num > 0) {
                resultData.add(accounts.pop());
                num--;
            }
            response.setData(resultData);
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
        } catch (Exception e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }

    /**
     * @api {post} /other/demand/test 测试
     * @apiSampleRequest /other/demand/test
     * @apiName test
     * @apiGroup /other/demand
     * @apiDescription 测试
     * @apiParamExample {json} 参数示例:
     * {
     *
     * }
     * @apiSuccessExample {json} 返回示例:
     * <p/>
     * HTTP/1.1 200 OK
     * {
     * code:0,
     * msg:'操作成功',
     * data:{
     * [
     * 13134562357
     * ]
     * }
     * }
     */
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public ServerResponse execQuerySql(String sql) {
        this.loggerParam("/tasks/test", sql);
        ServerResponse response = new ServerResponse();
        try {
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
        } catch (Exception e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }

}
