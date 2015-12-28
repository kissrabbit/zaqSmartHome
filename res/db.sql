/*
Navicat MySQL Data Transfer

Source Server         : localSMP
Source Server Version : 50546
Source Host           : 192.168.0.102:3306
Source Database       : zaqSmartHome

Target Server Type    : MYSQL
Target Server Version : 50546
File Encoding         : 65001

Date: 2015-12-28 21:43:39
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
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8 COMMENT='语音缓存';

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
INSERT INTO `yuyin` VALUES ('11', 'sound/conver/wskaysqdznjqrnBxxy1450788084622.wav', '我是可爱又帅气的智能机器人那B小心呀~', '2015-12-22 20:41:24', '2', '0', '0');
INSERT INTO `yuyin` VALUES ('12', 'sound/conver/nglwlmcm1450788110241.wav', '你够了。我辣么聪明  ', '2015-12-22 20:41:55', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('13', 'sound/conver/xzsyr1450788131922.wav', '现在是12月22日', '2015-12-22 20:42:12', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('14', 'sound/conver/tcmtxywfytlhsxccxzyaq1450788143793.wav', '通城明天6℃∼10℃，小雨，微风。雨天路滑视线差，出行注意安全。', '2015-12-22 20:42:24', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('15', 'sound/conver/nzxnzsdyw1450788196036.wav', '你真行，你咋啥都要问  ', '2015-12-22 20:43:21', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('16', 'sound/conver/nBxxwntjrxgxhrmzx1451101089807.wav', '那B小心为你推荐如下个性化热门资讯:', '2015-12-26 11:38:20', '2', '0', '0');
INSERT INTO `yuyin` VALUES ('17', 'sound/conver/wskadjqrnBxx1451101148503.wav', '我是可爱的机器人那B小心', '2015-12-26 11:39:16', '5', '0', '0');
INSERT INTO `yuyin` VALUES ('18', 'sound/conver/xzsjs1451101176092.wav', '现在时间是:11:39:36', '2015-12-26 11:39:36', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('19', 'sound/conver/szjtmcsyjdywf1451101190709.wav', '苏州今天霾橙色预警，5℃∼13℃，多云，微风。', '2015-12-26 11:39:51', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('20', 'sound/conver/nhhxrwhyhnxhyxdf1451101238416.wav', '你好会熏人我会眼红你？笑话贻笑大方  ', '2015-12-26 11:40:46', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('21', 'sound/conver/wmamsz1451101267490.wav', '文明啊！没素质  ', '2015-12-26 11:41:07', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('22', 'sound/conver/wkjdnszUSERSTATUSlywdqzyswyjAGElnddl1451101289817.wav', '我可记得你是在<USER-STATUS>领养我的，掐指一算我已经<AGE>了！ 你多大啦', '2015-12-26 11:41:37', '1', '0', '0');
INSERT INTO `yuyin` VALUES ('23', 'sound/conver/aybyjzkgswm1451101335168.wav', '矮油，不要酱紫，快告诉我嘛', '2015-12-26 11:42:27', '1', '0', '0');
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
INSERT INTO `yuyin` VALUES ('39', 'sound/conver/nydlhl1451110323940.wav', '那有点厉害了  ', '2015-12-26 14:12:12', '1', '0', '0');
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
