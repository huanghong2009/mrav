package com.mobcolor.ms.youjia.service;

import com.mobcolor.ms.youjia.model.ScriptModel;
import com.mobcolor.ms.youjia.model.dto.ScriptDTO;
import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.common.PageVO;
import com.mobcolor.framework.dao.BaseService;

import java.util.List;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2017/12/19
 */
public interface ScriptService extends BaseService {

    /**
     * 新增一个脚本
     *
     * @param scriptModel 脚本
     */
    void addScript(ScriptModel scriptModel) throws BusinessException;

    /**
     * 查询脚本
     *
     * @param scriptDTO
     * @return
     * @throws BusinessException
     */
    PageVO<ScriptModel> queryScriptByPage(ScriptDTO scriptDTO) throws BusinessException;

    /**
     * 查询执行脚本
     *
     * @return
     * @throws BusinessException
     */
    List<ScriptModel> selectExecutionScripts() throws BusinessException;

    /**
     * 修改脚本内容
     *
     * @param content
     * @throws BusinessException
     */
    int updateScript(String id, String content) throws BusinessException;

    /**
     * 删除一个脚本
     *
     * @param id 主键id
     * @return
     * @throws BusinessException
     */
    int deleteScript(String id) throws BusinessException;

    /**
     * 查询脚本数据
     *
     * @param scriptDTO
     * @return
     * @throws BusinessException
     */
    List<ScriptModel> selectScripts(ScriptDTO scriptDTO) throws BusinessException;

}
