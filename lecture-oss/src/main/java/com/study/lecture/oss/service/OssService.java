package com.study.lecture.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *
 * </p>
 * <br>
 * Creation Time: 2022/4/15 11:51
 *
 * @author zqc
 * @since 1.0
 */
public interface OssService {

    /**
     * 上传图片到阿里云os
     * @param file 文件
     * @return 文件图片url
     */
    String uploadImage(MultipartFile file);
}
