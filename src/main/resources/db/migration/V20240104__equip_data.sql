INSERT INTO `equip_type` (`id`, `type_code`, `type_name`, `create_time`, `update_time`) select 1, 'SXT', '摄像头', '2023-12-20 10:48:32', '2023-12-20 10:48:35' from dual where not exists(select 'x' from equip_type where id=1);

INSERT INTO `equip_model` (`id`, `type_id`, `model_code`, `model_name`, `create_time`, `update_time`) select 1, 1, 'HIK001', '海康球机', '2023-12-20 10:49:11', '2023-12-20 10:49:14' from dual where not exists(select 'x' from equip_model where id=1);

INSERT INTO `equip` (`id`, `model_id`, `equip_code`, `equip_name`, `access_mode`, `equip_addr`, `create_time`, `update_time`) select 1, 1, 'HIK00000001', '大厅', '200002', 'http://127.0.0.1:8080/live/bangong.flv', '2024-01-04 02:59:09', '2024-01-04 02:59:09' from dual where not exists(select 'x' from equip where id=1);

INSERT INTO `equip` (`id`, `model_id`, `equip_code`, `equip_name`, `access_mode`, `equip_addr`, `create_time`, `update_time`) select 2, 1, 'HIK00000002', '大门', '200002', 'http://127.0.0.1:8080/live/damen.flv', '2024-01-04 02:59:15', '2024-01-04 02:59:15' from dual where not exists(select 'x' from equip where id=2);
