package shoppingmall.domainrdb.mapper;

import shoppingmall.domainrdb.image.ImageDomain;
import shoppingmall.domainrdb.image.entity.Image;

public class ImageEntityMapper {
    public static Image toEntity(final ImageDomain imageDomain) {
        return Image.builder()
                .targetId(imageDomain.getTargetId())
                .fileType(imageDomain.getFileType())
                .fullPathUrl(imageDomain.getFullPathUrl())
                .uploadName(imageDomain.getUploadName())
                .deletedAt(imageDomain.getDeletedAt())
                .isDeleted(imageDomain.getIsDeleted())
                .build();

    }
}
