-- --------------------------------------------------------
-- 主机:                           192.168.1.212
-- 服务器版本:                        5.1.73 - Source distribution
-- 服务器操作系统:                      redhat-linux-gnu
-- HeidiSQL 版本:                  9.3.0.4984
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出 fmsdb 的数据库结构
DROP DATABASE IF EXISTS `fmsdb`;
CREATE DATABASE IF NOT EXISTS `fmsdb` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `fmsdb`;


-- 导出  函数 fmsdb.currval 结构
DROP FUNCTION IF EXISTS `currval`;
DELIMITER //
CREATE DEFINER=`root`@`%` FUNCTION `currval`(v_seq_name VARCHAR(50)) RETURNS int(11)
begin 
    declare value integer; 
    set value = 0; 
    select current_val into value 
    from t_sequence
    where seq_name = v_seq_name; 
    return value; 
end//
DELIMITER ;


-- 导出  函数 fmsdb.nextval 结构
DROP FUNCTION IF EXISTS `nextval`;
DELIMITER //
CREATE DEFINER=`root`@`%` FUNCTION `nextval`(v_seq_name VARCHAR(50)) RETURNS int(11)
begin
update t_sequence
set current_val=current_val+increment_val
where seq_name=v_seq_name;
return currval(v_seq_name); 
end//
DELIMITER ;


-- 导出  函数 fmsdb.setval 结构
DROP FUNCTION IF EXISTS `setval`;
DELIMITER //
CREATE DEFINER=`root`@`%` FUNCTION `setval`(v_seq_name VARCHAR(50), v_new_val INTEGER) RETURNS int(11)
begin 
  update t_sequence
  set current_val = v_new_val  
  where seq_name = v_seq_name;  
return currval(seq_name);
end//
DELIMITER ;


