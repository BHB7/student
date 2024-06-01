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

 Date: 31/05/2024 12:23:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `aid` varchar(11) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL COMMENT '唯一id',
  `aname` varchar(255) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL COMMENT '昵称',
  `apwd` varchar(255) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL COMMENT '密码',
  `adesc` varchar(255) CHARACTER SET gbk COLLATE gbk_chinese_ci NULL DEFAULT NULL COMMENT '描述',
  `aavatar` varchar(255) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL COMMENT '头像',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 37 CHARACTER SET = gbk COLLATE = gbk_chinese_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, '22200204', '7z测试编辑资料', '12345', '测试', 'http://sm101.test.upcdn.net/20200316232919_dgkqi.jpg463c2e4a-e825-4c5a-8f01-545e197d4b06.jpg');
INSERT INTO `admin` VALUES (2, '22200205', '管理员测试账号', '12345', NULL, '');
INSERT INTO `admin` VALUES (35, '177574792', '7z01', '12345', NULL, '');
INSERT INTO `admin` VALUES (36, '664395315', '阿萨德', '12345', NULL, '');

SET FOREIGN_KEY_CHECKS = 1;
