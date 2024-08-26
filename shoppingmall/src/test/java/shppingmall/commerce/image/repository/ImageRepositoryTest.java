package shppingmall.commerce.image.repository;

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
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ImageRepositoryTest {
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    void tearDown() {
        imageRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
    }

    @DisplayName("상품에 저장되어 있는 단일 이미지를 조회할 수 있다..")
    @Test
    void findImageWithProductId() {
        //given
        Product productA = createProduct(1000, "상품A");
        Product savedProduct = productRepository.save(productA);

        Image image = createImage(1L, "upload", savedProduct);


        imageRepository.save(image);


        //when
        List<Image> images = productRepository.findImagesByProductId(FileType.PRODUCT_IMAGE, savedProduct.getId());

        //then
        assertThat(images.get(0)).extracting(Image::getId, Image::getFileType, Image::getUploadName)
                .contains(1L, FileType.PRODUCT_IMAGE, "upload");

    }



    @DisplayName("상품에 저장되어 있는 다량의 이미지를 조회할 수 있다.")
    @Test
    void findImagesWithProductId() {
        //given
        Product productA = createProduct(1000, "상품A");
        Product savedProduct = productRepository.save(productA);

        Image image1 = createImage(1L, "upload1", savedProduct);

        Image image2 = createImage(2L, "upload2", savedProduct);


        imageRepository.save(image1);
        imageRepository.save(image2);


        //when
        List<Image> images = productRepository.findImagesByProductId(FileType.PRODUCT_IMAGE, savedProduct.getId());

        //then
        assertThat(images).extracting(Image::getFileType, Image::getId, Image::getUploadName)
                .contains(
                        tuple(FileType.PRODUCT_IMAGE, 1L, "upload1"),
                        tuple(FileType.PRODUCT_IMAGE, 2L, "upload2")
                );
    }

    private static Product createProduct(int price, String name) {
        Product product = Product.builder()
                .price(price)
                .name(name)
                .build();
        return product;
    }
    private static Image createImage(long id, String upload, Product savedProduct) {
        Image image = Image.builder()
                .id(id)
                .fileType(FileType.PRODUCT_IMAGE)
                .uploadName(upload)
                .targetId(savedProduct.getId())
                .build();
        return image;
    }



}