CREATE TABLE `PLATFORM` (
        `ID` varchar(50)  COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
        `NAME` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '渠道名称',
        `PLATFORM` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '平台',
        `CREATER_TIME` datetime DEFAULT NULL COMMENT '创建时间',
        `EXTEND_FIELD1` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '扩展字段1',
        `EXTEND_FIELD2` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '扩展字段2',
        PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='渠道表';


CREATE TABLE `APP_MANAGE` (
        `ID` varchar(50)  COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
        `BUNDLE_ID` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '应用bid',
        `NAME` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '游戏名称',
        `CREATER_TIME` datetime DEFAULT NULL COMMENT '创建时间',
        `EXTEND_FIELD1` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '扩展字段1',
        `EXTEND_FIELD2` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '扩展字段2',
        PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='游戏分组表';


CREATE TABLE `SCRIPT` (
        `ID` varchar(50)  COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
        `NAME` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '脚本名称',
        `PLATFORM` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '平台',
        `TYPE` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '脚本类型',
        `CONTENT` text COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '脚本内容',
        `REMARKS` varchar(300) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '脚本备注',
        `CREATER_TIME` datetime DEFAULT NULL COMMENT '创建时间',
        `STATE` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '逻辑状态',
        `EXTEND_FIELD1` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '扩展字段1',
        `EXTEND_FIELD2` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '扩展字段2',
        PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='脚本表';



CREATE TABLE `ADVERTISEMENT` (
        `ID` varchar(50)  COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
        `ORIGINAL_ADVERTISEMENT_ID` varchar(50)  COLLATE utf8mb4_general_ci NOT NULL COMMENT '原广告id',
        `APP_NAME` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'app名称',
        `PLATFORM_CHANNEL_ID` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '应用渠道id',
        `PLATFORM_CHANNEL_NAME` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '应用渠道名称',
        `SCRIPT_NAMES` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '脚本',
        `TASK_LISTS` varchar(300) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '留存模版',
        `SERVER_CONFIG` text COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '服务配置',
        `CLICK_RULES_ID` varchar(50)  COLLATE utf8mb4_general_ci NOT NULL COMMENT '补点击规则id',
        `MIN_CLICK_NUM` int(5) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '最小补点击数量',
        `CONVERSION_RATE` int(10) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '转换率',

        `ADD_EXEC_GROUP` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '新增执行组',
        `RETAINED_EXEC_GROUP` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '留存执行组',
`IS_NEED_REPEATEDLY` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否需要多次唤醒',
`REPEATEDLY_CONFIG` text COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '多次唤醒配置',
`REPEATEDLY_DAYS` varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '多次唤醒天数',

        `CREATER_TIME` datetime DEFAULT NULL COMMENT '创建时间',
        `STATE` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '逻辑状态',
        `EXTEND_FIELD1` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '扩展字段1',
        `EXTEND_FIELD2` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '扩展字段2',
        PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='任务组表';

CREATE TABLE `TASK_FORCE` (
        `ID` varchar(50)  COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
        `ORIGINAL_ADVERTISEMENT_ID` varchar(50)  COLLATE utf8mb4_general_ci NOT NULL COMMENT '原广告id',
        `CLICK_RULES_ID` varchar(50)  COLLATE utf8mb4_general_ci NOT NULL COMMENT '补点击规则id',
        `PLATFORM_CHANNEL_ID` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '应用渠道id',
        `PLATFORM_CHANNEL_NAME` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '应用渠道名称',
        `PATCH_CLICKS_TYPE` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '补点击方式',
        `APP_NAME` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'app名称',
        `SERVER_CONFIG` text COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '服务配置',
        `SCRIPT_NAMES` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '脚本',
        `CREATER_TIME` datetime DEFAULT NULL COMMENT '创建时间',
        `STATE` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '逻辑状态',
        `EXTEND_FIELD1` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '扩展字段1',
        `EXTEND_FIELD2` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '扩展字段2',
        PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='任务组表';

CREATE TABLE `TASK_LIST` (
        `ID` varchar(50)  COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
        `TASK_FORCE_ID` varchar(50)  COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务组主键',
        `CLICK_RULES_ID` varchar(50)  COLLATE utf8mb4_general_ci NOT NULL COMMENT '补点击规则id',

        `EXEC_GROUP` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '执行组',
        `PATCH_CLICKS_TYPE` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '补点击方式',
        `NAME` varchar(80) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'list名称',
        `APP_NAME` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'app名称',
        `SERVER_CONFIG` text COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '服务配置',
        `TYPE` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '任务类型',
        `NUM` int(10) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '任务类型',
        `TASK_SCHEDULE_START` int(10) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '任务类型',
        `TASK_SCHEDULE_END` int(10) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '任务类型',
        `IS_CREATE` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '任务是否创建',
        `IS_NEED_CALL_BACK` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '任务是否需要回调',

        `TASK_LIST_ID` varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '任务列表id',
        `REPEATEDLY_CORRELATION_ID` varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '唤醒任务关联的listid',
        `REPEATEDLY_INDEX` int(2) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '多次唤醒任务下标',

        `EXEC_TIME` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT  '执行时间',
        `CREATER_TIME` datetime DEFAULT NULL COMMENT '创建时间',
        `STATE` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '逻辑状态',
        `EXTEND_FIELD1` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '扩展字段1',
        `EXTEND_FIELD2` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '扩展字段2',
        PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='任务列表';


CREATE TABLE `TASK_DETAIL` (
        `ID` varchar(50)  COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
        `TASK_FORCE_ID` varchar(50)  COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务组主键',
        `CLICK_RULES_ID` varchar(50)  COLLATE utf8mb4_general_ci NOT NULL COMMENT '补点击规则id',
        `TASK_LIST_ID` varchar(50)  COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务列表主键',

        `EXEC_GROUP` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '执行组',

        `REPEATEDLY_CORRELATION_ID` varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '唤醒任务关联的listid',
        `REPEATEDLY_INDEX` int(2) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '多次唤醒任务下标',
        `PATCH_CLICKS_TYPE` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '补点击方式',
        `APP_NAME` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'app名称',
        `SERVER_CONFIG` text COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '服务配置',
        `TYPE` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '任务类型',
        `STATE` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '状态',
        `IS_CALL_BACK` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '任务是否回调',
        `SCRIPT_NAMES` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '脚本',
        `DEVICE` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '设备识别号',
        `OTHER_INFO` text COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '其他信息',
        `UPDATE_TIME` datetime DEFAULT NULL COMMENT '最后修改时间',
        `CREATER_TIME` datetime DEFAULT NULL COMMENT '创建时间',
        `EXTEND_FIELD1` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '扩展字段1',
        `EXTEND_FIELD2` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '扩展字段2',
        PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='任务详细表';

CREATE TABLE `CAKE_DOCK` (
        `ID` varchar(50)  COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
        `TASK_DETAIL_ID` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '任务bid',
        `STATE` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '状态',
        `CREATER_TIME` datetime DEFAULT NULL COMMENT '创建时间',
        `EXTEND_FIELD1` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '扩展字段1',
        `EXTEND_FIELD2` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '扩展字段2',
        PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='appcake对接表';

CREATE TABLE `OTHER_YOUWEIHUDONG` (
 `ID` varchar(50)  COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
 `CID`  varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '有位互动分配给己方的ID',
 `APP_NAME`  varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '应用名称',
 `APP_ID`  varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '应用id',
 `PLATFORM_CHANNEL_ID`  varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '应用渠道id',
 `PLATFORM_CHANNEL_NAME`  varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '应用渠道名称',
 `IDFA`  varchar(40) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'idfa',
 `IP`  varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'ip',
 `WORD`  varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户搜索的关键词 UTF-8 - urlencode编码',
 `SYS_VER`  varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'IOS系统版本号',
 `TYPE`  varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '任务类型',
 `STATE`  varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '任务状态',
 `DEVICE`  varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '设备',
 `OTHER_INFO`  text COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '其他信息',
 `IS_CALL_BACK`  varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否收到回调',
 `CALL_BACK_DATA`  varchar(300) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '回调的返回数据',
 `CREATER_TIME`  datetime DEFAULT NULL COMMENT '创建时间',
 `UPDATE_TIME`  datetime DEFAULT NULL COMMENT '修改时间',
 `EXTEND_FIELD1`  varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '扩展字段1',
 `EXTEND_FIELD2`  varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '扩展字段2',
 PRIMARY KEY (`ID`)
 )ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='有为互动';


CREATE TABLE `SUPPLEMENTARY_CLICK_RULE` (
 `ID` varchar(50)  COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
 `NAME`  varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '别名',
 `INIT_METHOD`  varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '初始化的方法',
 `CALL_BACK_METHOD`  varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '收到回调触发的方法',
 `CHANGE_STATE_METHOD`  varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '状态变更触发的方法',
 `CREATER_TIME`  datetime DEFAULT NULL COMMENT '创建时间',
 `UPDATE_TIME`  datetime DEFAULT NULL COMMENT '修改时间',
 `EXTEND_FIELD1`  varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '扩展字段1',
 `EXTEND_FIELD2`  varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '扩展字段2',
 PRIMARY KEY (`ID`)
 )ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='补量规则表';

CREATE TABLE `CAKE_SUPPLEMENTARY_CLICK` (
 `ID` varchar(50)  COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
 `TASK_LIST_ID`  varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '任务列表id',
 `ORIGINAL_ADVERTISEMENT_ID`  varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '原广告id',
 `TASK_SCHEDULE_START`  int(10) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '开始进度',
 `TASK_SCHEDULE_END`  int(10) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '结束进度',
 `CONVERSION_RATE` int(10) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '转换率',
 `CLICK_URL`  varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '点击连接',
 `STATE`  varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '状态',
 `CREATER_TIME`  datetime DEFAULT NULL COMMENT '创建时间',
 `UPDATE_TIME`  datetime DEFAULT NULL COMMENT '修改时间',
 `EXTEND_FIELD1`  varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '扩展字段1',
 `EXTEND_FIELD2`  varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '扩展字段2',
 PRIMARY KEY (`ID`)
 )ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='cake补点击';

CREATE TABLE `DEVICE` (
 `ID` varchar(50)  COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
 `DEVICE_GROUP` varchar(50)  COLLATE utf8mb4_general_ci NOT NULL COMMENT '设备组id',

 `DEVICE_NUMBER`  varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '设备编号',
 `TS_NUMBER`  varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '触动编号',
 `NAME`  varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '名称',
 `SYSTEM_VERSION`  varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '系统版本号',
 `DEVICE_MODEL`  varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '设置型号',
 `OTHER_INFO`  text COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '其他信息',
 `CREATER_TIME`  datetime DEFAULT NULL COMMENT '创建时间',
 `EXTEND_FIELD1`  varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '扩展字段',
 `EXTEND_FIELD2`  varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '扩展字段',
 PRIMARY KEY (`ID`),
 UNIQUE (DEVICE_NUMBER),
 UNIQUE (TS_NUMBER)
 )ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='DEVICE表';


 CREATE TABLE `DEVICE_GROUP` (
 `ID` varchar(50)  COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
 `NAME`  varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '名称',
 `REMARKS`  varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
 `EXEC_GROUP`  varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '执行组',
 `CREATER_TIME`  datetime DEFAULT NULL COMMENT '创建时间',
 `EXTEND_FIELD1`  varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '扩展字段',
 `EXTEND_FIELD2`  varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '扩展字段',
 PRIMARY KEY (`ID`)  )ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='设备组表';

CREATE TABLE `EXECUTION_GROUP` (
 `ID` varchar(50)  COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
 `NAME`  varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '名称',
 `REMARKS`  varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
 `CREATER_TIME`  datetime DEFAULT NULL COMMENT '创建时间',
 `EXTEND_FIELD1`  varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '扩展字段',
 `EXTEND_FIELD2`  varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '扩展字段',
 PRIMARY KEY (`ID`)  )ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='执行组表';

 CREATE TABLE `CRM_USER` (
 `ID` varchar(50)  COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
 `USERNAME`  varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '',
 `PASSWORD`  varchar(70) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '',
 `EMAIL`  varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '',
 `SALT`  varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '',
 `STATUS`  varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '',
 `CREATE_TIME`  datetime DEFAULT NULL COMMENT '',
 `EXTEND_FIELD1`  varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '',
 `EXTEND_FIELD2`  varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '',
  PRIMARY KEY (`ID`),
  UNIQUE (`EMAIL`)
  )ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='CRM_USER表';



  CREATE TABLE `ACCOUNT` (
 `ID` varchar(50)  COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
 `TYPE`  varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '账号类型',
 `ACCOUNT`  varchar(40) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '账号',
 `PASS_WORD`  varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '密码',
 `ACCOUNT_INDEX`  int(10) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '下标',
 `STATE`  varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '状态',
 `LAST_UPDATE_TIME`  datetime DEFAULT NULL COMMENT '最后一次修改时间',
 `CREATER_TIME`  datetime DEFAULT NULL COMMENT '',
 `EXTEND_FIELD1`  varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '扩展字段',
 `EXTEND_FIELD2`  varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '扩展字段',
  PRIMARY KEY (`ID`),
  UNIQUE (`TYPE`,`ACCOUNT`)
 )ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='ACCOUNT表';

CREATE TABLE `ACCOUNT_INDEX` (
 `ID` varchar(50)  COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
 `APP_ID`  varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '应用id',
 `TYPE`  varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '账号类型',
 `ACCOUNT_INDEX`  int(10) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '账号下标',
 `CREATER_TIME`  datetime DEFAULT NULL COMMENT '',
 `EXTEND_FIELD1`  varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '扩展字段',
 `EXTEND_FIELD2`  varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '扩展字段',
 PRIMARY KEY (`ID`),
 UNIQUE (`TYPE`,`APP_ID`)
 )ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='ACCOUNT_INDEX表';


CREATE TABLE `ACCOUNT_CATEGORIES` (
 `ID` varchar(50)  COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
 `NAME`  varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '类别名称',
 `CREATER_TIME`  datetime DEFAULT NULL COMMENT '创建时间',
 `EXTEND_FIELD1`  varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '扩展字段',
 `EXTEND_FIELD2`  varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '扩展字段',
 PRIMARY KEY (`ID`)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='账号类别表';

CREATE TABLE `VPN` (
 `ID` varchar(50)  COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
 `TYPE`  varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'vpn类型',
 `ACCOUNT`  varchar(40) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '账号',
 `PASS_WORD`  varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '密码',
 `REGION`  varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '地区',
 `DEVICE_ID`  varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '设备id',
 `STATE`  varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '状态',
 `LAST_UPDATE_TIME`  datetime DEFAULT NULL COMMENT '最后一次修改时间',
 `CREATER_TIME`  datetime DEFAULT NULL COMMENT '',
 `EXTEND_FIELD1`  varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '扩展字段',
 `EXTEND_FIELD2`  varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '扩展字段',
 PRIMARY KEY (`ID`),
 UNIQUE (`TYPE`,`ACCOUNT`),
 UNIQUE( `DEVICE_ID`)
 )ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='VPN表';

 CREATE TABLE `VPN_TYPE` (
 `ID` varchar(50)  COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
 `NAME`  varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '类别名称',
 `CREATER_TIME`  datetime DEFAULT NULL COMMENT '创建时间',
 `EXTEND_FIELD1`  varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '扩展字段',
 `EXTEND_FIELD2`  varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '扩展字段',
 PRIMARY KEY (`ID`)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='VPN类别表';



alter table `ADVERTISEMENT` add column `ADD_EXEC_GROUP` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '新增执行组';
alter table `ADVERTISEMENT` add column `RETAINED_EXEC_GROUP` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '留存执行组';
alter table `TASK_LIST` add column  `EXEC_GROUP` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '执行组';
alter table `TASK_DETAIL` add column  `EXEC_GROUP` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '执行组';


alter table `TASK_LIST` add column  `PATCH_CLICKS_TYPE` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '补点击方式';
alter table `TASK_FORCE` add column  `PATCH_CLICKS_TYPE` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '补点击方式';
alter table `TASK_DETAIL` add column  `PATCH_CLICKS_TYPE` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '补点击方式';


alter table `ADVERTISEMENT` add column `IS_NEED_REPEATEDLY` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否需要多次唤醒';
alter table `ADVERTISEMENT` add column `REPEATEDLY_CONFIG` text COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '多次唤醒配置';
alter table `ADVERTISEMENT` add column `REPEATEDLY_DAYS` varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '多次唤醒天数';
alter table `TASK_LIST` add column `REPEATEDLY_CORRELATION_ID` varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '唤醒任务关联的listid';
alter table `TASK_LIST` add column `TASK_LIST_ID` varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '任务列表id';
alter table `TASK_LIST` add column `REPEATEDLY_INDEX` int(2) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '多次唤醒任务下标';


alter table `TASK_DETAIL` add column `REPEATEDLY_CORRELATION_ID` varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '唤醒任务关联的listid';
alter table `TASK_DETAIL` add column `REPEATEDLY_INDEX` int(2) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '多次唤醒任务下标';




CREATE TABLE `KM` (
 `ID` varchar(20)  COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
 `NAME`  varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '',
 `SCRIPT_NAMES`  varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '',
 `TASK_TOTAL`  int(10) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '',
 `SUCCEED_NUMBER`  int(10) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '',
 `FAILED_NUMBER`  int(10) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '',
 `CONFIG`  text COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '',
 `EXEC_TIME`  varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '',
 `STATE`  varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '',
 `CREATER_TIME`  datetime DEFAULT NULL COMMENT '',
 `EXTEND_FIELD1`  varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '',
 `EXTEND_FIELD2`  varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '',
 PRIMARY KEY (`ID`) , INDEX(EXEC_TIME) )ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='KM表';

CREATE TABLE `KM_LIST` (
 `ID` varchar(20)  COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
 `TASK_ID`  varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '',
 `NAME`  varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '',
 `SCRIPT_NAMES`  varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '',
 `START_TIME`  datetime DEFAULT NULL COMMENT '',
 `END_TIME`  datetime DEFAULT NULL COMMENT '',
 `TOTAL`  int(10) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '',
 `SUCCEED_NUMBER`  int(10) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '',
 `FAILED_NUMBER`  int(10) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '',
 `EXEC_TIME`  varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '',
 `STATE`  varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '',
 `CREATER_TIME`  datetime DEFAULT NULL COMMENT '',
 `EXTEND_FIELD1`  varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '',
 `EXTEND_FIELD2`  varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '',
 PRIMARY KEY (`ID`) ,KEY `EXEC_TIME` (`EXEC_TIME`),KEY `TASK_ID` (`TASK_ID`) )ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='KM_LIST表';