package com.shuaiwu.wsbook.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import java.util.List;
import lombok.Data;

@Data
public class TreeMenu {
    private Long id;
    private Long parentId;
    private String name;
    private String path;
    private String title;
    private String icon;
    private Byte status;
    private Byte hidden;
    private List<TreeMenu> children;
}
