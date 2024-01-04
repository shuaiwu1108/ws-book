package com.shuaiwu.wsbook.controller;

import cn.hutool.core.map.MapUtil;
import com.shuaiwu.wsbook.service.IEquipService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 设备表 前端控制器
 * </p>
 *
 * @author shuaiwu
 * @since 2024-01-04
 */
@RestController
@RequestMapping("/api/equip")
public class EquipController {

    @Autowired
    private IEquipService iEquipService;

    @GetMapping("all")
    public Object all(){
        Map<Object, Object> rr = MapUtil.builder()
            .put("data", iEquipService.list())
            .put("code", 20000)
            .put("message", "查询成功")
            .build();
        return rr;
    }
}
