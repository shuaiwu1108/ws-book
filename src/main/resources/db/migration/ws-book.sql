/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1-MySql
 Source Server Type    : MySQL
 Source Server Version : 50743
 Source Host           : 127.0.0.1:3306
 Source Schema         : ws-book

 Target Server Type    : MySQL
 Target Server Version : 50743
 File Encoding         : 65001

 Date: 06/11/2023 17:30:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for flyway_schema_history
-- ----------------------------
DROP TABLE IF EXISTS `flyway_schema_history`;
CREATE TABLE `flyway_schema_history`  (
  `installed_rank` int(11) NOT NULL,
  `version` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `script` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `checksum` int(11) NULL DEFAULT NULL,
  `installed_by` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `execution_time` int(11) NOT NULL,
  `success` tinyint(1) NOT NULL,
  PRIMARY KEY (`installed_rank`) USING BTREE,
  INDEX `flyway_schema_history_s_idx`(`success`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of flyway_schema_history
-- ----------------------------
INSERT INTO `flyway_schema_history` VALUES (1, '1', '<< Flyway Baseline >>', 'BASELINE', '<< Flyway Baseline >>', NULL, 'root', '2023-10-23 08:21:01', 0, 1);

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) NULL DEFAULT NULL,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `path` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `redirect` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '重定向的指向地址',
  `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `icon` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `component` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` tinyint(4) NULL DEFAULT NULL,
  `hidden` tinyint(4) NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, NULL, NULL, '/', '/dashboard', '首页', 'dashboard', '@/views/dashboard/index', 1, 0, '2023-11-06 09:19:19', '2023-11-06 09:19:19');
INSERT INTO `menu` VALUES (3, NULL, NULL, '/system', NULL, '系统配置', 'el-icon-s-help', 'Layout', 1, 0, '2023-11-06 09:19:20', '2023-11-06 09:19:20');
INSERT INTO `menu` VALUES (4, 3, NULL, 'user', NULL, '用户管理', 'user', '@/views/system/user.vue', 1, 0, '2023-11-06 09:19:22', '2023-11-06 09:19:22');
INSERT INTO `menu` VALUES (5, 3, NULL, 'role', NULL, '角色管理', 'el-icon-s-custom', '@/views/system/role.vue', 1, 0, '2023-11-06 09:19:23', '2023-11-06 09:19:23');
INSERT INTO `menu` VALUES (6, 3, NULL, 'menu', NULL, '菜单管理', 'table', '@/views/system/menu.vue', 1, 0, '2023-11-06 09:19:24', '2023-11-06 09:19:24');
INSERT INTO `menu` VALUES (9, NULL, NULL, '/form', NULL, '表单', 'form', '@view/form/index', 1, 0, '2023-11-06 09:19:27', '2023-11-06 09:19:27');

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色 ID',
  `rolename` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `rolecode` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色编码',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态（0：禁用，1：启用）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES (1, '管理员', 'ROLE_ADMIN', 1, '2023-09-24 19:40:30', '2023-11-03 23:40:02');
INSERT INTO `roles` VALUES (2, '测试', 'ROLE_TEST', 1, '2023-11-03 08:58:40', '2023-11-03 02:19:53');
INSERT INTO `roles` VALUES (3, '开发', 'ROLE_DEV', 1, '2023-11-03 15:12:05', '2023-11-03 15:15:07');

-- ----------------------------
-- Table structure for roles_menu
-- ----------------------------
DROP TABLE IF EXISTS `roles_menu`;
CREATE TABLE `roles_menu`  (
  `role_id` bigint(20) NOT NULL,
  `menu_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of roles_menu
-- ----------------------------
INSERT INTO `roles_menu` VALUES (1, 1);
INSERT INTO `roles_menu` VALUES (1, 3);
INSERT INTO `roles_menu` VALUES (1, 4);
INSERT INTO `roles_menu` VALUES (1, 5);
INSERT INTO `roles_menu` VALUES (1, 6);
INSERT INTO `roles_menu` VALUES (1, 9);
INSERT INTO `roles_menu` VALUES (2, 1);
INSERT INTO `roles_menu` VALUES (2, 3);
INSERT INTO `roles_menu` VALUES (2, 4);
INSERT INTO `roles_menu` VALUES (2, 5);
INSERT INTO `roles_menu` VALUES (2, 6);

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户 ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态（0：禁用，1：启用）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, 'admin', 'admin', '1308802625@qq.com', '13476217396', 1, '2023-09-24 19:40:14', '2023-11-03 09:45:17');
INSERT INTO `users` VALUES (2, 'test', 'test', '1308802625@qq.com', '13476217399', 1, '2023-09-24 19:46:38', '2023-11-03 09:29:36');
INSERT INTO `users` VALUES (3, 'dev', 'dev', '1308802625@qq.com', '13476217399', 1, '2023-11-03 15:09:06', '2023-11-03 15:09:06');

-- ----------------------------
-- Table structure for users_roles
-- ----------------------------
DROP TABLE IF EXISTS `users_roles`;
CREATE TABLE `users_roles`  (
  `user_id` bigint(20) NOT NULL COMMENT '用户 ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色 ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `role_id`(`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户-角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users_roles
-- ----------------------------
INSERT INTO `users_roles` VALUES (1, 1);
INSERT INTO `users_roles` VALUES (2, 2);
INSERT INTO `users_roles` VALUES (3, 3);

SET FOREIGN_KEY_CHECKS = 1;
