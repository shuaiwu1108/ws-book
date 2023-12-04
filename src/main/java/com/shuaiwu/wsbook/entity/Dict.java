package com.shuaiwu.wsbook.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 字典
 * </p>
 *
 * @author shuaiwu
 * @since 2023-12-02
 */
@Getter
@Setter
@TableName("dict")
public class Dict implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * key
     */
    @TableField("dict_code")
    private String dictCode;

    /**
     * value
     */
    @TableField("dict_name")
    private String dictName;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;
}
