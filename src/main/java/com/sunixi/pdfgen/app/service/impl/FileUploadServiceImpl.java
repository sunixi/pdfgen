package com.sunixi.pdfgen.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunixi.pdfgen.app.dao.FileUploadRepository;
import com.sunixi.pdfgen.app.entity.FileUpload;
import com.sunixi.pdfgen.app.service.FileUploadService;

@Service
public class FileUploadServiceImpl implements FileUploadService {

	 

         @Autowired
         FileUploadRepository fileUploadRepository;

         // Retrieve file
         public FileUpload findByFilename(String filename) {
             return fileUploadRepository.findByFilename(filename);
         }

         // Upload the file
         public void uploadFile(FileUpload doc) {
             fileUploadRepository.save(doc);
         }
    
}
