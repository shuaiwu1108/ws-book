package com.shuaiwu.wsbook.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author shuaiwu
 * @since 2023-11-06
 */
@Getter
@Setter
@TableName("roles_menu")
public class RolesMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("role_id")
    private Long roleId;

    @TableField("menu_id")
    private Long menuId;
}
