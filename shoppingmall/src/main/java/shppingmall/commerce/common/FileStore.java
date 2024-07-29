package shppingmall.commerce.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import shppingmall.commerce.image.entity.Image;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {
    @Value("${file.dir}")
    private String fileDir;


    // MultipartFile을 지정된 fileDir에 저장하는 로직
    public List<String> uploadFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<String> imageList = new ArrayList<>();

        for (MultipartFile uploadFile : multipartFiles) {
            if (!uploadFile.isEmpty()) {
                imageList.add(storeFile(uploadFile));
            }
        }
        return imageList;
    }

    private String storeFile(MultipartFile multipartFile) throws IOException {
        //TODO : 예외처리 필요
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String uploadFileName = createUploadFileName(originalFilename);

        multipartFile.transferTo(new File(getFullPath(uploadFileName)));
        return uploadFileName;
    }

    public String getFullPath(final String uploadFileName) {
        return fileDir + uploadFileName;
    }

    private String createUploadFileName(String originalFileName) {
        String ext = extractExt(originalFileName);
        return UUID.randomUUID().toString() + "." + ext;
    }

    private String extractExt(final String originalFileName) {
        int position = originalFileName.lastIndexOf(".");
        // 확장자 빼내기
        return originalFileName.substring(position + 1);
    }


}
