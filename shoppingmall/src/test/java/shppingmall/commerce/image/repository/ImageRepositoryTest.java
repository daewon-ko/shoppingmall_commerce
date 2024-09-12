package shppingmall.commerce.image.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import shppingmall.commerce.image.entity.FileType;
import shppingmall.commerce.image.entity.Image;
import shppingmall.commerce.product.entity.Product;
import shppingmall.commerce.product.repository.ProductRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static shppingmall.commerce.support.TestFixture.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ImageRepositoryTest {
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ProductRepository productRepository;
    @PersistenceContext
    private EntityManager em;

    @AfterEach
    void tearDown() {
        imageRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();

    }

    @DisplayName("상품에 썸네일 이미지와 디테일 이미지를 저장후 썸네일 이미지만 조회할 수 있다.")
    @Test
    void findImageWithProductId() {
        //given
        Product productA = createProduct(1000, "상품A");
        Product savedProduct = productRepository.save(productA);

        Image image1 = createImage("upload", savedProduct, FileType.PRODUCT_THUMBNAIL);
        Image image2 = createImage("upload", savedProduct, FileType.PRODUCT_DETAIL_IMAGE);


        List<Image> savedImages = imageRepository.saveAll(List.of(image1, image2));


        //when
        List<Image> images = imageRepository.findImagesByTargetIdAndFileType(List.of(FileType.PRODUCT_THUMBNAIL), savedProduct.getId());

        //then
        assertThat(images.get(0)).extracting(Image::getFileType, Image::getUploadName)
                .contains( FileType.PRODUCT_THUMBNAIL, "upload");

    }



    @DisplayName("상품에 저장되어 있는 다량의 이미지를 조회할 수 있다.")
    @Test
    void findImagesWithProductId() {
        //given
        Product productA = createProduct(1000, "상품A");
        Product savedProduct = productRepository.save(productA);

        Image image1 = createImage("upload1", savedProduct, FileType.PRODUCT_THUMBNAIL);

        Image image2 = createImage( "upload2", savedProduct, FileType.PRODUCT_DETAIL_IMAGE);


        Image savedImage1 = imageRepository.save(image1);
        Image savedImage2 = imageRepository.save(image2);


        //when


        List<Image> images= imageRepository.findImagesByTargetIdAndFileType(List.of(FileType.PRODUCT_THUMBNAIL, FileType.PRODUCT_DETAIL_IMAGE), savedProduct.getId());


        //then
        assertThat(images).extracting(Image::getFileType, Image::getId, Image::getUploadName)
                .contains(
                        tuple(FileType.PRODUCT_THUMBNAIL, savedImage1.getId(), "upload1"),
                        tuple(FileType.PRODUCT_DETAIL_IMAGE, savedImage2.getId(), "upload2")
                );
    }

    private static Product createProduct(int price, String name) {
        Product product = Product.builder()
                .price(price)
                .name(name)
                .build();
        return product;
    }




}