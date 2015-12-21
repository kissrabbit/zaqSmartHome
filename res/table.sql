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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='指令';

-- ----------------------------
-- Records of cmd
-- ----------------------------

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='语音缓存';

-- ----------------------------
-- Records of yuyin
-- ----------------------------
