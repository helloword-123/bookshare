package com.jie.bookshare.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.jie.bookshare.service.IOssService;
import com.jie.bookshare.utils.OssPropertiesUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

/**
 * @ClassName OssService
 * @Description TODO
 * @Author wuhaojie
 * @Date 2021/8/8 21:01
 */

@Service
public class OssService implements IOssService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    //上传头像
    public String uploadFileAvatar(MultipartFile file) {
        // oss基本信息
        String endpoint = OssPropertiesUtils.END_POINT;
        String accessKeyId = OssPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = OssPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = OssPropertiesUtils.BUCKET_NAME;
        String projectName = OssPropertiesUtils.PROJECT_NAME;


        try {
            log.info("构造oss实例并开始上传...");

            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 获取上传文件输入流
            InputStream inputStream = file.getInputStream();
            // 依次填写Bucket名称和Object完整路径。Object完整路径中不能包含Bucket名称。
            // 1、获取文件名,并保证文件名唯一
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String filename = uuid + file.getOriginalFilename();
            // 2、把文件按日期分类
            String date = new DateTime().toString("yyyy/MM/dd");
            filename = projectName + "/" + date + "/" + filename;
            ossClient.putObject(bucketName, filename, inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();

            //返回上传文件后的url地址
            String url = "https://" + bucketName + "." + endpoint + "/" + filename;

            log.info("图片上传成功，生成图片url为: {}", url);
            return url;

        } catch (Exception e) {
            log.error("图片上传出错！");
            return null;
        }
    }
}
