package com.mobcolor.ms.youjia.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mobcolor.framework.utils.*;
import com.mobcolor.ms.supplementaryClicks.supplementaryClickRule.enums.SupplementaryClick;
import com.mobcolor.ms.supplementaryClicks.supplementaryClickRule.model.SupplementaryClickRuleModel;
import com.mobcolor.ms.supplementaryClicks.supplementaryClickRule.service.SupplementaryClickRuleService;
import com.mobcolor.ms.youjia.enums.*;
import com.mobcolor.ms.youjia.model.*;
import com.mobcolor.ms.youjia.model.dto.TaskDTO;
import com.mobcolor.ms.youjia.model.dto.TaskDetailDTO;
import com.mobcolor.ms.youjia.model.dto.TaskListDTO;
import com.mobcolor.ms.youjia.service.*;
import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.dao.BaseSupportServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.util.*;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2017/12/26
 */
@Service
public class TaskServiceImpl extends BaseSupportServiceImpl implements TaskService {
    private static final ZKLogger logger = ZKLoggerFactory.getLogger(TaskServiceImpl.class);

    /**
     * 存放执行组的任务list，key是执行组id，；list是任务
     */
    private HashMap<String, ArrayList<TaskDetailModel>> execGroupList = new HashMap<>();

    /**
     * 存放执行组的任务list，key是list id，；value 是执行组id
     */
    private HashMap<String, String> execGroupListIndex = new HashMap<>();

    /**
     * 存放暂停的list key是list id，list是任务
     */
    private HashMap<String, ArrayList<TaskDetailModel>> taskDetailsGarbage = new HashMap<>();


    private boolean taskLock = false;

    @Resource
    private TaskForcetService taskForcetService;

    @Resource
    private TaskListService taskListService;

    @Resource
    private TaskDetailService taskDetailService;

    @Resource
    private AdvertisementService advertisementService;

    @Resource
    private SupplementaryClickRuleService supplementaryClickRuleService;

    @Resource
    private DeviceService deviceService;

    @Resource
    private DeviceGroupService deviceGroupService;

