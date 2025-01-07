package shoppingmall.domainrdb.image.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.common.exception.ApiException;
import shoppingmall.common.exception.domain.ImageErrorCode;
import shoppingmall.domainrdb.common.annotation.DomainService;
import shoppingmall.domainrdb.image.ImageDomain;
import shoppingmall.domainrdb.image.entity.FileType;
import shoppingmall.domainrdb.image.entity.Image;
import shoppingmall.domainrdb.image.repository.ImageRepository;
import shoppingmall.domainrdb.mapper.ImageEntityMapper;

import java.time.LocalDateTime;
import java.util.List;


@DomainService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageRdbService {
    private final ImageRepository imageRepository;

    @Transactional
    public Long saveImage(final ImageDomain imageDomain) {
        // 파일 이름을 저장할 리스트
        // 이미지 저장

        Image savedImage = imageRepository.save(ImageEntityMapper.toEntity(imageDomain));
        return savedImage.getId();

    }

    /**
     * targetId와 FileType이 일치하는 Image를 찾아서 ImageDomain으로 변환 후 Return
     * @param targetId
     * @param fileType
     * @return
     */

    public ImageDomain getImage(Long targetId, FileType fileType) {

        Image image = imageRepository.findImagesBySearchCond(targetId, fileType)
                .orElseThrow(() -> new ApiException(ImageErrorCode.NO_EXIST_IMAGE));
        return ImageEntityMapper.toDomain(image);
    }


    @Transactional
    public void deleteImages(Long targetId, List<FileType> fileTypes) {
        // ImageRepository에서 FileType 및 targetId이 일치하는 이미지 조회
        List<Image> imageList = imageRepository.findImagesByTargetIdAndFileType(fileTypes, targetId);

        // 저장된 파일을 삭제하고 이미지도 삭제한다.
        for (Image image : imageList) {
            LocalDateTime now = LocalDateTime.now();
            image.deleteImage(now);
        }
    }
}
