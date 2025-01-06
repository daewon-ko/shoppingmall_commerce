package shoppingmall.domainrdb.image;

import lombok.Getter;
import shoppingmall.domainrdb.image.entity.FileType;

import java.time.LocalDateTime;

@Getter
public class ImageDomain {
    private String fullPathUrl;
    private String uploadName;
    private Long targetId;
    private FileType fileType;
    private Boolean isDeleted;
    private LocalDateTime deletedAt;

    public ImageDomain(String fullPathUrl, String uploadName, Long targetId, FileType fileType, Boolean isDeleted, LocalDateTime deletedAt) {
        this.fullPathUrl = fullPathUrl;
        this.uploadName = uploadName;
        this.targetId = targetId;
        this.fileType = fileType;
        this.isDeleted = isDeleted;
        this.deletedAt = deletedAt;
    }
}