    /**
     * 新增一个任务组
     *
     * @param taskDTO 任务组
     * @throws BusinessException
     */
    @Override
    public void addTask(TaskDTO taskDTO) throws BusinessException {
        if (BaseUtils.isBlank(taskDTO.getAdvertisementId()) ||
                0 == taskDTO.getNum() || null == taskDTO.getPatchClicksType()) {
            throw new BusinessException("缺少必要参数...");
        }
        String taskForceId = "";
        String taskListId = PrimaryUtil.getId();
        List<TaskListModel> taskListModelList = new ArrayList<>();
        TaskListModel taskListModel = null;

        AdvertisementModel advertisementModel = advertisementService.loadAdvertisement(taskDTO.getAdvertisementId());
        SupplementaryClickRuleModel supplementaryClickRuleModel = supplementaryClickRuleService.loadSupplementaryClickRules(advertisementModel.getClickRulesId());
        String[] init = supplementaryClickRuleModel.getInitMethod().split(":");

        try {
            Object bean = this.getApplicationContext().getBean(init[0]);
            Method method = bean.getClass().getMethod(init[1], AdvertisementModel.class, String.class);
            method.invoke(bean, advertisementModel, taskListId);
        } catch (Exception e) {
            logger.error("未找到初始化方法...", e);
            throw new BusinessException("未找到初始化方法...");
        }


        String appName = advertisementModel.getAppName();
        String serverConfig = advertisementModel.getServerConfig();
        String scriptNames = advertisementModel.getScriptNames();
        String taskList = advertisementModel.getTaskLists();
        String clickRulesId = advertisementModel.getClickRulesId();

        String originalAdvertisementID = advertisementModel.getOriginalAdvertisementId();
        String platformChannelId = advertisementModel.getPlatformChannelId();
        String platformChannelName = advertisementModel.getPlatformChannelName();

        String startTime = this.sdf.format(new Date());
        Integer execNum = taskDTO.getNum();
        String isNeedCallBack = "N";

        String addExecGroup = advertisementModel.getAddExecGroup();
        String retainedExecGroup = advertisementModel.getRetainedExecGroup();

        if (BaseUtils.isNotBlank(taskDTO.getIsNeedCallBack())) {
            isNeedCallBack = String.valueOf(taskDTO.getIsNeedCallBack());
        }

        if (BaseUtils.isNotBlank(taskDTO.getStartTime())) {
            startTime = taskDTO.getStartTime();
        }

        JSONArray tasks = null;

        try {
            tasks = JSONArray.parseArray(taskList);
        } catch (Exception e) {
            logger.error("留存模版格式有误", e);
            throw new BusinessException("留存模版格式有误");
        }

        if (tasks.size() == 0) {
            throw new BusinessException("留存列表缺少任务");
        }

        /**
         * 多次唤醒
         */

        boolean isNeedRepeatedly = false;

        String[] daysStr = null;

        JSONArray repeatedlyConfig = null;

        /**
         * 获得多次唤醒的随机天数
         */
        int days = 0;

        if (advertisementModel.getIsNeedRepeatedly().equals("Y")) {
            isNeedRepeatedly = true;
            daysStr = advertisementModel.getRepeatedlyDays().split("-");
            repeatedlyConfig = JSONArray.parseArray(advertisementModel.getRepeatedlyConfig());
            days = BaseUtils.getRandom(Integer.parseInt(daysStr[0]), Integer.parseInt(daysStr[1]));
        }


        try {

            TaskForcetModel taskForcetModel = new TaskForcetModel();
            taskForcetModel.setAppName(appName);
            taskForcetModel.setServerConfig(serverConfig);
            taskForcetModel.setScriptNames(scriptNames);
            taskForcetModel.setOriginalAdvertisementId(originalAdvertisementID);
            taskForcetModel.setPlatformChannelId(platformChannelId);
            taskForcetModel.setPlatformChannelName(platformChannelName);
            taskForcetModel.setClickRulesId(clickRulesId);
            taskForcetModel.setPatchClicksType(taskDTO.getPatchClicksType());

            taskForceId = PrimaryUtil.getId();
            taskForcetModel.setId(taskForceId);


            for (int i = 0; i < tasks.size() + 1; i++) {
                String id = PrimaryUtil.getId();
                taskListModel = new TaskListModel();
                String execGroup = "";

                TaskList type = null;
                Integer num = null;

                if (i == 0) {
                    id = taskListId;
                    type = TaskList.ADD;
                    num = execNum;
                    execGroup = addExecGroup;
                    taskListModel.setTaskScheduleEnd(num);

                } else {
                    type = TaskList.RETAINED;
                    num = tasks.getInteger(i - 1);
                    execGroup = retainedExecGroup;
                    if (num <= 0 || num > 100) {
                        throw new BusinessException("任务列表留存比例不规范 0-100");
                    }
                }

                taskListModel.setId(id);
                taskListModel.setTaskForcetId(taskForceId);
                taskListModel.setAppName(appName);
                taskListModel.setServerConfig(serverConfig);
                taskListModel.setType(type);
                taskListModel.setNum(num);
                taskListModel.setTaskScheduleStart(0);
                taskListModel.setCreaterTime(new Date());
                taskListModel.setIsCreate("N");
                taskListModel.setExecTime(DateUtils.addDay(startTime, i));
                taskListModel.setIsNeedCallBack(isNeedCallBack);
                taskListModel.setState(TaskDetail.WAIT);
                taskListModel.setName(platformChannelName + "-" + originalAdvertisementID + "-" + appName + "-" + type.getDesc() + "-" + String.valueOf(i + 1));
                taskListModel.setClickRulesId(clickRulesId);
                taskListModel.setExecGroup(execGroup);
                taskListModel.setPatchClicksType(taskDTO.getPatchClicksType());


                taskListModelList.add(taskListModel);

                /**
                 * 多次唤醒添加
                 */
                if (isNeedRepeatedly) {
                    TaskListModel repeatedlyModel = null;
                    String repeatedlyCorrelationId = id;
                    for (int j = 0; j < days; j++) {
                        repeatedlyModel = new TaskListModel();
                        /**
                         * 获取这个随机比列
                         */
                        String[] replyNums = repeatedlyConfig.getString(j).split("-");
                        repeatedlyModel.setNum(BaseUtils.getRandom(Integer.parseInt(replyNums[0]), Integer.parseInt(replyNums[1])));

                        repeatedlyModel.setId(PrimaryUtil.getId());
                        repeatedlyModel.setRepeatedlyCorrelationId(repeatedlyCorrelationId);
                        repeatedlyModel.setTaskListId(id);
                        repeatedlyModel.setRepeatedlyIndex(j + 1);

                        repeatedlyModel.setTaskForcetId(taskForceId);
                        repeatedlyModel.setAppName(appName);
                        repeatedlyModel.setServerConfig(serverConfig);
                        repeatedlyModel.setType(TaskList.RETAINED);

                        repeatedlyModel.setTaskScheduleStart(0);
                        repeatedlyModel.setCreaterTime(new Date());
                        repeatedlyModel.setIsCreate("N");
                        repeatedlyModel.setExecTime(DateUtils.addDay(startTime, i));
                        repeatedlyModel.setIsNeedCallBack("N");
                        repeatedlyModel.setState(TaskDetail.WAIT);
                        repeatedlyModel.setName(appName + "-" + type.getDesc() + "-" + String.valueOf(i + 1) + "-" + "多次唤醒" + "-" + String.valueOf(j + 1));
                        repeatedlyModel.setClickRulesId(clickRulesId);
                        repeatedlyModel.setExecGroup(execGroup);
                        repeatedlyModel.setPatchClicksType(taskDTO.getPatchClicksType());

                        repeatedlyCorrelationId = repeatedlyModel.getId();

                        taskListModelList.add(repeatedlyModel);
                    }
                }


            }

            taskForcetService.addTaskForcet(taskForcetModel);
            taskListService.addBatchTaskList(taskListModelList);


            createTodayTask("默认创建--ADD");

        } catch (Exception e) {
            logger.error("新增任务失败", e);
            throw new BusinessException("新增任务失败");
        }

    }

