/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 50724 (5.7.24)
 Source Host           : localhost:3306
 Source Schema         : student

 Target Server Type    : MySQL
 Target Server Version : 50724 (5.7.24)
 File Encoding         : 65001

 Date: 31/05/2024 12:23:52
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for mclasses
-- ----------------------------
DROP TABLE IF EXISTS `mclasses`;
CREATE TABLE `mclasses`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `aid` varchar(255) CHARACTER SET gbk COLLATE gbk_chinese_ci NULL DEFAULT NULL COMMENT '管理员ids',
  `classes` varchar(255) CHARACTER SET gbk COLLATE gbk_chinese_ci NULL DEFAULT NULL COMMENT '班级',
  `cont` varchar(255) CHARACTER SET gbk COLLATE gbk_chinese_ci NULL DEFAULT NULL COMMENT '班级描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = gbk COLLATE = gbk_chinese_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mclasses
-- ----------------------------
INSERT INTO `mclasses` VALUES (17, '22200204', '22206', '软件技术');
INSERT INTO `mclasses` VALUES (21, '22200204', '22201', '大数据人工智能');
INSERT INTO `mclasses` VALUES (22, '177574792', '22206', '软件技术');
INSERT INTO `mclasses` VALUES (23, '177574792', '7777777', 'asdasd');
INSERT INTO `mclasses` VALUES (24, '664395315', '22206', '软件技术');
INSERT INTO `mclasses` VALUES (25, '664395315', '22201', '大数据人工智能');
INSERT INTO `mclasses` VALUES (28, '664395315', '22202', '信息技术');
INSERT INTO `mclasses` VALUES (29, '664395315', '22205', '软件技术5');
INSERT INTO `mclasses` VALUES (30, '22200204', '22202', '软件工程');
INSERT INTO `mclasses` VALUES (31, '22200204', '22222', '超级尖子班');

SET FOREIGN_KEY_CHECKS = 1;
