package shoppingmall.aws.s3;

import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import shoppingmall.common.exception.ApiException;
import shoppingmall.common.exception.domain.S3UploaderErrorCode;
import software.amazon.awssdk.services.s3.S3Client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class S3Uploader {

    private final S3Template s3Template;
    private final S3Client s3Client;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;


    public List<String> upload(List<MultipartFile> files) throws IOException{

        List<String> uploadUrls = new ArrayList<>();

        for (MultipartFile file : files) {
            validFile(file);
            String fileName = validAndGetFileName(file.getOriginalFilename());
            String fileKey = getFileKey(fileName);
            try {
                ObjectMetadata objectMetadata = generateObjectMetadata(
                        extractExt(fileName), file.getSize());
                uploadUrls.add(s3Template.upload(bucket, fileKey, file.getInputStream(), objectMetadata).getURL().toString());
            } catch (IOException ex) {

            }
        }

        return uploadUrls;

    }


    private String validAndGetFileName(String originalFileName) {
        if (!StringUtils.hasText(originalFileName)) {
            throw new ApiException(S3UploaderErrorCode.INVALID_FILE_NAME);
        }
        return originalFileName;
    }

    private void validFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new ApiException(S3UploaderErrorCode.EMPTY_FILE);
        }
    }

    private ObjectMetadata generateObjectMetadata(String extension, Long length) {
        return new ObjectMetadata.Builder()
                .contentType(extension)
                .contentLength(length)
                .build();
    }

    private String getFileKey(String originalFileName) {
        String ext = extractExt(originalFileName);
        return UUID.randomUUID().toString() + "." + ext;
    }

    private String extractExt(final String originalFileName) {
        int position = originalFileName.lastIndexOf(".");
        // 확장자 빼내기
        return originalFileName.substring(position + 1);
    }

}