    /**
     * 任务重置
     *
     * @param taskForcetId
     * @throws BusinessException
     */
    @Override
    public void listReset(String taskForcetId, String execTime) throws BusinessException {
        if (BaseUtils.isBlank(taskForcetId) || BaseUtils.isBlank(execTime)) {
            throw new BusinessException("缺少必要参数...");
        }

        TaskListModel taskListModel = taskListService.load(taskForcetId, execTime);

        if (taskListModel == null) {
            throw new BusinessException("list数据不存在");
        }
        if (taskListModel.getType().equals(TaskList.ADD)) {
            throw new BusinessException("新增数据不能重置");
        }

        if (taskListModel.getIsCreate().equals("N")) {
            throw new BusinessException("list没有创建任务");
        }

        taskListModel.setIsCreate("N");
        taskListModel.setTaskScheduleStart(0);
        taskListModel.setTaskScheduleEnd(0);

        taskListService.update(taskListModel);
        taskDetailService.deleteTaskDetailByListId(taskListModel.getId());

    }

    @Override
    public synchronized void createTodayTask(String taskId) throws BusinessException {
        logger.info("---------{} 定时任务:{} 开始执行------------", new Date(), taskId);
        List<TaskListModel> taskListModelList = new ArrayList<>();
        List<TaskDetailModel> taskDetailModelList = new ArrayList<>();
        List<String> taskListIds = new ArrayList<>();

        List<TaskDetailModel> taskDetailModelListRandom = null;
        TaskDetailDTO taskDetailDTO = null;

        TaskDetailModel taskDetailModel = null;
        TaskForcetModel taskForcetModel = null;
        try {

            TaskListDTO taskListDTO = new TaskListDTO();
            taskListDTO.setExecTime(sdf.format(new Date()));
            taskListDTO.setIsCreate("N");
            taskListDTO.setIsRepeatedly("N");
            taskListModelList = taskListService.queryTaskList(taskListDTO);
            if (taskListModelList.size() == 0) {
                return;
            }
            for (TaskListModel taskListModel : taskListModelList) {


                taskForcetModel = taskForcetService.getTaskForcetService(taskListModel.getTaskForcetId());

                if (taskListModel.getType().equals(TaskList.ADD)) {
                    for (int i = 0; i < taskListModel.getNum(); i++) {
                        taskDetailModel = new TaskDetailModel();
                        taskDetailModel.setId(PrimaryUtil.getId());
                        taskDetailModel.setTaskForcetId(taskListModel.getTaskForcetId());
                        taskDetailModel.setTaskListId(taskListModel.getId());
                        taskDetailModel.setAppName(taskListModel.getName());
                        taskDetailModel.setServerConfig(taskListModel.getServerConfig());
                        taskDetailModel.setType(taskListModel.getType());
                        taskDetailModel.setIsCallBcak("N");
                        taskDetailModel.setPatchClicksType(taskListModel.getPatchClicksType());
                        taskDetailModel.setScriptNames(taskForcetModel.getScriptNames());
                        taskDetailModel.setClickRulesId(taskForcetModel.getClickRulesId());
                        taskDetailModel.setExecGroup(taskListModel.getExecGroup());
                        taskDetailModel.setState(TaskDetail.RUNNING);
                        taskDetailModel.setCreaterTime(new Date());
                        taskDetailModelList.add(taskDetailModel);
                    }
                } else {

                    taskDetailDTO = new TaskDetailDTO();
                    taskDetailDTO.setTaskForcetId(taskListModel.getTaskForcetId());
                    taskDetailDTO.setState(TaskDetail.SUCCEED);
                    taskDetailDTO.setType(TaskList.ADD);
                    if (taskListModel.getIsNeedCallBack().equals("Y")) {
                        taskDetailDTO.setIsCallBcak("Y");
                    }

                    taskDetailModelListRandom = taskDetailService.queryTaskDetails(taskDetailDTO);

                    if (taskDetailModelListRandom.size() == 0){
                        continue;
                    }
                    int min = (taskDetailModelListRandom.size() * 10) / 100;
                    int max = ((taskDetailModelListRandom.size() * taskListModel.getNum()) / 100) + 1;

                    /**
                     * 此处有个规则：业务上要求有一定的设备（min）坚持留存到最后一天，所以下列设置min 和max，
                     * 少于min则从min取，大于则随机
                     */
                    for (int i = 0; i < max; i++) {
                        if (i < min) {
                            taskDetailModel = taskDetailModelListRandom.get(i);
                        } else {
                            /**
                             * 随机取一条
                             */
                            int randomNumn = BaseUtils.getRandom(min, taskDetailModelListRandom.size());
                            taskDetailModel = taskDetailModelListRandom.get(randomNumn);
                            taskDetailModelListRandom.remove(randomNumn);
                        }

                        taskDetailModel.setId(PrimaryUtil.getId());
                        taskDetailModel.setTaskForcetId(taskListModel.getTaskForcetId());
                        taskDetailModel.setTaskListId(taskListModel.getId());
                        taskDetailModel.setAppName(taskListModel.getAppName());
                        taskDetailModel.setServerConfig(taskListModel.getServerConfig());
                        taskDetailModel.setType(taskListModel.getType());
                        taskDetailModel.setIsCallBcak("N");
                        taskDetailModel.setScriptNames(taskForcetModel.getScriptNames());
                        taskDetailModel.setClickRulesId(taskForcetModel.getClickRulesId());
                        taskDetailModel.setState(TaskDetail.RUNNING);
                        taskDetailModel.setExecGroup(taskListModel.getExecGroup());
                        taskDetailModel.setCreaterTime(new Date());
                        taskDetailModelList.add(taskDetailModel);
                    }

                    /**
                     * 修改任务进度的总数目
                     */
                    taskListService.updateTaskListTaskScheduleEnd(taskListModel.getId(), max);

                }
                taskListIds.add(taskListModel.getId());
            }

            taskListService.updateTaskListIsCreate(taskListIds, "Y");
            taskDetailService.addBatchTaskDetail(taskDetailModelList);
            loadTodayTask(taskListIds);

        } catch (Exception e) {
            logger.error("创建今天的任务失败", e);
            throw new BusinessException("创建今天的任务失败");
        }

    }

