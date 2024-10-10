package com.devkduck.duckshop.service;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobHttpHeaders;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
@Log
public class FileService {
    @Value("${azure.storage.connection-string}")
    private String connectionString;

    @Value("${azure.storage.container-name}")
    private String containerName;

    public String uploadFile(MultipartFile file) throws Exception {
        // BlobServiceClient 생성
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(connectionString).buildClient();

        // BlobContainerClient 생성
        BlobContainerClient blobContainerClient = blobServiceClient.getBlobContainerClient(containerName);

        // UUID로 파일명 생성
        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();

        // BlobClient 생성 및 파일 업로드
        BlobClient blobClient = blobContainerClient.getBlobClient(fileName);
        blobClient.upload(file.getInputStream(), file.getSize(), true);

        // Content-Type 설정
        BlobHttpHeaders headers = new BlobHttpHeaders().setContentType(file.getContentType());
        blobClient.setHttpHeaders(headers);

        // 파일의 Blob URL 반환
        return blobClient.getBlobUrl();
    }
//    public String uploadFile(String uploadPath, String originalFilename, byte[] fileData) throws Exception{
//        UUID uuid = UUID.randomUUID();
//        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
//        String savedFileName = uuid.toString() + extension;
//        String fileUploadFullUrl = uploadPath + "/" +savedFileName;
//        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
//        fos.write(fileData);
//        fos.close();
//        return savedFileName;
//    }

    public void deleteFile(String fileName) {
        // BlobServiceClient 생성
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(connectionString).buildClient();

        // BlobContainerClient 생성
        BlobContainerClient blobContainerClient = blobServiceClient.getBlobContainerClient(containerName);

        // BlobClient 생성 및 파일 삭제
        BlobClient blobClient = blobContainerClient.getBlobClient(fileName);
        blobClient.delete();
    }
//    public void deleteFile(String filePath) throws Exception{
//        File deleteFile = new File(filePath);
//
//        if (deleteFile.exists()) {
//            deleteFile.delete();
//            log.info("파일을 삭제하였습니다.");
//        }
//        else{
//            log.info("파일이 존재하지 않습니다.");
//        }
//    }

}
