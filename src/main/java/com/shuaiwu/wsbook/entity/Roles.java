package com.shuaiwu.wsbook.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
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
public class Roles extends BaseModel {
    /**
     * 角色名称
     */
    @TableField("rolename")
    private String rolename;

    /**
     * 角色编码
     */
    @TableField("rolecode")
    private String rolecode;

    /**
     * 菜单状态（0：禁用，1：启用）
     */
    @TableField("status")
    private String status;
}
