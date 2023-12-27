package com.shuaiwu.wsbook.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.shuaiwu.wscommon.dto.BaseModel;
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

    @TableField("icon")
    private String icon;


    @TableField("icon_file_url")
    private String iconFileUrl;

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
     * 书籍类别
     */
    @TableField("book_type")
    private String bookType;

    /**
     * 作品状态
     */
    @TableField("work_status")
    private String workStatus;

    @TableField("book_source")
    private String bookSource;

    @TableField("book_source_id")
    private String bookSourceId;
}
