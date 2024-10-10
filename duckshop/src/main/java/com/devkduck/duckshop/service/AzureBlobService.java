package com.devkduck.duckshop.service;
import com.azure.storage.blob.*;
import com.azure.storage.blob.models.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class AzureBlobService {

    private final BlobContainerClient containerClient;

    public AzureBlobService(
            @Value("${azure.storage.blob-endpoint}") String blobEndpoint,
            @Value("${azure.storage.sas-token}") String sasToken,
            @Value("${azure.storage.container-name}") String containerName) {

        // BlobServiceClient 생성 - Blob의 엔드포인트와 SAS 토큰 사용
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                .endpoint(blobEndpoint)
                .sasToken(sasToken)
                .buildClient();

        // 컨테이너 클라이언트 가져오기
        this.containerClient = blobServiceClient.getBlobContainerClient(containerName);
    }

    /**
     * 파일을 Azure Blob Storage에 업로드하는 메서드
     * @param file 업로드할 파일
     * @return 업로드된 파일의 URL
     * @throws IOException 파일 업로드 중 오류 발생 시 예외 처리
     */
    public String uploadFile(MultipartFile file) throws IOException {
        // 파일의 고유 이름 생성 (UUID 사용)
        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();

        // Blob Client 가져오기
        BlobClient blobClient = containerClient.getBlobClient(fileName);

        // 파일 업로드
        blobClient.upload(file.getInputStream(), file.getSize(), true);

        // 업로드된 파일의 URL 반환
        return blobClient.getBlobUrl();
    }

    /**
     * Blob 파일 삭제 메서드 (옵션)
     * @param fileName 삭제할 파일 이름
     */
    public void deleteFile(String fileName) {
        BlobClient blobClient = containerClient.getBlobClient(fileName);
        blobClient.delete();
    }
}
