package com.jie.bookshare.utils;

import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Locale;

/**
 * @author pzy
 * @version 3.1.0 大版本更新
 * 文件上传校验的公共方法
 * 严格校验
 */
public class UploadCheckUtils {

    /**
     * 文件大小 2MB(可用于图片和视频区分)
     */
    private static final long FILE_SIZE = 2 * 1024 * 1024;

    /**
     * 只支持图片格式
     */
    public static final String[] YES_IMAGE_SUPPORT = {"bmp", ".jpg", ".jpeg", ".png", ".gif"};

    /**
     * 只支持视频格式
     */
    public static final String[] YES_VIDEO_SUPPORT = {".mp4", ".avi", ".mp3"};

    /**
     * 只支持音频格式
     */
    public static final String[] YES_AUDIO_SUPPORT = {".mp3"};

    /**
     * 只支持文件格式
     */
    public static final String[] YES_FILE_SUPPORT = {".xlsx", ".xls", ".doc", ".docx", ".txt", ".csv"};


    /**
     * 全部文件(普通文件,图片, 视频,音频)后缀 支持的类型
     */
    private static final String[] FILE_SUFFIX_SUPPORT = {".xlsx", ".xls", ".doc", ".docx", ".txt", ".csv",
            ".jpg", ".jpeg", ".png", ".mp4", ".avi", ".mp3"};

    /**
     * 文件名字 需要排除的字符
     * 废弃:  "(", ")","",".", "——", "_","-"
     */
    private static final String[] FILE_NAME_EXCLUDE = {
            "`", "!", "@", "#", "$", "%", "^", "&", "*" , "=", "+",
            "~", "·", "！", "￥", "……", "（", "）",
            "?", ",", "<", ">", ":", ";", "[", "]", "{", "}", "/", "\\", "|",
            "？", "，", "。", "《", "》", "：", "；", "【", "】", "、"
    };


    /**
     * 多文件上传
     *
     * @param multipartFile
     */
    public static void uploadVerify(MultipartFile[] multipartFile) {

        /*校验1: 没有文件时,报错提示*/
        if (multipartFile == null) {
            throw new RuntimeException("文件不能为空！");
        }

        /*校验2: 上传文件的长度小于等于1 就一个直接校验*/
        if (multipartFile.length <= 1) {
            uploadVerify(multipartFile[0]);
            return;
        }
        /*校验3: 多个文件直接校验*/
        for (MultipartFile uploadFile : multipartFile) {
            uploadVerify(uploadFile);
        }

    }

    /**
     * 上传文件校验大小、名字、后缀
     *
     * @param multipartFile multipartFile
     */
    public static void uploadVerify(MultipartFile multipartFile) {
        // 校验文件是否为空
        if (multipartFile == null) {
            throw new RuntimeException("文件不能为空！");
        }

        // 校验文件大小(统一校验),配置文件中添加
        long size = multipartFile.getSize();
        if(size > FILE_SIZE){
            throw new RuntimeException("文件大小不能超过2MB！");
        }

        // 校验文件名字
        String originalFilename = multipartFile.getOriginalFilename();
        if (originalFilename == null) {
            throw new RuntimeException("文件名字不能为空！");
        }

        for (String realKey : FILE_NAME_EXCLUDE) {
            if (originalFilename.contains(realKey)) {
                throw new RuntimeException("文件名字不允许出现' " + realKey + " ' 关键字!");
            }
        }

        // 校验文件后缀
        if (!originalFilename.contains(".")) {
            throw new RuntimeException("文件不能没有后缀!");
        }

        String suffix = originalFilename.substring(originalFilename.lastIndexOf('.'));


        /*校验: 文件格式是否符合要求*/
        if (!Arrays.asList(YES_IMAGE_SUPPORT).contains(suffix.toLowerCase(Locale.ROOT))) {
            throw new RuntimeException("图片格式不支持,请更换后重试!");
        }

    }


}