    /**
     * 记载今天的任务
     *
     * @throws BusinessException
     */
    @Override
    public void loadTodayTask(List<String> ids) throws BusinessException {
        try {
            /**
             * 加载的时候设置一个锁，让获取任务 线程进入等待，等我这个方法执行完再释放
             */
            logger.warn("-----------TASKLOCK START--------------");
            taskLock = true;

            this.execGroupList.clear();
            this.taskDetailsGarbage.clear();

            initData(taskDetailService.queryTaskDetailByListId(ids));
        } catch (Exception e) {
            logger.error("加载任务失败", e);
            logger.warn("-----------TASKLOCK EXCEPTION END--------------");
            throw new BusinessException("加载任务失败");
        } finally {
            taskLock = false;
        }
    }

    @Override
    public void loadTodayTask() throws BusinessException {
        TaskListDTO taskListDTO = new TaskListDTO();
        taskListDTO.setExecTime(sdf.format(new Date()));
        taskListDTO.setIsQueryAll("Y");
        taskListDTO.setIsCreate("Y");
        taskListDTO.setIsQueryHaving("Y");
        List<TaskListModel> datas = taskListService.queryTaskList(taskListDTO);

        if (datas.size() == 0 ){
            return ;
        }

        List<String> ids = new ArrayList<>();
        for (TaskListModel data : datas) {
            ids.add(data.getId());
        }

        loadTodayTask(ids);
    }


