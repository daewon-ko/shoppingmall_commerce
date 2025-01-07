package shoppingmall.domainservice.domain.image.service;

import lombok.RequiredArgsConstructor;
import shoppingmall.domainrdb.common.annotation.DomainService;
import shoppingmall.domainrdb.image.entity.FileType;
import shoppingmall.domainrdb.image.service.ImageRdbService;

import java.util.List;

@DomainService
@RequiredArgsConstructor
public class ImageDeleteService {
    private final ImageRdbService imageRdbService;


    public void deleteImage(final Long targetId, final List<FileType> fileTypes) {
        imageRdbService.deleteImages(targetId, fileTypes);
    }


}
