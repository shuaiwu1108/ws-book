package com.shuaiwu.wsbook.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 书籍
 * </p>
 *
 * @author shuaiwu
 * @since 2023-12-02
 */
@Getter
@Setter
@TableName("book")
public class Book extends BaseModel {
    /**
     * 书名
     */
    @TableField("name")
    private String name;

    @TableField("url")
    private String url;

    /**
     * 作者id
     */
    @TableField("author_id")
    private Long authorId;

    /**
     * 书籍介绍
     */
    @TableField("description")
    private String description;

    /**
     * 作品状态
     */
    @TableField("work_status")
    private String workStatus;

    @TableField("catalog_size")
    private Long catalogSize;
}