package com.sunixi.pdfgen.app.service;


import com.sunixi.pdfgen.app.entity.FileUpload;


public interface FileUploadService {
	
	 public FileUpload findByFilename(String filename);
	 public void uploadFile(FileUpload doc);

}
