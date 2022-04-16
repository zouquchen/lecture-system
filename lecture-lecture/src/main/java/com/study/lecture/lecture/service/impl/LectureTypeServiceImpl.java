package com.study.lecture.lecture.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.lecture.common.entity.lecture.LectureType;
import com.study.lecture.common.utils.R;
import com.study.lecture.lecture.mapper.LectureTypeMapper;
import com.study.lecture.common.service.lecture.LectureTypeService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 讲座类型表 服务实现类
 * </p>
 *
 * @author zqc
 * @since 2022-04-13
 */
@DubboService
public class LectureTypeServiceImpl extends ServiceImpl<LectureTypeMapper, LectureType> implements LectureTypeService {

}
