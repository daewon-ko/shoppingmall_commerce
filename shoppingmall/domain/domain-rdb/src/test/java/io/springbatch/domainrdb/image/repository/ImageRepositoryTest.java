package io.springbatch.domainrdb.image.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import shoppingmall.domainrdb.domain.chat.repository.ChatRoomRepository;
import shoppingmall.domainrdb.domain.image.entity.FileType;
import shoppingmall.domainrdb.domain.image.entity.Image;
import shoppingmall.domainrdb.domain.message.repository.MessageRepository;
import shoppingmall.domainrdb.domain.product.entity.Product;
import shoppingmall.domainrdb.domain.product.repository.ProductRepository;
import shoppingmall.domainrdb.support.RepositoryTestSupport;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static shoppingmall.domainrdb.support.TestFixture.*;

class ImageRepositoryTest extends RepositoryTestSupport {
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ProductRepository productRepository;
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private MessageRepository messageRepository;

    @AfterEach
    void tearDown() {
        imageRepository.deleteAllInBatch();
        messageRepository.deleteAllInBatch();
        chatRoomRepository.deleteAllInBatch();
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
                .contains(FileType.PRODUCT_THUMBNAIL, "upload");

    }


    @DisplayName("상품에 저장되어 있는 다량의 이미지를 조회할 수 있다.")
    @Test
    void findImagesWithProductId() {
        //given
        Product productA = createProduct(1000, "상품A");
        Product savedProduct = productRepository.save(productA);

        Image image1 = createImage("upload1", savedProduct, FileType.PRODUCT_THUMBNAIL);

        Image image2 = createImage("upload2", savedProduct, FileType.PRODUCT_DETAIL_IMAGE);


        Image savedImage1 = imageRepository.save(image1);
        Image savedImage2 = imageRepository.save(image2);


        //when


        List<Image> images = imageRepository.findImagesByTargetIdAndFileType(List.of(FileType.PRODUCT_THUMBNAIL, FileType.PRODUCT_DETAIL_IMAGE), savedProduct.getId());


        //then
        assertThat(images).extracting(Image::getFileType, Image::getId, Image::getUploadName)
                .contains(
                        tuple(FileType.PRODUCT_THUMBNAIL, savedImage1.getId(), "upload1"),
                        tuple(FileType.PRODUCT_DETAIL_IMAGE, savedImage2.getId(), "upload2")
                );
    }

    @DisplayName("이미지를 삭제할 경우, isDeleted가 false인 이미지를 조회할 수 없다.")
    @Test
    void findByTargetIdAndIsDeletedIsFalse() {
        //given
        Product productA = createProduct(1000, "상품A");
        Product savedProduct = productRepository.save(productA);

        Image image = createImage("test", savedProduct, FileType.PRODUCT_THUMBNAIL);
        Image savedImage = imageRepository.save(image);
        savedImage.deleteImage(LocalDateTime.of(2024, 9, 19, 13, 23));

        //when
        Optional<List<Image>> images = imageRepository.findImagesBySearchCond(savedProduct.getId(), List.of(FileType.PRODUCT_THUMBNAIL));
        //then

        assertThat(images.orElseGet(null)).isNullOrEmpty();


    }

    @DisplayName("이미지를 삭제하지 않았을경우, IsDeleted가 Falsed인 이미지를 조회할 수 있다. ")
    @Test
    void findImageByTargetIdAndIsNotDeleted() {
        //given
        Product productA = createProduct(1000, "상품A");
        Product savedProduct = productRepository.save(productA);

        Image image = createImage("test", savedProduct, FileType.PRODUCT_THUMBNAIL);
        Image savedImage = imageRepository.save(image);

        //when
        Optional<List<Image>> images = imageRepository.findImagesBySearchCond(savedProduct.getId(), List.of(FileType.PRODUCT_THUMBNAIL));
        //then
        assertThat(images.orElse(null).get(0)).extracting(Image::getFileType).isEqualTo(FileType.PRODUCT_THUMBNAIL);

    }


    @DisplayName("상품과 이미지를 저장 후, 올바른 상품번호와 이미지 타입으로 조회가능하다.")
    @Test
    void findWithInTargetIdAndFileType() {

        //given
        Product productA = createProduct(1000, "상품A");
        Product savedProduct = productRepository.save(productA);

        Image image = createImage("test", savedProduct, FileType.PRODUCT_THUMBNAIL);
        Image savedImage = imageRepository.save(image);

        //when

        List<Image> result = imageRepository.findImagesByTargetIdAndFileType(List.of(FileType.PRODUCT_THUMBNAIL), savedProduct.getId());

        //then
        assertThat(result).extracting(Image::getFileType, Image::getId, Image::getUploadName)
                .containsExactly(tuple(FileType.PRODUCT_THUMBNAIL, savedImage.getId(), savedImage.getUploadName()));

    }

    @DisplayName("상품과 이미지를 저장 후, 올바르지 못한 파일 타입으로 조회시 조회 불가능하다.")
    @Test
    void findWithInTargetIdAndWrongFileType() {

        //given
        Product productA = createProduct(1000, "상품A");
        Product savedProduct = productRepository.save(productA);

        Image image = createImage("test", savedProduct, FileType.PRODUCT_THUMBNAIL);
        Image savedImage = imageRepository.save(image);

        //when
        List<Image> imageList = imageRepository.findImagesByTargetIdAndFileType(List.of(FileType.CHATMESSAGE_IMAGE), savedProduct.getId());

        //then
        assertThat(imageList).isNullOrEmpty();

    }



    private static Product createProduct(int price, String name) {
        Product product = Product.builder()
                .price(price)
                .name(name)
                .build();
        return product;
    }


}