package com.study.lecture.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.lecture.common.entity.user.Menu;
import com.study.lecture.user.mapper.MenuMapper;
import com.study.lecture.common.service.user.MenuService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author zqc
 * @since 2022-04-05
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

}
