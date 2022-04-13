package com.study.lecture.lecture.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.lecture.common.entity.lecture.LectureDescription;
import com.study.lecture.lecture.mapper.LectureDescriptionMapper;
import com.study.lecture.common.service.lecture.LectureDescriptionService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 讲座描述表 服务实现类
 * </p>
 *
 * @author zqc
 * @since 2022-04-13
 */
@Service
public class LectureDescriptionServiceImpl extends ServiceImpl<LectureDescriptionMapper, LectureDescription> implements LectureDescriptionService {

}
