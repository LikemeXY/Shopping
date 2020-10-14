package com.edu.controller;

import com.edu.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Controller
@RequestMapping("/pic")
public class PictureController {
    @Autowired
    private PictureService pictureService;

    @ResponseBody
    @RequestMapping("/upload")
    public Map<String,Object> upload(MultipartFile uploadFile) throws Exception{
        return pictureService.uploadImg(uploadFile);
    }
}
