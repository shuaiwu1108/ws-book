ALTER TABLE `author`
    CHANGE COLUMN `name` `author_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `id`;