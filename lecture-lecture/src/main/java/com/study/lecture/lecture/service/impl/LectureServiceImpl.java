package com.study.lecture.lecture.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.lecture.common.entity.lecture.Lecture;
import com.study.lecture.lecture.mapper.LectureMapper;
import com.study.lecture.common.service.lecture.LectureService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 讲座发布表 服务实现类
 * </p>
 *
 * @author zqc
 * @since 2022-04-13
 */
@Service
public class LectureServiceImpl extends ServiceImpl<LectureMapper, Lecture> implements LectureService {

}
