package com.study.lecture.oss.controller;

import com.study.lecture.common.utils.R;
import com.study.lecture.oss.service.OssService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * <p>
 *
 * </p>
 * <br>
 * Creation Time: 2022/4/15 11:50
 *
 * @author zqc
 * @since 1.0
 */
@CrossOrigin
@RestController
@RequestMapping("/oss")
public class OssController {

    @Resource
    private OssService ossService;

    /**
     * 上传图片测试
     * @param file 图片文件
     * @return 图片url
     */
    @PostMapping("uploadImage")
    public R uploadImage(MultipartFile file) {
        String url = ossService.uploadImage(file);
        return R.ok("文件上传成功！").put("url", url);
    }
}
