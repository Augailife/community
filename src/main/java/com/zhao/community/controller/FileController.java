package com.zhao.community.controller;

import com.zhao.community.dto.FileDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FileController {
    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDTO upload(){
        FileDTO fileDTO = new FileDTO();
        fileDTO.setUrl("/images/hello.jpg");
        fileDTO.setSuccess(1);

        return fileDTO;
    }
}
