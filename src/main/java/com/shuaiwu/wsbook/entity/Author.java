package com.shuaiwu.wsbook.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 作者
 * </p>
 *
 * @author shuaiwu
 * @since 2023-12-02
 */
@Getter
@Setter
@TableName("author")
public class Author extends BaseModel{
    /**
     * 笔名
     */
    @TableField("author_name")
    private String authorName;

    /**
     * 作者介绍
     */
    @TableField("description")
    private String description;
}
