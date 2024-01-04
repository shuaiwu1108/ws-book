package com.shuaiwu.wsbook.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 设备表
 * </p>
 *
 * @author shuaiwu
 * @since 2024-01-04
 */
@Getter
@Setter
@TableName("equip")
public class Equip implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 设备型号id
     */
    @TableField("model_id")
    private Long modelId;

    @TableField("equip_code")
    private String equipCode;

    @TableField("equip_name")
    private String equipName;

    /**
     * 接入模式，200001-设备作为客户端，200002-设备作为服务端
     */
    @TableField("access_mode")
    private String accessMode;

    /**
     * 设备地址，access_mode=200002时，该值有效，由系统请求该地址，获取数据
     */
    @TableField("equip_addr")
    private String equipAddr;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;
}
