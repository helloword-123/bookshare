package com.jie.bookshare.service;

import org.springframework.web.multipart.MultipartFile;

public interface IOssService {
    //上传头像
    String uploadFileAvatar(MultipartFile file) ;
}
