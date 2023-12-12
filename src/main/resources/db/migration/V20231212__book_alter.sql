ALTER TABLE `book`
    ADD COLUMN `icon_file_url` varchar(500) NULL COMMENT 'icon图片地址，minio地址' AFTER `icon`;