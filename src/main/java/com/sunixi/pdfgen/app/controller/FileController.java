package com.sunixi.pdfgen.app.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.sunixi.pdfgen.app.entity.FileUpload;
import com.sunixi.pdfgen.app.service.FileUploadService;

@CrossOrigin
@RestController
public class FileController {

    @Autowired
    FileUploadService fileUploadService;

    // Download a file
    @RequestMapping(
        value = "/download",
        method = RequestMethod.GET
    )
    public ResponseEntity downloadFile(@RequestParam("filename") String filename) {

        FileUpload fileUpload = fileUploadService.findByFilename(filename);

        // No file found based on the supplied filename
        if (fileUpload == null) {
            return new ResponseEntity<>("{}", HttpStatus.NOT_FOUND);
        }

        // Generate the http headers with the file properties
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-disposition", "attachment; filename=" + fileUpload.getFilename());

        // Split the mimeType into primary and sub types
        String primaryType, subType;
        try {
            primaryType = fileUpload.getMimeType().split("/")[0];
            subType = fileUpload.getMimeType().split("/")[1];
        }
            catch (IndexOutOfBoundsException | NullPointerException ex) {
            return new ResponseEntity<>("{}", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        headers.setContentType( new MediaType(primaryType, subType) );

        return new ResponseEntity<>(fileUpload.getFile(), headers, HttpStatus.OK);
    }
    
    @RequestMapping(
            value = "/upload",
            method = RequestMethod.POST
        )
        public ResponseEntity uploadFile(MultipartHttpServletRequest request) {

            try {
                Iterator<String> itr = request.getFileNames();

                while (itr.hasNext()) {
                    String uploadedFile = itr.next();
                    MultipartFile file = request.getFile(uploadedFile);
                    String mimeType = file.getContentType();
                    String filename = file.getOriginalFilename();
                    byte[] bytes = file.getBytes();

                    FileUpload newFile = new FileUpload(filename, bytes, mimeType);
                    
                    Document document = new Document();
                    try
                    {
                        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filename+".pdf"));
                        document.open();
                        document.add(new Paragraph("Some content here"));
                     
                        //Set attributes here
                        document.addAuthor("Lokesh Gupta");
                        document.addCreationDate();
                        document.addCreator("HowToDoInJava.com");
                        document.addTitle("Set Attribute Example");
                        document.addSubject("An example to show how attributes can be added to pdf files.");
                        
                        
                      //Add Image
                        Image image1 = Image.getInstance(bytes);
                        //Fixed Positioning
                        image1.setAbsolutePosition(100f, 550f);
                        //Scale to new height and new width of image
                        image1.scaleAbsolute(200, 200);
                        //Add to document
                        document.add(image1);
                     
                        
                        document.close();
                        writer.close();
                        
                        
                        

                       
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    
                    fileUploadService.uploadFile(newFile);
                }
            }
            catch (Exception e) {
                return new ResponseEntity<>("{}", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            return new ResponseEntity<>("{}", HttpStatus.OK);
        }
        
}
