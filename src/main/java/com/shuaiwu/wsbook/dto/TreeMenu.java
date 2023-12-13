package com.shuaiwu.wsbook.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import java.util.List;
import lombok.Data;

@Data
public class TreeMenu {
    private Long id;
    private Long parentId;
    private String code;
    private String name;
    private List<TreeMenu> children;
}
