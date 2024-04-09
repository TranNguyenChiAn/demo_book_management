package com.example.demo_book_management.controller;

import com.example.demo_book_management.IStorageService;
import com.example.demo_book_management.database.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

@Controller
@RequestMapping(path = "/api/v1/FileUpload")
public class FileUploadController {
    //This controller receive file/image from client
    @Autowired
    private IStorageService storageService;
    @PostMapping("")
    public ResponseEntity<ResponseObject> uploadFile(@RequestParam("file")MultipartFile file){
        try {
            //save file to a folder => use a service
            String generatedFilename = storageService.storeFile(file);
            return ResponseEntity.status(HttpStatus.OK).body(
              new ResponseObject("ok", " Upload file successfully", generatedFilename)
            );

        }catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", exception.getMessage(), "")
            );

        }
    }

    @GetMapping("/files/{fileName:.+}")

    //files/7a22630ead4a4b7e9489d382aabf8279.png
    public ResponseEntity<byte[]> readDetailFile(@PathVariable String fileName){
        try {
            byte[] bytes = storageService.readFileContent(fileName);
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(bytes);
        }
        catch (Exception exception){
            return ResponseEntity.noContent().build();
        }
    }
}
