SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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
INSERT INTO `roles` VALUES (1, '管理员', 'ROLE_ADMIN', 1, '2023-09-24 19:40:30', '2023-11-03 02:28:55');
INSERT INTO `roles` VALUES (2, '测试', 'ROLE_TEST', 1, '2023-11-03 08:58:40', '2023-11-03 02:19:53');
INSERT INTO `roles` VALUES (3, '开发', 'ROLE_DEV', 1, '2023-11-03 15:12:05', '2023-11-03 15:15:07');

-- ----------------------------
-- Table structure for user_roles
-- ----------------------------
DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE `user_roles`  (
                               `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户-角色关联 ID',
                               `user_id` bigint(20) NOT NULL COMMENT '用户 ID',
                               `role_id` bigint(20) NOT NULL COMMENT '角色 ID',
                               PRIMARY KEY (`id`) USING BTREE,
                               INDEX `user_id`(`user_id`) USING BTREE,
                               INDEX `role_id`(`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户-角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_roles
-- ----------------------------
INSERT INTO `user_roles` VALUES (2, 2, 2);
INSERT INTO `user_roles` VALUES (4, 1, 1);
INSERT INTO `user_roles` VALUES (6, 3, 3);

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
                          `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户图标',
                          `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态（0：禁用，1：启用）',
                          `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                          `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                          PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, 'admin', 'admin', '1308802625@qq.com', '13476217399', null, 1, '2023-09-24 19:40:14', '2023-11-03 09:45:17');
INSERT INTO `users` VALUES (2, 'test', 'test', '1308802625@qq.com', '13476217399', null, 1, '2023-09-24 19:46:38', '2023-11-03 09:29:36');
INSERT INTO `users` VALUES (3, 'dev', 'dev', '1308802625@qq.com', '13476217399', null, 1, '2023-11-03 15:09:06', '2023-11-03 15:09:06');

SET FOREIGN_KEY_CHECKS = 1;
