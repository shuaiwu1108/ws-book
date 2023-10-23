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
 * 角色表
 * </p>
 *
 * @author shuaiwu
 * @since 2023-09-18
 */
@Getter
@Setter
@TableName("roles")
public class Roles implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色 ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 角色名称
     */
    @TableField("rolename")
    private String rolename;

    /**
     * 角色描述
     */
    @TableField("description")
    private String description;

    /**
     * 菜单状态（0：禁用，1：启用）
     */
    @TableField("status")
    private Byte status;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;
}
