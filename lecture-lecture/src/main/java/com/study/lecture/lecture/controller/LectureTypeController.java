package com.study.lecture.lecture.controller;


import com.study.lecture.common.entity.lecture.LectureType;
import com.study.lecture.common.service.lecture.LectureTypeService;
import com.study.lecture.common.utils.R;
import com.study.lecture.common.vo.LectureTypeForAllListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 讲座类型表 前端控制器
 * </p>
 *
 * @author zqc
 * @since 2022-04-13
 */
@RestController
@RequestMapping("/lecture/type")
@CrossOrigin
public class LectureTypeController {

    @Resource
    private LectureTypeService lectureTypeService;

    /**
     * 获得所有讲座类型
     * @return R响应类
     */
    @GetMapping("listAll")
    private R listAll() {
        List<LectureType> list = lectureTypeService.list();
        List<LectureTypeForAllListVo> listVo = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            LectureTypeForAllListVo vo = new LectureTypeForAllListVo();
            BeanUtils.copyProperties(list.get(i), vo);
            listVo.add(vo);
        }
        return R.ok().put("typeList", listVo);
    }
}
