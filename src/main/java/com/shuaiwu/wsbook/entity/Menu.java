package com.shuaiwu.wsbook.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author shuaiwu
 * @since 2023-11-03
 */
@Data
@TableName("menu")
public class Menu extends BaseModel {
    @TableField("parent_id")
    private Long parentId;

    @TableField("name")
    private String name;

    @TableField("path")
    private String path;

    @TableField("redirect")
    private String redirect;

    @TableField("title")
    private String title;

    @TableField("icon")
    private String icon;

    @TableField("component")
    private String component;

    @TableField("status")
    private String status;

    @TableField("hidden")
    private String hidden;
}