    private void initData(List<TaskDetailModel> taskDetailModels) {

        for (TaskDetailModel taskDetailModel : taskDetailModels) {

            String taskListId = taskDetailModel.getTaskListId();
            String execGroup = taskDetailModel.getExecGroup();

            if (!this.execGroupListIndex.containsKey(taskListId)) {
                this.execGroupListIndex.put(taskListId, execGroup);
            }

            if (taskDetailModel.getState().equals(TaskDetail.RUNNING)) {
                if (!this.execGroupList.containsKey(execGroup)) {
                    this.execGroupList.put(taskDetailModel.getExecGroup(), new ArrayList<>());
                }
                this.execGroupList.get(execGroup).add(taskDetailModel);
            } else {

                if (!taskDetailsGarbage.containsKey(taskListId)) {
                    taskDetailsGarbage.put(taskListId, new ArrayList<TaskDetailModel>());
                }
                taskDetailsGarbage.get(taskListId).add(taskDetailModel);
            }
        }
    }

    /**
     * 获得一条任务
     *
     * @return
     * @throws BusinessException
     */
    @Override
    public synchronized TaskDetailModel getTask(String deviceNumber) throws BusinessException {
        try {
            if (taskLock) {
                return null;
            }
        } catch (Exception e) {
            logger.error("任务设置异常", e);
            throw new BusinessException("任务设置异常");
        }
        String deviceGroupId = deviceService.getDeviceGroupByDevice(deviceNumber);
        ArrayList<TaskDetailModel> detailModels = null;

        if (BaseUtils.isBlank(deviceGroupId)) {
            logger.error("设备没有上报或者绑定设备组,请联系管理员:{}...", deviceNumber);
            throw new BusinessException("设备没有上报或者绑定设备组,请联系管理员:...");
        }

        List<String> execGroupIds = deviceGroupService.getExecGroupByDeviceGroupId(deviceGroupId);

        if (null == execGroupIds || execGroupIds.size() == 0) {
            logger.error("设备组为空或没有绑定执行组,请联系管理员:{}...", deviceGroupId);
            throw new BusinessException("设备组为空或没有绑定执行组,请联系管理员:...");
        }

        /**
         * 找出可用的（有任务的）执行组
         */
        List<String> availableGroupIds = new ArrayList<>();
        for (String execGroupId : execGroupIds) {
            if (execGroupList.containsKey(execGroupId) && execGroupList.get(execGroupId).size() != 0) {
                availableGroupIds.add(execGroupId);
            }
        }

        /**
         * 随机取任务
         */

        if (availableGroupIds.size() == 0) {
            return null;
        } else if (availableGroupIds.size() == 1) {
            detailModels = execGroupList.get(availableGroupIds.get(0));
        } else {
            detailModels = execGroupList.get(availableGroupIds.get(BaseUtils.getRandom(0, availableGroupIds.size())));
        }

        while (detailModels.size() > 0) {
            /**
             * 随机取一条
             */
            int index = 0;
            if (detailModels.size() > 5) {
                index = BaseUtils.getRandom(0, detailModels.size());
            }

            TaskDetailModel taskDetailModel = detailModels.get(index);
            detailModels.remove(index);

            String taskListId = taskDetailModel.getTaskListId();

            /**;
             * 判断任务暂停map 里有没有这个list，如果有则是暂停任务，将这条加入这个list
             */
            if (taskDetailsGarbage.containsKey(taskListId)) {
                taskDetailsGarbage.get(taskListId).add(taskDetailModel);
                continue;
            }
            return taskDetailModel;
        }
        return null;
    }

