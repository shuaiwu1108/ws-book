package com.shuaiwu.wsbook.service;

import com.shuaiwu.wsbook.dto.TreeMenu;
import com.shuaiwu.wsbook.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shuaiwu
 * @since 2023-11-03
 */
public interface IMenuService extends IService<Menu> {

    List<TreeMenu> queryAll();
}
