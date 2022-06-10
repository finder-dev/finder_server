package com.cmc.finder.infra.file;

import lombok.Data;

@Data
public class UploadFile {

    private String originalFileName;  // 원본 파일 이름
    private String storeFileName;     // 저장된 파일 이름
    private String fileUploadUrl;     // 파일 저장 경로

    public UploadFile(String originalFileName, String storeFileName, String fileUploadUrl) {
        this.originalFileName = originalFileName;
        this.storeFileName = storeFileName;
        this.fileUploadUrl = fileUploadUrl;
    }

}