    /**
     * 批量启用或停用
     *
     * @param taskListId
     * @param taskDetail
     * @return
     * @throws BusinessException
     */
    @Override
    public int batchAbled(String taskListId, TaskDetail taskDetail) throws BusinessException {
        if (BaseUtils.isBlank(taskListId) || null == taskDetail ||
                (!taskDetail.equals(TaskDetail.SUSPEND) && !taskDetail.equals(TaskDetail.RUNNING))) {
            throw new BusinessException("缺少必要参数或参数不合法...");
        }

        List<String> ids = Arrays.asList(taskListId.split(","));

        if (ids.size() == 0) {
            throw new BusinessException("缺少必要参数...");
        }

        changeStateByIds(ids, taskDetail);

        try {
            /**
             * 修改的时候设置一个锁，让获取任务 线程进入等待，等我这个方法执行完再释放
             */
            logger.warn("-----------TASKLOCK START--------------");
            taskLock = true;


            int num = taskDetailService.updateTaskDetailStateById(ids, taskDetail);
            int num2 = taskListService.updateTaskListState(ids, taskDetail);
            if (taskDetail.equals(TaskDetail.RUNNING)) {
            /*
             * 如果任务垃圾任务列表有没有这个list暂停的任务，如果有则删除，将其的任务重新加入到任务列表
             */
                for (String id : ids) {
                    if (taskDetailsGarbage.containsKey(id)) {
                        if (!execGroupListIndex.containsKey(id)) {
                            continue;
                        }
                        String execGroupId = execGroupListIndex.get(id);
                        if (!execGroupList.containsKey(execGroupId)) {
                            execGroupList.put(execGroupId, new ArrayList<>());
                        }
                        execGroupList.get(execGroupId).addAll(taskDetailsGarbage.get(id));
                        taskDetailsGarbage.remove(id);
                    }
                }

            } else if (taskDetail.equals(TaskDetail.SUSPEND)) {
                /**
                 * 如果是暂停，则在暂停的map里加入这条list
                 */
                for (String id : ids) {
                    if (!taskDetailsGarbage.containsKey(id)) {
                        taskDetailsGarbage.put(id, new ArrayList<TaskDetailModel>());
                    }
                }

            } else {
                throw new BusinessException("状态码错误");
            }
            logger.warn("-----------TASKLOCK END--------------");
            return num;
        } catch (Exception e) {
            logger.error("批量执行失败", e);
            logger.warn("-----------TASKLOCK EXCEPTION END--------------");
            throw new BusinessException("批量执行失败");
        } finally {
            taskLock = false;
        }

    }

    @Override
    public int allAbled(TaskDetail taskDetail) throws BusinessException {
        if (null == taskDetail ||
                (!taskDetail.equals(TaskDetail.SUSPEND) && !taskDetail.equals(TaskDetail.RUNNING))) {
            throw new BusinessException("缺少必要参数或参数不合法...");
        }

        /**
         * 暂停或启用补量
         */
        changeTodayTaskState(taskDetail);
        try {
            /**
             * 修改的时候设置一个锁，让获取任务 线程进入等待，等我这个方法执行完再释放
             */
            logger.warn("-----------TASKLOCK START--------------");
            taskLock = true;
            TaskListDTO taskListDTO = new TaskListDTO();
            taskListDTO.setExecTime(sdf.format(new Date()));
            List<TaskListModel> datas = taskListService.queryTaskList(taskListDTO);

            if (datas.size() == 0 ){
                return 0;
            }

            List<String> ids = new ArrayList<>();
            for (TaskListModel data : datas) {
                ids.add(data.getId());
            }

            int num2 = taskListService.updateTaskListState(ids,taskDetail);

            int num = taskDetailService.updateTaskDetailStateById(ids,taskDetail);


            loadTodayTask(ids);

            logger.warn("-----------TASKLOCK END--------------");
            return num;
        } catch (Exception e) {
            logger.error("批量执行失败", e);
            logger.warn("-----------TASKLOCK EXCEPTION END--------------");
            throw new BusinessException("批量执行失败");
        } finally {
            taskLock = false;
        }
    }


