package shoppingmall.domainservice.domain.image.service;

import lombok.RequiredArgsConstructor;
import shoppingmall.common.exception.ApiException;
import shoppingmall.common.exception.domain.ImageErrorCode;
import shoppingmall.domainrdb.common.annotation.DomainService;
import shoppingmall.domainrdb.image.ImageDomain;
import shoppingmall.domainrdb.image.entity.FileType;
import shoppingmall.domainrdb.image.service.ImageRdbService;

import java.util.List;
import java.util.stream.Collectors;

@DomainService
@RequiredArgsConstructor
public class ImageSearchService {
    private final ImageRdbService imageRdbService;


    public List<ImageDomain> searchImage(final Long targetId, final List<FileType> fileTypes) {

        return fileTypes.stream().map(
                fileType -> imageRdbService.getImage(targetId, fileType)
        ).collect(Collectors.toUnmodifiableList());

    }
}
