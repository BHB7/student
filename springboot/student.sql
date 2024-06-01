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

 Date: 31/05/2024 12:24:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sid` varchar(20) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL COMMENT '学号',
  `sname` varchar(20) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL COMMENT '学生名字',
  `ssex` varchar(20) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL DEFAULT '男' COMMENT '学生性别 0男 1女',
  `sclass` varchar(20) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL COMMENT '班级',
  `spwd` varchar(255) CHARACTER SET euckr COLLATE euckr_korean_ci NULL DEFAULT '888888' COMMENT '学生密码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = gbk COLLATE = gbk_chinese_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (10, '22200209', '阿萨德', '1', '22206', NULL);
INSERT INTO `student` VALUES (12, '22200266', 'JJ', '0', '22206', NULL);
INSERT INTO `student` VALUES (18, '22200204', '小花', '1', '22205', NULL);
INSERT INTO `student` VALUES (20, '22200221', '大大怪', '1', '22205', NULL);
INSERT INTO `student` VALUES (21, '22200211', '小哈哈', '0', '22205', NULL);
INSERT INTO `student` VALUES (25, '22200222', '小哈', '0', '22201', NULL);
INSERT INTO `student` VALUES (26, '22200223', 'xiaosqwqw', '1', '22201', NULL);
INSERT INTO `student` VALUES (28, '22200201', '测试', '1', '22202', NULL);
INSERT INTO `student` VALUES (29, '22200200', '石川俊', '1', '22202', NULL);

SET FOREIGN_KEY_CHECKS = 1;
