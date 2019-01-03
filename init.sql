/*
Navicat MySQL Data Transfer

Target Server Type    : MYSQL
Target Server Version : 50527
File Encoding         : 65001
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `action`
-- ----------------------------
DROP TABLE IF EXISTS `action`;
CREATE TABLE `action` (
`actionId`  int(11) NOT NULL AUTO_INCREMENT ,
`actionName`  varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL ,
`description`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL ,
`projectId`  int(11) NULL DEFAULT NULL ,
`pageId`  int(11) NULL DEFAULT NULL ,
`actionType`  int(11) NOT NULL ,
`className`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL ,
`returnValue`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL ,
`creatorUid`  int(11) NULL DEFAULT NULL ,
`createTime`  datetime NULL DEFAULT NULL ,
`updatorUid`  int(11) NULL DEFAULT NULL ,
`updateTime`  datetime NULL DEFAULT NULL ,
`categoryId`  int(11) NULL DEFAULT NULL ,
PRIMARY KEY (`actionId`),
UNIQUE KEY `uniq_actionName_projectId_actionType` (`actionName`,`projectId`,`actionType`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_general_ci

;

-- ----------------------------
-- Table structure for `action_param`
-- ----------------------------
DROP TABLE IF EXISTS `action_param`;
CREATE TABLE `action_param` (
`actionParamId`  int(11) NOT NULL AUTO_INCREMENT ,
`actionParamName`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL ,
`description`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL ,
`actionId`  int(11) NOT NULL ,
PRIMARY KEY (`actionParamId`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_general_ci

;

-- ----------------------------
-- Table structure for `action_param_possible_value`
-- ----------------------------
DROP TABLE IF EXISTS `action_param_possible_value`;
CREATE TABLE `action_param_possible_value` (
`actionParamPossibleValueId`  int(11) NOT NULL AUTO_INCREMENT ,
`actionParamPossibleValue`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL ,
`description`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL ,
`actionParamId`  int(11) NOT NULL ,
PRIMARY KEY (`actionParamPossibleValueId`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_general_ci

;

-- ----------------------------
-- Table structure for `action_step`
-- ----------------------------
DROP TABLE IF EXISTS `action_step`;
CREATE TABLE `action_step` (
`actionStepId`  int(11) NOT NULL AUTO_INCREMENT ,
`actionId`  int(11) NOT NULL ,
`stepActionId`  int(11) NOT NULL ,
`actionStepName`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL ,
`evaluation`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL ,
`actionStepNumber`  int(11) NOT NULL ,
PRIMARY KEY (`actionStepId`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_general_ci

;

-- ----------------------------
-- Table structure for `action_step_param_value`
-- ----------------------------
DROP TABLE IF EXISTS `action_step_param_value`;
CREATE TABLE `action_step_param_value` (
`actionStepParamValueId`  int(11) NOT NULL AUTO_INCREMENT ,
`actionStepId`  int(11) NOT NULL ,
`actionParamId`  int(11) NULL DEFAULT NULL ,
`actionParamValue`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`actionStepParamValueId`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_general_ci

;

-- ----------------------------
-- Table structure for `action_var`
-- ----------------------------
DROP TABLE IF EXISTS `action_var`;
CREATE TABLE `action_var` (
`actionVarId`  int(11) NOT NULL AUTO_INCREMENT ,
`actionVarName`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL ,
`actionVarValue`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL ,
`description`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL ,
`actionId`  int(11) NOT NULL ,
PRIMARY KEY (`actionVarId`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_general_ci

;

-- ----------------------------
-- Table structure for `category`
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
`categoryId`  int(11) NOT NULL AUTO_INCREMENT ,
`categoryName`  varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL ,
`categoryType`  int(11) NOT NULL ,
`projectId`  int(11) NOT NULL ,
`createTime`  datetime NOT NULL ,
PRIMARY KEY (`categoryId`),
UNIQUE KEY `uniq_categoryName_categoryType_projectId` (`categoryName`,`categoryType`,`projectId`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_general_ci
;

-- ----------------------------
-- Table structure for `device`
-- ----------------------------
DROP TABLE IF EXISTS `device`;
CREATE TABLE `device` (
`deviceId`  varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL ,
`deviceName`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL ,
`phoneIp`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL ,
`agentIp`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL ,
`systemVersion`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL ,
`apiLevel`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL ,
`cpuAbi`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL ,
`cpuInfo`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL ,
`memSize`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL ,
`resolution`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL ,
`imgUrl`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL ,
`deviceType`  int(11) NULL DEFAULT NULL ,
`status`  int(11) NULL DEFAULT NULL ,
`stfStatus`  int(11) NULL DEFAULT NULL ,
`macacaStatus`  int(11) NULL DEFAULT NULL ,
`lastOnlineTime`  datetime NULL DEFAULT NULL ,
`lastOfflineTime`  datetime NULL DEFAULT NULL ,
`userName`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL ,
`createTime`  datetime NULL DEFAULT NULL ,
PRIMARY KEY (`deviceId`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_general_ci

;

-- ----------------------------
-- Table structure for `global_var`
-- ----------------------------
DROP TABLE IF EXISTS `global_var`;
CREATE TABLE `global_var` (
`globalVarId`  int(11) NOT NULL AUTO_INCREMENT ,
`globalVarName`  varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL ,
`globalVarValue`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL ,
`description`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL ,
`projectId`  int(11) NOT NULL ,
`creatorUid`  int(11) NOT NULL ,
`createTime`  datetime NOT NULL ,
`updatorUid`  int(11) NULL DEFAULT NULL ,
`updateTime`  datetime NULL DEFAULT NULL ,
PRIMARY KEY (`globalVarId`),
UNIQUE KEY `uniq_globalVarName_projectId` (`globalVarName`,`projectId`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_general_ci

;

-- ----------------------------
-- Table structure for `page`
-- ----------------------------
DROP TABLE IF EXISTS `page`;
CREATE TABLE `page` (
`pageId`  int(11) NOT NULL AUTO_INCREMENT ,
`pageName`  varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL ,
`projectId`  int(11) NOT NULL ,
`categoryId`  int(11) NOT NULL ,
`description`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL ,
`imgUrl`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL ,
`imgHeight`  int(11) NULL DEFAULT NULL ,
`imgWidth`  int(11) NULL DEFAULT NULL ,
`windowHierarchyJson`  mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL ,
`deviceId`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL ,
`creatorUid`  int(11) NOT NULL ,
`createTime`  datetime NOT NULL ,
`updatorUid`  int(11) NULL DEFAULT NULL ,
`updateTime`  datetime NULL DEFAULT NULL ,
PRIMARY KEY (`pageId`),
UNIQUE KEY `uniq_pageName_projectId` (`pageName`,`projectId`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_general_ci

;

-- ----------------------------
-- Table structure for `project`
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
`projectId`  int(11) NOT NULL AUTO_INCREMENT ,
`projectName`  varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL ,
`description`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL ,
`projectType`  int(11) NOT NULL ,
`creatorUid`  int(11) NOT NULL ,
`updatorUid`  int(11) NULL DEFAULT NULL ,
`createTime`  datetime NOT NULL ,
`updateTime`  datetime NULL DEFAULT NULL ,
PRIMARY KEY (`projectId`),
UNIQUE KEY `uniq_projectName_projectType` (`projectName`,`projectType`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_general_ci

;

-- ----------------------------
-- Table structure for `test_plan`
-- ----------------------------
DROP TABLE IF EXISTS `test_plan`;
CREATE TABLE `test_plan` (
`testPlanId`  int(11) NOT NULL AUTO_INCREMENT ,
`projectId`  int(11) NOT NULL ,
`testPlanName`  varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL ,
`description`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL ,
`creatorUid`  int(11) NULL DEFAULT NULL ,
`createTime`  datetime NULL DEFAULT NULL ,
`updatorUid`  int(11) NULL DEFAULT NULL ,
`updateTime`  datetime NULL DEFAULT NULL ,
PRIMARY KEY (`testPlanId`),
UNIQUE KEY `uniq_projectId_testPlanName` (`projectId`,`testPlanName`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_general_ci

;

-- ----------------------------
-- Table structure for `test_plan_before`
-- ----------------------------
DROP TABLE IF EXISTS `test_plan_before`;
CREATE TABLE `test_plan_before` (
`testPlanBeforeId`  int(11) NOT NULL AUTO_INCREMENT ,
`testPlanId`  int(11) NOT NULL ,
`actionId`  int(11) NOT NULL ,
`type`  int(11) NOT NULL ,
PRIMARY KEY (`testPlanBeforeId`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_general_ci

;

-- ----------------------------
-- Table structure for `test_plan_test_suite`
-- ----------------------------
DROP TABLE IF EXISTS `test_plan_test_suite`;
CREATE TABLE `test_plan_test_suite` (
`testPlanId`  int(11) NOT NULL ,
`testSuiteId`  int(11) NOT NULL ,
PRIMARY KEY (`testPlanId`, `testSuiteId`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_general_ci

;

-- ----------------------------
-- Table structure for `test_suite`
-- ----------------------------
DROP TABLE IF EXISTS `test_suite`;
CREATE TABLE `test_suite` (
`testSuiteId`  int(11) NOT NULL AUTO_INCREMENT ,
`testSuiteName`  varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL ,
`description`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL ,
`projectId`  int(11) NOT NULL ,
`creatorUid`  int(11) NULL DEFAULT NULL ,
`createTime`  datetime NULL DEFAULT NULL ,
`updatorUid`  int(11) NULL DEFAULT NULL ,
`updateTime`  datetime NULL DEFAULT NULL ,
PRIMARY KEY (`testSuiteId`),
UNIQUE KEY `uniq_testSuiteName_projectId` (`testSuiteName`,`projectId`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_general_ci

;

-- ----------------------------
-- Table structure for `test_suite_test_case`
-- ----------------------------
DROP TABLE IF EXISTS `test_suite_test_case`;
CREATE TABLE `test_suite_test_case` (
`testSuiteId`  int(11) NOT NULL ,
`testCaseId`  int(11) NOT NULL ,
PRIMARY KEY (`testSuiteId`, `testCaseId`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_general_ci

;

-- ----------------------------
-- Table structure for `test_task`
-- ----------------------------
DROP TABLE IF EXISTS `test_task`;
CREATE TABLE `test_task` (
`testTaskId`  int(11) NOT NULL AUTO_INCREMENT ,
`projectId`  int(11) NOT NULL ,
`testPlanId`  int(11) NOT NULL ,
`testTaskName`  varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL ,
`description`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL ,
`runMode`  int(11) NOT NULL ,
`status`  int(11) NULL DEFAULT NULL ,
`creatorUid`  int(11) NULL DEFAULT NULL ,
`startTime`  datetime NULL DEFAULT NULL ,
`endTime`  datetime NULL DEFAULT NULL ,
PRIMARY KEY (`testTaskId`),
UNIQUE KEY `uniq_projectId_testTaskName` (`projectId`,`testTaskName`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_general_ci

;

-- ----------------------------
-- Table structure for `test_task_device`
-- ----------------------------
DROP TABLE IF EXISTS `test_task_device`;
CREATE TABLE `test_task_device` (
`testTaskDeviceId`  int(11) NOT NULL AUTO_INCREMENT ,
`testTaskId`  int(11) NOT NULL ,
`deviceId`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL ,
`status`  int(11) NULL DEFAULT NULL ,
`startTime`  datetime NULL DEFAULT NULL ,
`endTime`  datetime NULL DEFAULT NULL ,
PRIMARY KEY (`testTaskDeviceId`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_general_ci

;

-- ----------------------------
-- Table structure for `test_task_report`
-- ----------------------------
DROP TABLE IF EXISTS `test_task_report`;
CREATE TABLE `test_task_report` (
`testTaskReportId`  int(11) NOT NULL AUTO_INCREMENT ,
`testTaskId`  int(11) NOT NULL ,
`passCount`  int(11) NULL DEFAULT NULL ,
`failCount`  int(11) NULL DEFAULT NULL ,
`skipCount`  int(11) NULL DEFAULT NULL ,
PRIMARY KEY (`testTaskReportId`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_general_ci

;

-- ----------------------------
-- Table structure for `test_task_report_test_case_record`
-- ----------------------------
DROP TABLE IF EXISTS `test_task_report_test_case_record`;
CREATE TABLE `test_task_report_test_case_record` (
`testTaskReportTestCaseRecordId`  int(11) NOT NULL AUTO_INCREMENT ,
`testTaskReportId`  int(11) NOT NULL ,
`deviceId`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL ,
`testCaseName`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL ,
`testCaseId`  int(11) NOT NULL ,
`startTime`  datetime NULL DEFAULT NULL ,
`endTime`  datetime NULL DEFAULT NULL ,
`imgUrl`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL ,
`errorInfo`  text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL ,
`videoUrl`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL ,
`status`  int(11) NULL DEFAULT NULL ,
PRIMARY KEY (`testTaskReportTestCaseRecordId`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_general_ci

;

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
`userId`  int(11) NOT NULL AUTO_INCREMENT ,
`username`  varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL ,
`password`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL ,
`nickname`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL ,
`createTime`  datetime NULL DEFAULT NULL ,
PRIMARY KEY (`userId`),
UNIQUE KEY `uniq_username` (`username`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_general_ci

;





-- records

-- 管理员
INSERT INTO `user` VALUES ('1','admin','admin','admin','2018-09-19 00:00:00');

-- 点击
INSERT INTO `action` VALUES ('1', '点击', '点击元素', null, null, '1', 'com.fgnb.actions.macaca.Click', null, null, '2018-09-19 00:00:00', null, null, null);
INSERT INTO `action_param` VALUES ('1', 'findBy', '查找方式', '1');
INSERT INTO `action_param` VALUES ('2', 'value', '查找值', '1');
INSERT INTO `action_param_possible_value` VALUES ('1', 'xpath', 'xpath查找方式', '1');
INSERT INTO `action_param_possible_value` VALUES ('2', 'id', 'id查找方式', '1');
INSERT INTO `action_param_possible_value` VALUES ('3', 'text_contains', 'text_contains查找方式', '1');
INSERT INTO `action_param_possible_value` VALUES ('4', 'name', 'name查找方式', '1');


-- 输入
INSERT INTO `action` VALUES ('2', '输入', '输入内容', null, null, '1', 'com.fgnb.actions.macaca.SendKeys', null, null, '2018-09-19 00:00:01', null, null, null);
INSERT INTO `action_param` VALUES ('3', 'findBy', '查找方式', '2');
INSERT INTO `action_param` VALUES ('4', 'value', '查找值', '2');
INSERT INTO `action_param` VALUES ('5', 'sendContent', '输入内容', '2');
INSERT INTO `action_param_possible_value` VALUES ('5', 'xpath', 'xpath查找方式', '3');
INSERT INTO `action_param_possible_value` VALUES ('6', 'id', 'id查找方式', '3');
INSERT INTO `action_param_possible_value` VALUES ('7', 'text_contains', 'text_contains查找方式', '3');
INSERT INTO `action_param_possible_value` VALUES ('8', 'name', 'name查找方式', '3');

-- 等待元素出现
INSERT INTO `action` VALUES ('3', '等待元素出现', '等待元素出现', null, null, '1', 'com.fgnb.actions.macaca.WaitForElement', null, null, '2018-09-19 00:00:02', null, null, null);
INSERT INTO `action_param` VALUES ('6', 'findBy', '查找方式', '3');
INSERT INTO `action_param` VALUES ('7', 'value', '查找值', '3');
INSERT INTO `action_param` VALUES ('8', 'timeout_second', '最大等待时间(秒)', '3');
INSERT INTO `action_param_possible_value` VALUES ('9', 'xpath', 'xpath查找方式', '6');
INSERT INTO `action_param_possible_value` VALUES ('10', 'id', 'id查找方式', '6');
INSERT INTO `action_param_possible_value` VALUES ('11', 'text_contains', 'text_contains查找方式', '6');
INSERT INTO `action_param_possible_value` VALUES ('12', 'name', 'name查找方式', '6');


-- 安卓点击返回按键
INSERT INTO `action` VALUES ('4', '[Android]点击返回按键', '点击返回按键', null, null, '1', 'com.fgnb.actions.macaca.PressBack', null, null, '2018-09-19 00:00:03', null, null, null);

-- 安卓点击Home按键
INSERT INTO `action` VALUES ('5', '[Android]点击Home按键', '点击Home按键', null, null, '1', 'com.fgnb.actions.macaca.PressHome', null, null, '2018-09-19 00:00:04', null, null, null);

-- 安卓检查toast
INSERT INTO `action` VALUES ('6', '[Android]检查toast', '检查toast', null, null, '1', 'com.fgnb.actions.macaca.CheckToast', null, null, '2018-09-19 00:00:05', null, null, null);
INSERT INTO `action_param` VALUES ('9', 'toast', '显示的toast', '6');
INSERT INTO `action_param` VALUES ('10', 'timeout_second', '最大等待时间(秒)', '6');

-- 休眠
INSERT INTO `action` VALUES ('7', '休眠', '休眠', null, null, '1', 'com.fgnb.actions.common.Sleep', null, null, '2018-09-19 00:00:06', null, null, null);
INSERT INTO `action_param` VALUES ('11', 'ms', '休眠毫秒数', '7');

-- Android清除APP数据
INSERT INTO `action` VALUES ('8', '[Android]清除APP数据', '使用shell pm clear ${packageName} 清除数据 ', null, null, '1', 'com.fgnb.actions.android.ClearAppData', null, null, '2018-09-19 00:00:07', null, null, null);
INSERT INTO `action_param` VALUES ('12', 'packageName', 'app包名', '8');

-- Android执行adb shell命令
INSERT INTO `action` VALUES ('9', '[Android]执行adb shell命令', '执行adb shell命令', null, null, '1', 'com.fgnb.actions.android.ExcuteAdbShell', null, null, '2018-09-19 00:00:08', null, null, null);
INSERT INTO `action_param` VALUES ('13', 'cmd', '命令', '9');

-- Android安装APP
INSERT INTO `action` VALUES ('10', '[Android]安装APP', '1.下载APP 2.卸载APP（如果安装了） 3.安装APP', null, null, '1', 'com.fgnb.actions.android.InstallApp', null, null, '2018-09-19 00:00:09', null, null, null);
INSERT INTO `action_param` VALUES ('14', 'appDownloadURL', 'APP下载地址', '10');
INSERT INTO `action_param` VALUES ('15', 'packageName', 'APP包名', '10');

-- 启动/重启APP
INSERT INTO `action` VALUES ('11', '[Android]启动/重启APP', '启动/重启APP', null, null, '1', 'com.fgnb.actions.android.LaunchApp', null, null, '2018-09-19 00:00:10', null, null, null);
INSERT INTO `action_param` VALUES ('16', 'packageName', 'APP包名', '11');
INSERT INTO `action_param` VALUES ('17', 'launchActivity', 'APP启动Activity名', '11');

--web
-- 打开网页
INSERT INTO `action` VALUES ('12', '[web]打开网页', '打开网页', null, null, '1', 'com.fgnb.actions.web.OpenURL', null, null, '2018-09-19 00:00:11', null, null, null);
INSERT INTO `action_param` VALUES ('18', 'url', '网页地址', '12');






















