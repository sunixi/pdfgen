package com.sunixi.pdfgen.app.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sunixi.pdfgen.app.entity.FileUpload;

@Repository
@Transactional
public interface FileUploadRepository extends CrudRepository<FileUpload, Long>, QueryByExampleExecutor<FileUpload> {
	@Query("Select c from FileUpload c where c.filename like ?1%")
	FileUpload findByFilename(String filename);
}
