package shoppingmall.domainrdb.image.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shoppingmall.domainrdb.common.BaseEntity;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Image extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uploadName;
    private String fullPathUrl;

    @Enumerated(EnumType.STRING)
    private FileType fileType;

    private Long targetId;
    @Column(columnDefinition = "TINYINT(1)")
    private Boolean isDeleted;


    private LocalDateTime deletedAt;


    @Builder
    private Image(Long id, String uploadName, String fullPathUrl, FileType fileType, Long targetId, Boolean isDeleted, LocalDateTime deletedAt) {
        this.id = id;
        this.uploadName = uploadName;
        this.fullPathUrl = fullPathUrl;
        this.fileType = fileType;
        this.targetId = targetId;
        this.isDeleted = isDeleted;
        this.deletedAt = deletedAt;
    }

    public void deleteImage(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
        this.isDeleted = true;
    }
}
