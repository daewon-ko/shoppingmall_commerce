package shppingmall.commerce.image.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shppingmall.commerce.support.TestFixture;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ImageTest {

    @DisplayName("이미지 삭제 시간과 상태를 변경할 수 있다.")
    @Test
    void logicallyDeletedAtAndDeletedTime() {
        //given
        Image image = Image.builder()
                .targetId(1L)
                .build();


        //when
        LocalDateTime givenTime = LocalDateTime.of(2024, 9, 12, 16, 0);
        image.deleteImage(givenTime);

        //then
        assertThat(image.getIsDeleted()).isTrue();
        assertThat(image.getDeletedAt()).isEqualTo(givenTime);


    }


}