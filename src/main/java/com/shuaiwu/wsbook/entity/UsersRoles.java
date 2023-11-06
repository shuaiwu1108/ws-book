package com.shuaiwu.wsbook.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * <p>
 * 用户-角色关联表
 * </p>
 *
 * @author shuaiwu
 * @since 2023-09-18
 */
@Data
@TableName("users_roles")
public class UsersRoles implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户 ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 角色 ID
     */
    @TableField("role_id")
    private Long roleId;
}
