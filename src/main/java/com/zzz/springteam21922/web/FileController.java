package com.zzz.springteam21922.web;

import com.zzz.springteam21922.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

/**
 * @description:
 * @version: 1.0
 * @author: zhaowei.zhang01@hand-china.com
 * @date: 2019/8/14
 */
@RestController
@RequestMapping("/api/file")
public class FileController {

    @Autowired
    private FileService fileService;

    private Principal principal;

    @GetMapping("/test")
    public String getString(){
        return "测试一下";
    }

    /**
     * 上传
     *
     * @param multipartFile
     * @return
     */
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile multipartFile) {
        return fileService.fileupload(multipartFile);
    }

    /**
     * 下载
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> downloadFile(String fileName)
            throws Exception {
        return fileService.filedownload(fileName);
    }
}
