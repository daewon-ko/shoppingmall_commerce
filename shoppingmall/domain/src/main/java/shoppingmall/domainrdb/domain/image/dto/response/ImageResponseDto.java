package shoppingmall.domainrdb.domain.image.dto.response;

import lombok.Builder;
import lombok.Getter;
import shoppingmall.domainrdb.domain.image.entity.FileType;

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
