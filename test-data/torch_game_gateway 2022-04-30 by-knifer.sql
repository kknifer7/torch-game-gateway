/*
Navicat MySQL Data Transfer

Source Server         : 192.168.231.132
Source Server Version : 50728
Source Host           : 192.168.231.132:6670
Source Database       : torch_game_gateway

Target Server Type    : MYSQL
Target Server Version : 50728
File Encoding         : 65001

Date: 2022-04-30 23:18:03
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
  `start_timestamp` bigint(20) NOT NULL DEFAULT '-1',
  `consume_time` smallint(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of call_log
-- ----------------------------
INSERT INTO `call_log` VALUES ('4b715a8a7ca4421f9ee62c8008cf8b52', 'b1e04ff22e1d45909bb3433edd7004e9', 'eureka8888.com', '8081', '/service/service-99', '123', null, 'www.10086.cn', '80', 'https://www.10086.cn/web_notice/protocol/5003337_673_1937.json', '2022-02-02 19:10:10', '2022-02-02 19:10:10', 'service-99', '1', '1651327069893', '73');
INSERT INTO `call_log` VALUES ('4e344fda6f2b4ddcb468506e1d03df6c', 'e2184dcad7fe47e8ba37bf6d3c055272', 'eureka8888.com', '8081', '/service/service-99', '123', '中国移动-用户协议获取服务', 'www.10086.cn', '80', 'https://www.10086.cn/web_notice/protocol/5003337_673_1937.json', '2022-02-02 19:10:10', '2022-02-02 19:10:10', 'service-99', '1', '1651331785525', '268');
INSERT INTO `call_log` VALUES ('5cd5c166937745fe97a2cf0db4e9ebc9', '8eac454fa62b4be8af019ba7c473df86', 'eureka8888.com', '8081', '/service/service-99', '123', '中国移动-用户协议获取服务', 'www.10086.cn', '80', 'https://www.10086.cn/web_notice/protocol/5003337_673_1937.json', '2022-02-02 19:10:10', '2022-02-02 19:10:10', 'service-99', '1', '1651331500387', '5598');
INSERT INTO `call_log` VALUES ('72ef83a519e24204bf65a0f3c4f52fe6', 'e2184dcad7fe47e8ba37bf6d3c055272', 'eureka8888.com', '8081', '/service/service-99', '123', '中国移动-用户协议获取服务', 'www.10086.cn', '80', 'https://www.10086.cn/web_notice/protocol/5003337_673_1937.json', '2022-02-02 19:10:10', '2022-02-02 19:10:10', 'service-99', '1', '1651331784962', '80');
INSERT INTO `call_log` VALUES ('77b510bc1d7b4495a7bfb33818120f33', '8eac454fa62b4be8af019ba7c473df86', 'eureka8888.com', '8081', '/service/service-99', '123', '中国移动-用户协议获取服务', 'www.10086.cn', '80', 'https://www.10086.cn/web_notice/protocol/5003337_673_1937.json', '2022-02-02 19:10:10', '2022-02-02 19:10:10', 'service-99', '1', '1651331519529', '66');
INSERT INTO `call_log` VALUES ('8518f6601139494bb4c8f0a61995cbf5', 'b1e04ff22e1d45909bb3433edd7004e9', 'eureka8888.com', '8081', '/service/service-99', '123', null, 'www.10086.cn', '80', 'https://www.10086.cn/web_notice/protocol/5003337_673_1937.json', '2022-02-02 19:10:10', '2022-02-02 19:10:10', 'service-99', '1', '1651327061101', '5625');
INSERT INTO `call_log` VALUES ('c71caf98a993447bb5999da15a875500', 'e2184dcad7fe47e8ba37bf6d3c055272', 'eureka8888.com', '8081', '/service/service-99', '123', '中国移动-用户协议获取服务', 'www.10086.cn', '80', 'https://www.10086.cn/web_notice/protocol/5003337_673_1937.json', '2022-02-02 19:10:10', '2022-02-02 19:10:10', 'service-99', '1', '1651331778523', '5672');

-- ----------------------------
-- Table structure for config
-- ----------------------------
DROP TABLE IF EXISTS `config`;
CREATE TABLE `config` (
  `kee` varchar(255) NOT NULL,
  `val` varchar(255) NOT NULL,
  PRIMARY KEY (`kee`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of config
-- ----------------------------
INSERT INTO `config` VALUES ('backup_enable_threshold', '2');
INSERT INTO `config` VALUES ('enable_fusing_on_limiting', 'true');
INSERT INTO `config` VALUES ('fusing_threshold', '3');
INSERT INTO `config` VALUES ('fusing_timeout', '1');
INSERT INTO `config` VALUES ('fusing_timeunit', 'MINUTES');
INSERT INTO `config` VALUES ('limiting_threshold', '5');
INSERT INTO `config` VALUES ('limiting_timeout', '0');
INSERT INTO `config` VALUES ('limiting_time_unit', 'SECONDS');

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of service
-- ----------------------------
INSERT INTO `service` VALUES ('1', null, '2022-04-30 22:45:53.539000', 'service-01', null, '', '2022-04-30 22:45:53.539000');
INSERT INTO `service` VALUES ('2', null, '2022-04-30 22:52:22.072000', 'service-02', null, '', '2022-04-30 22:52:22.072000');

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
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', null, '$2a$10$ObNqvVuRKu7cNM2F5RWd7.Z.P1tD9Xx89VOklryG8J3lrNy/F3aBq', null, 'kuizuo');
INSERT INTO `user` VALUES ('2', null, '$2a$10$ObNqvVuRKu7cNM2F5RWd7.Z.P1tD9Xx89VOklryG8J3lrNy/F3aBq', null, 'zeyu12');

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
