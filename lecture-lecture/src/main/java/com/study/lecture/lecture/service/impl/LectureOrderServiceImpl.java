package com.study.lecture.lecture.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.lecture.common.entity.lecture.LectureOrder;
import com.study.lecture.lecture.mapper.LectureOrderMapper;
import com.study.lecture.common.service.lecture.LectureOrderService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 讲座预约表 服务实现类
 * </p>
 *
 * @author zqc
 * @since 2022-04-13
 */
@Service
public class LectureOrderServiceImpl extends ServiceImpl<LectureOrderMapper, LectureOrder> implements LectureOrderService {

}
