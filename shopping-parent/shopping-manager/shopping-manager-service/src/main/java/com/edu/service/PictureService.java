package com.edu.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface PictureService {
    Map<String, Object> uploadImg(MultipartFile uploadFile) throws Exception;
}
