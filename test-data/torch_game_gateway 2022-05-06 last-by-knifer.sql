/*
Navicat MySQL Data Transfer

Source Server         : 192.168.231.132
Source Server Version : 50728
Source Host           : 192.168.231.132:6670
Source Database       : torch_game_gateway

Target Server Type    : MYSQL
Target Server Version : 50728
File Encoding         : 65001

Date: 2022-05-06 14:56:29
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for call_log
-- ----------------------------
DROP TABLE IF EXISTS `call_log`;
CREATE TABLE `call_log` (
  `id` char(32) NOT NULL,
  `gateway_id` char(32) NOT NULL,
  `gateway_host` varchar(64) NOT NULL,
  `gateway_port` mediumint(9) NOT NULL,
  `gateway_uri` varchar(255) NOT NULL,
  `route_id` char(32) NOT NULL,
  `route_desc` varchar(255) DEFAULT NULL,
  `route_host` varchar(255) NOT NULL,
  `route_port` mediumint(9) NOT NULL,
  `route_url` varchar(255) DEFAULT NULL,
  `route_creation_datetime` datetime DEFAULT NULL,
  `route_update_datetime` datetime DEFAULT NULL,
  `service_name` varchar(255) NOT NULL,
  `success` tinyint(1) NOT NULL DEFAULT '0',
  `start_timestamp` bigint(20) NOT NULL,
  `consume_time` smallint(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of call_log
-- ----------------------------
INSERT INTO `call_log` VALUES ('1a29642730284085a74b49d200e28b2b', '1b970053a9d740e49384a47ae095670d', 'eureka8888.com', '8081', '/service/service-99', '123', '中国移动-用户协议获取服务', 'www.10086.cn', '80', 'https://www.10086.cn/web_notice/protocol/5003337_673_1937.json', null, null, 'service-99', '1', '1651763737307', '5754');
INSERT INTO `call_log` VALUES ('d3e10effd00149f4836ffb8a5ae5432f', '1b970053a9d740e49384a47ae095670d', 'eureka8888.com', '8081', '/service/service-02', '333', '本地测试服务-rpc', 'localhost', '9000', null, null, null, 'service-02', '1', '1651763756664', '7');

-- ----------------------------
-- Table structure for config
-- ----------------------------
DROP TABLE IF EXISTS `config`;
CREATE TABLE `config` (
  `id` bigint(20) NOT NULL,
  `name` varchar(64) DEFAULT NULL,
  `kee` varchar(255) NOT NULL,
  `val` varchar(255) NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of config
-- ----------------------------
INSERT INTO `config` VALUES ('1', '备用集群激活阈值', 'backup_enable_threshold', '1', null);
INSERT INTO `config` VALUES ('2', '启用限流时熔断', 'enable_fusing_on_limiting', 'true', null);
INSERT INTO `config` VALUES ('3', '熔断阈值', 'fusing_threshold', '3', null);
INSERT INTO `config` VALUES ('4', '调用失败计数周期', 'fusing_timeout', '1', null);
INSERT INTO `config` VALUES ('5', '熔断功能时间单位', 'fusing_timeunit', 'MINUTES', null);
INSERT INTO `config` VALUES ('6', '限流阈值', 'limiting_threshold', '3', '到达即触发限流');
INSERT INTO `config` VALUES ('7', '限流等待时长', 'limiting_timeout', '0', '令牌不足时，可以设置此项让请求者额外等待一段时间');
INSERT INTO `config` VALUES ('8', '限流等待时间单位', 'limiting_time_unit', 'SECONDS', null);

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
INSERT INTO `hibernate_sequence` VALUES ('8');

-- ----------------------------
-- Table structure for route
-- ----------------------------
DROP TABLE IF EXISTS `route`;
CREATE TABLE `route` (
  `id` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `extra` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `host` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `port` int(11) DEFAULT NULL,
  `protocol` int(11) DEFAULT NULL,
  `remark` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `service_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `url` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `service_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FKgqvo1b979ul6f5b26ye33br26` (`service_id`) USING BTREE,
  CONSTRAINT `FKgqvo1b979ul6f5b26ye33br26` FOREIGN KEY (`service_id`) REFERENCES `service` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of route
-- ----------------------------
INSERT INTO `route` VALUES ('bb162029506446b0adfe1f722d353318', '2022-04-30 22:57:41.394000', null, 'localhost', '1234', '0', null, 'service-01', '', '2022-04-30 22:57:41.394000', '/test', '1');
INSERT INTO `route` VALUES ('dcf7e2bede6b453cb7fee8f5407463b7', '2022-04-30 22:59:57.176000', null, 'localhost', '1235', '1', null, 'service-02', '', '2022-04-30 22:59:57.176000', '/val', '2');

-- ----------------------------
-- Table structure for service
-- ----------------------------
DROP TABLE IF EXISTS `service`;
CREATE TABLE `service` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `app` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `remark` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of service
-- ----------------------------
INSERT INTO `service` VALUES ('1', null, '2022-04-30 22:45:53.539000', 'service-01', null, '', '2022-04-30 22:45:53.539000');
INSERT INTO `service` VALUES ('2', null, '2022-04-30 22:52:22.072000', 'service-02', null, '', '2022-04-30 22:52:22.072000');
INSERT INTO `service` VALUES ('3', '测试', '2022-05-03 20:46:23.749000', 'service-89', '123', '', '2022-05-03 20:46:23.749000');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `pwd` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `username` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', null, '$2a$10$ObNqvVuRKu7cNM2F5RWd7.Z.P1tD9Xx89VOklryG8J3lrNy/F3aBq', null, 'admin', null, null);
INSERT INTO `user` VALUES ('2', null, '$2a$10$ObNqvVuRKu7cNM2F5RWd7.Z.P1tD9Xx89VOklryG8J3lrNy/F3aBq', null, 'zeyu12', null, null);

-- ----------------------------
-- Table structure for user_auth
-- ----------------------------
DROP TABLE IF EXISTS `user_auth`;
CREATE TABLE `user_auth` (
  `user_id` bigint(20) NOT NULL,
  `auth` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  KEY `FKgw89yp0rylh67df48m4hhutkq` (`user_id`) USING BTREE,
  CONSTRAINT `FKgw89yp0rylh67df48m4hhutkq` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of user_auth
-- ----------------------------
INSERT INTO `user_auth` VALUES ('1', 'admin');
INSERT INTO `user_auth` VALUES ('2', 'service-99');
INSERT INTO `user_auth` VALUES ('2', 'service-01');
INSERT INTO `user_auth` VALUES ('1', 'service-99');
INSERT INTO `user_auth` VALUES ('1', 'service-05');
INSERT INTO `user_auth` VALUES ('1', 'https-test');
INSERT INTO `user_auth` VALUES ('1', 'service-02');

-- ----------------------------
-- Table structure for user_service
-- ----------------------------
DROP TABLE IF EXISTS `user_service`;
CREATE TABLE `user_service` (
  `user_id` bigint(20) NOT NULL,
  `service_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`service_id`) USING BTREE,
  KEY `FKh3j8qx28ovbd1pxoyetawnns6` (`service_id`) USING BTREE,
  CONSTRAINT `FKh3j8qx28ovbd1pxoyetawnns6` FOREIGN KEY (`service_id`) REFERENCES `service` (`id`),
  CONSTRAINT `FKptefqjlemxwhxfuqa23ht4tos` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of user_service
-- ----------------------------
INSERT INTO `user_service` VALUES ('1', '1');
INSERT INTO `user_service` VALUES ('2', '1');
INSERT INTO `user_service` VALUES ('2', '2');
