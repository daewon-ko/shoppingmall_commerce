package shoppingmall.domainservice.domain.image.service;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import shoppingmall.aws.s3.S3Uploader;
import shoppingmall.common.exception.ApiException;
import shoppingmall.common.exception.infra.StorageErrorCode;
import shoppingmall.domainrdb.common.annotation.DomainRdbService;
import shoppingmall.domainrdb.image.ImageDomain;
import shoppingmall.domainrdb.image.entity.FileType;
import shoppingmall.domainrdb.image.service.ImageRdbService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 이미지 저장 서비스
 * 특정 도메인에 대한 이미지 저장이 아닌 이미지 저장 일반에 대한 기능을 수행
 */
@DomainRdbService
@RequiredArgsConstructor
public class ImageSaveService {
    private final S3Uploader s3Uploader;
    private final ImageRdbService imageRdbService;

    public List<Long> saveImage(final List<MultipartFile> multipartFileList, final Long targetId, FileType fileType) {

        try {
            // S3에 이미지 업로드
            List<String> uploadUrls = s3Uploader.upload(multipartFileList);

            // 이미지 저장
            return IntStream.range(0, uploadUrls.size())
                    .mapToObj(image -> {
                        String uploadName = uploadUrls.get(image);
                        ImageDomain imageDomain = new ImageDomain(uploadName, uploadName, targetId, fileType, false, null);
                        return imageRdbService.saveImage(imageDomain);

                    })
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new ApiException(StorageErrorCode.INTERNAL_SERVER_ERROR);
        }

    }
}
