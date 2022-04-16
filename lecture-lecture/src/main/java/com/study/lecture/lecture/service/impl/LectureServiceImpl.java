package com.study.lecture.lecture.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.lecture.common.entity.lecture.Lecture;
import com.study.lecture.common.entity.user.LoginUser;
import com.study.lecture.common.utils.R;
import com.study.lecture.common.vo.LectureForAdminInfoVo;
import com.study.lecture.common.vo.LectureForAdminListVo;
import com.study.lecture.lecture.mapper.LectureMapper;
import com.study.lecture.common.service.lecture.LectureService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 讲座发布表 服务实现类
 * </p>
 *
 * @author zqc
 * @since 2022-04-13
 */
@DubboService(version = "1.0")
public class LectureServiceImpl extends ServiceImpl<LectureMapper, Lecture> implements LectureService {

    @Resource
    private LectureMapper lectureMapper;

    /**
     * 分页查询
     * @param page 当前页
     * @param limit 每页显示数量
     * @return 分页查询结果
     */
    @Override
    public R pageList(int page, int limit) {
        Page<Lecture> pageParam = new Page<>(page, limit);
        // 分页查询，查完后，会将数据封装在pageParam中
        this.page(pageParam, null);
        // 获取查询到的数据
        List<Lecture> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        return R.ok().put("total", total).put("records", records);
    }

    /**
     * admin分页查询lecture列表
     * @param page 当前页
     * @param limit 每页显示数量
     * @return 分页查询结果
     */
    @Override
    public R adminPageList(int page, int limit) {
        Page<LectureForAdminListVo> pageParam = new Page<>(page, limit);
        // 将查询结果封装到page中,作为page中的数据
        pageParam.setRecords(baseMapper.getLectureAdminList(pageParam));

        // 处理数据
        List<LectureForAdminListVo> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        // 根据时间设置状态
        for (int i = 0; i < records.size(); i++) {
            LectureForAdminListVo vo = records.get(i);
            Date curDate = new Date();
            if (curDate.before(vo.getOrderStartTime())) {
                vo.setState("未开放");
            } else if (curDate.after(vo.getOrderStartTime()) && curDate.before(vo.getOrderEndTime())) {
                vo.setState("预约中");
            } else {
                vo.setState("已结束");
            }
        }
        return R.ok().put("total", total).put("records", records);
    }

    /**
     * 添加 lecture
     * @param lecture lecture信息
     * @return 添加是否成功
     */
    @Override
    public int addLecture(Lecture lecture) {
        // TODO 事务、异常处理
        // 获取已登录用户，用于设置发布者
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // 创建lecture lectureDescription对象并赋值
        lecture.setStore(lecture.getReservation());
        lecture.setCreatorId(loginUser.getUser().getId());
        // 数据库插入记录
        return lectureMapper.insert(lecture);
    }

    /**
     * 通过id获得lecture详情
     * @param id lecture的id
     * @return 查询结果
     */
    @Override
    public Lecture getLectureById(Long id) {
        return lectureMapper.selectById(id);
    }

    /**
     * 更新lecture
     * @param lecture lecture信息
     * @return 更新是否成功
     */
    @Override
    public int updateLecture(Lecture lecture) {
        return lectureMapper.updateById(lecture);
    }

    /**
     * 根据id获取lecture详情, 显示详情页面
     * 与上面的方法相比，信息更全面，比如获取的是typeName而不是typeId
     * @param id lecture的id
     * @return 查询结果
     */
    @Override
    public LectureForAdminInfoVo getLectureInfoById(Long id) {
        return lectureMapper.getLectureInfoById(id);
    }
}
