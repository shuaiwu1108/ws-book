package com.shuaiwu.wsbook.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author shuaiwu
 * @since 2023-09-18
 */
@Getter
@Setter
@TableName("users")
public class Users extends BaseModel {
    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 菜单状态（0：禁用，1：启用）
     */
    @TableField("status")
    private String status;

    @TableField("email")
    private String email;

    @TableField("phone")
    private String phone;

    @TableField("avatar")
    private String avatar;
}
