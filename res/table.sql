/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50521
Source Host           : localhost:3306
Source Database       : zaqsmarthome

Target Server Type    : MYSQL
Target Server Version : 50521
File Encoding         : 65001

Date: 2015-12-13 15:19:15
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
  `type` smallint(6) NOT NULL COMMENT '发送类型 1：红外 2：无线 ',
  `code` varchar(32) NOT NULL COMMENT '发送给设备的指令',
  `isSys` smallint(6) NOT NULL COMMENT '是否为系统的指令 1：是 0：否',
  `isDel` smallint(6) NOT NULL COMMENT '是否删除 1:是 0：否',
  `timeCreate` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cmd
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
  `text` varchar(128) DEFAULT NULL COMMENT '文本',
  `timeCreate` datetime DEFAULT NULL COMMENT '创建时间',
  `userTimes` tinyint(4) DEFAULT NULL COMMENT '使用次数',
  `isSys` smallint(6) DEFAULT NULL COMMENT '是否为系统资源 1:是 0：否',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of yuyin
-- ----------------------------
