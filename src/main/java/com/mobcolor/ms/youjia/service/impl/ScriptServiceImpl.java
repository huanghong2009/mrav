package com.mobcolor.ms.youjia.service.impl;

import com.mobcolor.ms.youjia.enums.Script;
import com.mobcolor.ms.youjia.model.ScriptModel;
import com.mobcolor.ms.youjia.model.dto.ScriptDTO;
import com.mobcolor.ms.youjia.service.ScriptService;
import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.common.PageVO;
import com.mobcolor.framework.dao.BaseSupportServiceImpl;
import com.mobcolor.framework.utils.BaseUtils;
import com.mobcolor.framework.utils.PrimaryUtil;
import com.mobcolor.framework.utils.ZKLogger;
import com.mobcolor.framework.utils.ZKLoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2017/12/19
 */
@Service
public class ScriptServiceImpl extends BaseSupportServiceImpl implements ScriptService {
    private static final ZKLogger logger = ZKLoggerFactory.getLogger(ScriptServiceImpl.class);

    /**
     * 新增一个脚本
     *
     * @param scriptModel 脚本
     */
    public void addScript(ScriptModel scriptModel) {
        if (BaseUtils.isBlank(scriptModel.getName()) || null == scriptModel.getType()
                || BaseUtils.isBlank(scriptModel.getPlatform()) || BaseUtils.isBlank(scriptModel.getContent())) {
            throw new BusinessException("缺少必要参数(名称，类型，平台，内容)...");
        }
        scriptModel.setCreaterTime(new Date());
        scriptModel.setId(PrimaryUtil.getId());
        scriptModel.setState(Script.EXISTING.name());
        try {
            this.updateScriptStateByName(scriptModel.getName(), Script.DELETE);
            this.getDao().insert(scriptModel);
        } catch (Exception e) {
            logger.error("新增脚本失败", e);
            throw new BusinessException("新增脚本失败");
        }
    }

    /**
     * 分页查询脚本
     *
     * @param scriptDTO
     * @return
     * @throws BusinessException
     */
    @Override
    public PageVO<ScriptModel> queryScriptByPage(ScriptDTO scriptDTO) throws BusinessException {

        try {
            List<String> params = new ArrayList<String>();
            String sql = "SELECT * FROM SCRIPT WHERE 1=1 ";

            if (null != scriptDTO.getType()) {
                sql += " AND TYPE = ?";
                params.add(scriptDTO.getType().getName());
            }

            if (BaseUtils.isNotBlank(scriptDTO.getName())) {
                sql += " AND NAME LIKE ?";
                params.add("%" + scriptDTO.getName() + "%");
            }

            sql += " AND STATE = ? ";
            params.add(Script.EXISTING.name());

            PageVO<ScriptModel> scriptModelPageVO = this.getDao().pageQuery(ScriptModel.class, sql,
                    params.size() > 0 ? params.toArray(new Object[params.size()]) : null,
                    scriptDTO.getTo_page(), scriptDTO.getPage_size());
            return scriptModelPageVO;
        } catch (Exception e) {
            logger.error("分页查询脚本失败", e);
            throw new BusinessException("分页查询脚本失败");
        }

    }

    @Override
    public List<ScriptModel> selectExecutionScripts() throws BusinessException {
        try {
            Map<String,Object> params = new HashMap<>();

            params.put("TYPE",Script.EXECUTION.name());
            params.put("STATE",Script.EXISTING.name());

            String sql = "SELECT ID,NAME FROM SCRIPT WHERE  TYPE= :TYPE  AND STATE = :STATE ";
            return this.getDao().selectList(ScriptModel.class,sql, params);
        } catch (Exception e) {
            logger.error("查询执行脚本失败", e);
            throw new BusinessException("查询执行脚本失败");
        }
    }

    /**
     * 修改脚本
     *
     * @param id      主键id
     * @param content 脚本内容
     * @throws BusinessException
     */
    @Override
    public int updateScript(String id, String content) throws BusinessException {
        try {
            if (BaseUtils.isBlank(id) || BaseUtils.isBlank(content)) {
                throw new BusinessException("缺少必要参数...");
            }
            Map<String, Object> params = new HashMap<>();
            params.put("ID", id);
            params.put("CONTENT", content);
            return this.getDao().exec("UPDATE SCRIPT SET CONTENT = :CONTENT WHERE ID = :ID", params);
        } catch (Exception e) {
            logger.error("修改脚本信息失败", e);
            throw new BusinessException("修改脚本信息失败");
        }
    }

    /**
     * 根据id删除某个脚本
     *
     * @param id 主键id
     * @return
     * @throws BusinessException
     */
    @Override
    public int deleteScript(String id) throws BusinessException {
        if (BaseUtils.isBlank(id)) {
            throw new BusinessException("缺少必要参数...");

        }
        try {
            return this.getDao().delete(ScriptModel.class, id);
        } catch (Exception e) {
            logger.error("删除数据失败", e);
            throw new BusinessException("删除数据失败");
        }
    }

    /**
     * 查询脚本数据集
     * @param scriptDTO
     * @return
     * @throws BusinessException
     */
    @Override
    public List<ScriptModel> selectScripts(ScriptDTO scriptDTO) throws BusinessException {
        if (BaseUtils.isBlank(scriptDTO.getName()) || null == scriptDTO.getType()) {
            throw new BusinessException("缺少必要参数...");
        }
        try {
            Map<String, Object> params = new HashMap<>();
            String[] names = scriptDTO.getName().split(",");
            params.put("NAME", new ArrayList<String>(Arrays.asList(names)));
            params.put("TYPE", scriptDTO.getType().name());
            params.put("STATE",Script.EXISTING.name());
            String sql = " SELECT * FROM SCRIPT WHERE TYPE = :TYPE AND NAME IN ( :NAME ) AND STATE = :STATE";
            return this.getDao().selectList(ScriptModel.class,sql,params);
        } catch (Exception e) {
            logger.error("查询脚本数据失败", e);
            throw new BusinessException("查询脚本数据失败");
        }
    }

    /**
     * 根据name 修改库里脚本逻辑状态
     *
     * @param name
     * @param script
     * @return
     */
    private int updateScriptStateByName(String name, Script script) {
        if (BaseUtils.isBlank(name) || null == script) {
            throw new BusinessException("缺少必要参数...");
        }
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("NAME", name);
            params.put("STATE", script.getName());
            String sql = "UPDATE SCRIPT SET STATE = :STATE WHERE NAME = :NAME";
            return this.getDao().exec(sql,params);
        } catch (Exception e) {
            logger.error("修改脚本状态失败", e);
            throw new BusinessException("修改脚本状态失败");
        }
    }
}
