package shoppingmall.domainrdb.image.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shoppingmall.common.exception.ApiException;
import shoppingmall.common.exception.domain.ImageErrorCode;
import shoppingmall.domainrdb.common.annotation.DomainService;
import shoppingmall.domainrdb.domain.image.dto.response.ImageResponseDto;
import shoppingmall.domainrdb.domain.image.entity.FileType;
import shoppingmall.domainrdb.domain.image.entity.Image;
import shoppingmall.domainrdb.image.ImageDomain;
import shoppingmall.domainrdb.image.entity.FileType;
import shoppingmall.domainrdb.image.entity.Image;
import shoppingmall.domainrdb.image.repository.ImageRepository;
import shoppingmall.domainrdb.mapper.ImageEntityMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@DomainService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageRdbService {
    private final ImageRepository imageRepository;

    // TODO : 예외정의 후 처리 예정(ControllerAdvice 등) - 재학습 필요
    @Transactional
    public Long saveImage(final ImageDomain imageDomain) {
        // 파일 이름을 저장할 리스트
        // 이미지 저장

        Image savedImage = imageRepository.save(ImageEntityMapper.toEntity(imageDomain));
        return savedImage.getId();

    }

    public List<ImageResponseDto> getImages(Long targetId, List<FileType> fileTypes) {

        // 이미지 테이블에서 삭제되지 않은 Image를 조회 후 ImageResponseDto로 변환


        return imageRepository.findImagesBySearchCond(targetId, fileTypes).filter(images -> !images.isEmpty())
                .orElseThrow(() -> new ApiException(ImageErrorCode.NO_EXIST_IMAGE))
                .stream().map(

                        i -> ImageResponseDto.builder()
                                .targetId(targetId)
                                .imageUrl(i.getFullPathUrl())
                                .fileType(i.getFileType())
                                .build()
                ).collect(Collectors.toUnmodifiableList());


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
