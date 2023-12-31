package com.shuaiwu.wsbook.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shuaiwu.wsbook.dto.TreeMenu;
import com.shuaiwu.wsbook.entity.Menu;
import com.shuaiwu.wsbook.mapper.MenuMapper;
import com.shuaiwu.wsbook.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shuaiwu
 * @since 2023-11-03
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Override
    public List<TreeMenu> queryAll() {
       return query(new HashSet<>());
    }

    @Override
    public List<TreeMenu> queryByIds(Set<Long> ids) {
        return query(ids);
    }

    private List<TreeMenu> query(Set<Long> ids){
        List<Menu> menuList = ids.isEmpty() ?
            this.list() :
            this.list(new LambdaQueryWrapper<Menu>().in(Menu::getId, ids));
        List<TreeMenu> treeMenuList = new ArrayList<>();

        for(Menu m : menuList){
            if(m.getParentId() != null){
                continue;
            }
            TreeMenu tmp = new TreeMenu();
            tmp.setChildren(new ArrayList<>());
            BeanUtil.copyProperties(m, tmp);
            treeMenuList.add(tmp);
        }

        for (TreeMenu tM : treeMenuList){
            for (Menu m: menuList){
                if(m.getParentId() == null){
                    continue;
                }
                if(tM.getId() == m.getParentId()){
                    TreeMenu tmp = new TreeMenu();
                    BeanUtil.copyProperties(m, tmp);
                    tM.getChildren().add(tmp);
                }
            }
        }

        return treeMenuList;
    }
}
