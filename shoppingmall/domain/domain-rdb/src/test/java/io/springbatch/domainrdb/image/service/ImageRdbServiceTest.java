package io.springbatch.domainrdb.image.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import shoppingmall.common.exception.ApiException;
import shoppingmall.domainrdb.domain.image.dto.response.ImageResponseDto;
import shoppingmall.domainrdb.domain.image.entity.FileType;
import shoppingmall.domainrdb.domain.image.entity.Image;
import shoppingmall.domainrdb.domain.image.repository.ImageRepository;
import shoppingmall.domainrdb.domain.product.entity.Product;
import shoppingmall.domainrdb.domain.product.repository.ProductRepository;
import shoppingmall.domainrdb.support.IntegrationTestSupport;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static shoppingmall.domainrdb.support.TestFixture.*;


class ImageRdbServiceTest extends IntegrationTestSupport {

    //TODO : IntegrationTest를 상속받는 이유가 없어짐
    /**
     * FileStore에 물리적으로 저장되는 Data를 피하기 위해서 Mock처리를 했으나,
     * 타 Service Layer의 클래스들의 테스트들과 같이 일관적으로 Mock 처리 안 하는게 맞을까?
     * <p>
     * FileStore를 Mock처리를 하게되면 ImageRepository도 Mock처리를 해야 NPE 회피 가능
     * -> 아마 스프링이 빈 주입을 못하는 듯
     * <p>
     * Mock의 경우 일관되게 관리가 필요해 보임
     */

    @Autowired
    private ImageService imageService;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ProductRepository productRepository;

    @MockBean
    private LocalFileStore localFileStore;

    @Value("${file.test-dir}")
    private String testUploadDir;

    @BeforeEach
    void setUp() throws IOException {
        // Mocking으로 처리할 메서드 정의
        Mockito.when(localFileStore.uploadFiles(Mockito.anyList()))
                .thenReturn(List.of("mocked-file-name.jpg")); // 파일 업로드 시 항상 이 이름을 반환

        Mockito.when(localFileStore.getFullPath(Mockito.anyString()))
                .thenReturn(testUploadDir + "/mocked-file-name.jpg"); // 파일 경로 Mock 처리

        Mockito.when(localFileStore.getFileAsBytes(Mockito.anyString()))
                .thenReturn("test".getBytes()); // 파일 읽기 시 반환할 내용

        Mockito.when(localFileStore.getMimeType(Mockito.anyString()))
                .thenReturn("image/jpeg"); // MIME 타입 반환
    }

    @AfterEach
    void tearDown() {
        imageRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
    }

    @DisplayName("이미지에 관한 메타데이터를 저장한다.")
    @Test
    void saveImage() {
        //given
        MockMultipartFile mockMultipartFile = new MockMultipartFile("test", "test.jpg", "test.jpg", "test.jpg".getBytes());
        //when
        List<Image> images = imageService.saveImage(List.of(mockMultipartFile), 1L, FileType.PRODUCT_THUMBNAIL);
        //then

        assertThat(images).hasSize(1)
                .extracting(Image::getFileType, Image::getTargetId)
                .contains(
                        tuple(FileType.PRODUCT_THUMBNAIL, 1L)
                );







    }

    @DisplayName("이미지를 삭제한다.")
    @Test
    void deleteImage() {
        //given
        MockMultipartFile mockMultipartFile = new MockMultipartFile("test", "test.jpg", "test.jepg", "test.jpg".getBytes());
        List<Image> images = imageService.saveImage(List.of(mockMultipartFile), 1L, FileType.PRODUCT_THUMBNAIL);
        //when
        for (Image image : images) {
            imageService.deleteImages(image.getTargetId(), List.of(FileType.PRODUCT_THUMBNAIL));
        }

        //then

        assertThat(imageRepository.findById(images.get(0).getId()).orElse(null).getIsDeleted()).isTrue();


    }

    @DisplayName("이미지를 저장 후, 적절히 조회 시, 파일 형식 및 이미지와 연관된 도메인의 Id를 조회할 수 있다.")
    @Test
    void getImage() {
        //given

        Product productA = createProduct(10000, "상품A");
        Product savedProduct = productRepository.save(productA);
        Image image = createImage("test-name", savedProduct, FileType.PRODUCT_THUMBNAIL);
        Image savedImage = imageRepository.save(image);


        //when
        List<ImageResponseDto> imageResponseDtos = imageService.getImages(savedProduct.getId(), List.of(FileType.PRODUCT_THUMBNAIL));

        //then
        assertThat(imageResponseDtos).hasSize(1);
        assertThat(imageResponseDtos.get(0).getFileType()).isEqualTo(FileType.PRODUCT_THUMBNAIL);
        assertThat(imageResponseDtos.get(0).getTargetId()).isEqualTo(savedProduct.getId());

    }

    @DisplayName("이미지를 저장 후,부적절한 내용으로 조회하게 되면 예외가 발생한다.")
    @Test
    void getImageWithWrongCond() {
        //given

        Product productA = createProduct(10000, "상품A");
        Product savedProduct = productRepository.save(productA);
        Image image = createImage("test-name", savedProduct, FileType.PRODUCT_THUMBNAIL);
        Image savedImage = imageRepository.save(image);


        //when, then

        assertThatThrownBy(
                () -> imageService.getImages(savedImage.getId(), List.of(FileType.CHATMESSAGE_IMAGE))
        ).isInstanceOf(ApiException.class)
                .hasMessage("해당하는 이미지가 없습니다");
    }


}