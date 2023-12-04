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
 * 
 * </p>
 *
 * @author shuaiwu
 * @since 2023-12-04
 */
@Getter
@Setter
@TableName("book_catalog")
public class BookCatalog extends BaseModel {

    /**
     * 书籍id
     */
    @TableField("book_id")
    private Long bookId;

    /**
     * 卷名称
     */
    @TableField("roll_name")
    private String rollName;

    /**
     * 章节名称
     */
    @TableField("catalog_name")
    private String catalogName;

    /**
     * 章节地址
     */
    @TableField("catalog_url")
    private String catalogUrl;
}
