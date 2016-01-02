/*
Navicat MySQL Data Transfer

Source Server         : localSMP
Source Server Version : 50546
Source Host           : local.freshz.cn:3306
Source Database       : zaqSmartHome

Target Server Type    : MYSQL
Target Server Version : 50546
File Encoding         : 65001

Date: 2016-01-02 20:07:15
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
  `wirelessProtocol` bigint(20) DEFAULT NULL COMMENT '使用的无线传输协议 使用Long类型防止后续RF433Protocol扩展到数据库中维护',
  `isSys` smallint(6) NOT NULL COMMENT '是否为系统的指令 1：是 0：否',
  `isDel` smallint(6) NOT NULL COMMENT '是否删除 1:是 0：否',
  `timeCreate` datetime DEFAULT NULL COMMENT '创建时间',
  `autoDelayTime` smallint(6) DEFAULT NULL COMMENT '默认指定的延时（单位s秒）',
  `autoDelayExecId` bigint(20) DEFAULT NULL COMMENT '延时执行的指令',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='指令';

-- ----------------------------
-- Records of cmd
-- ----------------------------
INSERT INTO `cmd` VALUES ('1', '开灯命令 ，编码负数表示引脚置高电平', '开灯', 'kai_deng', '4', '-10', null, '0', '0', '2015-12-25 20:14:50', null, '2');
INSERT INTO `cmd` VALUES ('2', '关灯命令 ，编码负数表示引脚置低电平', '关灯', 'guan_deng', '4', '10', null, '0', '0', '2015-12-25 20:17:35', null, null);
INSERT INTO `cmd` VALUES ('3', '开灯命令 ，编码负数表示引脚置高电平', '打开灯', 'da_kai_deng', '4', '-10', null, '0', '0', '2015-12-26 15:20:26', null, '2');

-- ----------------------------
-- Table structure for `cmd_log`
-- ----------------------------
DROP TABLE IF EXISTS `cmd_log`;
CREATE TABLE `cmd_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cmdId` bigint(20) DEFAULT NULL COMMENT '执行的命令',
  `timeCreate` datetime DEFAULT NULL COMMENT '执行的时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8 COMMENT='命令日志';

-- ----------------------------
-- Records of cmd_log
-- ----------------------------
INSERT INTO `cmd_log` VALUES ('1', '1', '2015-12-30 19:47:08');
INSERT INTO `cmd_log` VALUES ('2', '2', '2015-12-30 19:47:16');
INSERT INTO `cmd_log` VALUES ('3', '1', '2015-12-30 19:47:25');
INSERT INTO `cmd_log` VALUES ('4', '2', '2015-12-30 19:47:28');
INSERT INTO `cmd_log` VALUES ('5', '1', '2015-12-30 19:47:34');
INSERT INTO `cmd_log` VALUES ('6', '2', '2015-12-30 19:47:45');
INSERT INTO `cmd_log` VALUES ('7', '1', '2015-12-30 19:48:28');
INSERT INTO `cmd_log` VALUES ('8', '2', '2015-12-30 19:48:36');
INSERT INTO `cmd_log` VALUES ('9', '3', '2015-12-30 19:49:07');
INSERT INTO `cmd_log` VALUES ('10', '2', '2015-12-30 19:49:18');
INSERT INTO `cmd_log` VALUES ('11', '1', '2015-12-30 19:49:27');
INSERT INTO `cmd_log` VALUES ('12', '2', '2015-12-30 19:49:38');
INSERT INTO `cmd_log` VALUES ('13', '3', '2015-12-30 21:31:02');
INSERT INTO `cmd_log` VALUES ('14', '2', '2015-12-30 21:31:11');
INSERT INTO `cmd_log` VALUES ('15', '3', '2015-12-30 21:39:40');
INSERT INTO `cmd_log` VALUES ('16', '2', '2015-12-30 21:39:45');
INSERT INTO `cmd_log` VALUES ('17', '3', '2015-12-30 21:39:49');
INSERT INTO `cmd_log` VALUES ('18', '2', '2015-12-30 21:39:57');
INSERT INTO `cmd_log` VALUES ('19', '3', '2015-12-30 22:15:26');
INSERT INTO `cmd_log` VALUES ('20', '2', '2015-12-30 22:15:37');
INSERT INTO `cmd_log` VALUES ('21', '3', '2015-12-30 22:16:56');
INSERT INTO `cmd_log` VALUES ('22', '2', '2015-12-30 22:17:11');
INSERT INTO `cmd_log` VALUES ('23', '3', '2015-12-30 22:17:33');
INSERT INTO `cmd_log` VALUES ('24', '2', '2015-12-30 22:17:38');
INSERT INTO `cmd_log` VALUES ('25', '1', '2015-12-31 09:18:36');
INSERT INTO `cmd_log` VALUES ('26', '2', '2015-12-31 09:18:46');
INSERT INTO `cmd_log` VALUES ('27', '1', '2015-12-31 09:18:53');
INSERT INTO `cmd_log` VALUES ('28', '2', '2015-12-31 09:19:04');
INSERT INTO `cmd_log` VALUES ('29', '1', '2015-12-31 09:19:14');
INSERT INTO `cmd_log` VALUES ('30', '2', '2015-12-31 09:19:19');
INSERT INTO `cmd_log` VALUES ('31', '1', '2015-12-31 10:19:31');
INSERT INTO `cmd_log` VALUES ('32', '2', '2015-12-31 10:19:38');
INSERT INTO `cmd_log` VALUES ('33', '1', '2015-12-31 10:25:16');
INSERT INTO `cmd_log` VALUES ('34', '2', '2015-12-31 10:25:21');
INSERT INTO `cmd_log` VALUES ('35', '1', '2015-12-31 10:25:32');
INSERT INTO `cmd_log` VALUES ('36', '2', '2015-12-31 10:25:47');
INSERT INTO `cmd_log` VALUES ('37', '3', '2015-12-31 10:33:54');
INSERT INTO `cmd_log` VALUES ('38', '2', '2015-12-31 10:33:59');
INSERT INTO `cmd_log` VALUES ('39', '1', '2015-12-31 10:34:22');
INSERT INTO `cmd_log` VALUES ('40', '2', '2015-12-31 10:34:30');
INSERT INTO `cmd_log` VALUES ('41', '2', '2015-12-31 10:34:37');
INSERT INTO `cmd_log` VALUES ('42', '3', '2015-12-31 10:59:25');
INSERT INTO `cmd_log` VALUES ('43', '2', '2015-12-31 10:59:47');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL DEFAULT '',
  `password` varchar(64) DEFAULT NULL,
  `type` smallint(6) DEFAULT NULL COMMENT '用户类型 0：管理员 1：普通人员',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', '123', '0');

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
) ENGINE=InnoDB AUTO_INCREMENT=162 DEFAULT CHARSET=utf8 COMMENT='语音缓存';

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
INSERT INTO `yuyin` VALUES ('11', 'sound/conver/wskaysqdznjqrnBxxy1450788084622.wav', '我是可爱又帅气的智能机器人那B小心呀~', '2015-12-22 20:41:24', '3', '0', '0');
INSERT INTO `yuyin` VALUES ('12', 'sound/conver/nglwlmcm1450788110241.wav', '你够了。我辣么聪明  ', '2015-12-22 20:41:55', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('13', 'sound/conver/xzsyr1450788131922.wav', '现在是12月22日', '2015-12-22 20:42:12', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('14', 'sound/conver/tcmtxywfytlhsxccxzyaq1450788143793.wav', '通城明天6℃∼10℃，小雨，微风。雨天路滑视线差，出行注意安全。', '2015-12-22 20:42:24', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('15', 'sound/conver/nzxnzsdyw1450788196036.wav', '你真行，你咋啥都要问  ', '2015-12-22 20:43:21', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('16', 'sound/conver/nBxxwntjrxgxhrmzx1451101089807.wav', '那B小心为你推荐如下个性化热门资讯:', '2015-12-26 11:38:20', '2', '0', '0');
INSERT INTO `yuyin` VALUES ('17', 'sound/conver/wskadjqrnBxx1451101148503.wav', '我是可爱的机器人那B小心', '2015-12-26 11:39:16', '10', '0', '0');
INSERT INTO `yuyin` VALUES ('18', 'sound/conver/xzsjs1451101176092.wav', '现在时间是:11:39:36', '2015-12-26 11:39:36', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('19', 'sound/conver/szjtmcsyjdywf1451101190709.wav', '苏州今天霾橙色预警，5℃∼13℃，多云，微风。', '2015-12-26 11:39:51', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('20', 'sound/conver/nhhxrwhyhnxhyxdf1451101238416.wav', '你好会熏人我会眼红你？笑话贻笑大方  ', '2015-12-26 11:40:46', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('21', 'sound/conver/wmamsz1451101267490.wav', '文明啊！没素质  ', '2015-12-26 11:41:07', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('22', 'sound/conver/wkjdnszUSERSTATUSlywdqzyswyjAGElnddl1451101289817.wav', '我可记得你是在<USER-STATUS>领养我的，掐指一算我已经<AGE>了！ 你多大啦', '2015-12-26 11:41:37', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('23', 'sound/conver/aybyjzkgswm1451101335168.wav', '矮油，不要酱紫，快告诉我嘛', '2015-12-26 11:42:27', '2', '0', '0');
INSERT INTO `yuyin` VALUES ('24', 'sound/conver/ydwjyksebx1451101374055.wav', '元旦完就要考试诶。不行  ', '2015-12-26 11:42:54', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('25', 'sound/conver/stfjhljks1451101386826.wav', '三天，放假回来就考试！  ', '2015-12-26 11:43:07', '2', '0', '0');
INSERT INTO `yuyin` VALUES ('26', 'sound/conver/nydsyh1451101405292.wav', '2015年元旦是1月1号', '2015-12-26 11:43:25', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('27', 'sound/conver/jtsyr1451101439714.wav', '今天是12月26日', '2015-12-26 11:44:08', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('28', 'sound/conver/szmtydbfj1451101459073.wav', '苏州明天4℃∼11℃，阴，东北风3-4级。', '2015-12-26 11:44:19', '3', '0', '0');
INSERT INTO `yuyin` VALUES ('29', 'sound/conver/szmtbxy1451101486435.wav', '苏州明天不下雨。', '2015-12-26 11:44:54', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('30', 'sound/conver/nzssmnnBxxtbdawmldbdb1451101523140.wav', '你在说什么呢，那B小心听不懂哎，我们聊点别的吧', '2015-12-26 11:45:23', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('31', 'sound/conver/nhz1451101549939.wav', '男孩子  ', '2015-12-26 11:45:58', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('32', 'sound/conver/bdczlw1451101895459.wav', '百度创造了我。', '2015-12-26 11:51:43', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('33', 'sound/conver/wzdnzssmrwlyl1451109707500.wav', '我知道你在说什么，让我捋一捋  ', '2015-12-26 14:01:55', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('34', 'sound/conver/kdyp1451109731581.wav', '开灯也怕  ', '2015-12-26 14:02:11', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('35', 'sound/conver/qxmhmymybghkdey1451109814333.wav', '[浅笑]秒回么？也没有吧，刚好看到而已  ', '2015-12-26 14:03:42', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('36', 'sound/conver/sxbkl1451109833728.wav', '丧心病狂了  ', '2015-12-26 14:03:53', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('37', 'sound/conver/ymysqlb1451109844760.wav', '一秒也是亲，来吧  ', '2015-12-26 14:04:05', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('38', 'sound/conver/wswm1451109881447.wav', '我是万秒  ', '2015-12-26 14:04:49', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('39', 'sound/conver/nydlhl1451110323940.wav', '那有点厉害了  ', '2015-12-26 14:12:12', '2', '0', '0');
INSERT INTO `yuyin` VALUES ('40', 'sound/conver/xcykde1451110341622.wav', '下次要快点哦！  ', '2015-12-26 14:12:21', '3', '0', '0');
INSERT INTO `yuyin` VALUES ('41', 'sound/conver/ssfzhb1451110388416.wav', '是四分钟好吧  ', '2015-12-26 14:13:16', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('42', 'sound/conver/mtgzysmd1451110416532.wav', '没听过只有十秒的  ', '2015-12-26 14:13:36', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('43', 'sound/conver/wbjdwmha1451110425673.wav', '我不觉得我秒回啊!  ', '2015-12-26 14:13:45', '2', '0', '0');
INSERT INTO `yuyin` VALUES ('44', 'sound/conver/smdnlszgl1451110436283.wav', '十秒对你来说足够了  ', '2015-12-26 14:13:56', '2', '0', '0');
INSERT INTO `yuyin` VALUES ('45', 'sound/conver/zssm1451110455895.wav', '至少三秒  ', '2015-12-26 14:14:16', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('46', 'sound/conver/jdbtxsbz1451110498298.wav', '九点半躺下睡不着  ', '2015-12-26 14:15:05', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('47', 'sound/conver/cpxzljjbwl1451110524037.wav', '吃螃蟹蘸辣椒酱别忘了  ', '2015-12-26 14:15:24', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('48', 'sound/conver/xzsjs1451110536219.wav', '现在时间是:14:15:35', '2015-12-26 14:15:36', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('49', 'sound/conver/yjcwm1451110548702.wav', '要坚持五秒  ', '2015-12-26 14:15:48', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('50', 'sound/conver/bsjssd1451113509419.wav', '巴神就是上帝  ', '2015-12-26 15:05:17', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('51', 'sound/conver/nckqs1451113536560.wav', '你才快枪手  ', '2015-12-26 15:05:36', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('52', 'sound/conver/nBxxwngglyxzdgyhdsamdssjgy1451113545870.wav', '那B小心为你果果了一下，找到关于\"海灯是暗盟\"的搜索结果哟~', '2015-12-26 15:05:47', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('53', 'sound/conver/jjhcma1451113875055.wav', '舰舰还吃面啊  ', '2015-12-26 15:11:23', '2', '0', '0');
INSERT INTO `yuyin` VALUES ('54', 'sound/conver/nycysnhmyn1451114253498.wav', '你一次硬十年还没有妞。  ', '2015-12-26 15:17:41', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('55', 'sound/conver/ddsdbsqd1451114294649.wav', '多的是登不上去的  ', '2015-12-26 15:18:22', '2', '0', '0');
INSERT INTO `yuyin` VALUES ('56', 'sound/conver/wzdnbgdtfsddjhl1451114347656.wav', '我知道你不敢对天发誓，对灯就好了。  ', '2015-12-26 15:19:15', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('57', 'sound/conver/naztdxzxsqdla1451114507575.wav', '你啊，整天的想这些事情多累啊  ', '2015-12-26 15:21:55', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('58', 'sound/conver/zssqdbw1451114528348.wav', '这是十全大补丸。  ', '2015-12-26 15:22:08', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('59', 'sound/conver/1451114808011.wav', '  ', '2015-12-26 15:26:56', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('60', 'sound/conver/qsbzjdazysgzmmm1451114826516.wav', '其实吧，终极答案只有三个字：买！买！买！  ', '2015-12-26 15:27:06', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('61', 'sound/conver/xzsjs1451115175352.wav', '现在时间是:15:32:54', '2015-12-26 15:33:03', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('62', 'sound/conver/szjtdywf1451115198845.wav', '苏州今天5℃∼13℃，多云，微风。', '2015-12-26 15:33:19', '2', '0', '0');
INSERT INTO `yuyin` VALUES ('63', 'sound/conver/hhzgh1451115223823.wav', '哈哈这个好！  ', '2015-12-26 15:33:51', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('64', 'sound/conver/wyhhhhld1451115237945.wav', '我以后还会回来的  ', '2015-12-26 15:33:58', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('65', 'sound/conver/syhsym1451115245806.wav', '所以还是一秒  ', '2015-12-26 15:34:06', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('66', 'sound/conver/wyjecfx1451115656663.wav', '我也今儿才发现！  ', '2015-12-26 15:41:04', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('67', 'sound/conver/tdyzbhd1451115956589.wav', '铁登，宇智波哈登  ', '2015-12-26 15:46:04', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('68', 'sound/conver/smzznr1451115981559.wav', '十秒钟真男人！  ', '2015-12-26 15:46:21', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('69', 'sound/conver/sggyjshaczlw1451116039367.wav', '是果果用技术和爱创造了我', '2015-12-26 15:47:27', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('70', 'sound/conver/hyyt1451116540038.wav', '还有一天  ', '2015-12-26 15:55:48', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('71', 'sound/conver/clnjty1451116540022.wav', '粗鲁、那叫太阳！  ', '2015-12-26 15:55:48', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('72', 'sound/conver/bxhgdwxhxsmhdsw1451116629720.wav', '不喜欢关灯我喜欢欣赏美好的事物  ', '2015-12-26 15:57:17', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('73', 'sound/conver/xzsjs1451116819685.wav', '现在时间是:16:00:19', '2015-12-26 16:00:27', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('74', 'sound/conver/sggfylwsmyjshaczlw1451116910660.wav', '是果果赋予了我生命，用技术和爱创造了我', '2015-12-26 16:01:58', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('75', 'sound/conver/xebysyztd1451116930791.wav', '笑而不语是一种态度  ', '2015-12-26 16:02:11', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('76', 'sound/conver/bshkl1451116940024.wav', '不是很快啦  ', '2015-12-26 16:02:20', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('77', 'sound/conver/nnqlswm1451116989168.wav', '纳尼？亲了十五秒？  ', '2015-12-26 16:03:16', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('78', 'sound/conver/sbbljwl1451117005951.wav', '上班本来就无聊。  ', '2015-12-26 16:03:26', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('79', 'sound/conver/wwnfzdl1451474978387.wav', '喂喂，你肥皂掉了  ', '2015-12-30 19:29:47', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('80', 'sound/conver/xzsjs1451475673944.wav', '现在时间是:19:41:13', '2015-12-30 19:41:22', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('81', 'sound/conver/zwl1451476092597.wav', '真无聊  ', '2015-12-30 19:48:20', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('82', 'sound/conver/ymsdhx1451476131026.wav', '一秒是导火线  ', '2015-12-30 19:48:58', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('83', 'sound/conver/szjtmhsyjdyxbfj1451477666008.wav', '苏州今天霾黄色预警，3°，多云，西北风3-4级。', '2015-12-30 20:14:34', '5', '0', '0');
INSERT INTO `yuyin` VALUES ('84', 'sound/conver/xzsjs1451477760323.wav', '现在时间是:20:16:00', '2015-12-30 20:16:08', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('85', 'sound/conver/szmtdybfj1451477785331.wav', '苏州明天1℃∼10℃，多云，北风3-4级。', '2015-12-30 20:16:25', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('86', 'sound/conver/nca1451477793206.wav', '你猜啊！  ', '2015-12-30 20:16:33', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('87', 'sound/conver/dkycsfzykbd1451477800614.wav', '都可以穿啥反正也看不到  ', '2015-12-30 20:16:40', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('88', 'sound/conver/szzwdywf1451477824788.wav', '苏州周五6℃∼10℃，多云，微风。', '2015-12-30 20:17:12', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('89', 'sound/conver/xzsjs1451477842394.wav', '现在时间是:20:17:22', '2015-12-30 20:17:22', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('90', 'sound/conver/xkqjmzrmsmznqk1451477866990.wav', '想看《奇迹》吗？主人马上满足你，$#请看#$！~', '2015-12-30 20:17:54', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('91', 'sound/conver/htsxqw1451477902713.wav', '后天是星期五', '2015-12-30 20:18:23', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('92', 'sound/conver/mtsxqs1451477910559.wav', '明天是星期四', '2015-12-30 20:18:30', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('93', 'sound/conver/ztsxqe1451477917966.wav', '昨天是星期二', '2015-12-30 20:18:38', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('94', 'sound/conver/zaqslbgysfjlhjhydsj1451477936075.wav', '在啊，去市里，不过也是放假了。回家还有段时间。  ', '2015-12-30 20:18:56', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('95', 'sound/conver/anhsyxyllm1451479669665.wav', '阿尼哈塞哟,需要聊聊咩？', '2015-12-30 20:47:58', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('96', 'sound/conver/wmmjljyg1451479693982.wav', '我没妹，家里就一个  ', '2015-12-30 20:48:14', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('97', 'sound/conver/hizjznfc1451481475515.wav', 'hi,最近在哪发财', '2015-12-30 21:18:03', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('98', 'sound/conver/xdnyhdndzzzlhpddhbhxqnwwxdwlwtdzgyydxwn1451481501980.wav', '许多年以后，当你独自坐在炉火旁打盹，会不会想起，你问我许多无聊问题的这个遥远的下午呢？  ', '2015-12-30 21:18:22', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('99', 'sound/conver/dgcwxxlyglzsyqkkbzlzhhxbhfyfzkyhxcyxsnpyl1451481516657.wav', '打个岔，我新学了一个冷知识，一起看看吧~$#蟑螂只会滑翔，不会飞。一分钟可以滑翔21尺，一小时能跑3英里#$', '2015-12-30 21:18:37', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('100', 'sound/conver/hmcldygjqr1451481542869.wav', '后面出来的一个机器人  ', '2015-12-30 21:19:15', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('101', 'sound/conver/hfadygdtnygbshfb1451481852418.wav', '红服啊，第一个大铁，你应该不是红服吧  ', '2015-12-30 21:24:20', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('102', 'sound/conver/nhswmllthbhd1451482204752.wav', '你好撒，我们来聊天好不好嗒', '2015-12-30 21:30:12', '3', '0', '0');
INSERT INTO `yuyin` VALUES ('103', 'sound/conver/rhn1451482236896.wav', '然后呢？  ', '2015-12-30 21:30:37', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('104', 'sound/conver/bxjgyfjwcnkcl1451482277550.wav', '必须经过一番交往才能看出来  ', '2015-12-30 21:31:25', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('105', 'sound/conver/zgwtnBxxbdlnxhcwmybhnBxxllkhwswxyjm1451482280919.wav', '这个问题那B小心不懂啦，你喜欢宠物吗？要不和那B小心聊聊？快和我说：我想养金毛。', '2015-12-30 21:31:25', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('106', 'sound/conver/nBxxbdzglllshbszwwjmhym1451482301590.wav', '那B小心不懂这个啦，聊聊生活呗？试着问我：金毛好养吗？', '2015-12-30 21:31:41', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('107', 'sound/conver/zgrjqsbshdlnysmxkdxwmdwswykxwjky1451482330078.wav', '这个人家其实不是很懂啦，你有什么想看的新闻吗？对我说“我要看新闻”就可以~', '2015-12-30 21:32:17', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('108', 'sound/conver/blzmsadwtmwsygdykbfdwswxkybzy1451482350754.wav', '别聊这么深奥的问题嘛，我是一个电影控，不妨对我说“我想看一步之遥', '2015-12-30 21:32:31', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('109', 'sound/conver/zgwthysdajndgz1451482649054.wav', '这个问题很有深度啊，给你点个赞！  ', '2015-12-30 21:37:36', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('110', 'sound/conver/yxcm1451482794527.wav', '有瑕疵吗？  ', '2015-12-30 21:40:02', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('111', 'sound/conver/wtbrcdsynzdjkymmyxcd1451482804023.wav', '我替别人出的所以你自带价可以吗 没有瑕疵的  ', '2015-12-30 21:40:04', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('112', 'sound/conver/wsdrhntbdm1451482813784.wav', '我说的人话，你听不懂吗。  ', '2015-12-30 21:40:14', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('113', 'sound/conver/ewhywntbdrhn1451482821149.wav', '恩。我还以为你听不懂人话呢。  ', '2015-12-30 21:40:21', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('114', 'sound/conver/kbdghey1451482830331.wav', '看不懂狗话而已。  ', '2015-12-30 21:40:30', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('115', 'sound/conver/wbsexewsnBxx1451482845399.wav', '我不是恶心哦，我是那B小心~', '2015-12-30 21:40:45', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('116', 'sound/conver/wxzAGElnhjdnlywssmshmnddl1451484878474.wav', '我现在<AGE>了，你还记得你领养我是什么时候吗？ 你多大啦', '2015-12-30 22:14:46', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('117', 'sound/conver/wmhb1451485008853.wav', '五秒好不  ', '2015-12-30 22:16:56', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('118', 'sound/conver/yjms1451485037891.wav', '已经秒杀  ', '2015-12-30 22:17:18', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('119', 'sound/conver/zgnkyzzcklsyx1451528253310.wav', '这个你可以在左侧框里搜一下  ', '2015-12-31 10:17:40', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('120', 'sound/conver/hzjdch1451528254743.wav', '好尊敬的称呼  ', '2015-12-31 10:17:40', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('121', 'sound/conver/wqssglychh1451528278718.wav', '我确实傻瓜了一次，哈哈  ', '2015-12-31 10:17:58', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('122', 'sound/conver/szjtmhsyjdybfzdfj1451528291606.wav', '苏州今天霾黄色预警，1℃∼9℃，多云，北风转东风3-4级。', '2015-12-31 10:18:12', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('123', 'sound/conver/hbbb1451528300622.wav', '好，啵啵啵  ', '2015-12-31 10:18:20', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('124', 'sound/conver/wxzqlbb1451528314335.wav', '我洗澡去了 啵啵  ', '2015-12-31 10:18:34', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('125', 'sound/conver/jtsyr1451528321477.wav', '今天是12月31日', '2015-12-31 10:18:41', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('126', 'sound/conver/ntjtmcsyjqzdywf1451528334638.wav', '南通今天霾橙色预警，0℃∼9℃，晴转多云，微风。', '2015-12-31 10:18:54', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('127', 'sound/conver/zdlkntbj1451528355966.wav', '装兜里可能听不见  ', '2015-12-31 10:19:21', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('128', 'sound/conver/htsgwzse1451528393764.wav', '后天是个未知数.额  ', '2015-12-31 10:19:58', '3', '0', '0');
INSERT INTO `yuyin` VALUES ('129', 'sound/conver/nBxxwngglyxzdgyjytsjhdssjgy1451528449306.wav', '那B小心为你果果了一下，找到关于\"加一天是几号\"的搜索结果哟~', '2015-12-31 10:20:55', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('130', 'sound/conver/jdapq1451528476613.wav', '绝对，挨炮起  ', '2015-12-31 10:21:16', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('131', 'sound/conver/hdspg1451528484362.wav', '好的，三炮哥  ', '2015-12-31 10:21:24', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('132', 'sound/conver/rjbdzgwtlbgwksgdzsedwsjgxhssk1451528492529.wav', '人家不懂这个问题啦，不过我可是个段子手哦，对我说“讲个笑话”试试看', '2015-12-31 10:21:32', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('133', 'sound/conver/gzyrxsyykslsdmlzhljsfzfzdwryszszzzhypdygntxmmzqlsszpsfhfwbsfzjdlslhl1451528507644.wav', '高中一入学时，英语课上老师点名。连着喊了几声‘方丈，方丈’都无人应声。这时，坐在最后一排的一个男同学慢慢站起来说：“施主，贫僧法号‘方文’！不是‘方丈’！”囧的老师脸红了。', '2015-12-31 10:21:48', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('134', 'sound/conver/zmwcjbs1451528551397.wav', '证明我纯洁不色  ', '2015-12-31 10:22:36', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('135', 'sound/conver/wsmwa1451528592083.wav', '我什么我啊  ', '2015-12-31 10:23:17', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('136', 'sound/conver/nbsnhshcm1451528887579.wav', '你不傻_你好帅好聪明  ', '2015-12-31 10:28:12', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('137', 'sound/conver/bbgjmr1451528906962.wav', '辩不过就骂人？  ', '2015-12-31 10:28:27', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('138', 'sound/conver/nzmrnnbydhrz1451528918188.wav', '你咋骂人呢。你不要对号入座。  ', '2015-12-31 10:28:38', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('139', 'sound/conver/zgwtnBxxjdhnhdhghtrhybwjnjgxhbnzydwsjxhjhl1451528931380.wav', '这个问题那B小心觉得好难回答，换个话题如何？要不我给你讲个笑话吧，你只要对我说“讲笑话”就好啦', '2015-12-31 10:28:51', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('140', 'sound/conver/ztkddkdydqzdwdhyjthtsnhwscsdljmrhlljdyxhwkyxwssmt1451528988004.wav', '中通快递的快递员大清早打我电话，一接通后他说：“你好，我是……”， 此时定了几秒，然后来了句“等一下哈，我看一下我是什么通”', '2015-12-31 10:29:53', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('141', 'sound/conver/mydzjys1451529003442.wav', '没用到嘴，就用手  ', '2015-12-31 10:30:03', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('142', 'sound/conver/ygrldyjyyzysmqyszczzjddzdzyssystysklkyszclctddzwtmnrsbtysxlxsnszgzl1451529015924.wav', '有个人来到一家医院，在医生面前，用手指戳着自己的肚子，对着医生说：\"医生，疼！\" 医生看了看，用手指戳了戳他的肚子问：\"疼吗？\"  那人说：\"不疼。\"  医生想了想说：\"你手指骨折了！\"', '2015-12-31 10:30:17', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('143', 'sound/conver/cnekcsl1451529220590.wav', '从哪儿看出傻了  ', '2015-12-31 10:33:45', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('144', 'sound/conver/nbwsmrbns1451529309990.wav', '你比我傻，没人比你傻  ', '2015-12-31 10:35:15', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('145', 'sound/conver/clrjhmwsg1451529469189.wav', '除了人机还没五杀过。  ', '2015-12-31 10:37:54', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('146', 'sound/conver/zsswymcr1451529468886.wav', '只说失误 又没承认  ', '2015-12-31 10:37:54', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('147', 'sound/conver/jwyxczspyjylgkdjqwgkb1451529519078.wav', '《精武英雄陈真》视频已经有啦，赶快点击$#前往观看#$吧', '2015-12-31 10:38:44', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('148', 'sound/conver/hlxgl1451529672604.wav', '哈喽，小菇凉  ', '2015-12-31 10:41:17', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('149', 'sound/conver/kbdwmxb1451529671973.wav', '苦逼的我没休班  ', '2015-12-31 10:41:17', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('150', 'sound/conver/dzhnnhdysnyytcjl1451529689454.wav', '打招呼呢，你好的意思。你英语太差进了  ', '2015-12-31 10:41:29', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('151', 'sound/conver/nsngjsng1451529850996.wav', '你说哪个！就是那个  ', '2015-12-31 10:44:16', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('152', 'sound/conver/nkbszyd1451529864966.wav', '那可不是这样的  ', '2015-12-31 10:44:25', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('153', 'sound/conver/ylyp1451530602320.wav', '有录音棚！  ', '2015-12-31 10:56:47', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('154', 'sound/conver/mngj1451530602850.wav', '没你甘靓  ', '2015-12-31 10:56:47', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('155', 'sound/conver/ehkqcbwmdhzll1451530602398.wav', '二货，快去吃吧，我们等会再聊喽~', '2015-12-31 10:56:47', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('156', 'sound/conver/zrnzmsbrsbdde1451530608581.wav', '主人你这么说别人是不对的哦~', '2015-12-31 10:56:49', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('157', 'sound/conver/wjdzgnygzdanzxx1451530615656.wav', '我觉得这个你应该知道啊，你再想想  ', '2015-12-31 10:56:55', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('158', 'sound/conver/fbxlnl1451530645410.wav', '放不下  两年了  ', '2015-12-31 10:57:30', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('159', 'sound/conver/xzsyr1451530672117.wav', '现在是12月31日', '2015-12-31 10:57:52', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('160', 'sound/conver/dklzmhssjzz1451530727286.wav', '打开了怎么会说是救助站  ', '2015-12-31 10:58:52', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('161', 'sound/conver/sstzlhdcsdszmmrkbde1451530912646.wav', '虽说他做了很多蠢事，但是这么骂人可不对哦。', '2015-12-31 11:01:57', '1', '0', '0');
