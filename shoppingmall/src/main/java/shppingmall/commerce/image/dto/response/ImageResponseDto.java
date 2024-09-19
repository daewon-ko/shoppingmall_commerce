package shppingmall.commerce.image.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import shppingmall.commerce.image.entity.FileType;

@Getter
public class ImageResponseDto {

    private String imageUrl;
    private FileType fileType;
    private Long targetId;

    @Builder
    public ImageResponseDto(String imageUrl, FileType fileType, Long targetId) {
        this.imageUrl = imageUrl;
        this.fileType = fileType;
        this.targetId = targetId;
    }
}
