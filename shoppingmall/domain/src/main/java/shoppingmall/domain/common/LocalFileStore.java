package shoppingmall.domain.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * TODO: S3이전 시 해당 메서드 동적테스트로 작성 필요
 */
@Component
@Profile("local")
public class LocalFileStore {
    @Value("${file.dir}")
    private String fileDir;


    // MultipartFile을 지정된 fileDir에 저장하는 로직
    public List<String> uploadFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<String> imageNameList = new ArrayList<>();

        for (MultipartFile uploadFile : multipartFiles) {
            if (!uploadFile.isEmpty()) {
                imageNameList.add(storeFile(uploadFile));
            }
        }
        return imageNameList;
    }

    private String storeFile(MultipartFile multipartFile) throws IOException {
        //TODO : 예외처리 필요
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String uploadFileName = createUploadFileName(originalFilename);

        // 전체 저장경로
        String fullPath = getFullPath(uploadFileName);

        // 파일 저장
        multipartFile.transferTo(new File(fullPath));
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

    public void deleteFile(String uploadFileName) {
        String fullPath = getFullPath(uploadFileName);
        System.out.println("fullPath = " + fullPath);
        File file = new File(fullPath);
        file.delete();

//        if (!file.canWrite()) {
//            throw new IllegalStateException("No write permissions for file: " + fullPath);
//        }

        if (file.exists() || !file.delete()) {
            throw new IllegalStateException("파일 삭제에 실패했습니다." + uploadFileName);
        }

    }

    public byte[] getFileAsBytes(String uploadFileName) {

        Path filePath = Paths.get(getFullPath(uploadFileName));
        try {
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public String getMimeType(String uploadFileName) {
        Path filePath = Paths.get(getFullPath(uploadFileName));
        try {
            return Files.probeContentType(filePath);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }


}
