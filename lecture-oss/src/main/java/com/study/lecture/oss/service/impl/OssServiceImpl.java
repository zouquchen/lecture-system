package com.study.lecture.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.study.lecture.oss.service.OssService;
import com.study.lecture.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

/**
 * <p>
 * 阿里云OSS存储服务器上传
 * </p>
 * <br>
 * Creation Time: 2022/4/15 12:15
 *
 * @author zqc
 * @since 1.0
 */
@Service
public class OssServiceImpl implements OssService {

    /**
     * 上传图片到阿里云os
     * @param file 文件
     * @return 文件图片url
     */
    @Override
    public String uploadImage(MultipartFile file) {
        // 工具类获取值
        String endpoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;
        OSS ossClient = null;


        try {
            // 创建OSSClient实例。
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 上传文件输入流
            InputStream inputStream = file.getInputStream();
            String filename = file.getOriginalFilename();

            // 在文件名称里面添加随机唯一的值
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            filename = uuid + filename;

            // 把文件按照日期进行分类
            // 获取当前日期
            String datePath = new DateTime().toString("yyyy/MM/dd");

            // 拼接
            filename = datePath + "/" + filename;

            // 调用oss方法实现上传
            // bucket名称，上传到oss文件路径和文件名称，上传文件输入流
            ossClient.putObject(bucketName, filename, inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();

            // 把上传之后文件路径返回
            return "https://" + bucketName + "." + endpoint + "/" + filename;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
