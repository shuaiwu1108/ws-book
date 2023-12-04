
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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
