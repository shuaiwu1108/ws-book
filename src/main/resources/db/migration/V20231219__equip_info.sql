CREATE TABLE IF NOT EXISTS `equip` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `model_id` bigint(20) DEFAULT NULL COMMENT '设备型号id',
    `equip_code` varchar(255) DEFAULT NULL,
    `equip_name` varchar(255) DEFAULT NULL,
    `access_mode` varchar(6) DEFAULT NULL COMMENT '接入模式，200001-设备作为客户端，200002-设备作为服务端',
    `equip_addr` varchar(1000) DEFAULT NULL COMMENT '设备地址，access_mode=200002时，该值有效，由系统请求该地址，获取数据',
    `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='设备表';

CREATE TABLE IF NOT EXISTS `equip_model` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `type_id` bigint NULL COMMENT '设别类型id',
    `model_code` varchar(255) NULL COMMENT '型号编码',
    `model_name` varchar(255) NULL COMMENT '型号名称',
    `create_time` datetime NULL,
    `update_time` datetime NULL,
    `create_by` bigint NULL,
    `update_by` bigint NULL,
    PRIMARY KEY (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='设备型号' ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `equip_type` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `type_code` varchar(255) NULL COMMENT '类型编码',
    `type_name` varchar(255) NULL COMMENT '类型名称',
    `create_time` datetime NULL,
    `update_time` datetime NULL,
    `create_by` bigint NULL,
    `update_by` bigint NULL,
    PRIMARY KEY (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='设备类型' ROW_FORMAT=DYNAMIC;

