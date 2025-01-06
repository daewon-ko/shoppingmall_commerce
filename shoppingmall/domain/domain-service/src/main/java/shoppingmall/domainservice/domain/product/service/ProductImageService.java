package shoppingmall.domainservice.domain.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import shoppingmall.domainrdb.common.annotation.DomainService;
import shoppingmall.domainrdb.image.entity.FileType;
import shoppingmall.domainservice.domain.image.service.ImageSaveService;

import java.util.ArrayList;
import java.util.List;

@DomainService
@RequiredArgsConstructor
public class ProductImageService {
    // ImageSaveRdbService 네이밍 변경 필요..
    // ImagePersistService 으로(?) -> DB쪽 가는 것으로 이해가능.
    // Image
    private final ImageSaveService imageSaveService;

    /**
     * 같은 Layer에서 호출 시에는 DTO로 왔다갔다 하게끔..하는게 좋다.
     * 도메인POJO로 하는것보단..
     *
     * 도메인 객체는 persistence와 domain-service간 왔다갔다 할때 사용하게끔 구성
     *
     * Usecase에서 domain-service를 할때는 dto를 왔다갔다가 한다.
     *
     */


    /**
     * 상품 이미지 저장과 관련된 도메인 로직이 포함되어있다.
     * 이미지를 단순하게 저장하는게 아니라, 여러 이미지가 포함되어있을떄, 상품등록시에는
     * 첫번째 이미지를 썸네일 이미지로 처리하고 이하 이미지를 디테일 이미지로 처리한다.
     * <p>
     * 따라서 단순히 ProductUsecase에서 ImageSaveService를 호출하는게 아닌, ProductImageService를 호출한다.
     *
     * @param images
     * @param productId
     * @return
     */
    public List<Long> saveProductImages(final List<MultipartFile> images, final Long productId) {

        // 이미지를 파일 타입에 맞춰 저장
        List<Long> imageIds = new ArrayList<>();

        // 첫 번째 이미지는 썸네일로 처리
        if (!images.isEmpty()) {
            MultipartFile thumbnailImage = images.get(0);
            List<Long> thumbNailImage = imageSaveService.saveImage(List.of(thumbnailImage), productId, FileType.PRODUCT_THUMBNAIL);
            imageIds.addAll(thumbNailImage);

            // 나머지 이미지는 상세 이미지로 처리
            List<MultipartFile> detailImageFiles = images.subList(1, images.size());
            if (!detailImageFiles.isEmpty()) {
                List<Long> detailImageIds = imageSaveService.saveImage(detailImageFiles, productId, FileType.PRODUCT_DETAIL_IMAGE);
                imageIds.addAll(detailImageIds);
            }
        }

        // 저장된 이미지 ID 리스트를 반환
        return imageIds;

    }
}
