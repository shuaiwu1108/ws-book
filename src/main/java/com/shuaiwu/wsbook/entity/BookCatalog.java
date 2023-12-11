package com.shuaiwu.wsbook.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Comparator;
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
public class BookCatalog extends BaseModel implements Comparable<BookCatalog> {

    /**
     * 书籍id
     */
    @TableField("book_id")
    private Long bookId;

    /**
     * 卷名称
     */
    @TableField("order_no")
    private String orderNo;

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

    /**
     * 章节文件地址
     */
    @TableField("catalog_file_url")
    private String catalogFileUrl;

    @Override
    public int compareTo(BookCatalog o) {
        return Integer.compare(Integer.parseInt(this.getOrderNo()), Integer.parseInt(o.getOrderNo()));
    }
}
