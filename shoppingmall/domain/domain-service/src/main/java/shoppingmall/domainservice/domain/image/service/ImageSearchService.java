package shoppingmall.domainservice.domain.image.service;

import lombok.RequiredArgsConstructor;
import shoppingmall.domainrdb.common.annotation.DomainRdbService;
import shoppingmall.domainrdb.image.ImageDomain;
import shoppingmall.domainrdb.image.entity.FileType;
import shoppingmall.domainrdb.image.service.ImageRdbService;

import java.util.List;
import java.util.stream.Collectors;

@DomainRdbService
@RequiredArgsConstructor
public class ImageSearchService {
    private final ImageRdbService imageRdbService;


    public List<ImageDomain> searchImage(final Long targetId, final List<FileType> fileTypes) {



        if ( fileTypes == null || fileTypes.isEmpty()) {
            return imageRdbService.getAllImageByTargetId(targetId);
        }
        return fileTypes.stream().map(fileType -> imageRdbService.getImage(targetId, fileType)).collect(Collectors.toList());

    }
}
