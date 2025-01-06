package shoppingmall.domainservice.domain.image.service;

import lombok.RequiredArgsConstructor;
import shoppingmall.domainrdb.common.annotation.DomainService;
import shoppingmall.domainrdb.image.service.ImageRdbService;

@DomainService
@RequiredArgsConstructor
public class ImageSearchService {
    private final ImageRdbService imageRdbService;
}
