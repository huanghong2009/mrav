
package com.mobcolor.ms.youjia.service.impl;

import com.mobcolor.ms.youjia.model.ExecutionGroupModel;
import com.mobcolor.ms.youjia.model.dto.ExecutionGroupDTO;
import com.mobcolor.ms.youjia.service.ExecutionGroupService;
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
 * @version 创建时间：2018-01-30 21:36:21
 */
@Service
public class ExecutionGroupServiceImpl extends BaseSupportServiceImpl implements ExecutionGroupService {
    private static final ZKLogger logger = ZKLoggerFactory.getLogger(ExecutionGroupServiceImpl.class);

    /**
     * 新增一个执行组
     *
     * @param executionGroupModel 执行组
     */
    public void addExecutionGroup(ExecutionGroupModel executionGroupModel) {
        if (BaseUtils.isBlank(executionGroupModel.getName())) {
            throw new BusinessException("缺少必要参数...");
        }
        executionGroupModel.setCreaterTime(new Date());
        executionGroupModel.setId(PrimaryUtil.getId());
        try {
            this.getDao().insert(executionGroupModel);
        } catch (Exception e) {
            logger.error("新增执行组失败", e);
            throw new BusinessException("新增执行组失败");
        }
    }

    /**
     * 分页查询执行组
     *
     * @param executionGroupDTO
     * @return
     * @throws BusinessException
     */
    @Override
    public PageVO<ExecutionGroupModel> queryExecutionGroupByPage(ExecutionGroupDTO executionGroupDTO) throws BusinessException {

        try {
            List<String> params = new ArrayList<String>();
            String sql = "SELECT * FROM EXECUTION_GROUP WHERE 1=1 ";

            if (BaseUtils.isNotBlank(executionGroupDTO.getName())) {
                sql += " AND NAME LIKE ? ";
                params.add("%" + executionGroupDTO.getName() + "%");
            }

            PageVO<ExecutionGroupModel> executionGroupModelPageVO = this.getDao().pageQuery(ExecutionGroupModel.class, sql,
                    params.size() > 0 ? params.toArray(new Object[params.size()]) : null,
                    executionGroupDTO.getTo_page(), executionGroupDTO.getPage_size());
            return executionGroupModelPageVO;
        } catch (Exception e) {
            logger.error("分页查询执行组失败", e);
            throw new BusinessException("分页查询执行组失败");
        }

    }

    /**
     * 修改执行组
     *
     * @param executionGroupModel
     * @throws BusinessException
     */
    @Override
    public int updateExecutionGroup(ExecutionGroupModel executionGroupModel) throws BusinessException {
        try {
            if (BaseUtils.isBlank(executionGroupModel.getId())) {
                throw new BusinessException("缺少必要参数...");
            }
            return this.getDao().update(executionGroupModel);
        } catch (Exception e) {
            logger.error("修改执行组信息失败", e);
            throw new BusinessException("修改执行组信息失败");
        }
    }

    /**
     * 根据id删除某个执行组
     *
     * @param id 主键id
     * @return
     * @throws BusinessException
     */
    @Override
    public int deleteExecutionGroup(String id) throws BusinessException {
        if (BaseUtils.isBlank(id)) {
            throw new BusinessException("缺少必要参数...");

        }
        try {
            return this.getDao().delete(ExecutionGroupModel.class, id);
        } catch (Exception e) {
            logger.error("删除数据失败", e);
            throw new BusinessException("删除数据失败");
        }
    }

    /**
     * 查询执行组数据集
     * @param executionGroupDTO
     * @return
     * @throws BusinessException
     */
    @Override
    public List<ExecutionGroupModel> selectExecutionGroups(ExecutionGroupDTO executionGroupDTO) throws BusinessException {

        try {
            Map<String, Object> params = new HashMap<>();
            String sql = " SELECT * FROM EXECUTION_GROUP ";
            return this.getDao().selectList(ExecutionGroupModel.class,sql,params);
        } catch (Exception e) {
            logger.error("查询执行组数据失败", e);
            throw new BusinessException("查询执行组数据失败");
        }
    }

   
}
