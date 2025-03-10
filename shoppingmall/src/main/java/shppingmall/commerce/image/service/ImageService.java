package shppingmall.commerce.image.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shppingmall.commerce.common.FileStore;
import shppingmall.commerce.global.exception.ApiException;
import shppingmall.commerce.global.exception.domain.ImageErrorCode;
import shppingmall.commerce.image.dto.response.ImageResponseDto;
import shppingmall.commerce.image.entity.FileType;
import shppingmall.commerce.image.repository.ImageRepository;
import shppingmall.commerce.image.entity.Image;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageService {
    private final ImageRepository imageRepository;
    private final FileStore fileStore;

    // TODO : 예외정의 후 처리 예정(ControllerAdvice 등) - 재학습 필요
    @Transactional
    public List<Image> saveImage(List<MultipartFile> multipartFiles, Long targetId, FileType fileType) {
        // 파일 이름을 저장할 리스트
        List<String> fileNames;

        try {
            fileNames = fileStore.uploadFiles(multipartFiles);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }


        // 파일 이름과 파일 타입을 stream을 이용하여 매칭하고 저장
        return IntStream.range(0, fileNames.size())
                .mapToObj(i -> {
                    String uploadName = fileNames.get(i);
//                    FileType fileType = fileTypes.get(i);

                    String fullPathUrl = fileStore.getFullPath(uploadName);

                    // 이미지 객체 생성
                    Image image = Image.builder()
                            .uploadName(uploadName)
                            .targetId(targetId)
                            .fullPathUrl(fullPathUrl)
                            .isDeleted(false)
                            .fileType(fileType)
                            .build();

                    // 이미지 저장
                    imageRepository.save(image);

                    return image;
                })
                .collect(Collectors.toList());  // 저장된 이미지 리스트 반환


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