    /**
     * 暂停或启用批量补量
     *
     * @param ids
     * @param taskDetail
     * @throws BusinessException
     */
    private void changeStateByIds(List<String> ids, TaskDetail taskDetail) throws BusinessException {
        /**
         * 反射调用补量规则(暂停/启用所有补量)------
         */
        changeSupplementaryState(taskListService.queryTaskListByIds(ids), taskDetail);

    }

    /**
     * 暂停或启用今天的所有补量
     *
     * @param taskDetail
     * @throws BusinessException
     */
    private void changeTodayTaskState(TaskDetail taskDetail) throws BusinessException {
        /**
         * 反射调用补量规则(暂停/启用所有补量)------
         */
        changeSupplementaryState(taskListService.queryTodayTaskList(), taskDetail);

    }


    /**
     * 暂停或启用补量
     *
     * @param taskListModelList
     * @param taskDetail
     */
    private void changeSupplementaryState(List<TaskListModel> taskListModelList, TaskDetail taskDetail) {
        Map<String, List<String>> clickRuleIdIndex = new HashMap<>();

        for (TaskListModel taskListModel : taskListModelList) {
            if (taskListModel.getType().name().equals("ADD") && BaseUtils.isNotBlank(taskListModel.getClickRulesId())) {
                String clickRuleId = taskListModel.getClickRulesId();

                if (!clickRuleIdIndex.containsKey(clickRuleId)) {
                    clickRuleIdIndex.put(clickRuleId, new ArrayList<String>());
                }
                clickRuleIdIndex.get(clickRuleId).add(taskListModel.getId());

            }
        }

        Set<String> rulesIds = clickRuleIdIndex.keySet();
        try {
            for (String rulesId : rulesIds) {
                SupplementaryClickRuleModel supplementaryClickRuleModel = supplementaryClickRuleService.loadSupplementaryClickRules(rulesId);
                String[] init = supplementaryClickRuleModel.getChangeStateMethod().split(":");
                Object bean = this.getApplicationContext().getBean(init[0]);
                Method method = bean.getClass().getMethod(init[1], List.class, SupplementaryClick.class);
                method.invoke(bean, clickRuleIdIndex.get(rulesId), SupplementaryClick.valueOf(taskDetail.name()));
            }
        } catch (Exception e) {
            logger.error("修改补量-未找到初始化方法...", e);
            throw new BusinessException("修改补量-未找到初始化方法...");
        }

    }


    @Override
    public List<JSONObject> execQuerySql(String sql) throws BusinessException {
        sql = sql.toUpperCase();
        if (!sql.startsWith("SELECT") || sql.indexOf("UPDATE") != -1 || sql.indexOf("DELETE") != -1) {
            throw new BusinessException("非法sql");
        }
        try {
            return this.getDao().selectList(JSONObject.class, sql);
        } catch (Exception e) {
            logger.error("查询失败", e);
            throw new BusinessException("查询失败");
        }
    }

    /**
     * 添加一个任务
     *
     * @param id
     * @throws BusinessException
     */
    @Override
    public void addTaskToList(String id) throws BusinessException {
        if (BaseUtils.isBlank(id)) {
            throw new BusinessException("缺少必要参数...");
        }
        TaskDetailModel taskDetailModel = taskDetailService.load(id);
        if (null == taskDetailModel) {
            throw new BusinessException("错误的参数（id）...");
        } else if (taskDetailModel.getState().equals(TaskDetail.WAIT) || taskDetailModel.getState().equals(TaskDetail.SUCCEED) || taskDetailModel.getState().equals(TaskDetail.FAILED)) {
            logger.warn("任务已完成，无法加入");
            throw new BusinessException("任务已完成，无法加入(id)...");
        }

        List<TaskDetailModel> taskDetailModels = new ArrayList<>();
        taskDetailModels.add(taskDetailModel);
        initData(taskDetailModels);
    }

