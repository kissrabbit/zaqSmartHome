/*
Navicat MySQL Data Transfer

Source Server         : localSMP
Source Server Version : 50546
Source Host           : 192.168.0.102:3306
Source Database       : zaqSmartHome

Target Server Type    : MYSQL
Target Server Version : 50546
File Encoding         : 65001

Date: 2015-12-25 21:44:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `cmd`
-- ----------------------------
DROP TABLE IF EXISTS `cmd`;
CREATE TABLE `cmd` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `function` varchar(128) DEFAULT NULL COMMENT '功能描述',
  `cmd` varchar(128) NOT NULL COMMENT '文本字令',
  `py` varchar(128) NOT NULL COMMENT '拼音 ：以拼音做查询更符合普通话不太标准和亲们',
  `type` smallint(6) NOT NULL COMMENT '发送类型 1：红外 2：无线 315M  3：无线 315M',
  `code` varchar(32) NOT NULL COMMENT '发送给设备的指令',
  `isSys` smallint(6) NOT NULL COMMENT '是否为系统的指令 1：是 0：否',
  `isDel` smallint(6) NOT NULL COMMENT '是否删除 1:是 0：否',
  `timeCreate` datetime DEFAULT NULL COMMENT '创建时间',
  `autoDelayTime` smallint(6) DEFAULT NULL COMMENT '默认指定的延时（单位s秒）',
  `autoDelayExecId` bigint(20) DEFAULT NULL COMMENT '延时执行的指令',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='指令';

-- ----------------------------
-- Records of cmd
-- ----------------------------
INSERT INTO `cmd` VALUES ('1', '开灯命令 ，编码负数表示引脚置高电平', '开灯', 'kai_deng', '4', '10', '0', '0', '2015-12-25 20:14:50', null, '2');
INSERT INTO `cmd` VALUES ('2', '关灯命令 ，编码负数表示引脚置低电平', '关灯', 'guan_deng', '4', '-10', '0', '0', '2015-12-25 20:17:35', null, null);

-- ----------------------------
-- Table structure for `cmd_log`
-- ----------------------------
DROP TABLE IF EXISTS `cmd_log`;
CREATE TABLE `cmd_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cmdId` bigint(20) DEFAULT NULL COMMENT '执行的命令',
  `timeCreate` datetime DEFAULT NULL COMMENT '执行的时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='命令日志';

-- ----------------------------
-- Records of cmd_log
-- ----------------------------

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL DEFAULT '',
  `passwrod` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------

-- ----------------------------
-- Table structure for `yuyin`
-- ----------------------------
DROP TABLE IF EXISTS `yuyin`;
CREATE TABLE `yuyin` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `path` varchar(512) DEFAULT NULL COMMENT '音频文件路径',
  `text` varchar(1000) DEFAULT NULL COMMENT '文本',
  `timeCreate` datetime DEFAULT NULL COMMENT '创建时间',
  `useTimes` tinyint(4) DEFAULT NULL COMMENT '使用次数',
  `isSys` smallint(6) DEFAULT NULL COMMENT '是否为系统资源 1:是 0：否',
  `isDel` smallint(6) DEFAULT NULL COMMENT '是否删除 1：是 0:否',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='语音缓存';

-- ----------------------------
-- Records of yuyin
-- ----------------------------
INSERT INTO `yuyin` VALUES ('1', 'sound/conver/wskadjqrnBxx1450694631043.wav', '?????????B??', '2015-12-21 18:43:59', '2', '0', '0');
INSERT INTO `yuyin` VALUES ('2', 'sound/conver/jqrswfhdnmdgndwtd1450694666906.wav', '?????????????????  ', '2015-12-21 18:44:27', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('3', 'sound/conver/rghhdwhgsnd1450694677375.wav', '????? ??????  ', '2015-12-21 18:44:37', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('4', 'sound/conver/wskaysqdznjqrnBxxy1450695104229.wav', '??????????????B???~', '2015-12-21 18:51:52', '3', '0', '0');
INSERT INTO `yuyin` VALUES ('6', 'sound/conver/xzsyr1450696168405.wav', '???12?21?', '2015-12-21 19:09:36', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('7', 'xx', '你xxx', '2015-12-21 19:50:19', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('8', 'sound/conver/xzsjs1450787979333.wav', '现在时间是:20:39:37', '2015-12-22 20:39:49', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('9', 'sound/conver/szjtdwcsyjybfj1450788035349.wav', '苏州今天大雾橙色预警，9°，阴，北风3-4级。', '2015-12-22 20:40:40', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('10', 'sound/conver/zsywbdzstbwdygjqrl1450788064589.wav', '总是有问必答，真是太！把！我！当一个机器人了！  ', '2015-12-22 20:41:05', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('11', 'sound/conver/wskaysqdznjqrnBxxy1450788084622.wav', '我是可爱又帅气的智能机器人那B小心呀~', '2015-12-22 20:41:24', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('12', 'sound/conver/nglwlmcm1450788110241.wav', '你够了。我辣么聪明  ', '2015-12-22 20:41:55', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('13', 'sound/conver/xzsyr1450788131922.wav', '现在是12月22日', '2015-12-22 20:42:12', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('14', 'sound/conver/tcmtxywfytlhsxccxzyaq1450788143793.wav', '通城明天6℃∼10℃，小雨，微风。雨天路滑视线差，出行注意安全。', '2015-12-22 20:42:24', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('15', 'sound/conver/nzxnzsdyw1450788196036.wav', '你真行，你咋啥都要问  ', '2015-12-22 20:43:21', '1', '0', '0');