-- 导出  表 fmsdb.t_font 结构
DROP TABLE IF EXISTS `t_font`;
CREATE TABLE IF NOT EXISTS `t_font` (
  `fontid` int(11) NOT NULL AUTO_INCREMENT,
  `fontname` varchar(255) DEFAULT NULL COMMENT '字体名称',
  `downurl` varchar(255) DEFAULT NULL COMMENT '字体文件下载地址',
  `filesize` varchar(255) DEFAULT NULL COMMENT '字体文件大小',
  `userid` int(11) DEFAULT NULL COMMENT '上传用户id，关联表t_userinfo',
  `fonttags` varchar(255) DEFAULT NULL COMMENT '字体标签，用逗号分隔',
  `fontdesc` varchar(2048) DEFAULT NULL COMMENT '字体描述',
  `fonticon` varchar(255) DEFAULT NULL COMMENT '字体icon',
  `fontimg` varchar(255) DEFAULT NULL COMMENT '字体图片',
  `createtime` timestamp NULL DEFAULT NULL,
  `updatetime` timestamp NULL DEFAULT NULL,
  `downloadtimes` int(11) DEFAULT '0' COMMENT '下载次数',
  `wholerank` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`fontid`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8 COMMENT='字体表';

-- 正在导出表  fmsdb.t_font 的数据：~3 rows (大约)
DELETE FROM `t_font`;
/*!40000 ALTER TABLE `t_font` DISABLE KEYS */;
INSERT INTO `t_font` (`fontid`, `fontname`, `downurl`, `filesize`, `userid`, `fonttags`, `fontdesc`, `fonticon`, `fontimg`, `createtime`, `updatetime`, `downloadtimes`, `wholerank`) VALUES
	(11, '楷体', 'http://fontms.51changmi.com/download/font/fontfile/1474524445239-628.pdf', '33.69M', 1, '正楷,厚重', '方正楷体', 'http://fontms.51changmi.com/download/font/icon/1474523499692759.jpg', 'http://fontms.51changmi.com/download/font/img/1474523500401325.jpg', '2016-09-22 13:51:41', '2016-09-27 16:59:04', 0, 3),
	(12, '黑体', 'http://fontms.51changmi.com/download/font/fontfile/1474523587462771.gz', '1.30M', 1, '经典,美观', '黑体是标准体', 'http://fontms.51changmi.com/download/font/icon/1474523588605-60.jpg', 'http://fontms.51changmi.com/download/font/img/1474523589397-954.jpg', '2016-09-22 13:53:10', '2016-09-22 13:53:10', 0, 1),
	(13, '花体', 'http://fontms.51changmi.com/download/font/fontfile/1474523682839359.gz', '1.30M', 1, '90后,新潮,灵动', '美观而又俏皮的字体', 'http://fontms.51changmi.com/download/font/icon/1474523683999-121.jpg', 'http://fontms.51changmi.com/download/font/img/1474523684844-295.jpg', '2016-09-22 13:54:45', '2016-09-22 13:54:45', 0, 2);
/*!40000 ALTER TABLE `t_font` ENABLE KEYS */;


-- 导出  表 fmsdb.t_menu 结构
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE IF NOT EXISTS `t_menu` (
  `menuid` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单编号',
  `menuname` varchar(500) DEFAULT NULL COMMENT '菜单名称',
  `menutype` int(11) DEFAULT NULL COMMENT '菜单类型',
  `uri` varchar(500) DEFAULT NULL COMMENT 'uri',
  `visible` int(11) DEFAULT NULL COMMENT '是否显示',
  `parentid` int(11) DEFAULT NULL COMMENT '父编号',
  `creatorid` int(11) DEFAULT NULL COMMENT '添加人',
  `adddate` date DEFAULT NULL COMMENT '添加日期',
  `icon` varchar(300) DEFAULT NULL COMMENT 'icon',
  `corder` int(11) DEFAULT NULL COMMENT '排序',
  `menulevel` int(11) DEFAULT NULL COMMENT '菜单级别',
  PRIMARY KEY (`menuid`)
) ENGINE=MyISAM AUTO_INCREMENT=136 DEFAULT CHARSET=utf8 COMMENT='系统菜单表';

-- 正在导出表  fmsdb.t_menu 的数据：10 rows
DELETE FROM `t_menu`;
/*!40000 ALTER TABLE `t_menu` DISABLE KEYS */;
INSERT INTO `t_menu` (`menuid`, `menuname`, `menutype`, `uri`, `visible`, `parentid`, `creatorid`, `adddate`, `icon`, `corder`, `menulevel`) VALUES
	(1, '系统设置', NULL, '', 1, 0, 1, '2016-07-06', 'fa-cogs', 2, 1),
	(2, '菜单管理', NULL, '/menu/index', 1, 1, 1, '2016-07-06', NULL, 3, 2),
	(3, '字体管理', NULL, '', 1, 0, 1, '2016-07-06', 'fa-android', 3, 1),
	(4, '所有字体', NULL, '/font/index', 1, 3, 1, '2016-07-06', '', 1, 2),
	(9, '用户管理', NULL, '/user/usermng', 1, 1, 1, '2016-07-06', NULL, 1, 2),
	(10, '日志管理', NULL, '/log/index', 1, 1, 1, '2016-07-06', NULL, 4, 2),
	(134, '精彩推荐', NULL, '/recommend/index', 1, 3, 1, '2016-09-27', '', 2, 2),
	(135, '轮播字体', NULL, '/rotation/index', 1, 3, 1, '2016-09-27', '', 3, 2),
	(76, '角色管理', NULL, '/role/index', 1, 1, 1, '2016-07-14', NULL, 2, 2),
	(127, '修改密码', NULL, '/user/changepwd', 1, 0, 1, '2016-08-08', 'fa-diamond', 1, 1);
/*!40000 ALTER TABLE `t_menu` ENABLE KEYS */;


-- 导出  表 fmsdb.t_recommend 结构
DROP TABLE IF EXISTS `t_recommend`;
CREATE TABLE IF NOT EXISTS `t_recommend` (
  `recommendid` int(11) NOT NULL AUTO_INCREMENT,
  `fontid` int(11) DEFAULT NULL COMMENT '字体ID，关联t_font',
  `recommendtype` int(11) DEFAULT NULL COMMENT '推荐类型：0行推荐 1列推荐',
  `rank` int(11) DEFAULT NULL COMMENT '（行或列下的）排序',
  PRIMARY KEY (`recommendid`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COMMENT='精品推荐表';

-- 正在导出表  fmsdb.t_recommend 的数据：~3 rows (大约)
DELETE FROM `t_recommend`;
/*!40000 ALTER TABLE `t_recommend` DISABLE KEYS */;
INSERT INTO `t_recommend` (`recommendid`, `fontid`, `recommendtype`, `rank`) VALUES
	(19, 11, 0, 1),
	(21, 12, 0, 2),
	(23, 13, 1, 1);
/*!40000 ALTER TABLE `t_recommend` ENABLE KEYS */;


-- 导出  表 fmsdb.t_role 结构
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE IF NOT EXISTS `t_role` (
  `roleid` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `rolename` varchar(255) DEFAULT NULL COMMENT '角色名称',
  `creatorid` int(11) DEFAULT NULL COMMENT '创建用户ID',
  `adddate` datetime DEFAULT NULL COMMENT '创建时间',
  `state` int(11) DEFAULT NULL COMMENT '是否启用',
  PRIMARY KEY (`roleid`)
) ENGINE=MyISAM AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- 正在导出表  fmsdb.t_role 的数据：3 rows
DELETE FROM `t_role`;
/*!40000 ALTER TABLE `t_role` DISABLE KEYS */;
INSERT INTO `t_role` (`roleid`, `rolename`, `creatorid`, `adddate`, `state`) VALUES
	(1, '超级管理员', 1, '2016-07-14 15:13:08', 1),
	(12, '桌面定制测试', 1, '2016-08-17 17:50:02', 1),
	(13, '产品测试', 1, '2016-08-23 09:25:28', 1);
/*!40000 ALTER TABLE `t_role` ENABLE KEYS */;


-- 导出  表 fmsdb.t_role_menu 结构
DROP TABLE IF EXISTS `t_role_menu`;
CREATE TABLE IF NOT EXISTS `t_role_menu` (
  `rolemenuid` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色菜单ID',
  `roleid` int(11) DEFAULT NULL COMMENT '角色ID（管理t_role）',
  `menuid` int(11) DEFAULT NULL COMMENT '菜单ID（关联t_menu）',
  PRIMARY KEY (`rolemenuid`)
) ENGINE=MyISAM AUTO_INCREMENT=249 DEFAULT CHARSET=utf8 COMMENT='角色菜单表';

-- 正在导出表  fmsdb.t_role_menu 的数据：55 rows
DELETE FROM `t_role_menu`;
/*!40000 ALTER TABLE `t_role_menu` DISABLE KEYS */;
INSERT INTO `t_role_menu` (`rolemenuid`, `roleid`, `menuid`) VALUES
	(188, 1, 88),
	(187, 1, 87),
	(186, 1, 86),
	(185, 1, 85),
	(16, 2, 3),
	(17, 2, 4),
	(248, 1, 135),
	(172, 1, 4),
	(171, 1, 3),
	(247, 1, 134),
	(110, 10, 4),
	(109, 10, 3),
	(74, 9, 76),
	(243, 1, 10),
	(245, 1, 2),
	(166, 1, 76),
	(242, 1, 9),
	(244, 1, 1),
	(191, 1, 91),
	(192, 1, 92),
	(193, 1, 93),
	(194, 1, 94),
	(195, 1, 95),
	(196, 1, 96),
	(197, 1, 97),
	(198, 1, 98),
	(199, 1, 99),
	(200, 1, 100),
	(201, 1, 101),
	(202, 1, 102),
	(203, 1, 103),
	(204, 1, 104),
	(205, 1, 105),
	(206, 1, 106),
	(207, 1, 107),
	(208, 1, 108),
	(209, 1, 109),
	(210, 1, 110),
	(211, 1, 111),
	(212, 1, 112),
	(213, 1, 113),
	(214, 1, 114),
	(215, 1, 115),
	(216, 1, 116),
	(217, 1, 117),
	(218, 1, 118),
	(219, 1, 119),
	(220, 1, 120),
	(221, 1, 121),
	(222, 1, 122),
	(223, 1, 123),
	(224, 1, 124),
	(225, 1, 125),
	(226, 1, 126),
	(227, 1, 127);
/*!40000 ALTER TABLE `t_role_menu` ENABLE KEYS */;


-- 导出  表 fmsdb.t_rotation 结构
DROP TABLE IF EXISTS `t_rotation`;
CREATE TABLE IF NOT EXISTS `t_rotation` (
  `rotationid` int(11) NOT NULL AUTO_INCREMENT,
  `fontid` int(11) DEFAULT NULL COMMENT '字体ID，关联表t_font',
  `rank` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`rotationid`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='轮播字体表';

-- 正在导出表  fmsdb.t_rotation 的数据：~1 rows (大约)
DELETE FROM `t_rotation`;
/*!40000 ALTER TABLE `t_rotation` DISABLE KEYS */;
INSERT INTO `t_rotation` (`rotationid`, `fontid`, `rank`) VALUES
	(8, 12, 1);
/*!40000 ALTER TABLE `t_rotation` ENABLE KEYS */;


-- 导出  表 fmsdb.t_sequence 结构
DROP TABLE IF EXISTS `t_sequence`;
CREATE TABLE IF NOT EXISTS `t_sequence` (
  `seq_name` varchar(50) NOT NULL,
  `current_val` int(11) NOT NULL DEFAULT '0',
  `increment_val` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`seq_name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 正在导出表  fmsdb.t_sequence 的数据：1 rows
DELETE FROM `t_sequence`;
/*!40000 ALTER TABLE `t_sequence` DISABLE KEYS */;
INSERT INTO `t_sequence` (`seq_name`, `current_val`, `increment_val`) VALUES
	('t_font', 43, 1);
/*!40000 ALTER TABLE `t_sequence` ENABLE KEYS */;


-- 导出  表 fmsdb.t_userinfo 结构
DROP TABLE IF EXISTS `t_userinfo`;
CREATE TABLE IF NOT EXISTS `t_userinfo` (
  `userid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `nikename` varchar(255) DEFAULT NULL,
  `usericon` varchar(255) DEFAULT NULL,
  `logintime` datetime DEFAULT NULL COMMENT '登录时间',
  `lastlogintime` datetime DEFAULT NULL COMMENT '上次登录时间',
  `loginip` varchar(255) DEFAULT NULL COMMENT '本次登录IP',
  `state` int(11) DEFAULT NULL,
  `creatorid` int(11) DEFAULT NULL,
  `adddate` datetime DEFAULT NULL COMMENT '创建时间',
  `password` varchar(255) NOT NULL,
  `usertype` int(11) DEFAULT NULL,
  `logintimes` int(11) DEFAULT NULL COMMENT '登录次数',
  `roleid` int(11) DEFAULT NULL COMMENT '角色ID（关联t_role）',
  `lastloginip` varchar(255) DEFAULT NULL COMMENT '上次登录IP',
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

-- 正在导出表  fmsdb.t_userinfo 的数据：~7 rows (大约)
DELETE FROM `t_userinfo`;
/*!40000 ALTER TABLE `t_userinfo` DISABLE KEYS */;
INSERT INTO `t_userinfo` (`userid`, `username`, `nikename`, `usericon`, `logintime`, `lastlogintime`, `loginip`, `state`, `creatorid`, `adddate`, `password`, `usertype`, `logintimes`, `roleid`, `lastloginip`) VALUES
	(1, 'admin', '超级管理员', NULL, '2016-09-27 23:52:56', '2016-09-28 12:38:53', '192.168.79.1', 1, NULL, NULL, 'e10adc3949ba59abbe56e057f20f883e', 1, 1026, 1, '0:0:0:0:0:0:0:1'),
	(17, 'test1', '测试1', NULL, '2016-07-13 17:02:21', '2016-07-13 16:56:42', '0:0:0:0:0:0:0:1', 1, 1, '2016-07-13 16:15:41', 'c4ca4238a0b923820dcc509a6f75849b', 0, 4, 1, '0:0:0:0:0:0:0:1'),
	(19, 'a', 'a', NULL, '2016-07-15 21:31:10', '2016-07-15 21:20:52', '0:0:0:0:0:0:0:1', 1, 1, '2016-07-15 20:41:50', 'c4ca4238a0b923820dcc509a6f75849b', 1, 7, 8, '0:0:0:0:0:0:0:1'),
	(24, 'dingzhi', '', NULL, '2016-09-01 12:14:09', '2016-09-01 11:50:29', '127.0.0.1', 1, 1, '2016-08-17 17:50:38', '10bacce7badb004cbf85f789224f630e', 1, 37, 12, '127.0.0.1'),
	(25, 'chanpin', '', NULL, '2016-08-25 18:53:25', '2016-08-23 09:26:13', '127.0.0.1', 1, 1, '2016-08-23 09:25:54', 'bfbe0ad3b8ee986d4dbef44e7df51071', 1, 2, 13, '127.0.0.1'),
	(27, 'launcher', '', NULL, '2016-09-01 10:22:19', '2016-08-31 13:57:58', '127.0.0.1', 1, 1, '2016-08-25 09:04:47', 'f3e08b5119358a8f58f17a678759f60c', 1, 25, 12, '127.0.0.1'),
	(28, 'zmtest', '', NULL, '2016-09-02 10:24:12', '2016-09-01 14:53:17', '127.0.0.1', 1, 1, '2016-08-29 15:56:09', 'df80bb0a38d09c32080cffbcd8f062e7', 1, 17, 12, '127.0.0.1');
/*!40000 ALTER TABLE `t_userinfo` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
