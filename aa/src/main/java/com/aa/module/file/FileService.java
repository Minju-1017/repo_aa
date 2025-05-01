package com.aa.module.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aa.module.animal.AnimalDto;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;

@Service
public class FileService {
	
	@Autowired
	private AmazonS3Client amazonS3Client;
	
	@Autowired
	private FileDao dao;
	
	// AWS Bucket
	@Value("${cloud_aws_bucket}")
	private String awsBucket;
	
	public FileDto selectOne(FileDto fileDto, String fDbTableName) {
		fileDto.setfDbTableName(fDbTableName);
		return dao.selectOne(fileDto);
	}
	
	/**
	 * DB Table 1개마다 rSeq당 1개의 파일만 업로드
	 * @param fileDto
	 * @param fDbTableNames
	 * @param rSeq
	 * @param insert
	 * @throws Exception
	 */
	public void uploadFilesToS3(FileDto fileDto, String[] fDbTableNames, String rSeq) throws Exception {
		// fileDto.getfUploadFiles()의 length는 fDbTableNames length와 일치함
		MultipartFile[] fUploadFiles = fileDto.getfUploadFiles(); 
		if (fUploadFiles == null || fUploadFiles.length == 0 || fUploadFiles.length != fDbTableNames.length) return;
		
		for (int i = 0; i < fUploadFiles.length; i++) {
			MultipartFile fUploadFile = fUploadFiles[i];
			String fDbTableName = fDbTableNames[i];
			
			if (!(fUploadFile == null || fUploadFile.isEmpty() || 
					fDbTableName == null || fDbTableName.equals(""))) {
				// AWS에 올릴 파일명 만들기
				String originalfileName = fUploadFile.getOriginalFilename();						// 실제 파일명
				String fExt = originalfileName.substring(originalfileName.lastIndexOf(".") + 1); 	// 확장자
				String fPath = fDbTableName + "/"; 		// Aws 경로(폴더명)
				String fFileName = rSeq + "." + fExt;	// 파일명(연관 관계에 있는 테이블의 seq - 고유값)
				
				// 메타 데이터
		        ObjectMetadata metadata = new ObjectMetadata();
		        metadata.setContentLength(fUploadFile.getSize());
		        metadata.setContentType(fUploadFile.getContentType());
		        
		        // AWS 파일 업로드
		        amazonS3Client.putObject(awsBucket, fPath + fFileName, fUploadFile.getInputStream(), metadata);
		    
		        String objectUrl = 
		        		amazonS3Client.getUrl(awsBucket, fPath + fFileName).toString(); // AWS 파일 Full Path
		        
		        // DB insert/update할 데이터 저장
		        fileDto.setfDbTableName(fDbTableName);
				fileDto.setfPath(objectUrl);
				fileDto.setfFileName(fFileName);
				fileDto.setfExt(fExt);
				fileDto.setfSize(fUploadFile.getSize());
				fileDto.setrSeq(rSeq);
				
				// DB 저장
				int count = dao.selectOneCount(fileDto);
				
				if (count == 0) {
					dao.insertFile(fileDto);
				} else {
					dao.updateFile(fileDto);
				}
			}
		}
	}
	
	public int ueleteFile(FileDto fileDto) {
		return dao.ueleteFile(fileDto);
	}
	
}
