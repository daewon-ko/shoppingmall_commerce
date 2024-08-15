package shppingmall.commerce.image.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import shppingmall.commerce.common.FileStore;
import shppingmall.commerce.image.entity.FileType;
import shppingmall.commerce.image.entity.Image;
import shppingmall.commerce.image.repository.ImageRepository;
import shppingmall.commerce.support.IntegrationTestSupport;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


class ImageServiceImplTest extends IntegrationTestSupport {

    //TODO : IntegrationTest를 상속받는 이유가 없어짐
    /**
     * FileStore에 물리적으로 저장되는 Data를 피하기 위해서 Mock처리를 했으나,
     * 타 Service Layer의 클래스들의 테스트들과 같이 일관적으로 Mock 처리 안 하는게 맞을까?
     *
     * FileStore를 Mock처리를 하게되면 ImageRepository도 Mock처리를 해야 NPE 회피 가능
     * -> 아마 스프링이 빈 주입을 못하는 듯
     *
     * Mock의 경우 일관되게 관리가 필요해 보임
     */
    @InjectMocks
    private ImageServiceImpl imageService;
    @Mock
    private ImageRepository imageRepository;

    @Mock
    private FileStore fileStore;

    @BeforeEach
    void setUp() throws IOException {
//        MockitoAnnotations.openMocks(this);

        // FileStore의 uploadFiles 메서드를 목(mock)으로 처리
        Mockito.when(fileStore.uploadFiles(Mockito.anyList()))
                .thenReturn(List.of("test.jpg"));

        // FileStore의 getFullPath 메서드를 목(mock)으로 처리
        Mockito.when(fileStore.getFullPath(Mockito.anyString()))
                .thenReturn("mocked/full/path/mocked-file-name.jpg");
    }

    @AfterEach
    void tearDown() {
        imageRepository.deleteAllInBatch();

    }

    @DisplayName("이미지에 관한 메타데이터를 저장한다.")
    @Test
    void saveImage() {
        //given
        MockMultipartFile mockMultipartFile = new MockMultipartFile("test", "test.jpg", "test.jpg", "test.jpg".getBytes());
        //when
        List<Image> images = imageService.saveImage(List.of(mockMultipartFile), 1L, FileType.PRODUCT_IMAGE);
        //then

        assertThat(images).hasSize(1)
                .extracting(Image::getFileType, Image::getTargetId)
                .contains(
                        tuple(FileType.PRODUCT_IMAGE, 1L)
                );

    }


}