    /**
     * 定时任务加载 多次唤醒任务
     *
     * @param taskId
     * @throws BusinessException
     */
    @Override
    public synchronized void loadRepeatedlyData(String taskId) throws BusinessException {
        logger.info("------------loadRepeatedlyData:{}------------", taskId);

        TaskListDTO taskListDTO = new TaskListDTO();
        taskListDTO.setExecTime(sdf.format(new Date()));
        taskListDTO.setIsCreate("N");
        taskListDTO.setIsRepeatedly("Y");

        List<TaskListModel> taskListModelList = taskListService.queryTaskList(taskListDTO);
        if (taskListModelList.size() == 0) {
            return;
        }

        TaskListModel taskRepeatedlyModel = null;
        List<String> ids = new ArrayList<>();
        for (TaskListModel taskListModel : taskListModelList) {

            TaskForcetModel taskForcetModel = taskForcetService.getTaskForcetService(taskListModel.getTaskForcetId());

            taskRepeatedlyModel = taskListService.load(taskListModel.getRepeatedlyCorrelationId());
            TaskDetailDTO taskDetailDTO = new TaskDetailDTO();

            taskDetailDTO.setTaskListId(taskRepeatedlyModel.getId());
            if (taskRepeatedlyModel.getType().equals(TaskList.ADD) && taskRepeatedlyModel.getPatchClicksType().equals(PatchClicksType.ADVERTISER_CALL_BACK)) {
                taskDetailDTO.setIsCallBcak("Y");
            } else {
                taskDetailDTO.setState(TaskDetail.SUCCEED);
            }

            int repeatedlyNum = taskRepeatedlyModel.getTaskScheduleEnd() * taskListModel.getNum() / 100;

            Long num = taskDetailService.queryCountTaskDetails(taskDetailDTO);

            if (repeatedlyNum <= num) {
                List<TaskDetailModel> data = new ArrayList<>();
                List<TaskDetailModel> taskRepeatedlyDatas = taskDetailService.queryTaskDetails(taskDetailDTO);
                for (int i = 0; i < repeatedlyNum; i++) {

                    TaskDetailModel taskDetailModel = new TaskDetailModel();

                    taskDetailModel.setId(PrimaryUtil.getId());
                    taskDetailModel.setTaskForcetId(taskListModel.getTaskForcetId());
                    taskDetailModel.setTaskListId(taskListModel.getId());
                    taskDetailModel.setAppName(taskListModel.getAppName());
                    taskDetailModel.setServerConfig(taskListModel.getServerConfig());
                    taskDetailModel.setType(taskListModel.getType());
                    taskDetailModel.setIsCallBcak("N");
                    taskDetailModel.setScriptNames(taskForcetModel.getScriptNames());
                    taskDetailModel.setClickRulesId(null);
                    taskDetailModel.setState(TaskDetail.RUNNING);
                    taskDetailModel.setExecGroup(taskListModel.getExecGroup());
                    taskDetailModel.setCreaterTime(new Date());
                    taskDetailModel.setClickRulesId(taskListModel.getClickRulesId());
                    taskDetailModel.setRepeatedlyCorrelationId(taskListModel.getRepeatedlyCorrelationId());
                    taskDetailModel.setRepeatedlyIndex(taskListModel.getRepeatedlyIndex());
                    taskDetailModel.setOtherInfo(taskRepeatedlyDatas.get(i).getOtherInfo());

                    data.add(taskDetailModel);
                }

                taskListModel.setTaskScheduleEnd(repeatedlyNum);
                taskListModel.setIsCreate("Y");
                taskListModel.setIsNeedCallBack("N");
                taskListModel.setState(TaskDetail.RUNNING);

                taskListService.update(taskListModel);
                taskDetailService.addBatchTaskDetail(data);
                ids.add(taskListModel.getId());
            }
        }

        loadTodayTask(ids);
    }


}
