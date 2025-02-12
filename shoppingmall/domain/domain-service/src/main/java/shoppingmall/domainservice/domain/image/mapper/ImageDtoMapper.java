package shoppingmall.domainservice.domain.image.mapper;

import shoppingmall.domainrdb.image.ImageDomain;
import shoppingmall.domainservice.domain.image.dto.response.ImageResponseDto;

import java.util.List;
import java.util.stream.Collectors;

public class ImageDtoMapper {

    public static List<ImageResponseDto> toImageResponseDto(final List<ImageDomain> imageDomains) {
        return imageDomains.stream()
                .map(imageDomain -> {
                    return ImageResponseDto.builder()
                            .imageUrl(imageDomain.getFullPathUrl())
                            .fileType(imageDomain.getFileType())
                            .targetId(imageDomain.getTargetId())
                            .build();
                }).collect(Collectors.toUnmodifiableList());

    }
}
